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

@Tag(name = "Proprietários de Restaurante", description = "Operações relacionadas aos proprietários de restaurante")
public interface RestaurantOwnerControllerApi {

    @PostMapping
    @Operation(summary = "Criar proprietário", description = "Cadastra um novo proprietário de restaurante no sistema.")
    @RequestBody(description = "Dados do proprietário", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantOwnerRequest.class), examples = @ExampleObject(value = "{\"name\": \"Maria Oliveira\", \"email\": \"maria@example.com\", \"login\": \"maria\", \"password\": \"senha123\", \"address\": {\"street\": \"Rua C\", \"number\": \"789\", \"city\": \"Rio de Janeiro\", \"state\": \"RJ\", \"zipCode\": \"20000-000\"}, \"restaurantName\": \"Restaurante da Maria\", \"cnpj\": \"12.345.678/0001-90\"}")))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Proprietário criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantOwnerResponse.class), examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Maria Oliveira\", \"email\": \"maria@example.com\", \"login\": \"maria\", \"address\": {\"street\": \"Rua C\", \"number\": \"789\", \"city\": \"Rio de Janeiro\", \"state\": \"RJ\", \"zipCode\": \"20000-000\"}, \"restaurantName\": \"Restaurante da Maria\", \"cnpj\": \"12.345.678/0001-90\", \"lastModified\": \"2023-10-01T10:00:00\"}"))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Campo obrigatório não preenchido\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners\"}"))),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 409, \"detail\": \"Email já cadastrado\", \"code\": \"EMAIL_ALREADY_EXISTS\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners\"}"))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<RestaurantOwnerResponse> createOwner(@RequestBody @Valid RestaurantOwnerRequest request);

    @GetMapping("/search")
    @Operation(summary = "Buscar proprietários por nome", description = "Busca proprietários de restaurante pelo nome fornecido.")
    @Parameter(name = "name", description = "Nome do proprietário", required = true, example = "Maria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proprietários encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantOwnerResponse.class), examples = @ExampleObject(value = "[{\"id\": 1, \"name\": \"Maria Oliveira\", \"email\": \"maria@example.com\", \"login\": \"maria\", \"address\": {\"street\": \"Rua C\", \"number\": \"789\", \"city\": \"Rio de Janeiro\", \"state\": \"RJ\", \"zipCode\": \"20000-000\"}, \"restaurantName\": \"Restaurante da Maria\", \"cnpj\": \"12.345.678/0001-90\", \"lastModified\": \"2023-10-01T10:00:00\"}]"))),
        @ApiResponse(responseCode = "404", description = "Nenhum proprietário encontrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/resource-not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Nenhum proprietário encontrado com o nome fornecido\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners/search\"}"))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners/search\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<List<RestaurantOwnerResponse>> findByName(@RequestParam String name);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar proprietário", description = "Remove um proprietário de restaurante do sistema pelo ID.")
    @Parameter(name = "id", description = "ID do proprietário", required = true, example = "1")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Proprietário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Proprietário não encontrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/resource-not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Proprietário não encontrado\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners/1\"}"))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners/1\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar proprietário", description = "Atualiza os dados de um proprietário de restaurante existente.")
    @Parameter(name = "id", description = "ID do proprietário", required = true, example = "1")
    @RequestBody(description = "Dados atualizados do proprietário", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantOwnerRequest.class), examples = @ExampleObject(value = "{\"name\": \"Maria Oliveira Atualizada\", \"email\": \"maria@example.com\", \"login\": \"maria\", \"password\": \"novaSenha123\", \"address\": {\"street\": \"Rua D\", \"number\": \"101\", \"city\": \"Rio de Janeiro\", \"state\": \"RJ\", \"zipCode\": \"20000-000\"}, \"restaurantName\": \"Restaurante da Maria Atualizado\", \"cnpj\": \"12.345.678/0001-90\"}")))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proprietário atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantOwnerResponse.class), examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Maria Oliveira Atualizada\", \"email\": \"maria@example.com\", \"login\": \"maria\", \"address\": {\"street\": \"Rua D\", \"number\": \"101\", \"city\": \"Rio de Janeiro\", \"state\": \"RJ\", \"zipCode\": \"20000-000\"}, \"restaurantName\": \"Restaurante da Maria Atualizado\", \"cnpj\": \"12.345.678/0001-90\", \"lastModified\": \"2023-10-01T10:00:00\"}"))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Campo obrigatório não preenchido\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners/1\"}"))),
        @ApiResponse(responseCode = "404", description = "Proprietário não encontrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/resource-not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Proprietário não encontrado\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners/1\"}"))),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 409, \"detail\": \"Email já cadastrado\", \"code\": \"EMAIL_ALREADY_EXISTS\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners/1\"}"))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners/1\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<RestaurantOwnerResponse> updateOwner(@PathVariable Long id, @RequestBody @Valid RestaurantOwnerRequest request);

    @PatchMapping("/{id}/password")
    @Operation(summary = "Alterar senha do proprietário", description = "Altera a senha de um proprietário de restaurante existente.")
    @Parameter(name = "id", description = "ID do proprietário", required = true, example = "1")
    @RequestBody(description = "Dados para alteração de senha", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChangePasswordRequest.class), examples = @ExampleObject(value = "{\"oldPassword\": \"senha123\", \"newPassword\": \"novaSenha456\"}")))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou senha antiga incorreta", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Senha antiga incorreta\", \"code\": \"INVALID_PASSWORD\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners/1/password\"}"))),
        @ApiResponse(responseCode = "404", description = "Proprietário não encontrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/resource-not-found\", \"title\": \"Recurso não encontrado\", \"status\": 404, \"detail\": \"Proprietário não encontrado\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners/1/password\"}"))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/owners/1/password\", \"errorId\": \"Exception\"}")))
    })
    ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody @Valid ChangePasswordRequest request);

    @PostMapping("/login")
    @Operation(summary = "Validar login do proprietário de restaurante no sistema", description = "Valida os dados de login de um proprietário de restaurante no sistema.")
    @RequestBody(
            description = "Dados de login do proprietário do restaurante",
            required = true,
            content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = LoginRequest.class)))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login do usuário validado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", " +
                                            "\"title\": \"Violação de regra de negócio\", " +
                                            "\"status\": 400, " +
                                            "\"detail\": \"Campo obrigatório não preenchido\", " +
                                            "\"code\": \"VALIDATION_ERROR\", " +
                                            "\"timestamp\": \"2023-10-01T10:00:00\", " +
                                            "\"path\": \"/v1/customers\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais inválidas",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"type\": \"https://api.restaurant-management.com/errors/invalid-credentials\", " +
                                            "\"title\": \"Credenciais inválidas\", " +
                                            "\"status\": 401, " +
                                            "\"detail\": \"Usuário e/ou senha inválido\", " +
                                            "\"timestamp\": \"2023-10-01T10:00:00\", " +
                                            "\"path\": \"/v1/auth/login\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\"," +
                                            " \"title\": \"Erro interno do servidor\"," +
                                            " \"status\": 500, " +
                                            "\"detail\": \"Um erro inesperado ocorreu\", " +
                                            "\"timestamp\": \"2023-10-01T10:00:00\", " +
                                            "\"path\": \"/v1/customers\", " +
                                            "\"errorId\": \"Exception\"}")
                    )
            )
    })
    ResponseEntity<LoginResponse> validateLogin(@RequestBody @Valid LoginRequest request);
}

