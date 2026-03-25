# Sistema de Gestão de Restaurantes

## O Problema

Na nossa região, um grupo de restaurantes decidiu contratar estudantes para construir um sistema de gestão para seus estabelecimentos. Essa decisão foi motivada pelo alto custo de sistemas individuais, o que levou os restaurantes a se unirem para desenvolver um sistema único e compartilhado.

Esse sistema permitirá que os clientes escolham restaurantes com base na comida oferecida, em vez de se basearem na qualidade do sistema de gestão.

O objetivo é criar um sistema robusto que permita a todos os restaurantes gerenciar eficientemente suas operações, enquanto os clientes poderão consultar informações, deixar avaliações e fazer pedidos online.

Devido à limitação de recursos financeiros, foi acordado que a entrega do sistema será realizada em fases, garantindo que cada etapa seja desenvolvida de forma cuidadosa e eficaz.

A divisão em fases possibilitará uma implementação gradual e controlada, permitindo ajustes e melhorias contínuas conforme o sistema for sendo utilizado e avaliado tanto pelos restaurantes quanto pelos clientes.

---

# Objetivo

Desenvolver um backend completo e robusto utilizando o framework **Spring Boot**, com foco no gerenciamento de usuários, incluindo operações de criação, atualização, exclusão e validação de login.

O projeto será configurado para rodar em um ambiente **Docker**, utilizando **Docker Compose**, o que permitirá a orquestração dos serviços e a integração com um banco de dados relacional, como **PostgreSQL, MySQL ou H2**.

A configuração com Docker Compose garantirá que a aplicação seja facilmente replicável e escalável, permitindo a implantação em diversos ambientes de forma consistente e eficiente.

Além disso, o projeto será desenvolvido seguindo as melhores práticas de arquitetura e segurança, de modo que o sistema seja não apenas funcional, mas também **seguro, escalável e de fácil manutenção**.

---

# Usuário

Os usuários terão uma interface para se cadastrar, porém, nessa parte do projeto, vamos focar apenas nas entregas de **backend**.

O sistema terá **dois tipos de usuário**:

- Dono de restaurante
- Cliente

Os campos necessários para um bom cadastro de usuário são:

- Nome (String)  
- Email (String)  
- Login (String)  
- Senha (String)  
- Data da última alteração (Date)  
- Endereço  

---

# Funcionalidades

## Gerenciar Usuário

Possibilidade de alteração de dados do usuário.

## Troca de Senha

Permite trocar a senha do usuário.

## Validação de Login

Validação do login do usuário.

---

# Entregáveis e Fatores de Avaliação da Fase 1

## 1. Funcionalidade

- O backend atende a todos os requisitos especificados.
- Os endpoints funcionam conforme descrito.

## 2. Qualidade do Código

- Uso adequado das práticas de desenvolvimento do Spring Boot.
- Código organizado e bem documentado.

## 3. Documentação do Projeto

Descrição detalhada do projeto, incluindo:

- Arquitetura
- Endpoints da API
- Instruções de configuração e execução

## 4. Collections para Teste

Collections do **Postman** ou ferramenta similar para testar os endpoints da API.

---

# Configuração Docker Compose

Arquivo `docker-compose.yml` configurado para subir:

- A aplicação Java
- O banco de dados

---

# Repositório de Código

Repositório de fontes aberto (**GitHub, GitLab etc.**) onde professores possam baixar o código-fonte do projeto.
