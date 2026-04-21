package com.logistic.hexagonal.domain.model;


import java.util.UUID;

public class Cliente {
    private final UUID id;
    private final String dni;
    private final String telefonoWhatsapp;
    private final String direccionEntrega;
    private final String coordenadasGPS;

    public Cliente(UUID id, String dni, String telefonoWhatsapp, String direccionEntrega, String coordenadasGPS) {
        this.id = id;
        this.dni = dni;
        this.telefonoWhatsapp = telefonoWhatsapp;
        this.direccionEntrega = direccionEntrega;
        this.coordenadasGPS = coordenadasGPS;
    }


    public UUID getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getTelefonoWhatsapp() {
        return telefonoWhatsapp;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public String getCoordenadasGPS() {
        return coordenadasGPS;
    }
}
