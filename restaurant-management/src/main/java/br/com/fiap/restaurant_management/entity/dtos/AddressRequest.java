package br.com.fiap.restaurant_management.entity.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(@NotBlank(message = "A rua é obrigatória")
                             String street,

                             @NotBlank(message = "O número é obrigatório")
                             String number,

                             @NotBlank(message = "A cidade é obrigatória")
                             String city,

                             @NotBlank(message = "O estado é obrigatório")
                             String state,

                             @NotBlank(message = "O CEP é obrigatório")
                             String zipCode) {
}
