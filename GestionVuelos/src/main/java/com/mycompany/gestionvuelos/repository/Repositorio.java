package com.mycompany.gestionvuelos.repository;

import java.util.ArrayList;
import java.util.List;

public class Repositorio<T> {
    private List<T> elementos = new ArrayList<>();

    public void agregar(T elemento) {
        elementos.add(elemento);
    }

    public List<T> obtenerTodos() {
        return new ArrayList<>(elementos);
    }

    public boolean existe(T elemento) {
        return elementos.contains(elemento);
    }

    public int size() {
        return elementos.size();
    }

    public void limpiar() {
        elementos.clear();
    }
}