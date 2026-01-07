package ecotrack.model;

import java.io.Serializable;

/**
 * Representa los recolectores de basura y permite gestionar su capacidad y
 * asignación de zonas. Facilita la planificación de rutas y el despacho de residuos,
 * manteniendo la lógica clara y modular.
 */
public class Vehiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idVehiculo;
    private double capacidadMaxima;
    private double cargaActual;
    private Zona zonaAsignada;

    public Vehiculo(String idVehiculo, double capacidadMaxima) {
        this.idVehiculo = idVehiculo;
        this.capacidadMaxima = capacidadMaxima;
        this.cargaActual = 0.0;
        this.zonaAsignada = null;
    }

    /**
     * Verifica si el vehículo está lleno
     */
    public boolean estaLleno() {
        return cargaActual >= capacidadMaxima;
    }

    /**
     * Agrega carga al vehículo
     */
    public boolean agregarCarga(double peso) {
        if (cargaActual + peso <= capacidadMaxima) {
            cargaActual += peso;
            return true;
        }
        return false;
    }

    /**
     * Descarga el vehículo
     */
    public void descargar() {
        cargaActual = 0.0;
    }

    // Getters
    public String getIdVehiculo() {
        return idVehiculo;
    }

    public double getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public double getCargaActual() {
        return cargaActual;
    }

    public Zona getZonaAsignada() {
        return zonaAsignada;
    }

    // Setters
    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public void setCapacidadMaxima(double capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public void setCargaActual(double cargaActual) {
        this.cargaActual = cargaActual;
    }

    public void setZonaAsignada(Zona zonaAsignada) {
        this.zonaAsignada = zonaAsignada;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "idVehiculo='" + idVehiculo + '\'' +
                ", capacidadMaxima=" + capacidadMaxima +
                ", cargaActual=" + cargaActual +
                ", zonaAsignada=" + (zonaAsignada != null ? zonaAsignada.getNombre() : "N/A") +
                '}';
    }
}
