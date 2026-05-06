package com.logistic.hexagonal.domain.port;

import com.logistic.hexagonal.domain.event.PaqueteEntregadoEvent;

public interface PaqueteEventPublisherPort {
    void publicarEntrega(PaqueteEntregadoEvent evento);
}
