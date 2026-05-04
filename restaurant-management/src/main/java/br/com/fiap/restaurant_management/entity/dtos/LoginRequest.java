package br.com.fiap.restaurant_management.entity.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "LoginRequest", description = "Requisição da validação de login")
public record LoginRequest(

        @Schema(description = "Login", example = "joao.silva")
        @NotBlank
        String login,

        @Schema(description = "Senha", example = "SuperSecreto")
        @NotBlank
        String password
) {}
