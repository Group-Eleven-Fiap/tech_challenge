package br.com.fiap.restaurant_management.Config;

import br.com.fiap.restaurant_management.entity.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clientes", description = "Operações relacionadas aos clientes do sistema")
public interface CustomerControllerApi {

    @PostMapping
    @Operation(summary = "Criar cliente", description = "Cadastra um novo cliente no sistema.")
    @RequestBody(description = "Dados do cliente", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerRequest.class), examples = @ExampleObject(value = "{\"name\": \"João Silva\", \"email\": \"joao@example.com\", \"login\": \"joao\", \"password\": \"senha123\", \"address\": {\"street\": \"Rua A\", \"number\": \"123\", \"city\": \"São Paulo\", \"state\": \"SP\", \"zipCode\": \"01234-567\"}}")))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class), examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"João Silva\", \"email\": \"joao@example.com\", \"login\": \"joao\", \"address\": {\"street\": \"Rua A\", \"number\": \"123\", \"city\": \"São Paulo\", \"state\": \"SP\", \"zipCode\": \"01234-567\"}, \"lastModified\": \"2023-10-01T10:00:00\"}"))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Campo obrigatório não preenchido\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers\"}"))),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 409, \"detail\": \"Email já cadastrado\", \"code\": \"EMAIL_ALREADY_EXISTS\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers\"}"))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest request);

    @GetMapping("/search")
    @Operation(summary = "Buscar clientes por nome", description = "Busca clientes pelo nome fornecido.")
    @Parameter(name = "name", description = "Nome do cliente", required = true, example = "João")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class), examples = @ExampleObject(value = "[{\"id\": 1, \"name\": \"João Silva\", \"email\": \"joao@example.com\", \"login\": \"joao\", \"address\": {\"street\": \"Rua A\", \"number\": \"123\", \"city\": \"São Paulo\", \"state\": \"SP\", \"zipCode\": \"01234-567\"}, \"lastModified\": \"2023-10-01T10:00:00\"}]"))),
        @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/resource-not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Nenhum cliente encontrado com o nome fornecido\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/search\"}"))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/search\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<List<CustomerResponse>> findByName(@RequestParam String name);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema pelo ID.")
    @Parameter(name = "id", description = "ID do cliente", required = true, example = "1")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/resource-not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Cliente não encontrado\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/1\"}"))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/1\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente.")
    @Parameter(name = "id", description = "ID do cliente", required = true, example = "1")
    @RequestBody(description = "Dados atualizados do cliente", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerRequest.class), examples = @ExampleObject(value = "{\"name\": \"João Silva Atualizado\", \"email\": \"joao@example.com\", \"login\": \"joao\", \"password\": \"novaSenha123\", \"address\": {\"street\": \"Rua B\", \"number\": \"456\", \"city\": \"São Paulo\", \"state\": \"SP\", \"zipCode\": \"01234-567\"}}")))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class), examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"João Silva Atualizado\", \"email\": \"joao@example.com\", \"login\": \"joao\", \"address\": {\"street\": \"Rua B\", \"number\": \"456\", \"city\": \"São Paulo\", \"state\": \"SP\", \"zipCode\": \"01234-567\"}, \"lastModified\": \"2023-10-01T10:00:00\"}"))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Campo obrigatório não preenchido\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/1\"}"))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/resource-not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Cliente não encontrado\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/1\"}"))),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 409, \"detail\": \"Email já cadastrado\", \"code\": \"EMAIL_ALREADY_EXISTS\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/1\"}"))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/1\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequest request);

    @PatchMapping("/{id}/password")
    @Operation(summary = "Alterar senha do cliente", description = "Altera a senha de um cliente existente.")
    @Parameter(name = "id", description = "ID do cliente", required = true, example = "1")
    @RequestBody(description = "Dados para alteração de senha", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChangePasswordRequest.class), examples = @ExampleObject(value = "{\"oldPassword\": \"senha123\", \"newPassword\": \"novaSenha456\"}")))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou senha antiga incorreta", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Senha antiga incorreta\", \"code\": \"INVALID_PASSWORD\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/1/password\"}"))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/resource-not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Cliente não encontrado\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/1/password\"}"))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/1/password\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody @Valid ChangePasswordRequest request);

    @PostMapping("/login")
    @Operation(summary = "Validar login no sistema", description = "Valida os dados de login de um cliente no sistema.")
    @RequestBody(description = "Dados de login do cliente", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequest.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login do usuário validado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Campo obrigatório não preenchido\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/login\"}"))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/invalid-credentials\", \"title\": \"Credenciais inválidas\", \"status\": 401, \"detail\": \"Usuário e/ou senha inválido\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/login\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers/login\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<LoginResponse> validateLogin(@RequestBody @Valid LoginRequest request);
}

