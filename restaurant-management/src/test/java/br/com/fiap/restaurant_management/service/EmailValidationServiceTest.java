package br.com.fiap.restaurant_management.service;

import br.com.fiap.restaurant_management.exception.ResourceAlreadyExistsException;
import br.com.fiap.restaurant_management.repository.CustomerRepository;
import br.com.fiap.restaurant_management.repository.RestaurantOwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class EmailValidationServiceTest {

    @InjectMocks
    private EmailValidationService emailValidationService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RestaurantOwnerRepository ownerRepository;

    @Test
    void shouldValidateUniquenessSuccessfully() {

        String email = "testando@teste.com";

        when(customerRepository.existsByEmail(email)).thenReturn(false);
        when(ownerRepository.existsByEmail(email)).thenReturn(false);

        emailValidationService.validateEmailUniqueness(email);

        verify(customerRepository).existsByEmail(email);
        verify(ownerRepository).existsByEmail(email);
    }

    @Test
    void shouldThrowWhenEmailAlreadyExistsAsCustomer() {

        String email = "testando@teste.com";

        when(customerRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> emailValidationService.validateEmailUniqueness(email));

        verify(customerRepository).existsByEmail(email);
        verify(ownerRepository, never()).existsByEmail(email);

    }

    @Test
    void shouldThrowWhenEmailAlreadyExistsAsRestaurantOwner() {

        String email = "testando@teste.com";

        when(customerRepository.existsByEmail(email)).thenReturn(false);
        when(ownerRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> emailValidationService.validateEmailUniqueness(email));

        verify(ownerRepository).existsByEmail(email);

    }

}
