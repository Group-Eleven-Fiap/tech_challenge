package br.com.fiap.restaurant_management.entity.dtos;


import java.time.LocalDateTime;

public record CustomerResponse(Long id,
                               String name,
                               String email,
                               String login,
                               AddressResponse address,
                               LocalDateTime lastModified)
{ }
