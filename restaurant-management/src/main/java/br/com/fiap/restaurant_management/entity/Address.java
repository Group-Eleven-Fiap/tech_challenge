package br.com.fiap.restaurant_management.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    private String street;   // Rua
    private String number;   // Número
    private String city;     // Cidade
    private String state;    // Estado
    private String zipCode; // CEP
}
