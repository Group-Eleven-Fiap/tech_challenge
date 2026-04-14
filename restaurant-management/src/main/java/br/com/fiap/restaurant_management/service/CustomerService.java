package br.com.fiap.restaurant_management.service;

import br.com.fiap.restaurant_management.entity.Customer;
import br.com.fiap.restaurant_management.entity.dtos.ChangePasswordRequest;
import br.com.fiap.restaurant_management.entity.dtos.CustomerRequest;
import br.com.fiap.restaurant_management.entity.dtos.CustomerResponse;
import br.com.fiap.restaurant_management.mapper.AddressMapper;
import br.com.fiap.restaurant_management.mapper.CustomerMapper;
import br.com.fiap.restaurant_management.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, AddressMapper addressMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
    }

    public CustomerResponse create(CustomerRequest request){

        Customer customer = Customer.builder()
                .name(request.name())
                .email(request.email())
                .login(request.login())
                .password(passwordEncoder.encode(request.password()))
                .address(addressMapper.toModel(request.address()))
                .build();

        Customer saved = customerRepository.save(customer);

        return customerMapper.toCustomerResponse(saved);
    }

    public void delete(Long id){
        customerRepository.deleteById(id);
    }

    public Customer findById(Long id){
        return customerRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Nenhum cliente encontrado com o id " + id));
    }

    public List<Customer> findByName(String name){
        var customer = customerRepository.findByNameContainingIgnoreCase(name);
        if(customer.isEmpty()){
            throw new RuntimeException("Nenhum cliente encontrado com o nome " + name);
        }
        return customer;
    }

    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = findById(id);

        customer.setName(request.name());
        customer.setAddress(addressMapper.toModel(request.address()));

        return customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    public void changePassword(Long id, ChangePasswordRequest request) {
        Customer customer = findById(id);

        if (!passwordEncoder.matches(request.oldPassword(), customer.getPassword())) {
            throw new RuntimeException("Senha atual inválida");
        }

        customer.setPassword(passwordEncoder.encode(request.newPassword()));
        customerRepository.save(customer);
    }
}

