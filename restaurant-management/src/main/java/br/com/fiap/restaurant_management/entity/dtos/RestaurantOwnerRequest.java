package br.com.fiap.restaurant_management.entity.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestaurantOwnerRequest(

        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "O login é obrigatório")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        String password,

        @NotNull(message = "O endereço é obrigatório")
        AddressRequest address,

        @NotBlank(message = "O nome do restaurante é obrigatório")
        String restaurantName,

        @NotBlank(message = "O CNPJ é obrigatório")
        String cnpj

) {}