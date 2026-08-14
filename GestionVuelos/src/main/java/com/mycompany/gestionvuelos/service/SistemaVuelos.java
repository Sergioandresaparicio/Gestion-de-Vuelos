package com.mycompany.gestionvuelos.service;

import com.mycompany.gestionvuelos.model.Aeropuerto;
import com.mycompany.gestionvuelos.model.EstadoVuelo;
import com.mycompany.gestionvuelos.model.Pasajero;
import com.mycompany.gestionvuelos.model.Piloto;
import com.mycompany.gestionvuelos.model.Persona;
import com.mycompany.gestionvuelos.model.Vuelo;
import com.mycompany.gestionvuelos.model.VueloLlenoException;
import com.mycompany.gestionvuelos.repository.Repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SistemaVuelos {
    private Repositorio<Aeropuerto> aeropuertos;
    private Repositorio<Pasajero> pasajeros;
    private Repositorio<Vuelo> vuelos;
    private List<Persona> personas;
    private int totalReservas;

    public SistemaVuelos() {
        this.aeropuertos = new Repositorio<>();
        this.pasajeros = new Repositorio<>();
        this.vuelos = new Repositorio<>();
        this.personas = new ArrayList<>();
        this.totalReservas = 0;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        // Aeropuertos
        aeropuertos.agregar(new Aeropuerto("BOG", "El Dorado", "Bogotá", "Colombia"));
        aeropuertos.agregar(new Aeropuerto("MDE", "José María Córdova", "Medellín", "Colombia"));
        aeropuertos.agregar(new Aeropuerto("CLO", "Alfonso Bonilla Aragón", "Cali", "Colombia"));
        aeropuertos.agregar(new Aeropuerto("CTG", "Rafael Núñez", "Cartagena", "Colombia"));
        aeropuertos.agregar(new Aeropuerto("PSO", "Antonio Nariño", "Pasto", "Colombia"));

        // Pasajeros
        Pasajero p1 = new Pasajero("1001", "Laura Gómez", 25);
        Pasajero p2 = new Pasajero("1002", "Andrés Martínez", 32);
        Pasajero p3 = new Pasajero("1003", "Carolina Díaz", 28);
        Pasajero p4 = new Pasajero("1004", "Daniel Torres", 41);
        pasajeros.agregar(p1);
        pasajeros.agregar(p2);
        pasajeros.agregar(p3);
        pasajeros.agregar(p4);
        personas.add(p1);
        personas.add(p2);
        personas.add(p3);
        personas.add(p4);

        // Pilotos
        Piloto pil1 = new Piloto("P001", "Carlos Rodríguez", "LIC-001", 2500);
        Piloto pil2 = new Piloto("P002", "Ana Martínez", "LIC-002", 1800);
        personas.add(pil1);
        personas.add(pil2);

        // Vuelos
        Aeropuerto bog = buscarAeropuerto("BOG");
        Aeropuerto mde = buscarAeropuerto("MDE");
        Aeropuerto clo = buscarAeropuerto("CLO");
        Aeropuerto ctg = buscarAeropuerto("CTG");
        Aeropuerto pso = buscarAeropuerto("PSO");

        vuelos.agregar(new Vuelo("AV101", bog, mde, 180));
        vuelos.agregar(new Vuelo("AV202", bog, ctg, 150));
        vuelos.agregar(new Vuelo("AV303", mde, clo, 120));
        vuelos.agregar(new Vuelo("AV404", clo, bog, 100));
        vuelos.agregar(new Vuelo("AV505", bog, pso, 80));

        // Agregar algunos pasajeros a vuelos para estadísticas
        Vuelo v1 = buscarVuelo("AV101");
        Vuelo v2 = buscarVuelo("AV202");
        if (v1 != null) {
            for (int i = 0; i < 95; i++) {
                v1.agregarPasajero(new Pasajero("T" + i, "Temp " + i, 30));
            }
            totalReservas += 95;
        }
        if (v2 != null) {
            for (int i = 0; i < 150; i++) {
                v2.agregarPasajero(new Pasajero("T2" + i, "Temp2 " + i, 30));
            }
            totalReservas += 150;
            v2.setEstado(EstadoVuelo.ABORDANDO);
        }
    }

    // Aeropuertos
    public void registrarAeropuerto(String codigo, String nombre, String ciudad, String pais) {
        if (buscarAeropuerto(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un aeropuerto con ese código.");
        }
        aeropuertos.agregar(new Aeropuerto(codigo, nombre, ciudad, pais));
    }

    public Aeropuerto buscarAeropuerto(String codigo) {
        return aeropuertos.obtenerTodos().stream()
                .filter(a -> a.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Aeropuerto> getAeropuertos() {
        return aeropuertos.obtenerTodos();
    }

    // Pasajeros
    public void registrarPasajero(String documento, String nombre, int edad) {
        if (buscarPasajero(documento) != null) {
            throw new IllegalArgumentException("Ya existe un pasajero con ese documento.");
        }
        if (edad <= 0) {
            throw new IllegalArgumentException("La edad debe ser mayor que 0.");
        }
        Pasajero p = new Pasajero(documento, nombre, edad);
        pasajeros.agregar(p);
        personas.add(p);
    }

    public Pasajero buscarPasajero(String documento) {
        return pasajeros.obtenerTodos().stream()
                .filter(p -> p.getDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros.obtenerTodos();
    }

    // Vuelos
    public void registrarVuelo(String codigo, String codigoOrigen, String codigoDestino, int capacidad) {
        if (buscarVuelo(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un vuelo con ese código.");
        }
        Aeropuerto origen = buscarAeropuerto(codigoOrigen);
        Aeropuerto destino = buscarAeropuerto(codigoDestino);
        if (origen == null) {
            throw new IllegalArgumentException("El aeropuerto de origen no existe.");
        }
        if (destino == null) {
            throw new IllegalArgumentException("El aeropuerto de destino no existe.");
        }
        if (origen.getCodigo().equals(destino.getCodigo())) {
            throw new IllegalArgumentException("El origen y destino deben ser diferentes.");
        }
        vuelos.agregar(new Vuelo(codigo, origen, destino, capacidad));
    }

    public Vuelo buscarVuelo(String codigo) {
        return vuelos.obtenerTodos().stream()
                .filter(v -> v.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Vuelo> getVuelos() {
        return vuelos.obtenerTodos();
    }

    public List<Vuelo> buscarVuelosPorDestino(String ciudad) {
        String ciudadLower = ciudad.trim().toLowerCase();
        return vuelos.obtenerTodos().stream()
                .filter(v -> v.getDestino().getCiudad().toLowerCase().contains(ciudadLower))
                .collect(Collectors.toList());
    }

    public List<Vuelo> buscarVuelosPorRuta(String ciudadOrigen, String ciudadDestino) {
        String origenLower = ciudadOrigen.trim().toLowerCase();
        String destinoLower = ciudadDestino.trim().toLowerCase();
        return vuelos.obtenerTodos().stream()
                .filter(v -> v.getOrigen().getCiudad().toLowerCase().contains(origenLower)
                        && v.getDestino().getCiudad().toLowerCase().contains(destinoLower))
                .collect(Collectors.toList());
    }

    public void reservarVuelo(String documento, String codigoVuelo) throws VueloLlenoException {
        Pasajero pasajero = buscarPasajero(documento);
        if (pasajero == null) {
            throw new IllegalArgumentException("El pasajero no existe.");
        }
        Vuelo vuelo = buscarVuelo(codigoVuelo);
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no existe.");
        }
        if (vuelo.getEstado() != EstadoVuelo.PROGRAMADO) {
            throw new IllegalArgumentException("El vuelo no está en estado PROGRAMADO.");
        }
        if (!vuelo.tieneCupo()) {
            throw new VueloLlenoException("No existen asientos disponibles.");
        }
        if (vuelo.getPasajeros().contains(pasajero)) {
            throw new IllegalArgumentException("El pasajero ya está registrado en este vuelo.");
        }
        vuelo.agregarPasajero(pasajero);
        totalReservas++;
    }

    public void cambiarEstadoVuelo(String codigo, EstadoVuelo nuevoEstado) {
        Vuelo vuelo = buscarVuelo(codigo);
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no existe.");
        }
        vuelo.setEstado(nuevoEstado);
    }

    public Set<String> getCiudadesDestino() {
        Set<String> ciudades = new HashSet<>();
        for (Vuelo v : vuelos.obtenerTodos()) {
            ciudades.add(v.getDestino().getCiudad());
        }
        return ciudades;
    }

    public Map<String, Integer> getVuelosPorDestino() {
        Map<String, Integer> mapa = new HashMap<>();
        for (Vuelo v : vuelos.obtenerTodos()) {
            String ciudad = v.getDestino().getCiudad();
            mapa.put(ciudad, mapa.getOrDefault(ciudad, 0) + 1);
        }
        return mapa;
    }

    public void mostrarEstadisticas() {
        System.out.println("\n========= ESTADÍSTICAS=========");
        System.out.println("Aeropuertos registrados: " + aeropuertos.size());
        System.out.println("Vuelos registrados: " + vuelos.size());
        System.out.println("Pasajeros registrados: " + pasajeros.size());

        int programados = 0, enVuelo = 0, finalizados = 0, cancelados = 0, abordando = 0;
        Vuelo vueloMaxOcupacion = null;
        double maxOcupacion = -1;

        for (Vuelo v : vuelos.obtenerTodos()) {
            switch (v.getEstado()) {
                case PROGRAMADO -> programados++;
                case ABORDANDO -> abordando++;
                case EN_VUELO -> enVuelo++;
                case FINALIZADO -> finalizados++;
                case CANCELADO -> cancelados++;
            }
            double ocup = v.getPorcentajeOcupacion();
            if (ocup > maxOcupacion) {
                maxOcupacion = ocup;
                vueloMaxOcupacion = v;
            }
        }

        System.out.println("Vuelos programados: " + programados);
        System.out.println("Vuelos abordando: " + abordando);
        System.out.println("Vuelos en vuelo: " + enVuelo);
        System.out.println("Vuelos finalizados: " + finalizados);
        System.out.println("Vuelos cancelados: " + cancelados);
        System.out.println("Total reservas realizadas: " + totalReservas);

        if (vueloMaxOcupacion != null) {
            System.out.printf("Vuelo con mayor ocupación: %s - %s-> %s %d/ %d pasajeros (%.1f%%)%n",
                    vueloMaxOcupacion.getCodigo(),
                    vueloMaxOcupacion.getOrigen().getCiudad(),
                    vueloMaxOcupacion.getDestino().getCiudad(),
                    vueloMaxOcupacion.getPasajerosCount(),
                    vueloMaxOcupacion.getCapacidad(),
                    maxOcupacion);
        }

        System.out.print("Destinos disponibles: ");
        Set<String> destinos = getCiudadesDestino();
        for (String d : destinos) {
            System.out.print(d + " ");
        }
        System.out.println();
    }

    public void mostrarPersonas() {
        for (Persona p : personas) {
            System.out.println(p.obtenerDescripcion());
        }
    }

    public List<Persona> getPersonas() {
        return new ArrayList<>(personas);
    }
}