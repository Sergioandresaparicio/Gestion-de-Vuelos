package com.mycompany.gestionvuelos.util;

import com.mycompany.gestionvuelos.model.EstadoVuelo;
import com.mycompany.gestionvuelos.model.Vuelo;
import com.mycompany.gestionvuelos.model.VueloLlenoException;
import com.mycompany.gestionvuelos.service.SistemaVuelos;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Menu {
    private SistemaVuelos sistema;
    private Scanner scanner;

    public Menu() {
        this.sistema = new SistemaVuelos();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            procesarOpcion(opcion);
        } while (opcion != 17);
        System.out.println("¡Gracias por usar el sistema!");
    }

    private void mostrarMenu() {
        System.out.println("\n========== SISTEMA AEROPUERTO JAVA ==========");
        System.out.println("1.  Registrar aeropuerto");
        System.out.println("2.  Registrar pasajero");
        System.out.println("3.  Registrar vuelo");
        System.out.println("4.  Listar vuelos");
        System.out.println("5.  Buscar vuelos por destino");
        System.out.println("6.  Reservar vuelo");
        System.out.println("7.  Ver pasajeros de un vuelo");
        System.out.println("8.  Cambiar estado de vuelo");
        System.out.println("9.  Ver estadísticas");
        System.out.println("10. Ver destinos disponibles (Set)");
        System.out.println("11. Ver vuelos por destino (Map)");
        System.out.println("12. Consultar rutas disponibles");
        System.out.println("13. Ver personas (Polimorfismo)");
        System.out.println("17. Salir");
        System.out.println("=============================================");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> registrarAeropuerto();
            case 2 -> registrarPasajero();
            case 3 -> registrarVuelo();
            case 4 -> listarVuelos();
            case 5 -> buscarVuelosPorDestino();
            case 6 -> reservarVuelo();
            case 7 -> verPasajerosVuelo();
            case 8 -> cambiarEstadoVuelo();
            case 9 -> sistema.mostrarEstadisticas();
            case 10 -> mostrarDestinosDisponibles();
            case 11 -> mostrarVuelosPorDestino();
            case 12 -> consultarRutasDisponibles();
            case 13 -> sistema.mostrarPersonas();
            case 17 -> System.out.println("Saliendo del sistema...");
            default -> System.out.println("Opción no válida.");
        }
    }

    private void registrarAeropuerto() {
        System.out.println("\n--- REGISTRAR AEROPUERTO ---");
        String codigo = leerString("Código: ");
        if (sistema.buscarAeropuerto(codigo) != null) {
            System.out.println("Error: Ya existe un aeropuerto con ese código.");
            return;
        }
        String nombre = leerString("Nombre: ");
        String ciudad = leerString("Ciudad: ");
        String pais = leerString("País: ");
        try {
            sistema.registrarAeropuerto(codigo, nombre, ciudad, pais);
            System.out.println("Aeropuerto registrado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarPasajero() {
        System.out.println("\n--- REGISTRAR PASAJERO ---");
        String documento = leerString("Documento: ");
        if (sistema.buscarPasajero(documento) != null) {
            System.out.println("Error: Ya existe un pasajero con ese documento.");
            return;
        }
        String nombre = leerString("Nombre: ");
        int edad = leerEntero("Edad: ");
        if (edad <= 0) {
            System.out.println("Error: La edad debe ser mayor que 0.");
            return;
        }
        try {
            sistema.registrarPasajero(documento, nombre, edad);
            System.out.println("Pasajero registrado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarVuelo() {
        System.out.println("\n--- REGISTRAR VUELO ---");
        String codigo = leerString("Código del vuelo: ");
        if (sistema.buscarVuelo(codigo) != null) {
            System.out.println("Error: Ya existe un vuelo con ese código.");
            return;
        }
        String codigoOrigen = leerString("Código aeropuerto origen: ");
        String codigoDestino = leerString("Código aeropuerto destino: ");
        int capacidad = leerEntero("Capacidad máxima: ");

        try {
            sistema.registrarVuelo(codigo, codigoOrigen, codigoDestino, capacidad);
            System.out.println("Vuelo registrado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarVuelos() {
        System.out.println("\n--- LISTA DE VUELOS ---");
        List<Vuelo> vuelos = sistema.getVuelos();
        if (vuelos.isEmpty()) {
            System.out.println("No hay vuelos registrados.");
            return;
        }
        for (Vuelo v : vuelos) {
            System.out.println("---");
            System.out.println(v);
            System.out.println("---");
        }
    }

    private void buscarVuelosPorDestino() {
        System.out.println("\n--- BUSCAR VUELOS POR DESTINO ---");
        String ciudad = leerString("Ingrese destino: ");
        List<Vuelo> resultados = sistema.buscarVuelosPorDestino(ciudad);
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron vuelos para ese destino.");
            return;
        }
        System.out.println("Resultados encontrados:");
        for (Vuelo v : resultados) {
            System.out.println(v.getCodigo() + " - " + v.getOrigen().getCiudad() + " -> " + v.getDestino().getCiudad() + " - Estado: " + v.getEstado());
        }
    }

    private void reservarVuelo() {
        System.out.println("\n--- RESERVAR VUELO ---");
        String documento = leerString("Documento del pasajero: ");
        String codigoVuelo = leerString("Código del vuelo: ");
        try {
            sistema.reservarVuelo(documento, codigoVuelo);
            System.out.println("Reserva realizada correctamente.");
        } catch (VueloLlenoException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verPasajerosVuelo() {
        System.out.println("\n--- PASAJEROS DE UN VUELO ---");
        String codigo = leerString("Código del vuelo: ");
        Vuelo vuelo = sistema.buscarVuelo(codigo);
        if (vuelo == null) {
            System.out.println("El vuelo no existe.");
            return;
        }
        List<com.mycompany.gestionvuelos.model.Pasajero> pasajeros = vuelo.getPasajeros();
        if (pasajeros.isEmpty()) {
            System.out.println("No hay pasajeros en este vuelo.");
            return;
        }
        System.out.println("Pasajeros en vuelo " + codigo + ":");
        for (com.mycompany.gestionvuelos.model.Pasajero p : pasajeros) {
            System.out.println("- " + p.getDocumento() + " - " + p.getNombre() + " - " + p.getEdad() + " años");
        }
    }

    private void cambiarEstadoVuelo() {
        System.out.println("\n--- CAMBIAR ESTADO DE VUELO ---");
        String codigo = leerString("Código del vuelo: ");
        Vuelo vuelo = sistema.buscarVuelo(codigo);
        if (vuelo == null) {
            System.out.println("El vuelo no existe.");
            return;
        }
        System.out.println("Estados disponibles:");
        for (EstadoVuelo e : EstadoVuelo.values()) {
            System.out.println("- " + e);
        }
        String estadoStr = leerString("Nuevo estado: ").toUpperCase();
        try {
            EstadoVuelo nuevoEstado = EstadoVuelo.valueOf(estadoStr);
            sistema.cambiarEstadoVuelo(codigo, nuevoEstado);
            System.out.println("Estado actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Estado no válido.");
        }
    }

    private void mostrarDestinosDisponibles() {
        System.out.println("\n=== DESTINOS DISPONIBLES ===");
        Set<String> ciudades = sistema.getCiudadesDestino();
        for (String c : ciudades) {
            System.out.println(c);
        }
    }

    private void mostrarVuelosPorDestino() {
        System.out.println("\n=== VUELOS POR DESTINO ===");
        Map<String, Integer> mapa = sistema.getVuelosPorDestino();
        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void consultarRutasDisponibles() {
        System.out.println("\n--- CONSULTAR RUTAS DISPONIBLES ---");
        String origen = leerString("Ciudad origen: ");
        String destino = leerString("Ciudad destino: ");
        List<Vuelo> vuelos = sistema.buscarVuelosPorRuta(origen, destino);
        if (vuelos.isEmpty()) {
            System.out.println("No hay vuelos para esa ruta.");
            return;
        }
        System.out.println("Vuelos " + origen + " -> " + destino);
        for (Vuelo v : vuelos) {
            int disponibles = v.getCapacidad() - v.getPasajerosCount();
            System.out.println(v.getCodigo() + " - Estado: " + v.getEstado() + " - Disponibles: " + disponibles + " asientos");
            System.out.printf("Ocupación: %d/%d Porcentaje: %.1f%%%n",
                    v.getPasajerosCount(), v.getCapacidad(), v.getPorcentajeOcupacion());
        }
    }

    private String leerString(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
            }
        }
    }
}