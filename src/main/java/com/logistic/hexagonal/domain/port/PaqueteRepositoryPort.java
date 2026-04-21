package com.logistic.hexagonal.domain.port;

import com.logistic.hexagonal.domain.model.Paquete;

import java.util.Optional;

public interface PaqueteRepositoryPort {
    void guardar(Paquete paquete);
    Optional<Paquete> buscarPorTrackingId(String trackingId);
}
