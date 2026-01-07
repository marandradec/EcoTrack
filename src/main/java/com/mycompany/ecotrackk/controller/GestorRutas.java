package ecotrack.controller;

import ecotrack.model.Vehiculo;
import ecotrack.model.estructuras.ColaPrioridad;

/**
 * Gestiona la cola de prioridad de los vehículos.
 */
public class GestorRutas {
    private ColaPrioridad<Vehiculo> colaVehiculos;

    public GestorRutas(ColaPrioridad<Vehiculo> cola) {
        this.colaVehiculos = cola;
    }

    /**
     * Despacha el vehículo con mayor prioridad
     */
    public Vehiculo despachar() {
        return colaVehiculos.desencolar();
    }

    /**
     * Obtiene el siguiente vehículo sin despacharlo
     */
    public Vehiculo obtenerSiguiente() {
        return colaVehiculos.frente();
    }

    /**
     * Agrega un vehículo a la cola de prioridad
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
        colaVehiculos.encolar(vehiculo);
    }

    /**
     * Verifica si hay vehículos disponibles
     */
    public boolean hayVehiculosDisponibles() {
        return !colaVehiculos.estaVacia();
    }

    /**
     * Obtiene el tamaño de la cola
     */
    public int obtenerCantidadVehiculos() {
        return colaVehiculos.obtenerTamano();
    }

    /**
     * Obtiene la cola de vehículos
     */
    public ColaPrioridad<Vehiculo> getColaVehiculos() {
        return colaVehiculos;
    }
}
