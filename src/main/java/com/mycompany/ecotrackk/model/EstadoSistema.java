/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maran
 */
package ecotrack.model;

import ecotrack.model.estructuras.*;
import java.io.Serializable;

public class EstadoSistema implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ListaCircular<Residuo> listaResiduos;
    private ColaPrioridad<Vehiculo> colaVehiculos;
    private Pila<Residuo> pilaReciclaje;
    private MapaEstadisticas<String, Double> estadisticas;

    public EstadoSistema() {}

    public EstadoSistema(ListaCircular<Residuo> listaResiduos, 
                         ColaPrioridad<Vehiculo> colaVehiculos, 
                         Pila<Residuo> pilaReciclaje, 
                         MapaEstadisticas<String, Double> estadisticas) {
        this.listaResiduos = listaResiduos;
        this.colaVehiculos = colaVehiculos;
        this.pilaReciclaje = pilaReciclaje;
        this.estadisticas = estadisticas;
    }

    // Getters y setters
    public ListaCircular<Residuo> getListaResiduos() { return listaResiduos; }
    public void setListaResiduos(ListaCircular<Residuo> listaResiduos) { this.listaResiduos = listaResiduos; }

    public ColaPrioridad<Vehiculo> getColaVehiculos() { return colaVehiculos; }
    public void setColaVehiculos(ColaPrioridad<Vehiculo> colaVehiculos) { this.colaVehiculos = colaVehiculos; }

    public Pila<Residuo> getPilaReciclaje() { return pilaReciclaje; }
    public void setPilaReciclaje(Pila<Residuo> pilaReciclaje) { this.pilaReciclaje = pilaReciclaje; }

    public MapaEstadisticas<String, Double> getEstadisticas() { return estadisticas; }
    public void setEstadisticas(MapaEstadisticas<String, Double> estadisticas) { this.estadisticas = estadisticas; }
}