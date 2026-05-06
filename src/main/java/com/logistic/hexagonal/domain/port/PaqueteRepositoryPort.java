package com.logistic.hexagonal.domain.port;

import com.logistic.hexagonal.domain.model.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaqueteRepositoryPort {
    void guardar(Paquete paquete);
    Optional<Paquete> buscarPorId(UUID id);
    Optional<Paquete> buscarPorTrackingId(String trackingId);
}
