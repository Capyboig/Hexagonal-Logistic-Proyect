package com.logistic.hexagonal.application.rest;


import com.logistic.hexagonal.application.dto.PaqueteRegistroRequest;
import com.logistic.hexagonal.domain.service.RegistrarPaqueteUseCase;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paquetes")
public class PaqueteController {

    private final RegistrarPaqueteUseCase registrarPaqueteUseCase;


    public PaqueteController(RegistrarPaqueteUseCase registrarPaqueteUseCase) {
        this.registrarPaqueteUseCase = registrarPaqueteUseCase;
    }


    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody PaqueteRegistroRequest request) {
        registrarPaqueteUseCase.ejecutar(request);
        return ResponseEntity.ok("Paquete registrado");
    }
}
