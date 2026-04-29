package com.logistic.hexagonal.domain.model;



import java.util.UUID;



public class Paquete {
    private final UUID id;
    private final String trackingId;
    private final String categoria;
    private final Double peso;
    private EstadoPaquete estado;


    public Paquete(String trackingId, String categoria, Double peso) {
        this.id = UUID.randomUUID();
        this.trackingId = trackingId;
        this.categoria = categoria;
        this.peso = peso;
        this.estado = EstadoPaquete.EN_ALMACEN;
    }

    public Paquete(UUID id, String trackingId, String categoria, Double peso, EstadoPaquete estado) {
        this.id = id;
        this.trackingId = trackingId;
        this.categoria = categoria;
        this.peso = peso;
        this.estado = estado;
    }


    public void asignarARuta() {
        if (this.estado != EstadoPaquete.EN_ALMACEN && this.estado != EstadoPaquete.INTENTO_FALLIDO) {
            throw new IllegalStateException("Solo se puede asignar a una ruta un paquete que esté en el almacén.");
        }
        this.estado = EstadoPaquete.ASIGNADO_A_RUTA;
    }

    public void iniciarAproximacion() {
        if (this.estado != EstadoPaquete.ASIGNADO_A_RUTA) {
            throw new IllegalStateException("El paquete debe estar asignado a una ruta antes de estar en camino.");
        }
        this.estado = EstadoPaquete.EN_CAMINO;
    }

    public void registrarEntregaExitosa() {
        if (this.estado != EstadoPaquete.EN_CAMINO) {
            throw new IllegalStateException("No se puede entregar un paquete que no está en camino.");
        }
        this.estado = EstadoPaquete.ENTREGADO;
    }

    public void registrarIntentoFallido() {
        if (this.estado != EstadoPaquete.EN_CAMINO) {
            throw new IllegalStateException("El intento fallido solo se puede marcar si el paquete estaba en camino.");
        }
        this.estado = EstadoPaquete.INTENTO_FALLIDO;
    }

    public UUID getId() {return id;}

    public String getTrackingId() {return trackingId;}

    public String getCategoria() {return categoria;}

    public double getPeso() { return peso;}

    public EstadoPaquete getEstado() {return estado;}
}