package com.logistic.hexagonal.infrastructure.adapters.messaging;

import com.logistic.hexagonal.domain.event.PaqueteEntregadoEvent;
import com.logistic.hexagonal.domain.port.PaqueteEventPublisherPort;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisherAdapter implements PaqueteEventPublisherPort {

    @Override
    public void publicarEntrega(PaqueteEntregadoEvent evento) {
        System.out.println("PREPARANDO KAFKA: Evento recibido y listo para enviar -> " + evento);
    }
}