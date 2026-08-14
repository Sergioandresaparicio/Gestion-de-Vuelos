# Sistema de Gestión de Vuelos - Taller 2

Este proyecto es una aplicación de consola desarrollada en Java que implementa un sistema integral para la gestión de vuelos, aeropuertos, pasajeros y pilotos. Fue diseñado aplicando principios clave de la Programación Orientada a Objetos (POO), arquitectura por capas, clases genéricas, excepciones personalizadas y el uso de las APIs de colecciones y streams de Java.

---

## Características Principales

- **Gestión de Aeropuertos:** Registro y búsqueda de aeropuertos por su código IATA/identificador, incluyendo nombre, ciudad y país.
- **Gestión de Pasajeros y Pilotos:** Registro y representación polimórfica de personas en el sistema.
- **Gestión de Vuelos:** Creación de vuelos especificando origen, destino y capacidad máxima.
- **Control de Reservas y Asientos:** Asignación de pasajeros a vuelos verificando disponibilidad. Dispara excepciones cuando el vuelo alcanza su capacidad total.
- **Estados de Vuelo:** Transiciones de estado gestionadas mediante enumeraciones (PROGRAMADO, ABORDANDO, EN_VUELO, FINALIZADO, CANCELADO).
- **Consultas Avanzadas:**
  - Búsqueda de vuelos por destino o ruta (origen-destino).
  - Consulta de destinos únicos disponibles mediante `Set`.
  - Agrupación y conteo de vuelos por ciudad destino mediante `Map`.
- **Mapeo Polimórfico:** Visualización de todas las personas registradas (pasajeros y pilotos) aprovechando el polimorfismo.
- **Estadísticas del Sistema:** Métricas detalladas sobre estados de vuelos, porcentaje de ocupación y vuelo con mayor demanda.

---

## Estructura del Proyecto

El código fuente sigue una arquitectura organizada en capas dentro del paquete `com.mycompany.gestionvuelos`:

```text
GestionVuelos/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── mycompany/
                    └── gestionvuelos/
                        ├── GestionVuelos.java (Clase principal / Main)
                        ├── model/
                        │   ├── Aeropuerto.java
                        │   ├── EstadoVuelo.java (Enum)
                        │   ├── Pasajero.java (Hereda de Persona)
                        │   ├── Persona.java (Clase abstracta)
                        │   ├── Piloto.java (Hereda de Persona)
                        │   ├── Vuelo.java
                        │   └── VueloLlenoException.java (Excepción personalizada)
                        ├── repository/
                        │   └── Repositorio.java (Clase genérica)
                        ├── service/
                        │   └── SistemaVuelos.java (Lógica de negocio)
                        └── util/
                            └── Menu.java (Interfaz de consola)
```

---

## Conceptos de Programación Aplicados

1. **Abstracción y Herencia:**
   - La clase abstracta `Persona` define la estructura base (`documento`, `nombre`) y el método abstracto `obtenerDescripcion()`.
   - Las clases `Pasajero` y `Piloto` extienden de `Persona` agregando atributos específicos (edad, licencia, horas de vuelo).

2. **Polimorfismo:**
   - Implementación sobreescrita de `obtenerDescripcion()` en subclases.
   - Procesamiento unificado de listas de tipo `Persona`.

3. **Genéricos (`Generics`):**
   - La clase `Repositorio<T>` encapsula la gestión de colecciones en memoria de manera reutilizable para cualquier tipo de entidad (`Aeropuerto`, `Pasajero`, `Vuelo`).

4. **Colecciones (`java.util`):**
   - `List` y `ArrayList`: Para listas ordenadas de entidades y pasajeros por vuelo.
   - `Set` y `HashSet`: Para garantizar la unicidad de los destinos disponibles sin duplicados.
   - `Map` y `HashMap`: Para agrupar y contar la cantidad de vuelos asociados a cada ciudad de destino.

5. **Manejo de Excepciones:**
   - Excepción personalizada `VueloLlenoException` lanzada al intentar reservar en un vuelo sin cupo disponible.
   - Validaciones con `IllegalArgumentException` para prevenir duplicados o datos inconsistentes.

---

## Requisitos del Sistema

- **Java Development Kit (JDK):** Versión 17 o superior (compatible con JDK 21 / 26).
- **Apache Maven:** Versión 3.6 o superior.

---

## Compilación y Ejecución

### Opción 1: Mediante Maven desde la línea de comandos

1. Navegar hasta la carpeta del proyecto donde se encuentra el archivo `pom.xml`:
   ```bash
   cd GestionVuelos
   ```

2. Compilar el proyecto:
   ```bash
   mvn clean compile
   ```

3. Ejecutar la aplicación:
   ```bash
   mvn exec:java
   ```

### Opción 2: Empaquetado ejecutable

1. Generar el archivo JAR:
   ```bash
   mvn clean package
   ```

2. Ejecutar el JAR generado (ubicado en la carpeta `target/`):
   ```bash
   java -jar target/GestionVuelos-1.0-SNAPSHOT.jar
   ```

---

## Menú de Opciones de la Aplicación

Al iniciar la aplicación, se presenta una interfaz interactiva por consola con las siguientes opciones:

1. **Registrar aeropuerto:** Añadir un nuevo aeropuerto (código, nombre, ciudad, país).
2. **Registrar pasajero:** Registrar un nuevo pasajero (documento, nombre, edad).
3. **Registrar vuelo:** Crear un vuelo asignando origen, destino y capacidad.
4. **Listar vuelos:** Mostrar el detalle de todos los vuelos registrados.
5. **Buscar vuelos por destino:** Filtrar vuelos coincidiendo con una ciudad de destino.
6. **Reservar vuelo:** Asignar un pasajero a un vuelo existente.
7. **Ver pasajeros de un vuelo:** Consultar la lista de pasajeros asociados a un código de vuelo.
8. **Cambiar estado de vuelo:** Modificar la fase operativa del vuelo (PROGRAMADO, ABORDANDO, etc.).
9. **Ver estadísticas:** Mostrar métricas globales, ocupación máxima y estado de vuelos.
10. **Ver destinos disponibles (Set):** Consultar el conjunto de ciudades destino sin repetición.
11. **Ver vuelos por destino (Map):** Consultar el mapa con la cantidad de vuelos por cada ciudad.
12. **Consultar rutas disponibles:** Consultar vuelos filtrando por ciudad de origen y ciudad de destino.
13. **Ver personas (Polimorfismo):** Listar todas las personas registradas ejecutando su descripción polimórfica.
17. **Salir:** Finalizar la ejecución del programa.
