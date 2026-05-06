package com.logistic.hexagonal.domain.service;

import com.logistic.hexagonal.domain.model.EstadoPaquete;
import com.logistic.hexagonal.domain.model.Paquete;
import com.logistic.hexagonal.domain.port.PaqueteRepositoryPort;

import java.util.UUID;

public class RegistrarPaqueteUseCase {

    private final PaqueteRepositoryPort repositoryPort;

    public RegistrarPaqueteUseCase(PaqueteRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public void ejecutar(String trackingId, String categoria, Double peso) {


        if (repositoryPort.buscarPorTrackingId(trackingId).isPresent()) {
            throw new IllegalStateException("Ya existe el paquete que se ha intentado registrar");
        }


        Paquete paquete = new Paquete(
                UUID.randomUUID(),
                trackingId,
                categoria,
                peso,
                EstadoPaquete.EN_ALMACEN
        );

        repositoryPort.guardar(paquete);
    }
}