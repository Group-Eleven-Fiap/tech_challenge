# 📖 Documentação Swagger - Restaurant Management API

## 📑 Índice
1. [Visão Geral](#visão-geral)
2. [Como Acessar](#como-acessar)
3. [Configuração do Swagger](#configuração-do-swagger)
4. [Estrutura da Documentação](#estrutura-da-documentação)
5. [Endpoints Documentados](#endpoints-documentados)
6. [Exemplos de Requisições](#exemplos-de-requisições)
7. [Tratamento de Erros](#tratamento-de-erros)
8. [Autenticação e Segurança](#autenticação-e-segurança)

---

## 🎯 Visão Geral

A **Restaurant Management API** utiliza **OpenAPI 3.0 (Swagger)** para fornecer documentação interativa e completa da API REST. A documentação inclui:

- ✅ Descrições detalhadas de todos os endpoints
- ✅ Exemplos de requisições e respostas
- ✅ Esquemas de dados (DTOs)
- ✅ Códigos de status HTTP
- ✅ Tratamento de erros padronizado
- ✅ Interface interativa para testes

---

## 🌐 Como Acessar

### **URL da Documentação:**
```
http://localhost:8090/swagger-ui/index.html
```

### **Interface Principal:**
Após acessar a URL, você verá a interface do Swagger UI com:

```
┌─ Restaurant Management API v1.0
│  └─ Descrição: API para gerenciamento de restaurantes, clientes e proprietários
│
│  Contato: Equipe de Desenvolvimento
│  Email: dev@restaurant-management.com
│  GitHub: https://github.com/Group-Eleven-Fiap/tech_challenge
│
│  Licença: MIT License
│
│  Servidor: http://localhost:8090 (Servidor Local)
└─
```

### **Navegação:**
- **Tags:** Agrupamento por funcionalidade (Clientes, Proprietários)
- **Endpoints:** Lista de operações disponíveis
- **Schemas:** Definições de objetos de dados
- **Try it out:** Botão para testar endpoints diretamente

---

## ⚙️ Configuração do Swagger

### **Classe SwaggerConfig:**

```java
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restaurant Management API")
                        .version("1.0")
                        .description("API para gerenciamento de restaurantes, clientes e proprietários")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("dev@restaurant-management.com")
                                .url("https://github.com/Group-Eleven-Fiap/tech_challenge"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8090")
                                .description("Servidor Local")
                ));
    }
}
```

### **Anotações Customizadas:**

Para manter o código limpo, foram criadas interfaces customizadas que agrupam todas as anotações Swagger:

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Criar cliente", description = "Cadastra um novo cliente no sistema.")
@Parameter(name = "request", description = "Dados do cliente", required = true)
@RequestBody(description = "Dados do cliente", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerRequest.class), examples = @ExampleObject(value = "{\"name\": \"João Silva\", \"email\": \"joao@example.com\", \"login\": \"joao\", \"password\": \"senha123\", \"address\": {\"street\": \"Rua A\", \"number\": \"123\", \"city\": \"São Paulo\", \"state\": \"SP\", \"zipCode\": \"01234-567\"}}")))
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class), examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"João Silva\", \"email\": \"joao@example.com\", \"login\": \"joao\", \"address\": {\"street\": \"Rua A\", \"number\": \"123\", \"city\": \"São Paulo\", \"state\": \"SP\", \"zipCode\": \"01234-567\"}, \"lastModified\": \"2023-10-01T10:00:00\"}"))),
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 400, \"detail\": \"Campo obrigatório não preenchido\", \"code\": \"VALIDATION_ERROR\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers\"}"))),
    @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/business-rule-violation\", \"title\": \"Violação de regra de negócio\", \"status\": 409, \"detail\": \"Email já cadastrado\", \"code\": \"EMAIL_ALREADY_EXISTS\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers\"}"))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class), examples = @ExampleObject(value = "{\"type\": \"https://api.restaurant-management.com/errors/internal-server-error\", \"title\": \"Erro interno do servidor\", \"status\": 500, \"detail\": \"Um erro inesperado ocorreu\", \"timestamp\": \"2023-10-01T10:00:00\", \"path\": \"/v1/customers\", \"errorId\": \"Exception\"}")))
})
public @interface CreateCustomerApiDocs {
}
```

---

## 📋 Estrutura da Documentação

### **Organização por Tags:**

```
📁 Clientes
 ├── POST /v1/customers - Criar cliente
 ├── GET /v1/customers/search - Buscar por nome
 ├── PUT /v1/customers/{id} - Atualizar cliente
 ├── DELETE /v1/customers/{id} - Deletar cliente
 ├── PATCH /v1/customers/{id}/password - Alterar senha
 └── POST /v1/customers/login - Validar login

📁 Proprietários de Restaurante
 ├── POST /v1/owners - Criar proprietário
 ├── GET /v1/owners/search - Buscar por nome
 ├── PUT /v1/owners/{id} - Atualizar proprietário
 ├── DELETE /v1/owners/{id} - Deletar proprietário
 ├── PATCH /v1/owners/{id}/password - Alterar senha
 └── POST /v1/owners/login - Validar login
```

### **Schemas Disponíveis:**

```
📋 Request DTOs:
 ├── CustomerRequest
 ├── RestaurantOwnerRequest
 ├── ChangePasswordRequest
 ├── LoginRequest
 └── AddressRequest

📋 Response DTOs:
 ├── CustomerResponse
 ├── RestaurantOwnerResponse
 ├── LoginResponse
 └── AddressResponse

📋 Error Handling:
 └── ProblemDetail (RFC 7807)
```

---

## 🔗 Endpoints Documentados

### **1. Clientes (/v1/customers)**

#### **POST /v1/customers - Criar Cliente**
- **Descrição:** Cadastra um novo cliente no sistema
- **Parâmetros:** Body (CustomerRequest)
- **Respostas:**
  - `201` - Cliente criado com sucesso
  - `400` - Dados inválidos
  - `409` - Email já cadastrado
  - `500` - Erro interno

#### **GET /v1/customers/search - Buscar Clientes**
- **Descrição:** Busca clientes pelo nome
- **Parâmetros:** Query param `name` (String)
- **Respostas:**
  - `200` - Lista de clientes encontrados
  - `404` - Nenhum cliente encontrado
  - `500` - Erro interno

#### **PUT /v1/customers/{id} - Atualizar Cliente**
- **Descrição:** Atualiza dados de um cliente existente
- **Parâmetros:** Path `id` (Long), Body (CustomerRequest)
- **Respostas:**
  - `200` - Cliente atualizado
  - `400` - Dados inválidos
  - `404` - Cliente não encontrado
  - `409` - Email já cadastrado
  - `500` - Erro interno

#### **DELETE /v1/customers/{id} - Deletar Cliente**
- **Descrição:** Remove um cliente do sistema
- **Parâmetros:** Path `id` (Long)
- **Respostas:**
  - `204` - Cliente deletado
  - `404` - Cliente não encontrado
  - `500` - Erro interno

#### **PATCH /v1/owners/{id}/password - Alterar Senha**
- **Descrição:** Altera a senha de um proprietário
- **Parâmetros:** Path `id` (Long), Body (ChangePasswordRequest)
- **Respostas:**
  - `204` - Senha alterada
  - `400` - Dados inválidos ou senha antiga incorreta
  - `404` - Proprietário não encontrado
  - `500` - Erro interno

#### **POST /v1/owners/login - Validar Login**
- **Descrição:** Valida os dados de login de um proprietário de restaurante no sistema
- **Parâmetros:** Body (LoginRequest)
- **Respostas:**
  - `200` - Login validado com sucesso (retorna LoginResponse)
  - `400` - Dados inválidos
  - `401` - Credenciais inválidas (usuário e/ou senha inválido)
  - `500` - Erro interno

#### **POST /v1/customers/login - Validar Login**
- **Descrição:** Valida os dados de login de um cliente no sistema
- **Parâmetros:** Body (LoginRequest)
- **Respostas:**
  - `200` - Login validado com sucesso (retorna LoginResponse)
  - `400` - Dados inválidos
  - `401` - Credenciais inválidas (usuário e/ou senha inválido)
  - `500` - Erro interno

### **2. Proprietários (/v1/owners)**

#### **POST /v1/owners - Criar Proprietário**
- **Descrição:** Cadastra um novo proprietário de restaurante
- **Parâmetros:** Body (RestaurantOwnerRequest)
- **Respostas:** Similar ao endpoint de clientes

#### **GET /v1/owners/search - Buscar Proprietários**
- **Descrição:** Busca proprietários pelo nome
- **Parâmetros:** Query param `name` (String)

#### **PUT /v1/owners/{id} - Atualizar Proprietário**
- **Descrição:** Atualiza dados de um proprietário existente
- **Parâmetros:** Path `id` (Long), Body (RestaurantOwnerRequest)

#### **DELETE /v1/owners/{id} - Deletar Proprietário**
- **Descrição:** Remove um proprietário do sistema
- **Parâmetros:** Path `id` (Long)

#### **PATCH /v1/owners/{id}/password - Alterar Senha**
- **Descrição:** Altera a senha de um proprietário
- **Parâmetros:** Path `id` (Long), Body (ChangePasswordRequest)
- **Respostas:**
  - `204` - Senha alterada
  - `400` - Dados inválidos ou senha antiga incorreta
  - `404` - Proprietário não encontrado
  - `500` - Erro interno

#### **POST /v1/owners/login - Validar Login**
- **Descrição:** Valida os dados de login de um proprietário de restaurante no sistema
- **Parâmetros:** Body (LoginRequest)
- **Respostas:**
  - `200` - Login validado com sucesso (retorna LoginResponse)
  - `400` - Dados inválidos
  - `401` - Credenciais inválidas (usuário e/ou senha inválido)
  - `500` - Erro interno

---

## 📝 Exemplos de Requisições

### **Exemplo 1: Criar Cliente**

**Request:**
```http
POST /v1/customers
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@example.com",
  "login": "joao",
  "password": "senha123",
  "address": {
    "street": "Rua das Flores",
    "number": "123",
    "city": "São Paulo",
    "state": "SP",
    "zipCode": "01234-567"
  }
}
```

**Response (201):**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@example.com",
  "login": "joao",
  "address": {
    "street": "Rua das Flores",
    "number": "123",
    "city": "São Paulo",
    "state": "SP",
    "zipCode": "01234-567"
  },
  "lastModified": "2023-10-01T10:00:00"
}
```

### **Exemplo 2: Buscar Clientes**

**Request:**
```http
GET /v1/customers/search?name=João
```

**Response (200):**
```json
[
  {
    "id": 1,
    "name": "João Silva",
    "email": "joao@example.com",
    "login": "joao",
    "address": {
      "street": "Rua das Flores",
      "number": "123",
      "city": "São Paulo",
      "state": "SP",
      "zipCode": "01234-567"
    },
    "lastModified": "2023-10-01T10:00:00"
  }
]
```

---

## ⚠️ Tratamento de Erros

### **Estrutura de Erro (RFC 7807):**

```json
{
  "type": "https://api.restaurant-management.com/errors/resource-not-found",
  "title": "Recurso não encontrado",
  "status": 404,
  "detail": "Cliente não encontrado",
  "timestamp": "2023-10-01T10:00:00",
  "path": "/v1/customers/999",
  "fieldName": "id",
  "fieldValue": "999"
}
```

### **Tipos de Erro:**

| Código | Tipo | Descrição |
|---|---|---|
| 400 | `business-rule-violation` | Violação de regra de negócio |
| 401 | `invalid-credentials` | Credenciais inválidas |
| 404 | `resource-not-found` | Recurso não encontrado |
| 409 | `business-rule-violation` | Conflito (email duplicado) |
| 500 | `internal-server-error` | Erro interno do servidor |

### **Exemplo de Erro de Validação:**

```json
{
  "type": "https://api.restaurant-management.com/errors/business-rule-violation",
  "title": "Violação de regra de negócio",
  "status": 400,
  "detail": "Campo obrigatório não preenchido",
  "code": "VALIDATION_ERROR",
  "timestamp": "2023-10-01T10:00:00",
  "path": "/v1/customers"
}
```

---

## 🎯 Conclusão

A documentação Swagger da **Restaurant Management API** oferece:

✅ **Interface Interativa** - Teste endpoints diretamente no navegador  
✅ **Documentação Completa** - Todos os endpoints, parâmetros e respostas  
✅ **Exemplos Práticos** - Requisições e respostas de exemplo  
✅ **Tratamento de Erros** - Documentação clara de cenários de erro  
✅ **Schemas de Dados** - Definições completas dos objetos  
✅ **Organização Clara** - Agrupamento por tags e funcionalidades

A documentação facilita o desenvolvimento, testes e integração com a API, seguindo as melhores práticas de API REST documentada.

---

**Versão:** 1.0  
**Data:** 02/05/2026  
**API Version:** v1.0  
**Swagger Version:** OpenAPI 3.0</content>
