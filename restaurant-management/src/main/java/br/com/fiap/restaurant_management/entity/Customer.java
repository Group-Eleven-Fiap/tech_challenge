package br.com.fiap.restaurant_management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "TB_CUSTOMER")
public class Customer extends User {}
