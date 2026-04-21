package com.logistic.hexagonal.domain.model;

import lombok.Data;


public class Repartidor {
    private String idTrabajador;
    private final String nombre;
    private final String telefono;
    private final String matriculaCamion;



    public Repartidor(String idTrabajador, String nombre, String telefono, String matriculaCamion) {
        this.idTrabajador = idTrabajador;
        this.nombre = nombre;
        this.telefono = telefono;
        this.matriculaCamion = matriculaCamion;
    }


    public String getIdTrabajador() {
        return idTrabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getMatriculaCamion() {
        return matriculaCamion;
    }
}

