package com.logistic.hexagonal.infrastructure.adapters.ingesta;

import com.logistic.hexagonal.application.dto.PaqueteRegistroRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class IngestaRouterService {

    private final List<LectorDeArchivosEstrategy> lectores;

    public IngestaRouterService(List<LectorDeArchivosEstrategy> lectores) {
        this.lectores = lectores;
    }

    public List<PaqueteRegistroRequest> procesarArchivo(MultipartFile archivo) {
        String nombreArchivo = archivo.getOriginalFilename();
        if (nombreArchivo == null || !nombreArchivo.contains(".")) {
            throw new IllegalArgumentException("El archivo no tiene una extensión válida.");
        }

        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);

        for (LectorDeArchivosEstrategy lector : lectores) {
            if (lector.soporta(extension)) {
                return lector.extraer(archivo); // ¡Encontrado! Que haga el trabajo.
            }
        }

        throw new IllegalArgumentException("Formato de archivo no soportado: " + extension);
    }
}