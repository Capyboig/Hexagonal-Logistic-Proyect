package com.logistic.hexagonal.domain.port;


import com.logistic.hexagonal.domain.model.Paquete;

public interface GeneradorEtiquetaPort {
    byte[] generarEtiquetaPdf(Paquete paquete);
}
