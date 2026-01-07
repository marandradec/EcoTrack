package ecotrack.view;

import ecotrack.controller.*;
import ecotrack.model.*;
import ecotrack.model.comparadores.*;
import ecotrack.model.estructuras.*;
import ecotrack.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.*;

/**
 * Es la ventana principal del programa, contiene los botones, menús, tablas,
 * formularios y gráficos que ve el usuario.
 */
public class EcoTrackUI {
    private BorderPane root;
    
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
    
    // Componentes de la interfaz
    private TableView<Residuo> tablaResiduos;
    private ObservableList<Residuo> datosResiduos;
    private ComboBox<String> comboOrdenamiento;
    private TextArea areaEstadisticas;
    private ListView<String> listaVehiculos;
    private ListView<String> listaReciclaje;
    
    // Agrega esta variable para el ID de zona
    private int zonaIdCounter = 1;

    public EcoTrackUI() {
        inicializarEstructuras();
        crearInterfaz();
        configurarEventos();
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

        datosResiduos = FXCollections.observableArrayList();
    }

    /**
     * Crea la interfaz gráfica principal
     */
    public void crearInterfaz() {
        root = new BorderPane();
        root.setPadding(new Insets(10));

        // Barra superior con menú
        MenuBar menuBar = crearMenuBar();
        root.setTop(menuBar);

        // Panel central con pestañas
        TabPane tabPane = new TabPane();
        
        Tab tabResiduos = crearTabResiduos();
        Tab tabRutas = crearTabRutas();
        Tab tabReciclaje = crearTabReciclaje();
        Tab tabEstadisticas = crearTabEstadisticas();
        
        tabPane.getTabs().addAll(tabResiduos, tabRutas, tabReciclaje, tabEstadisticas);
        
        root.setCenter(tabPane);
    }

    /**
     * Crea la barra de menú
     */
    private MenuBar crearMenuBar() {
        MenuBar menuBar = new MenuBar();
        
        Menu menuArchivo = new Menu("Archivo");
        MenuItem itemGuardar = new MenuItem("Guardar Datos");
        MenuItem itemCargar = new MenuItem("Cargar Datos");
        MenuItem itemSalir = new MenuItem("Salir");
        
        itemGuardar.setOnAction(e -> guardarDatos());
        itemCargar.setOnAction(e -> cargarDatos());
        itemSalir.setOnAction(e -> System.exit(0));
        
        menuArchivo.getItems().addAll(itemGuardar, itemCargar, new SeparatorMenuItem(), itemSalir);
        
        Menu menuAyuda = new Menu("Ayuda");
        MenuItem itemAcerca = new MenuItem("Acerca de");
        itemAcerca.setOnAction(e -> mostrarAcercaDe());
        menuAyuda.getItems().add(itemAcerca);
        
        menuBar.getMenus().addAll(menuArchivo, menuAyuda);
        return menuBar;
    }

    /**
     * Crea la pestaña de gestión de residuos
     */
    private Tab crearTabResiduos() {
        Tab tab = new Tab("Gestión de Residuos");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        // Panel de botones
        HBox hboxBotones = new HBox(10);
        Button btnRegistrar = new Button("Registrar Residuo");
        Button btnOrdenar = new Button("Ordenar");
        comboOrdenamiento = new ComboBox<>();
        comboOrdenamiento.getItems().addAll("Por Peso", "Por Tipo", "Por Prioridad");
        comboOrdenamiento.setValue("Por Peso");
        
        btnRegistrar.setOnAction(e -> abrirVentanaRegistro());
        btnOrdenar.setOnAction(e -> ordenarResiduos());
        
        hboxBotones.getChildren().addAll(btnRegistrar, btnOrdenar, comboOrdenamiento);
        
        // Tabla de residuos
        tablaResiduos = new TableView<>();
        tablaResiduos.setItems(datosResiduos);
        
        TableColumn<Residuo, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Residuo, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        
        TableColumn<Residuo, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        
        TableColumn<Residuo, Double> colPeso = new TableColumn<>("Peso (kg)");
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        
        TableColumn<Residuo, String> colZona = new TableColumn<>("Zona");
        colZona.setCellValueFactory(cell -> {
            Zona zona = cell.getValue().getZona();
            return new javafx.beans.property.SimpleStringProperty(
                zona != null ? zona.getNombre() : "N/A");
        });
        
        TableColumn<Residuo, Integer> colPrioridad = new TableColumn<>("Prioridad");
        colPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridadAmbiental"));
        
        tablaResiduos.getColumns().addAll(colId, colNombre, colTipo, colPeso, colZona, colPrioridad);
        
        vbox.getChildren().addAll(hboxBotones, tablaResiduos);
        
        tab.setContent(vbox);
        return tab;
    }

    /**
     * Crea la pestaña de gestión de rutas
     */
    private Tab crearTabRutas() {
        Tab tab = new Tab("Rutas de Recolección");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        HBox hboxBotones = new HBox(10);
        Button btnDespachar = new Button("Despachar Vehículo");
        Button btnAgregarVehiculo = new Button("Agregar Vehículo");
        
        btnDespachar.setOnAction(e -> despacharVehiculo());
        btnAgregarVehiculo.setOnAction(e -> agregarVehiculo());
        
        hboxBotones.getChildren().addAll(btnDespachar, btnAgregarVehiculo);
        
        listaVehiculos = new ListView<>();
        listaVehiculos.setPrefHeight(400);
        
        vbox.getChildren().addAll(hboxBotones, listaVehiculos);
        
        tab.setContent(vbox);
        return tab;
    }

    /**
     * Crea la pestaña de centro de reciclaje
     */
    private Tab crearTabReciclaje() {
        Tab tab = new Tab("Centro de Reciclaje");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        HBox hboxBotones = new HBox(10);
        Button btnProcesar = new Button("Procesar Residuo");
        Button btnEnviarAReciclaje = new Button("Enviar Residuo a Reciclaje");
        
        btnProcesar.setOnAction(e -> procesarResiduo());
        btnEnviarAReciclaje.setOnAction(e -> enviarAReciclaje());
        
        hboxBotones.getChildren().addAll(btnProcesar, btnEnviarAReciclaje);
        
        listaReciclaje = new ListView<>();
        listaReciclaje.setPrefHeight(400);
        
        vbox.getChildren().addAll(hboxBotones, listaReciclaje);
        
        tab.setContent(vbox);
        return tab;
    }

    /**
     * Crea la pestaña de estadísticas
     */
    private Tab crearTabEstadisticas() {
        Tab tab = new Tab("Estadísticas");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        
        Button btnActualizar = new Button("Actualizar Estadísticas");
        btnActualizar.setOnAction(e -> actualizarEstadisticas());
        
        areaEstadisticas = new TextArea();
        areaEstadisticas.setEditable(false);
        areaEstadisticas.setPrefRowCount(20);
        
        vbox.getChildren().addAll(btnActualizar, areaEstadisticas);
        
        tab.setContent(vbox);
        return tab;
    }

    /**
     * Configura los eventos de la interfaz
     */
    public void configurarEventos() {
        // Los eventos ya están configurados en los métodos de creación
    }

    /**
     * Abre una ventana para registrar un nuevo residuo
     */
    public void abrirVentanaRegistro() {
        Stage ventanaRegistro = new Stage();
        ventanaRegistro.setTitle("Registrar Nuevo Residuo");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        
        TextField txtId = new TextField("R" + (listaResiduos.obtenerTamano() + 1));
        TextField txtNombre = new TextField();
        ComboBox<String> comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll(Constantes.TIPOS_RESIDUO);
        comboTipo.setValue("plástico");
        TextField txtPeso = new TextField();
        ComboBox<String> comboZona = new ComboBox<>();
        comboZona.getItems().addAll(Constantes.ZONAS_PREDEFINIDAS);
        comboZona.setValue("Centro");
        TextField txtPrioridad = new TextField("3");
        
        vbox.getChildren().addAll(
            new Label("ID:"), txtId,
            new Label("Nombre:"), txtNombre,
            new Label("Tipo:"), comboTipo,
            new Label("Peso (kg):"), txtPeso,
            new Label("Zona:"), comboZona,
            new Label("Prioridad Ambiental (1-5):"), txtPrioridad
        );
        
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(e -> {
            try {
                String id = txtId.getText();
                String nombre = txtNombre.getText();
                String tipo = comboTipo.getValue();
                double peso = Double.parseDouble(txtPeso.getText());
                String nombreZona = comboZona.getValue();
                int prioridad = Integer.parseInt(txtPrioridad.getText());
                
                if (nombre.isEmpty() || tipo == null || peso <= 0 || prioridad < 1 || prioridad > 5) {
                    mostrarError("Por favor complete todos los campos correctamente");
                    return;
                }
                
                // Crear zona (si no existe, asignar valores por defecto)
                Zona zona = new Zona("Z" + zonaIdCounter++, nombreZona);
                zona.setPPendiente(peso); // El peso del residuo como pendiente inicial
                
                Residuo residuo = new Residuo(id, nombre, tipo, peso, LocalDate.now(), zona, prioridad);
                
                gestorResiduos.registrarResiduo(residuo);
                actualizarTablaResiduos();
                ventanaRegistro.close();
            } catch (NumberFormatException ex) {
                mostrarError("Por favor ingrese valores numéricos válidos para peso y prioridad");
            } catch (Exception ex) {
                mostrarError("Error al registrar residuo: " + ex.getMessage());
            }
        });
        
        vbox.getChildren().add(btnGuardar);
        
        ventanaRegistro.setScene(new javafx.scene.Scene(vbox, 400, 500));
        ventanaRegistro.show();
    }

    /**
     * Ordena los residuos según el criterio seleccionado
     */
    private void ordenarResiduos() {
        String criterio = comboOrdenamiento.getValue();
        Comparator<Residuo> comparador;
        
        switch (criterio) {
            case "Por Peso":
                comparador = new ComparadorPorPeso();
                break;
            case "Por Tipo":
                comparador = new ComparadorPorTipo();
                break;
            case "Por Prioridad":
                comparador = new ComparadorPorPrioridad();
                break;
            default:
                comparador = new ComparadorPorPeso();
        }
        
        gestorResiduos.ordenar(comparador);
        actualizarTablaResiduos();
    }

    /**
     * Actualiza la tabla de residuos
     */
    private void actualizarTablaResiduos() {
        datosResiduos.clear();
        List<Residuo> residuos = gestorResiduos.obtenerTodosResiduos();
        datosResiduos.addAll(residuos);
    }

    /**
     * Despacha un vehículo
     */
    private void despacharVehiculo() {
        Vehiculo vehiculo = gestorRutas.despachar();
        if (vehiculo != null) {
            mostrarInfo("Vehículo " + vehiculo.getIdVehiculo() + " despachado a zona " + 
                       (vehiculo.getZonaAsignada() != null ? vehiculo.getZonaAsignada().getNombre() : "N/A"));
            actualizarListaVehiculos();
        } else {
            mostrarInfo("No hay vehículos disponibles");
        }
    }

    /**
     * Agrega un vehículo a la cola
     */
    private void agregarVehiculo() {
        TextInputDialog dialog = new TextInputDialog("V" + (colaVehiculos.obtenerTamano() + 1));
        dialog.setTitle("Agregar Vehículo");
        dialog.setHeaderText("Ingrese el ID del vehículo");
        dialog.setContentText("ID:");
        
        dialog.showAndWait().ifPresent(id -> {
            if (!id.isEmpty()) {
                Vehiculo vehiculo = new Vehiculo(id, Constantes.MAX_CAPACIDAD_VEHICULO);
                gestorRutas.agregarVehiculo(vehiculo);
                actualizarListaVehiculos();
            }
        });
    }

    /**
     * Actualiza la lista de vehículos
     */
    private void actualizarListaVehiculos() {
        listaVehiculos.getItems().clear();
        
        // Mostrar solo el próximo vehículo a despachar (más simple)
        Vehiculo proximo = gestorRutas.obtenerSiguiente();
        if (proximo != null) {
            listaVehiculos.getItems().add("Próximo a despachar: " + proximo.toString());
            listaVehiculos.getItems().add("Vehículos en cola: " + gestorRutas.obtenerCantidadVehiculos());
        } else {
            listaVehiculos.getItems().add("No hay vehículos en la cola");
        }
    }

    /**
     * Procesa un residuo del centro de reciclaje
     */
    private void procesarResiduo() {
        Residuo residuo = gestorReciclaje.procesarResiduo();
        if (residuo != null) {
            mostrarInfo("Residuo procesado: " + residuo.getNombre());
            actualizarListaReciclaje();
            actualizarEstadisticas();
        } else {
            mostrarInfo("No hay residuos para procesar");
        }
    }

    /**
     * Envía un residuo al centro de reciclaje
     */
    private void enviarAReciclaje() {
        Residuo seleccionado = tablaResiduos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            gestorReciclaje.apilarResiduo(seleccionado);
            mostrarInfo("Residuo enviado al centro de reciclaje: " + seleccionado.getNombre());
            actualizarListaReciclaje();
        } else {
            mostrarError("Seleccione un residuo de la tabla primero");
        }
    }

    /**
     * Actualiza la lista del centro de reciclaje
     */
    private void actualizarListaReciclaje() {
        listaReciclaje.getItems().clear();
        
        // Mostrar solo el próximo residuo a procesar (más simple)
        Residuo proximo = gestorReciclaje.verSiguiente();
        if (proximo != null) {
            listaReciclaje.getItems().add("Próximo a procesar: " + proximo.toString());
            listaReciclaje.getItems().add("Residuos en pila: " + gestorReciclaje.obtenerCantidadResiduos());
        } else {
            listaReciclaje.getItems().add("No hay residuos en el centro de reciclaje");
        }
    }

    /**
     * Actualiza las estadísticas
     */
    private void actualizarEstadisticas() {
        // Usar el mapa de estadísticas para almacenar valores
        estadisticas = new MapaEstadisticas<>();

        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADÍSTICAS DEL SISTEMA ===\n\n");

        // Total de residuos
        int totalResiduos = listaResiduos.obtenerTamano();
        estadisticas.put("total_residuos", (double) totalResiduos);
        sb.append("Total de Residuos: ").append(totalResiduos).append("\n");

        // Residuos en reciclaje
        int residuosReciclaje = pilaReciclaje.obtenerTamano();
        estadisticas.put("residuos_reciclaje", (double) residuosReciclaje);
        sb.append("Residuos en Reciclaje: ").append(residuosReciclaje).append("\n");

        // Vehículos disponibles
        int vehiculosDisponibles = colaVehiculos.obtenerTamano();
        estadisticas.put("vehiculos_disponibles", (double) vehiculosDisponibles);
        sb.append("Vehículos Disponibles: ").append(vehiculosDisponibles).append("\n\n");

        // Estadísticas por tipo de residuo
        sb.append("=== POR TIPO DE RESIDUO ===\n");
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
                estadisticas.put("cantidad_" + tipo, (double) count);
                estadisticas.put("peso_" + tipo, pesoTotal);

                sb.append(tipo).append(": ").append(count)
                  .append(" residuos, ").append(String.format("%.1f", pesoTotal)).append(" kg\n");
            }
        }

        // Calcular utilidad por zona (según fórmula del enunciado)
        sb.append("\n=== UTILIDAD POR ZONA ===\n");
        for (String zonaNombre : Constantes.ZONAS_PREDEFINIDAS) {
            double pPendiente = 0;
            double pRecolectado = 0;

            ListaCircular<Residuo>.IteradorCircular iterador = listaResiduos.iterador();
            while (iterador.tieneSiguiente()) {
                Residuo r = iterador.siguiente();
                if (r.getZona() != null && r.getZona().getNombre().equals(zonaNombre)) {
                    pPendiente += r.getPeso();
                    // Suponemos que si está en la pila de reciclaje, ya fue recolectado
                    // Esto es una simplificación
                }
            }

            // Calcular utilidad: u = P_recolectado - P_pendiente
            double utilidad = pRecolectado - pPendiente;
            estadisticas.put("utilidad_" + zonaNombre, utilidad);

            sb.append(zonaNombre).append(":\n");
            sb.append("  Pendiente: ").append(String.format("%.1f", pPendiente)).append(" kg\n");
            sb.append("  Recolectado: ").append(String.format("%.1f", pRecolectado)).append(" kg\n");
            sb.append("  Utilidad: ").append(String.format("%.1f", utilidad)).append("\n");
        }

        areaEstadisticas.setText(sb.toString());

        // Guardar estadísticas en archivo de texto
        gestorDatos.guardarTexto(sb.toString(), Constantes.ARCHIVO_ESTADISTICAS);
    }

    /**
     * Carga datos iniciales de ejemplo
     */
    private void cargarDatosIniciales() {
        // Crear algunas zonas de ejemplo
        Zona zona1 = new Zona("Z001", "Centro");
        zona1.setPPendiente(150.0);
        zona1.setPRecolectado(75.0);
        zona1.calcularUtilidad();
        
        Zona zona2 = new Zona("Z002", "Norte");
        zona2.setPPendiente(200.0);
        zona2.setPRecolectado(50.0);
        zona2.calcularUtilidad();
        
        // Crear algunos residuos de ejemplo
        Residuo r1 = new Residuo("R001", "Botellas plásticas", "plástico", 10.5, 
                                 LocalDate.now(), zona1, 4);
        Residuo r2 = new Residuo("R002", "Papel de oficina", "papel", 5.0, 
                                 LocalDate.now(), zona2, 3);
        Residuo r3 = new Residuo("R003", "Vidrio roto", "vidrio", 8.2, 
                                 LocalDate.now(), zona1, 5);
        
        gestorResiduos.registrarResiduo(r1);
        gestorResiduos.registrarResiduo(r2);
        gestorResiduos.registrarResiduo(r3);
        
        // Crear vehículos de ejemplo
        Vehiculo v1 = new Vehiculo("V001", Constantes.MAX_CAPACIDAD_VEHICULO);
        v1.setZonaAsignada(zona1);
        gestorRutas.agregarVehiculo(v1);
        
        Vehiculo v2 = new Vehiculo("V002", Constantes.MAX_CAPACIDAD_VEHICULO);
        v2.setZonaAsignada(zona2);
        gestorRutas.agregarVehiculo(v2);
        
        actualizarTablaResiduos();
        actualizarListaVehiculos();
        actualizarEstadisticas();
    }

    /**
     * Guarda los datos del sistema
     */
    private void guardarDatos() {
        try {
            // Crear el estado del sistema
            EstadoSistema estado = new EstadoSistema(
                listaResiduos,
                colaVehiculos,
                pilaReciclaje,
                estadisticas
            );
            
            gestorDatos.guardar(estado, Constantes.ARCHIVO_DATOS);
            mostrarInfo("Datos guardados exitosamente en " + Constantes.ARCHIVO_DATOS);
        } catch (Exception e) {
            mostrarError("Error al guardar datos: " + e.getMessage());
        }
    }

    /**
     * Carga los datos del sistema
     */
    private void cargarDatos() {
        try {
            Object datos = gestorDatos.cargar(Constantes.ARCHIVO_DATOS);
            
            if (datos == null) {
                mostrarInfo("No se encontraron datos guardados. Se usarán datos iniciales.");
                return;
            }
            
            if (datos instanceof EstadoSistema) {
                EstadoSistema estado = (EstadoSistema) datos;
                
                // Restaurar todas las estructuras
                listaResiduos = estado.getListaResiduos();
                colaVehiculos = estado.getColaVehiculos();
                pilaReciclaje = estado.getPilaReciclaje();
                estadisticas = estado.getEstadisticas();
                
                // Recrear controladores
                gestorResiduos = new GestorResiduos(listaResiduos);
                gestorRutas = new GestorRutas(colaVehiculos);
                gestorReciclaje = new GestorReciclaje(pilaReciclaje);
                
                // Actualizar interfaz
                actualizarTablaResiduos();
                actualizarListaVehiculos();
                actualizarListaReciclaje();
                actualizarEstadisticas();
                
                mostrarInfo("Datos cargados exitosamente desde " + Constantes.ARCHIVO_DATOS);
            } else {
                mostrarError("El archivo no contiene datos válidos del sistema");
            }
        } catch (Exception e) {
            mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }

    /**
     * Muestra información al usuario
     */
    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un error al usuario
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra información acerca de la aplicación
     */
    private void mostrarAcercaDe() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de EcoTrack");
        alert.setHeaderText("EcoTrack - Sistema de Gestión de Residuos Urbanos");
        alert.setContentText("Sistema inteligente para la gestión eficiente de residuos urbanos.\n" +
                           "Desarrollado para el curso de Estructuras de Datos.\n\n" +
                           "Estructuras implementadas:\n" +
                           "- Lista Circular Propia\n" +
                           "- Pila LIFO Propia\n" +
                           "- Cola de Prioridad Propia\n" +
                           "- Mapa de Estadísticas Propio\n" +
                           "- 3 Comparadores diferentes");
        alert.showAndWait();
    }

    /**
     * Retorna el panel raíz de la interfaz
     */
    public BorderPane getRoot() {
        return root;
    }
}