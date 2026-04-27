package br.com.fiap.restaurant_management.entity.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(@NotBlank String name,
                              @NotBlank @Email String email,
                              @NotBlank String login,
                              @NotBlank String password,
                              @NotNull AddressRequest address
) {}
