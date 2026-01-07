package ecotrack.model;

import java.io.Serializable;

/**
 * Representa cada área de la ciudad y centraliza información sobre la
 * acumulación y gestión de residuos. Permite calcular la utilidad ambiental
 * y priorizar acciones de recolección, manteniendo el código modular y fácil de extender.
 */
public class Zona implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idZona;
    private String nombre;
    private double pRecolectado;
    private double pPendiente;
    private double utilidad;
    private int prioridad;

    public Zona(String idZona, String nombre) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.pRecolectado = 0.0;
        this.pPendiente = 0.0;
        this.utilidad = 0.0;
        this.prioridad = 0;
    }

    /**
     * Calcula la utilidad ambiental de la zona
     * u = P_recolectado - P_pendiente
     */
    public void calcularUtilidad() {
        this.utilidad = this.pRecolectado - this.pPendiente;
        // La prioridad es inversa a la utilidad (menor utilidad = mayor prioridad)
        this.prioridad = (int) Math.max(0, -this.utilidad);
    }

    // Getters
    public String getIdZona() {
        return idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPRecolectado() {
        return pRecolectado;
    }

    public double getPPendiente() {
        return pPendiente;
    }

    public double getUtilidad() {
        return utilidad;
    }

    public int getPrioridad() {
        return prioridad;
    }

    // Setters
    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPRecolectado(double pRecolectado) {
        this.pRecolectado = pRecolectado;
        calcularUtilidad();
    }

    public void setPPendiente(double pPendiente) {
        this.pPendiente = pPendiente;
        calcularUtilidad();
    }

    public void setUtilidad(double utilidad) {
        this.utilidad = utilidad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Zona{" +
                "idZona='" + idZona + '\'' +
                ", nombre='" + nombre + '\'' +
                ", pRecolectado=" + pRecolectado +
                ", pPendiente=" + pPendiente +
                ", utilidad=" + utilidad +
                ", prioridad=" + prioridad +
                '}';
    }
}
