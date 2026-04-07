# 🚀 TECH CHALLENGE

O **Tech Challenge** é o projeto da fase que engloba os conhecimentos obtidos em todas as disciplinas.  
Esta atividade deve ser desenvolvida em grupo e é **obrigatória**, compondo a nota final.

---

## 📌 O Problema

Na nossa região, um grupo de restaurantes decidiu contratar estudantes para construir um sistema de gestão compartilhado.

Essa decisão foi motivada pelo alto custo de sistemas individuais, levando os restaurantes a desenvolverem uma solução única.

O sistema permitirá que:

- Clientes escolham restaurantes com base na comida oferecida  
- Restaurantes gerenciem suas operações de forma eficiente  
- Clientes consultem informações, façam pedidos e deixem avaliações  

Devido a limitações financeiras, o sistema será entregue **em fases**, garantindo:

- Desenvolvimento cuidadoso  
- Implementação gradual  
- Melhorias contínuas  

---

## 🎯 Objetivo

Desenvolver um **backend robusto com Spring Boot**, aplicando os conceitos aprendidos na Fase 1.

---

## ⚙️ Funcionalidades Obrigatórias

O sistema deve permitir:

- Cadastro de usuários  
- Atualização de usuários  
- Exclusão de usuários  
- Troca de senha (endpoint separado)  
- Atualização de dados (endpoint separado do de senha)  
- Registro da data da última alteração  
- Busca de usuários por nome  
- Garantia de e-mail único  
- Validação de login (login + senha)  

### 🔐 Observações

- Não é obrigatório usar **Spring Security**  
- Pode ser feita validação simples via banco de dados  

---

## 🐳 Docker

A aplicação deve ser dockerizada com:

- Docker Compose  
- Banco relacional (MySQL ou PostgreSQL)  

---

## 👥 Tipos de Usuários

O sistema deve ter obrigatoriamente:

- Dono de restaurante  
- Cliente  

Outros tipos podem ser adicionados.

---

## 🧾 Campos Obrigatórios

Todo usuário deve conter:

- Nome (String)  
- E-mail (String - único)  
- Login (String)  
- Senha (String)  
- Data da última alteração (Date)  
- Endereço (String ou objeto com rua, número, cidade, CEP)  

---

## 📊 Critérios de Avaliação

### 1. Funcionalidade

- Backend atende todos os requisitos  
- Endpoints funcionando corretamente  
- Tratamento de erros adequado  
- Versionamento de API  
- Uso do padrão **ProblemDetail (RFC 7807)**  

Requisitos específicos:

- Dois tipos de usuários implementados  
- Busca por nome  
- E-mail único  
- Validação de login  
- Endpoint de troca de senha separado  
- Endpoint de atualização separado  

---

### 2. Qualidade do Código

- Boas práticas de Spring Boot  
- Princípios SOLID  
- Código limpo e organizado  

---

### 3. 📄 Documentação (Swagger)

- Endpoints documentados com Swagger/OpenAPI  
- Exemplos de requisições e respostas (sucesso e erro)  

---

### 4. 🗄️ Banco de Dados

- Uso obrigatório de banco relacional  
- Recomendados: MySQL ou PostgreSQL  
- Deve rodar em container Docker  

---

### 5. 📬 Collections (Postman)

Deve conter uma collection JSON com:

- Cadastro de usuário válido  
- Cadastro inválido (ex: e-mail duplicado)  
- Alteração de senha (sucesso/erro)  
- Atualização de dados do usuário  
- Busca por nome  
- Validação de login  

---

### 6. 📑 Relatório Técnico (ENTREGÁVEL)

**Único arquivo entregue: PDF**

Deve conter:

- Arquitetura da aplicação  
- Modelagem de entidades e relacionamentos  
- Descrição dos endpoints (com exemplos)  
- Documentação Swagger  
- Collection Postman  
- Estrutura do banco de dados  
- Guia de execução com Docker Compose  

---

### 7. ▶️ Execução

- Arquivo `docker-compose.yml` obrigatório  

---

### 8. 💻 Repositório

- GitHub ou GitLab público  
- Deve conter:
  - Código-fonte  
  - README  
  - Swagger  
  - Collection Postman  

O relatório PDF deve ser enviado separadamente.

---

## ⭐ Opcional (Desafio Extra)

- Autenticação com Spring Security + JWT  
- Testes unitários com JUnit + Mockito  
