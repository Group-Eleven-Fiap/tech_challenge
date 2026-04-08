package br.com.fiap.restaurant_management.repository;

import br.com.fiap.restaurant_management.entity.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Long> {
    Optional<RestaurantOwner> findByName(String name);

    Optional<RestaurantOwner> findByEmail(String email);
}