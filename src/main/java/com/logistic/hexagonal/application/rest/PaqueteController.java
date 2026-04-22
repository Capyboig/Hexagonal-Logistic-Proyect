package com.logistic.hexagonal.application.rest;


import com.logistic.hexagonal.application.dto.PaqueteRegistroRequest;
import com.logistic.hexagonal.application.service.ExcelLectorAdapter;
import com.logistic.hexagonal.domain.service.RegistrarPaqueteUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/paquetes")
public class PaqueteController {

    private final RegistrarPaqueteUseCase registrarPaqueteUseCase;
    private final ExcelLectorAdapter excelLectorAdapter;

    public PaqueteController(RegistrarPaqueteUseCase registrarPaqueteUseCase, ExcelLectorAdapter excelLectorAdapter) {
        this.registrarPaqueteUseCase = registrarPaqueteUseCase;
        this.excelLectorAdapter = excelLectorAdapter;
    }


    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody PaqueteRegistroRequest request) {
        registrarPaqueteUseCase.ejecutar(request);
        return ResponseEntity.ok("Paquete registrado");
    }


    @PostMapping(value = "/ingesta", consumes = "multipart/form-data")
    public ResponseEntity<String> ingestaMasiva(@RequestParam("archivo")MultipartFile archivo) {
        if (archivo.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo esta vacio");
        }

        List<PaqueteRegistroRequest> peticiones = excelLectorAdapter.extraer(archivo);

        int paquetesRegistrados = 0;
        for (PaqueteRegistroRequest peticion : peticiones) {
            try {
                registrarPaqueteUseCase.ejecutar(peticion);
                paquetesRegistrados++;
            } catch (Exception e) {
                System.err.println("Error procesando paquete " + peticion.getTrackingId());
            }


        }
        return ResponseEntity.ok("Ingesta Completada. Paquetes registrados: " + paquetesRegistrados);
    }
}
