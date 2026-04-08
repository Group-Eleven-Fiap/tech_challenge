package br.com.fiap.restaurant_management.repository;

import br.com.fiap.restaurant_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);
}