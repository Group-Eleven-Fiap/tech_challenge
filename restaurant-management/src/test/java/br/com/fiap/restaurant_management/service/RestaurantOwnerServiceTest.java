package br.com.fiap.restaurant_management.service;

import br.com.fiap.restaurant_management.entity.Address;
import br.com.fiap.restaurant_management.entity.RestaurantOwner;
import br.com.fiap.restaurant_management.entity.dtos.ChangePasswordRequest;
import br.com.fiap.restaurant_management.entity.dtos.RestaurantOwnerRequest;
import br.com.fiap.restaurant_management.entity.dtos.RestaurantOwnerResponse;
import br.com.fiap.restaurant_management.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.exception.InvalidCredentialsException;
import br.com.fiap.restaurant_management.exception.ResourceNotFoundException;
import br.com.fiap.restaurant_management.factory.AddressFactory;
import br.com.fiap.restaurant_management.factory.RestaurantOwnerFactory;
import br.com.fiap.restaurant_management.mapper.AddressMapper;
import br.com.fiap.restaurant_management.mapper.RestaurantOwnerMapper;
import br.com.fiap.restaurant_management.repository.RestaurantOwnerRepository;
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
class RestaurantOwnerServiceTest {

    @InjectMocks
    private RestaurantOwnerService restaurantOwnerService;

    @Mock
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private RestaurantOwnerMapper restaurantOwnerMapper;

    @Mock
    private EmailValidationService emailValidationService;

    @Captor
    private ArgumentCaptor<RestaurantOwner> restaurantOwnerCaptor;

    // create

    @Test
    void shouldCreateRestaurantOwnerSuccessfully() {

        Address addressModel = AddressFactory.defaultModel();
        RestaurantOwnerRequest restaurantOwnerRequest = RestaurantOwnerFactory.defaultRequest();
        RestaurantOwnerResponse restaurantOwnerResponse = RestaurantOwnerFactory.defaultResponse();

        doNothing().when(emailValidationService).validateEmailUniqueness(any());

        when(passwordEncoder.encode(any())).thenReturn("super-criptografado");
        when(addressMapper.toModel(any())).thenReturn(addressModel);

        when(restaurantOwnerMapper.toRestaurantOwnerResponse(any())).thenReturn(restaurantOwnerResponse);

        when(restaurantOwnerRepository.save(any())).thenAnswer(invocation -> {
            RestaurantOwner c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });

        RestaurantOwnerResponse result = restaurantOwnerService.create(restaurantOwnerRequest);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("lastModified")
                .isEqualTo(restaurantOwnerResponse);

        verify(restaurantOwnerRepository).save(restaurantOwnerCaptor.capture());
        verify(passwordEncoder).encode(restaurantOwnerRequest.password());
        verify(restaurantOwnerMapper).toRestaurantOwnerResponse(any());

        RestaurantOwner captured = restaurantOwnerCaptor.getValue();

        assertThat(captured.getEmail()).isEqualTo(restaurantOwnerRequest.email());
        assertThat(captured.getName()).isEqualTo(restaurantOwnerRequest.name());

    }

    @Test
    void shouldThrowWhenCreateWithDuplicateEmail() {

        RestaurantOwnerRequest restaurantOwnerRequest = RestaurantOwnerFactory.defaultRequest();

        doThrow(new BusinessRuleException(""))
                .when(emailValidationService)
                .validateEmailUniqueness(restaurantOwnerRequest.email());

        assertThrows(BusinessRuleException.class, () -> restaurantOwnerService.create(restaurantOwnerRequest));

        verify(restaurantOwnerRepository, never()).save(any());
    }

    @Test
    void shouldEncodePasswordBeforeCreating() {

        RestaurantOwnerRequest restaurantOwnerRequest = RestaurantOwnerFactory.defaultRequest();

        when(passwordEncoder.encode(any())).thenReturn("super-criptografado");

        when(restaurantOwnerRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        restaurantOwnerService.create(restaurantOwnerRequest);

        verify(restaurantOwnerRepository).save(restaurantOwnerCaptor.capture());
        verify(restaurantOwnerMapper).toRestaurantOwnerResponse(any());

        RestaurantOwner captured = restaurantOwnerCaptor.getValue();

        assertThat(captured.getPassword()).isEqualTo("super-criptografado");
    }

    // delete

    @Test
    void shouldDeleteRestaurantOwnerSuccessfully() {
        Long validId = 1L;
        RestaurantOwner restaurantOwner = RestaurantOwnerFactory.defaultModel();

        when(restaurantOwnerRepository.findById(validId)).thenReturn(Optional.of(restaurantOwner));

        restaurantOwnerService.delete(validId);

        verify(restaurantOwnerRepository).findById(validId);
        verify(restaurantOwnerRepository).delete(restaurantOwner);
    }

    @Test
    void shouldThrowWhenDeleteNonExistingRestaurantOwner() {

        Long invalidId = 1L;

        when(restaurantOwnerRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> restaurantOwnerService.delete(invalidId));

        verify(restaurantOwnerRepository).findById(invalidId);
        verify(restaurantOwnerRepository, never()).delete(any());
    }

    // update

    @Test
    void shouldUpdateRestaurantOwnerSuccessfully() {

        Long validId = 1L;

        RestaurantOwner existingRestaurantOwner = RestaurantOwnerFactory.withName("Nome Antigo");

        String originalName = existingRestaurantOwner.getName();
        String originalEmail = existingRestaurantOwner.getEmail();
        String originalPassword = existingRestaurantOwner.getPassword();
        String originalLogin = existingRestaurantOwner.getLogin();
        String originalRestarantName = existingRestaurantOwner.getRestaurantName();
        String originalCnpj = existingRestaurantOwner.getCnpj();

        Address updatedAddress = AddressFactory.defaultModel();

        RestaurantOwnerRequest request = RestaurantOwnerFactory.defaultRequest();
        RestaurantOwnerResponse expectedResponse = RestaurantOwnerFactory.defaultResponse();

        when(restaurantOwnerRepository.findById(validId)).thenReturn(Optional.of(existingRestaurantOwner));
        when(addressMapper.toModel(request.address())).thenReturn(updatedAddress);
        when(restaurantOwnerRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(restaurantOwnerMapper.toRestaurantOwnerResponse(any())).thenReturn(expectedResponse);

        RestaurantOwnerResponse result = restaurantOwnerService.update(validId, request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("lastModified")
                .isEqualTo(expectedResponse);

        verify(restaurantOwnerRepository).save(restaurantOwnerCaptor.capture());

        RestaurantOwner saved = restaurantOwnerCaptor.getValue();

        assertThat(saved.getName())
                .isEqualTo(request.name())
                .isNotEqualTo(originalName);

        assertThat(saved.getRestaurantName())
                .isEqualTo(request.restaurantName())
                .isNotEqualTo(originalRestarantName);

        assertThat(saved.getEmail()).isEqualTo(originalEmail);
        assertThat(saved.getPassword()).isEqualTo(originalPassword);
        assertThat(saved.getLogin()).isEqualTo(originalLogin);
        assertThat(saved.getCnpj()).isEqualTo(originalCnpj);
        assertThat(saved.getAddress()).isEqualTo(updatedAddress);

        verify(restaurantOwnerRepository).findById(validId);
        verify(addressMapper).toModel(request.address());
        verify(restaurantOwnerMapper).toRestaurantOwnerResponse(any());
    }

    @Test
    void shouldThrowWhenUpdateNonExistingRestaurantOwner() {

        Long invalidId = 1L;

        when(restaurantOwnerRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> restaurantOwnerService.update(invalidId, RestaurantOwnerFactory.defaultRequest()));

        verify(restaurantOwnerRepository).findById(invalidId);
        verify(restaurantOwnerRepository, never()).save(any());
    }

    // findByName

    @Test
    void shouldReturnRestaurantOwnerListWhenNameExists() {

        String name = "joao";

        RestaurantOwner restaurantOwner1 = RestaurantOwnerFactory.withName("Teste João 1");
        RestaurantOwner restaurantOwner2 = RestaurantOwnerFactory.withName("Teste João 2");

        RestaurantOwnerResponse response1 = RestaurantOwnerFactory.defaultResponse();
        RestaurantOwnerResponse response2 = RestaurantOwnerFactory.defaultResponse();

        when(restaurantOwnerRepository.findByNameContainingIgnoreCase(name))
                .thenReturn(java.util.List.of(restaurantOwner1, restaurantOwner2));
        when(restaurantOwnerMapper.toRestaurantOwnerResponse(restaurantOwner1))
                .thenReturn(response1);
        when(restaurantOwnerMapper.toRestaurantOwnerResponse(restaurantOwner2))
                .thenReturn(response2);

        var result = restaurantOwnerService.findByName(name);

        assertThat(result).containsExactly(response1, response2);

        verify(restaurantOwnerRepository).findByNameContainingIgnoreCase(name);
        verify(restaurantOwnerMapper).toRestaurantOwnerResponse(restaurantOwner1);
        verify(restaurantOwnerMapper).toRestaurantOwnerResponse(restaurantOwner2);
        verify(restaurantOwnerMapper, times(2)).toRestaurantOwnerResponse(any());
    }

    @Test
    void shouldThrowWhenRestaurantOwnerNameNotFound() {

        String name = "issononecziste";

        when(restaurantOwnerRepository.findByNameContainingIgnoreCase(name))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> restaurantOwnerService.findByName(name));

        verify(restaurantOwnerRepository).findByNameContainingIgnoreCase(name);

    }

    // changePassword

    @Test
    void shouldChangePasswordSuccessfully() {

        Long validId = 1L;

        String oldPassword = "123senhaantiga";
        String newPassword = "senhanova456";
        String newEncodedPassword = "super-criptografado";

        RestaurantOwner restaurantOwner = RestaurantOwnerFactory.withPassword(oldPassword);

        ChangePasswordRequest request = new ChangePasswordRequest(oldPassword, newPassword);

        when(restaurantOwnerRepository.findById(validId)).thenReturn(Optional.of(restaurantOwner));
        when(passwordEncoder.matches(oldPassword, restaurantOwner.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(newEncodedPassword);
        when(restaurantOwnerRepository.save(restaurantOwner)).thenAnswer(invocation -> invocation.getArgument(0));

        restaurantOwnerService.changePassword(validId, request);

        verify(restaurantOwnerRepository).save(restaurantOwnerCaptor.capture());

        RestaurantOwner saved = restaurantOwnerCaptor.getValue();

        assertThat(saved.getPassword()).isEqualTo(newEncodedPassword);

        verify(restaurantOwnerRepository).findById(validId);
        verify(restaurantOwnerRepository).save(restaurantOwner);
    }

    @Test
    void shouldThrowWhenOldPasswordIncorrect() {

        String oldPassword = "123senhaantiga";
        String newPassword = "senhanova456";

        Long validId = 1L;

        RestaurantOwner restaurantOwner = RestaurantOwnerFactory.defaultModel();

        ChangePasswordRequest request = new ChangePasswordRequest(oldPassword, newPassword);

        when(restaurantOwnerRepository.findById(validId)).thenReturn(Optional.of(restaurantOwner));
        when(passwordEncoder.matches(eq(oldPassword), any())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> restaurantOwnerService.changePassword(validId, request));

        verify(restaurantOwnerRepository).findById(validId);
        verify(passwordEncoder).matches(eq(oldPassword), any());
        verify(restaurantOwnerRepository, never()).save(any());

    }

    @Test
    void shouldThrowWhenChangePasswordNonExistingRestaurantOwner() {

        Long invalidId = 1L;

        ChangePasswordRequest request = new ChangePasswordRequest("123senhaantiga", "senhanova456");

        when(restaurantOwnerRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> restaurantOwnerService.changePassword(invalidId, request));

        verify(restaurantOwnerRepository).findById(invalidId);
        verify(restaurantOwnerRepository, never()).save(any());


    }
    

}
