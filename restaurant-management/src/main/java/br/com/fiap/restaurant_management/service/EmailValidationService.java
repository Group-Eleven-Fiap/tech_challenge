package br.com.fiap.restaurant_management.service;

import br.com.fiap.restaurant_management.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.exception.ResourceAlreadyExistsException;
import br.com.fiap.restaurant_management.repository.CustomerRepository;
import br.com.fiap.restaurant_management.repository.RestaurantOwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailValidationService {

    private final CustomerRepository customerRepository;
    private final RestaurantOwnerRepository ownerRepository;

    public void validateEmailUniqueness(String email) {
        log.debug("Validando unicidade de email: {}", email);

        if (customerRepository.existsByEmail(email) || ownerRepository.existsByEmail(email)) {
            log.warn("Email já cadastrado no sistema: {}", email);
//            throw new BusinessRuleException("EMAIL_DUPLICATE", "Email já cadastrado no sistema");
            throw new ResourceAlreadyExistsException("Email já cadastrado no sistema");
        }

        log.debug("Email validado com sucesso: {}", email);
    }
}

