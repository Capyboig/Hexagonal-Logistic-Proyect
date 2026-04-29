package com.logistic.hexagonal.infrastructure.adapters.ingesta;

import com.logistic.hexagonal.application.dto.PaqueteRegistroRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LectorDeArchivosEstrategy {
    boolean soporta(String extension);
    List<PaqueteRegistroRequest> extraer(MultipartFile archivo);


}
