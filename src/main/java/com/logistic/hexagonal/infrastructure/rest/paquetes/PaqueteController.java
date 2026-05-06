package com.logistic.hexagonal.infrastructure.rest.paquetes;

import com.logistic.hexagonal.application.dto.PaqueteRegistroRequest;
import com.logistic.hexagonal.domain.model.EstadoPaquete;
import com.logistic.hexagonal.domain.service.ActualizarEstadoPaqueteUseCase;
import com.logistic.hexagonal.domain.service.GenerarEtiquetaUseCase;
import com.logistic.hexagonal.domain.service.RegistrarPaqueteUseCase;
import com.logistic.hexagonal.infrastructure.adapters.ingesta.LectorDeArchivosEstrategy;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/paquetes")
public class PaqueteController {

    private final LectorDeArchivosEstrategy lectorDeArchivos;
    private final RegistrarPaqueteUseCase registrarPaqueteUseCase;
    private final ActualizarEstadoPaqueteUseCase actualizarEstadoPaqueteUseCase;
    private final GenerarEtiquetaUseCase generarEtiquetaUseCase; // ¡Faltaba declarar esta línea!

    public PaqueteController(ActualizarEstadoPaqueteUseCase actualizarEstadoPaqueteUseCase,
                             GenerarEtiquetaUseCase generarEtiquetaUseCase,
                             RegistrarPaqueteUseCase registrarPaqueteUseCase,
                             LectorDeArchivosEstrategy lectorDeArchivos) {
        this.registrarPaqueteUseCase = registrarPaqueteUseCase;
        this.lectorDeArchivos = lectorDeArchivos;
        this.generarEtiquetaUseCase = generarEtiquetaUseCase;
        this.actualizarEstadoPaqueteUseCase = actualizarEstadoPaqueteUseCase;
    }

    public record ActualizarEstadoRequest (String nuevoEstado) {}

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasAuthority('GERENTE')")
    public ResponseEntity<String> actualizarEstado(
            @PathVariable UUID id,
            @RequestBody Map<String, String> body
    ) {
        EstadoPaquete estadoEnum = EstadoPaquete.valueOf(body.get("nuevoEstado").toUpperCase());
        actualizarEstadoPaqueteUseCase.ejecutar(id, estadoEnum);
        return ResponseEntity.ok("Estado actualizado");
    }

    @GetMapping("/{id}/etiqueta")
    public ResponseEntity<byte[]> obtenerEtiqueta(@PathVariable UUID id) {

        byte[] pdfBytes = generarEtiquetaUseCase.ejecutar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "etiqueta-" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('GERENTE')")
    public ResponseEntity<String> registrar(@Valid @RequestBody PaqueteRegistroRequest request) {
        registrarPaqueteUseCase.ejecutar(
                request.getTrackingId(),
                request.getCategoria(),
                request.getPeso()
        );
        return ResponseEntity.ok("Paquete registrado");
    }

    @PostMapping(value = "/ingesta", consumes = "multipart/form-data")
    @PreAuthorize("hasAuthority('GERENTE')")
    public ResponseEntity<String> ingestaMasiva(@RequestParam("archivo")MultipartFile archivo) {
        if (archivo.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo esta vacio");
        }

        List<PaqueteRegistroRequest> peticiones = lectorDeArchivos.extraer(archivo);

        int paquetesRegistrados = 0;
        for (PaqueteRegistroRequest peticion : peticiones) {
            try {
                registrarPaqueteUseCase.ejecutar(
                        peticion.getTrackingId(),
                        peticion.getCategoria(),
                        peticion.getPeso()
                );
                paquetesRegistrados++;
            } catch (Exception e) {
                System.err.println("Error procesando paquete " + peticion.getTrackingId());
            }
        }
        return ResponseEntity.ok("Ingesta Completada. Paquetes registrados: " + paquetesRegistrados);
    }
}