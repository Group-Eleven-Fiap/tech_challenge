package br.com.fiap.restaurant_management.repository;

import br.com.fiap.restaurant_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);
}