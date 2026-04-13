package br.com.fiap.restaurant_management.repository;

import br.com.fiap.restaurant_management.entity.Customer;
import br.com.fiap.restaurant_management.entity.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Long> {
    List<RestaurantOwner> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);

    Optional<RestaurantOwner> findByEmail(String email);
}