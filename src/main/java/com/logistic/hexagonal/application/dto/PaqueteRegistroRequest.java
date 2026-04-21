package com.logistic.hexagonal.application.dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaqueteRegistroRequest {
    private String trackingId;
    private String categoria;
    private Double peso;
}
