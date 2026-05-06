package com.logistic.hexagonal.domain.event;

import java.time.LocalDateTime;

public record PaqueteEntregadoEvent(
        String trackingId,
        LocalDateTime fechaEntrega
) {
    public PaqueteEntregadoEvent(String trackingId) {
        this(trackingId, LocalDateTime.now());
    }

}
