package com.logistic.hexagonal.domain.service;

import com.logistic.hexagonal.application.dto.PaqueteRegistroRequest;
import com.logistic.hexagonal.domain.model.Paquete;
import com.logistic.hexagonal.domain.port.PaqueteRepositoryPort;

public class RegistrarPaqueteUseCase {

    private final PaqueteRepositoryPort repositoryPort;


    public RegistrarPaqueteUseCase(PaqueteRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }


    public void ejecutar(PaqueteRegistroRequest request) {
        if (request.getTrackingId() == null || request.getTrackingId().trim().isEmpty()) {
            throw new IllegalArgumentException("El paquete debe tener un código de seguimiento válido.");
        }

        if (request.getPeso() <= 0) {
            throw new IllegalArgumentException("El peso del paquete no puede ser negativo o cero.");
        }


        if (repositoryPort.buscarPorTrackingId(request.getTrackingId()).isPresent()) {
            throw new IllegalStateException("Ya existe un paquete registrado con el tracking ID: " + request.getTrackingId());
        }
        Paquete nuevoPaquete = new Paquete(
                request.getTrackingId(),
                request.getCategoria(),
                request.getPeso()
        );

        repositoryPort.guardar(nuevoPaquete);
    }


}
