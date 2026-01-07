package ecotrack;

import ecotrack.controller.*;
import ecotrack.model.*;
import ecotrack.model.comparadores.*;
import ecotrack.model.estructuras.*;
import ecotrack.util.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Versión de consola del sistema EcoTrack para probar todas las funcionalidades
 * sin necesidad de JavaFX.
 */
public class EcoTrackConsola {
    private Scanner scanner;
    
    // Modelos y estructuras de datos
    private ListaCircular<Residuo> listaResiduos;
    private ColaPrioridad<Vehiculo> colaVehiculos;
    private Pila<Residuo> pilaReciclaje;
    private MapaEstadisticas<String, Double> estadisticas;
    
    // Controladores
    private GestorResiduos gestorResiduos;
    private GestorRutas gestorRutas;
    private GestorReciclaje gestorReciclaje;
    private GestorDatos gestorDatos;

    public EcoTrackConsola() {
        scanner = new Scanner(System.in);
        inicializarEstructuras();
        cargarDatosIniciales();
    }

    /**
     * Inicializa las estructuras de datos y controladores
     */
    private void inicializarEstructuras() {
        listaResiduos = new ListaCircular<>();
        pilaReciclaje = new Pila<>();
        estadisticas = new MapaEstadisticas<>();
        
        // Crear comparador para vehículos (por prioridad de zona)
        Comparator<Vehiculo> comparadorVehiculos = (v1, v2) -> {
            if (v1.getZonaAsignada() == null && v2.getZonaAsignada() == null) return 0;
            if (v1.getZonaAsignada() == null) return 1;
            if (v2.getZonaAsignada() == null) return -1;
            return Integer.compare(v2.getZonaAsignada().getPrioridad(), 
                                 v1.getZonaAsignada().getPrioridad());
        };
        
        colaVehiculos = new ColaPrioridad<>(comparadorVehiculos);
        
        gestorResiduos = new GestorResiduos(listaResiduos);
        gestorRutas = new GestorRutas(colaVehiculos);
        gestorReciclaje = new GestorReciclaje(pilaReciclaje);
        gestorDatos = new GestorDatos();
    }

    /**
     * Carga datos iniciales de ejemplo
     */
    private void cargarDatosIniciales() {
        // Crear algunas zonas de ejemplo
        Zona zona1 = new Zona("Z001", "Centro");
        zona1.setPPendiente(100.0);
        zona1.setPRecolectado(50.0);
        zona1.calcularUtilidad();
        
        Zona zona2 = new Zona("Z002", "Norte");
        zona2.setPPendiente(200.0);
        zona2.setPRecolectado(30.0);
        zona2.calcularUtilidad();
        
        Zona zona3 = new Zona("Z003", "Sur");
        zona3.setPPendiente(150.0);
        zona3.setPRecolectado(80.0);
        zona3.calcularUtilidad();
        
        // Crear algunos residuos de ejemplo
        Residuo r1 = new Residuo("R001", "Botellas plásticas", "plástico", 25.5, 
                                 LocalDate.now(), zona1, 5);
        Residuo r2 = new Residuo("R002", "Papel usado", "papel", 15.0, 
                                 LocalDate.now(), zona2, 3);
        Residuo r3 = new Residuo("R003", "Vidrio roto", "vidrio", 30.0, 
                                 LocalDate.now(), zona3, 7);
        Residuo r4 = new Residuo("R004", "Latas de aluminio", "metal", 12.5, 
                                 LocalDate.now(), zona1, 4);
        
        gestorResiduos.registrarResiduo(r1);
        gestorResiduos.registrarResiduo(r2);
        gestorResiduos.registrarResiduo(r3);
        gestorResiduos.registrarResiduo(r4);
        
        // Crear vehículos de ejemplo
        Vehiculo v1 = new Vehiculo("V001", Constantes.MAX_CAPACIDAD_VEHICULO);
        v1.setZonaAsignada(zona1);
        gestorRutas.agregarVehiculo(v1);
        
        Vehiculo v2 = new Vehiculo("V002", Constantes.MAX_CAPACIDAD_VEHICULO);
        v2.setZonaAsignada(zona2);
        gestorRutas.agregarVehiculo(v2);
        
        System.out.println("✓ Datos iniciales cargados correctamente");
        System.out.println("  - 4 residuos registrados");
        System.out.println("  - 2 vehículos disponibles");
        System.out.println("  - 3 zonas configuradas\n");
    }

    /**
     * Muestra el menú principal
     */
    public void mostrarMenu() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║     EcoTrack - Sistema de Gestión de Residuos         ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("1. Registrar nuevo residuo");
        System.out.println("2. Ver todos los residuos");
        System.out.println("3. Ordenar residuos");
        System.out.println("4. Buscar residuo por ID");
        System.out.println("5. Despachar vehículo");
        System.out.println("6. Agregar vehículo");
        System.out.println("7. Enviar residuo a reciclaje");
        System.out.println("8. Procesar residuo del centro de reciclaje");
        System.out.println("9. Ver estadísticas");
        System.out.println("10. Ver utilidad de zonas");
        System.out.println("11. Guardar datos");
        System.out.println("12. Cargar datos");
        System.out.println("0. Salir");
        System.out.print("\nSeleccione una opción: ");
    }

    /**
     * Ejecuta el programa principal
     */
    public void ejecutar() {
        int opcion = -1;
        
        while (opcion != 0) {
            mostrarMenu();
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir salto de línea
                
                System.out.println();
                
                switch (opcion) {
                    case 1:
                        registrarResiduo();
                        break;
                    case 2:
                        verResiduos();
                        break;
                    case 3:
                        ordenarResiduos();
                        break;
                    case 4:
                        buscarResiduo();
                        break;
                    case 5:
                        despacharVehiculo();
                        break;
                    case 6:
                        agregarVehiculo();
                        break;
                    case 7:
                        enviarAReciclaje();
                        break;
                    case 8:
                        procesarResiduo();
                        break;
                    case 9:
                        verEstadisticas();
                        break;
                    case 10:
                        verUtilidadZonas();
                        break;
                    case 11:
                        guardarDatos();
                        break;
                    case 12:
                        cargarDatos();
                        break;
                    case 0:
                        System.out.println("¡Gracias por usar EcoTrack!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Limpiar buffer
            }
        }
        
        scanner.close();
    }

    private void registrarResiduo() {
        System.out.println("--- Registrar Nuevo Residuo ---");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.println("Tipos disponibles: " + Constantes.TIPOS_RESIDUO);
        System.out.print("Tipo: ");
        String tipo = scanner.nextLine();
        
        System.out.print("Peso (kg): ");
        double peso = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.println("Zonas disponibles: " + Constantes.ZONAS_PREDEFINIDAS);
        System.out.print("Zona: ");
        String nombreZona = scanner.nextLine();
        
        System.out.print("Prioridad ambiental (1-10): ");
        int prioridad = scanner.nextInt();
        scanner.nextLine();
        
        Zona zona = new Zona("Z" + nombreZona.hashCode(), nombreZona);
        Residuo residuo = new Residuo(id, nombre, tipo, peso, LocalDate.now(), zona, prioridad);
        
        gestorResiduos.registrarResiduo(residuo);
        System.out.println("✓ Residuo registrado exitosamente!");
    }

    private void verResiduos() {
        System.out.println("--- Lista de Residuos ---");
        if (listaResiduos.estaVacia()) {
            System.out.println("No hay residuos registrados.");
            return;
        }
        
        ListaCircular<Residuo>.IteradorCircular iterador = listaResiduos.iterador();
        int contador = 1;
        while (iterador.tieneSiguiente()) {
            Residuo r = iterador.siguiente();
            System.out.println(contador + ". " + r);
            contador++;
        }
        System.out.println("\nTotal: " + listaResiduos.obtenerTamano() + " residuos");
    }

    private void ordenarResiduos() {
        System.out.println("--- Ordenar Residuos ---");
        System.out.println("1. Por Peso");
        System.out.println("2. Por Tipo");
        System.out.println("3. Por Prioridad Ambiental");
        System.out.print("Seleccione criterio: ");
        
        int criterio = scanner.nextInt();
        scanner.nextLine();
        
        Comparator<Residuo> comparador;
        String nombreCriterio;
        
        switch (criterio) {
            case 1:
                comparador = new ComparadorPorPeso();
                nombreCriterio = "Peso";
                break;
            case 2:
                comparador = new ComparadorPorTipo();
                nombreCriterio = "Tipo";
                break;
            case 3:
                comparador = new ComparadorPorPrioridad();
                nombreCriterio = "Prioridad Ambiental";
                break;
            default:
                System.out.println("Criterio inválido.");
                return;
        }
        
        gestorResiduos.ordenar(comparador);
        System.out.println("✓ Residuos ordenados por " + nombreCriterio);
        verResiduos();
    }

    private void buscarResiduo() {
        System.out.print("Ingrese el ID del residuo a buscar: ");
        String id = scanner.nextLine();
        
        Residuo encontrado = gestorResiduos.buscarPorId(id);
        if (encontrado != null) {
            System.out.println("✓ Residuo encontrado:");
            System.out.println(encontrado);
        } else {
            System.out.println("✗ Residuo no encontrado.");
        }
    }

    private void despacharVehiculo() {
        Vehiculo vehiculo = gestorRutas.despachar();
        if (vehiculo != null) {
            System.out.println("✓ Vehículo despachado:");
            System.out.println(vehiculo);
            System.out.println("Zona asignada: " + 
                (vehiculo.getZonaAsignada() != null ? vehiculo.getZonaAsignada().getNombre() : "N/A"));
        } else {
            System.out.println("✗ No hay vehículos disponibles para despachar.");
        }
    }

    private void agregarVehiculo() {
        System.out.print("Ingrese el ID del vehículo: ");
        String id = scanner.nextLine();
        
        Vehiculo vehiculo = new Vehiculo(id, Constantes.MAX_CAPACIDAD_VEHICULO);
        gestorRutas.agregarVehiculo(vehiculo);
        System.out.println("✓ Vehículo agregado exitosamente.");
    }

    private void enviarAReciclaje() {
        System.out.print("Ingrese el ID del residuo a enviar a reciclaje: ");
        String id = scanner.nextLine();
        
        Residuo residuo = gestorResiduos.buscarPorId(id);
        if (residuo != null) {
            gestorReciclaje.apilarResiduo(residuo);
            System.out.println("✓ Residuo enviado al centro de reciclaje.");
            System.out.println("Residuos en cola de reciclaje: " + gestorReciclaje.obtenerCantidadResiduos());
        } else {
            System.out.println("✗ Residuo no encontrado.");
        }
    }

    private void procesarResiduo() {
        Residuo residuo = gestorReciclaje.procesarResiduo();
        if (residuo != null) {
            System.out.println("✓ Residuo procesado:");
            System.out.println(residuo);
            System.out.println("Residuos restantes en cola: " + gestorReciclaje.obtenerCantidadResiduos());
        } else {
            System.out.println("✗ No hay residuos para procesar.");
        }
    }

    private void verEstadisticas() {
        System.out.println("--- Estadísticas del Sistema ---");
        System.out.println("Total de Residuos: " + listaResiduos.obtenerTamano());
        System.out.println("Residuos en Reciclaje: " + pilaReciclaje.obtenerTamano());
        System.out.println("Vehículos Disponibles: " + colaVehiculos.obtenerTamano());
        System.out.println("\n--- Por Tipo de Residuo ---");
        
        for (String tipo : Constantes.TIPOS_RESIDUO) {
            int count = 0;
            double pesoTotal = 0;
            
            ListaCircular<Residuo>.IteradorCircular iterador = listaResiduos.iterador();
            while (iterador.tieneSiguiente()) {
                Residuo r = iterador.siguiente();
                if (r.getTipo().equals(tipo)) {
                    count++;
                    pesoTotal += r.getPeso();
                }
            }
            
            if (count > 0) {
                System.out.println(tipo + ": " + count + " residuos, " + pesoTotal + " kg");
            }
        }
    }

    private void verUtilidadZonas() {
        System.out.println("--- Utilidad de Zonas ---");
        System.out.println("La utilidad se calcula como: u = P_recolectado - P_pendiente");
        System.out.println("Mayor utilidad = zona más limpia\n");
        
        // Recopilar zonas únicas de los residuos
        java.util.Set<String> zonasUnicas = new java.util.HashSet<>();
        ListaCircular<Residuo>.IteradorCircular iterador = listaResiduos.iterador();
        while (iterador.tieneSiguiente()) {
            Residuo r = iterador.siguiente();
            if (r.getZona() != null) {
                zonasUnicas.add(r.getZona().getNombre());
            }
        }
        
        for (String nombreZona : zonasUnicas) {
            ListaCircular<Residuo>.IteradorCircular iter = listaResiduos.iterador();
            Zona zona = null;
            while (iter.tieneSiguiente()) {
                Residuo r = iter.siguiente();
                if (r.getZona() != null && r.getZona().getNombre().equals(nombreZona)) {
                    zona = r.getZona();
                    break;
                }
            }
            
            if (zona != null) {
                double utilidad = CalculadoraUtilidad.calcularUtilidad(zona);
                System.out.println("Zona: " + zona.getNombre());
                System.out.println("  P. Recolectado: " + zona.getPRecolectado());
                System.out.println("  P. Pendiente: " + zona.getPPendiente());
                System.out.println("  Utilidad: " + utilidad);
                System.out.println("  Prioridad: " + zona.getPrioridad());
                System.out.println();
            }
        }
    }

    private void guardarDatos() {
        gestorDatos.guardar(listaResiduos, Constantes.ARCHIVO_DATOS);
        System.out.println("✓ Datos guardados en " + Constantes.ARCHIVO_DATOS);
    }

    private void cargarDatos() {
        Object datos = gestorDatos.cargar(Constantes.ARCHIVO_DATOS);
        if (datos != null && datos instanceof ListaCircular) {
            @SuppressWarnings("unchecked")
            ListaCircular<Residuo> datosCargados = (ListaCircular<Residuo>) datos;
            listaResiduos = datosCargados;
            gestorResiduos = new GestorResiduos(listaResiduos);
            System.out.println("✓ Datos cargados desde " + Constantes.ARCHIVO_DATOS);
        } else {
            System.out.println("✗ No se pudieron cargar los datos.");
        }
    }

    /**
     * Método principal para ejecutar la aplicación de consola
     */
    public static void main(String[] args) {
        EcoTrackConsola app = new EcoTrackConsola();
        app.ejecutar();
    }
}
