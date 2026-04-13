package br.com.fiap.restaurant_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "TB_OWNER")
public class RestaurantOwner extends User {

    private String restaurantName;
    private String cnpj;

}
