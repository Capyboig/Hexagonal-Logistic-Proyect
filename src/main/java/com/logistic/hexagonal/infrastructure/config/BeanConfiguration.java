package com.logistic.hexagonal.infrastructure.config;


import com.logistic.hexagonal.domain.port.GeneradorEtiquetaPort;
import com.logistic.hexagonal.domain.port.PaqueteEventPublisherPort;
import com.logistic.hexagonal.domain.port.PaqueteRepositoryPort;
import com.logistic.hexagonal.domain.service.ActualizarEstadoPaqueteUseCase;
import com.logistic.hexagonal.domain.service.GenerarEtiquetaUseCase;
import com.logistic.hexagonal.domain.service.RegistrarPaqueteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RegistrarPaqueteUseCase registrarPaqueteUseCase(PaqueteRepositoryPort repositoryPort) {
        return new RegistrarPaqueteUseCase(repositoryPort);
    }

    @Bean
    public GenerarEtiquetaUseCase generarEtiquetaUseCase(
            PaqueteRepositoryPort paqueteRepositoryPort,
            GeneradorEtiquetaPort generadorEtiquetaPort) {
        return new GenerarEtiquetaUseCase(paqueteRepositoryPort, generadorEtiquetaPort);
    }

    @Bean
    public ActualizarEstadoPaqueteUseCase actualizarEstadoPaqueteUseCase(
            PaqueteRepositoryPort repositoryPort,
            PaqueteEventPublisherPort eventPublisherPort
    ) {
        return new ActualizarEstadoPaqueteUseCase(repositoryPort, eventPublisherPort);
    }

}
