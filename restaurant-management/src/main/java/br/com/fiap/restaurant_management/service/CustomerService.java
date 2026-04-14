package br.com.fiap.restaurant_management.service;

import br.com.fiap.restaurant_management.entity.Customer;
import br.com.fiap.restaurant_management.entity.dtos.ChangePasswordRequest;
import br.com.fiap.restaurant_management.entity.dtos.CustomerRequest;
import br.com.fiap.restaurant_management.entity.dtos.CustomerResponse;
import br.com.fiap.restaurant_management.mapper.AddressMapper;
import br.com.fiap.restaurant_management.mapper.CustomerMapper;
import br.com.fiap.restaurant_management.repository.CustomerRepository;
import br.com.fiap.restaurant_management.repository.RestaurantOwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestaurantOwnerRepository ownerRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerResponse create(CustomerRequest request) {

        validateEmail(request.email());

        Customer customer = Customer.builder()
                .name(request.name())
                .email(request.email())
                .login(request.login())
                .password(passwordEncoder.encode(request.password()))
                .address(addressMapper.toModel(request.address()))
                .build();

        Customer saved = customerRepository.save(customer);

        log.debug("Usuário persistido | id={} nome={}", saved.getId(), saved.getName());
        return customerMapper.toCustomerResponse(saved);
    }

    public void delete(Long id) {
        log.debug("Verificando existência do usuário ID: {}", id);

        var customer = customerRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id: " + id));
        customerRepository.delete(customer);
    }

    public CustomerResponse update(Long id, CustomerRequest request) {
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + id));

        customer.setName(request.name());
        customer.setAddress(addressMapper.toModel(request.address()));

        Customer updated = customerRepository.save(customer);

        log.info("Usuário atualizado no banco de dados - ID: {}", id);

        return customerMapper.toCustomerResponse(updated);
    }

    public void changePassword(Long id, ChangePasswordRequest request) {
        Customer customer = findById(id);

        if (!passwordEncoder.matches(request.oldPassword(), customer.getPassword())) {
            throw new RuntimeException("Senha atual inválida");
        }

        customer.setPassword(passwordEncoder.encode(request.newPassword()));
        customerRepository.save(customer);
    }

    public List<CustomerResponse> findByName(String name) {
        var customers = customerRepository.findByNameContainingIgnoreCase(name);

        if (customers.isEmpty()) {
            throw new RuntimeException("Nenhum cliente encontrado com o nome: " + name);
        }

        return customers.stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }

    private Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));
    }

    private void validateEmail(String email) {
        if (customerRepository.existsByEmail(email) || ownerRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já cadastrado");
        }
    }
}

