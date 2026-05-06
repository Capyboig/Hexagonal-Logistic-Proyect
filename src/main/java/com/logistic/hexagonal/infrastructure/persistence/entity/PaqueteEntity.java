package com.logistic.hexagonal.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "paquetes")
@Getter
@Setter
public class PaqueteEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String trackingId;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false)
    private String estado;


}