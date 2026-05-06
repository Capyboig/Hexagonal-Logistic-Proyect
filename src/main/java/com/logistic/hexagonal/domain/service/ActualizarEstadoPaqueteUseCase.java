package com.logistic.hexagonal.domain.service;

import com.logistic.hexagonal.domain.event.PaqueteEntregadoEvent;
import com.logistic.hexagonal.domain.model.EstadoPaquete;
import com.logistic.hexagonal.domain.model.Paquete;
import com.logistic.hexagonal.domain.port.PaqueteEventPublisherPort;
import com.logistic.hexagonal.domain.port.PaqueteRepositoryPort;

import java.util.UUID;


public class ActualizarEstadoPaqueteUseCase {


    private final PaqueteRepositoryPort repositoryPort;
    private final PaqueteEventPublisherPort eventPublisherPort;


    public ActualizarEstadoPaqueteUseCase(PaqueteRepositoryPort repositoryPort, PaqueteEventPublisherPort eventPublisherPort) {
        this.repositoryPort = repositoryPort;
        this.eventPublisherPort = eventPublisherPort;
    }

    public void ejecutar(UUID id, EstadoPaquete nuevoEstado) {

        Paquete paquete = repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el paquete interno"));

        switch (nuevoEstado) {
            case ASIGNADO_A_RUTA -> paquete.asignarARuta();
            case ENTREGADO -> paquete.registrarEntregaExitosa();
            case INTENTO_FALLIDO -> paquete.registrarIntentoFallido();
            default -> throw new IllegalStateException("Transición a " + nuevoEstado + " no permitida.");
        }

        repositoryPort.guardar(paquete);

        if(nuevoEstado == EstadoPaquete.ENTREGADO) {
            PaqueteEntregadoEvent evento = new PaqueteEntregadoEvent(paquete.getTrackingId());
            eventPublisherPort.publicarEntrega(evento);
        }
    }
}
