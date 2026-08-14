package com.mycompany.gestionvuelos.model;

public class VueloLlenoException extends Exception {
    public VueloLlenoException(String mensaje) {
        super(mensaje);
    }
}