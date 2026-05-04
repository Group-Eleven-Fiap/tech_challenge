package br.com.fiap.restaurant_management.service;

import br.com.fiap.restaurant_management.entity.Address;
import br.com.fiap.restaurant_management.entity.Customer;
import br.com.fiap.restaurant_management.entity.dtos.ChangePasswordRequest;
import br.com.fiap.restaurant_management.entity.dtos.CustomerRequest;
import br.com.fiap.restaurant_management.entity.dtos.CustomerResponse;
import br.com.fiap.restaurant_management.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.exception.InvalidCredentialsException;
import br.com.fiap.restaurant_management.exception.ResourceNotFoundException;
import br.com.fiap.restaurant_management.factory.AddressFactory;
import br.com.fiap.restaurant_management.factory.CustomerFactory;
import br.com.fiap.restaurant_management.mapper.AddressMapper;
import br.com.fiap.restaurant_management.mapper.CustomerMapper;
import br.com.fiap.restaurant_management.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private EmailValidationService emailValidationService;

    @Captor
    private ArgumentCaptor<Customer> customerCaptor;

    // create

    @Test
    void shouldCreateCustomerSuccessfully() {

        Address addressModel = AddressFactory.defaultModel();
        CustomerRequest customerRequest = CustomerFactory.defaultRequest();
        CustomerResponse customerResponse = CustomerFactory.defaultResponse();

        doNothing().when(emailValidationService).validateEmailUniqueness(any());

        when(passwordEncoder.encode(any())).thenReturn("super-criptografado");
        when(addressMapper.toModel(any())).thenReturn(addressModel);

        when(customerMapper.toCustomerResponse(any())).thenReturn(customerResponse);

        when(customerRepository.save(any())).thenAnswer(invocation -> {
            Customer c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });

        CustomerResponse result = customerService.create(customerRequest);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("lastModified")
                .isEqualTo(customerResponse);

        verify(customerRepository).save(customerCaptor.capture());
        verify(passwordEncoder).encode(customerRequest.password());
        verify(customerMapper).toCustomerResponse(any());

        Customer captured = customerCaptor.getValue();

        assertThat(captured.getEmail()).isEqualTo(customerRequest.email());
        assertThat(captured.getName()).isEqualTo(customerRequest.name());

    }

    @Test
    void shouldThrowWhenCreateWithDuplicateEmail() {

        CustomerRequest customerRequest = CustomerFactory.defaultRequest();

        doThrow(new BusinessRuleException(""))
                .when(emailValidationService)
                .validateEmailUniqueness(customerRequest.email());

        assertThrows(BusinessRuleException.class, () -> customerService.create(customerRequest));

        verify(customerRepository, never()).save(any());
    }

    @Test
    void shouldEncodePasswordBeforeCreating() {

        CustomerRequest customerRequest = CustomerFactory.defaultRequest();

        when(passwordEncoder.encode(any())).thenReturn("super-criptografado");

        when(customerRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        customerService.create(customerRequest);

        verify(customerRepository).save(customerCaptor.capture());
        verify(customerMapper).toCustomerResponse(any());

        Customer captured = customerCaptor.getValue();

        assertThat(captured.getPassword()).isEqualTo("super-criptografado");
    }

    // delete

    @Test
    void shouldDeleteCustomerSuccessfully() {
        Long validId = 1L;
        Customer customer = CustomerFactory.defaultModel();

        when(customerRepository.findById(validId)).thenReturn(Optional.of(customer));

        customerService.delete(validId);

        verify(customerRepository).findById(validId);
        verify(customerRepository).delete(customer);
    }

    @Test
    void shouldThrowWhenDeleteNonExistingCustomer() {

        Long invalidId = 1L;

        when(customerRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.delete(invalidId));

        verify(customerRepository).findById(invalidId);
        verify(customerRepository, never()).delete(any());
    }

    // update

    @Test
    void shouldUpdateCustomerSuccessfully() {

        Long validId = 1L;

        Customer existingCustomer = CustomerFactory.withName("Nome Antigo");

        String originalName = existingCustomer.getName();
        String originalEmail = existingCustomer.getEmail();
        String originalPassword = existingCustomer.getPassword();
        String originalLogin = existingCustomer.getLogin();

        Address updatedAddress = AddressFactory.defaultModel();

        CustomerRequest request = CustomerFactory.defaultRequest();
        CustomerResponse expectedResponse = CustomerFactory.defaultResponse();

        when(customerRepository.findById(validId)).thenReturn(Optional.of(existingCustomer));
        when(addressMapper.toModel(request.address())).thenReturn(updatedAddress);
        when(customerRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(customerMapper.toCustomerResponse(any())).thenReturn(expectedResponse);

        CustomerResponse result = customerService.update(validId, request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("lastModified")
                .isEqualTo(expectedResponse);

        verify(customerRepository).save(customerCaptor.capture());

        Customer saved = customerCaptor.getValue();

        assertThat(saved.getName())
                .isEqualTo(request.name())
                .isNotEqualTo(originalName);

        assertThat(saved.getEmail()).isEqualTo(originalEmail);
        assertThat(saved.getPassword()).isEqualTo(originalPassword);
        assertThat(saved.getLogin()).isEqualTo(originalLogin);
        assertThat(saved.getAddress()).isEqualTo(updatedAddress);

        verify(customerRepository).findById(validId);
        verify(addressMapper).toModel(request.address());
        verify(customerMapper).toCustomerResponse(any());
    }

    @Test
    void shouldThrowWhenUpdateNonExistingCustomer() {

        Long invalidId = 1L;

        when(customerRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.update(invalidId, CustomerFactory.defaultRequest()));

        verify(customerRepository).findById(invalidId);
        verify(customerRepository, never()).save(any());
    }

    // findByName

    @Test
    void shouldReturnCustomerListWhenNameExists() {

        String name = "joao";

        Customer customer1 = CustomerFactory.withName("Teste João 1");
        Customer customer2 = CustomerFactory.withName("Teste João 2");

        CustomerResponse response1 = CustomerFactory.defaultResponse();
        CustomerResponse response2 = CustomerFactory.defaultResponse();

        when(customerRepository.findByNameContainingIgnoreCase(name))
                .thenReturn(java.util.List.of(customer1, customer2));
        when(customerMapper.toCustomerResponse(customer1))
                .thenReturn(response1);
        when(customerMapper.toCustomerResponse(customer2))
                .thenReturn(response2);

        var result = customerService.findByName(name);

        assertThat(result).containsExactly(response1, response2);

        verify(customerRepository).findByNameContainingIgnoreCase(name);
        verify(customerMapper).toCustomerResponse(customer1);
        verify(customerMapper).toCustomerResponse(customer2);
        verify(customerMapper, times(2)).toCustomerResponse(any());
    }

    @Test
    void shouldThrowWhenCustomerNameNotFound() {

        String name = "issononecziste";

        when(customerRepository.findByNameContainingIgnoreCase(name))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> customerService.findByName(name));

        verify(customerRepository).findByNameContainingIgnoreCase(name);

    }

    // changePassword

    @Test
    void shouldChangePasswordSuccessfully() {

        Long validId = 1L;

        String oldPassword = "123senhaantiga";
        String newPassword = "senhanova456";
        String newEncodedPassword = "super-criptografado";

        Customer customer = CustomerFactory.withPassword(oldPassword);
        Customer expectedCustomer = CustomerFactory.withPassword(newPassword);

        ChangePasswordRequest request = new ChangePasswordRequest(oldPassword, newPassword);

        when(customerRepository.findById(validId)).thenReturn(Optional.of(customer));
        when(passwordEncoder.matches(oldPassword, customer.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(newEncodedPassword);
        when(customerRepository.save(customer)).thenAnswer(invocation -> invocation.getArgument(0));

        customerService.changePassword(validId, request);

        verify(customerRepository).save(customerCaptor.capture());

        Customer saved = customerCaptor.getValue();

        assertThat(saved.getPassword()).isEqualTo(newEncodedPassword);

        verify(customerRepository).findById(validId);
        verify(customerRepository).save(customer);
    }

    @Test
    void shouldThrowWhenOldPasswordIncorrect() {

        String oldPassword = "123senhaantiga";
        String newPassword = "senhanova456";

        Long validId = 1L;

        Customer customer = CustomerFactory.defaultModel();

        ChangePasswordRequest request = new ChangePasswordRequest(oldPassword, newPassword);

        when(customerRepository.findById(validId)).thenReturn(Optional.of(customer));
        when(passwordEncoder.matches(eq(oldPassword), any())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> customerService.changePassword(validId, request));

        verify(customerRepository).findById(validId);
        verify(passwordEncoder).matches(eq(oldPassword), any());
        verify(customerRepository, never()).save(any());

    }

    @Test
    void shouldThrowWhenChangePasswordNonExistingCustomer() {

        Long invalidId = 1L;

        ChangePasswordRequest request = new ChangePasswordRequest("123senhaantiga", "senhanova456");

        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.changePassword(invalidId, request));

        verify(customerRepository).findById(invalidId);
        verify(customerRepository, never()).save(any());


    }
    

}
