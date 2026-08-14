package com.mycompany.gestionvuelos.model;

import java.util.ArrayList;
import java.util.List;

public class Vuelo {
    private String codigo;
    private Aeropuerto origen;
    private Aeropuerto destino;
    private int capacidad;
    private EstadoVuelo estado;
    private List<Pasajero> pasajeros;

    public Vuelo(String codigo, Aeropuerto origen, Aeropuerto destino, int capacidad) {
        this.codigo = codigo;
        this.origen = origen;
        this.destino = destino;
        this.capacidad = capacidad;
        this.estado = EstadoVuelo.PROGRAMADO;
        this.pasajeros = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public void setOrigen(Aeropuerto origen) {
        this.origen = origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public void setDestino(Aeropuerto destino) {
        this.destino = destino;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoVuelo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVuelo estado) {
        this.estado = estado;
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public void agregarPasajero(Pasajero pasajero) {
        pasajeros.add(pasajero);
    }

    public boolean tieneCupo() {
        return pasajeros.size() < capacidad;
    }

    public int getPasajerosCount() {
        return pasajeros.size();
    }

    public double getPorcentajeOcupacion() {
        if (capacidad == 0) return 0;
        return (pasajeros.size() * 100.0) / capacidad;
    }

    @Override
    public String toString() {
        return "Vuelo: " + codigo + "\n" +
               "Origen: " + origen.getCiudad() + "\n" +
               "Destino: " + destino.getCiudad() + "\n" +
               "Capacidad: " + capacidad + "\n" +
               "Pasajeros: " + pasajeros.size() + "\n" +
               "Estado: " + estado;
    }
}