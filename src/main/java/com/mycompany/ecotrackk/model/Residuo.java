package ecotrack.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Representa de forma completa y clara la unidad mínima del sistema de
 * gestión de residuos. Centraliza la información necesaria para la clasificación,
 * el cálculo de prioridades y las estadísticas.
 */
public class Residuo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String nombre;
    private String tipo;
    private double peso;
    private LocalDate fechaRecoleccion;
    private Zona zona;
    private int prioridadAmbiental;

    public Residuo(String id, String nombre, String tipo, double peso, 
                   LocalDate fechaRecoleccion, Zona zona, int prioridadAmbiental) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.peso = peso;
        this.fechaRecoleccion = fechaRecoleccion;
        this.zona = zona;
        this.prioridadAmbiental = prioridadAmbiental;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPeso() {
        return peso;
    }

    public LocalDate getFechaRecoleccion() {
        return fechaRecoleccion;
    }

    public Zona getZona() {
        return zona;
    }

    public int getPrioridadAmbiental() {
        return prioridadAmbiental;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setFechaRecoleccion(LocalDate fechaRecoleccion) {
        this.fechaRecoleccion = fechaRecoleccion;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public void setPrioridadAmbiental(int prioridadAmbiental) {
        this.prioridadAmbiental = prioridadAmbiental;
    }

    @Override
    public String toString() {
        return "Residuo{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", peso=" + peso +
                ", fechaRecoleccion=" + fechaRecoleccion +
                ", zona=" + (zona != null ? zona.getNombre() : "N/A") +
                ", prioridadAmbiental=" + prioridadAmbiental +
                '}';
    }
}
