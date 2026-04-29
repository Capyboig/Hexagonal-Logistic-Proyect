package com.logistic.hexagonal.infrastructure.config;


import com.logistic.hexagonal.domain.port.PaqueteRepositoryPort;
import com.logistic.hexagonal.domain.service.RegistrarPaqueteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RegistrarPaqueteUseCase registrarPaqueteUseCase(PaqueteRepositoryPort repositoryPort) {
        return new RegistrarPaqueteUseCase(repositoryPort);
    }
}
