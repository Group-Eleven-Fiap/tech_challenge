package br.com.fiap.restaurant_management.entity.dtos;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
    @NotBlank String oldPassword,
    @NotBlank String newPassword
) {}