package com.logistic.hexagonal.domain.service;

import com.logistic.hexagonal.domain.model.Paquete;
import com.logistic.hexagonal.domain.port.GeneradorEtiquetaPort;
import com.logistic.hexagonal.domain.port.PaqueteRepositoryPort;
import java.util.UUID;

public class GenerarEtiquetaUseCase {

    private final PaqueteRepositoryPort repositoryPort;
    private final GeneradorEtiquetaPort generadorEtiquetaPort;

    public GenerarEtiquetaUseCase(PaqueteRepositoryPort repositoryPort, GeneradorEtiquetaPort generadorEtiquetaPort) {
        this.repositoryPort = repositoryPort;
        this.generadorEtiquetaPort = generadorEtiquetaPort;
    }

    public byte[] ejecutar(UUID id) {
        Paquete paquete = repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el paquete con ID: " + id));

        return generadorEtiquetaPort.generarEtiquetaPdf(paquete);
    }
}