package ecotrack.controller;

import ecotrack.model.Residuo;
import ecotrack.model.estructuras.Pila;

/**
 * Controla la pila del centro de reciclaje.
 */
public class GestorReciclaje {
    private Pila<Residuo> pilaReciclaje;

    public GestorReciclaje(Pila<Residuo> pilaReciclaje) {
        this.pilaReciclaje = pilaReciclaje;
    }

    /**
     * Apila un residuo en el centro de reciclaje
     */
    public void apilarResiduo(Residuo r) {
        pilaReciclaje.apilar(r);
    }

    /**
     * Procesa el siguiente residuo (desapila)
     */
    public Residuo procesarResiduo() {
        return pilaReciclaje.desapilar();
    }

    /**
     * Obtiene el siguiente residuo a procesar sin desapilarlo
     */
    public Residuo verSiguiente() {
        return pilaReciclaje.cima();
    }

    /**
     * Verifica si hay residuos para procesar
     */
    public boolean hayResiduosParaProcesar() {
        return !pilaReciclaje.estaVacia();
    }

    /**
     * Obtiene la cantidad de residuos en espera
     */
    public int obtenerCantidadResiduos() {
        return pilaReciclaje.obtenerTamano();
    }

    /**
     * Obtiene la pila de reciclaje
     */
    public Pila<Residuo> getPilaReciclaje() {
        return pilaReciclaje;
    }
}
