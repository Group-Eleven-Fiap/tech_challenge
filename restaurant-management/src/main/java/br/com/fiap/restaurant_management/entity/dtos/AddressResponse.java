package br.com.fiap.restaurant_management.entity.dtos;

public record AddressResponse(
    String street,
    String number,
    String city,
    String state,
    String zipCode
) {}