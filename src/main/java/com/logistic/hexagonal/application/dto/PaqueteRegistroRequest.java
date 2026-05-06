package com.logistic.hexagonal.application.dto;



import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;



import java.util.UUID;

@Getter
@Setter
public class PaqueteRegistroRequest {


    @NotBlank(message = "El tracking ID no puede estar vacio")
    private String trackingId;

    @NotBlank(message = "La categoria es obligatoria asignarla")
    private String categoria;

    @NotNull(message = "El peso no puede ser nulo")
    @Positive(message = "El peso del paquete debe ser mayor que cero")
    private Double peso;
}
