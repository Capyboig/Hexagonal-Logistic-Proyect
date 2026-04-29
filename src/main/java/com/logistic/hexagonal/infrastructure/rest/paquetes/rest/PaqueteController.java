package com.logistic.hexagonal.infrastructure.rest.paquetes.rest;


import com.logistic.hexagonal.application.dto.PaqueteRegistroRequest;
import com.logistic.hexagonal.application.service.ExcelLectorAdapter;
import com.logistic.hexagonal.domain.model.EstadoPaquete;
import com.logistic.hexagonal.domain.model.Paquete;
import com.logistic.hexagonal.domain.port.GeneradorEtiquetaPort;
import com.logistic.hexagonal.domain.service.RegistrarPaqueteUseCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/paquetes")
public class PaqueteController {
    private final GeneradorEtiquetaPort generadorEtiquetaPort;
    private final RegistrarPaqueteUseCase registrarPaqueteUseCase;
    private final ExcelLectorAdapter excelLectorAdapter;

    public PaqueteController(GeneradorEtiquetaPort generadorEtiquetaPort, RegistrarPaqueteUseCase registrarPaqueteUseCase, ExcelLectorAdapter excelLectorAdapter) {
        this.registrarPaqueteUseCase = registrarPaqueteUseCase;
        this.excelLectorAdapter = excelLectorAdapter;
        this.generadorEtiquetaPort = generadorEtiquetaPort;
    }




    public record ActualizarEstadoRequest (String nuevoEstado) {}

    @PutMapping("/{id}/estado")
    public ResponseEntity<String> actualizarEstado(
            @PathVariable String id,
            @RequestBody ActualizarEstadoRequest request
    ) {
        System.out.printf("Exito, el paquete [" + id + "] ha cambiado a -> " + request.nuevoEstado());


        return ResponseEntity.ok("Estado del paquete " + id + " actualizado a: " + request.nuevoEstado());
    }




    @GetMapping("/{id}/etiqueta")
    public ResponseEntity<byte[]> obtenerEtiqueta(@PathVariable UUID id) {

        Paquete paqueteDePrueba = new Paquete(
                id,
                "Cliente Demo S.L.",
                "Av. Falsa 123, Madrid",
                12.5,
                EstadoPaquete.EN_ALMACEN
        );

        byte[] pdfBytes = generadorEtiquetaPort.generarEtiquetaPdf(paqueteDePrueba);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "etiqueta-" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
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
