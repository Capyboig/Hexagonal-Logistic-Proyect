package com.logistic.hexagonal.infrastructure.persistence.adapter;


import com.logistic.hexagonal.domain.model.Paquete;
import com.logistic.hexagonal.domain.port.PaqueteRepositoryPort;
import com.logistic.hexagonal.infrastructure.persistence.entity.PaqueteEntity;
import com.logistic.hexagonal.infrastructure.persistence.repository.SpringDataPaqueteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaPaqueteRepositoryAdapter implements PaqueteRepositoryPort {

    private final SpringDataPaqueteRepository repository;


    public JpaPaqueteRepositoryAdapter(SpringDataPaqueteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void guardar(Paquete paquete) {
        PaqueteEntity entity = new PaqueteEntity();

        entity.setId(paquete.getId());
        entity.setTrackingId(paquete.getTrackingId());
        entity.setCategoria(paquete.getCategoria());
        entity.setPeso(paquete.getPeso());
        entity.setEstado(paquete.getEstado());
    }



    @Override
    public Optional<Paquete> buscarPorTrackingId(String trackingId) {
        return repository.findByTrackingId(trackingId)
                .map(entity -> new Paquete(
                        entity.getId(),
                        entity.getTrackingId(),
                        entity.getCategoria(),
                        entity.getPeso(),
                        entity.getEstado()
                ));
    }
}
