package br.com.fiap.restaurant_management.entity.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginResponse", description = "Resposta da validação de login")
public record LoginResponse(

        @Schema(description = "Mensagem", example = "Usuário validado com sucesso")
        String message
) {}
