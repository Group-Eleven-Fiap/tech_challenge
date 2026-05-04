package br.com.fiap.restaurant_management.entity.dtos;

import java.time.LocalDateTime;

public record RestaurantOwnerResponse(

        Long id,
        String name,
        String email,
        String login,
        AddressResponse address,
        String restaurantName,
        String cnpj,
        LocalDateTime lastModified

) {}