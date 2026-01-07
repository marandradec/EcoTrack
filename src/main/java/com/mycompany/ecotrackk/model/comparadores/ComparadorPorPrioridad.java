package ecotrack.model.comparadores;

import ecotrack.model.Residuo;
import java.util.Comparator;

/**
 * Comparador para ordenar residuos por prioridad ambiental
 * Mayor prioridad primero (orden descendente)
 */
public class ComparadorPorPrioridad implements Comparator<Residuo> {
    @Override
    public int compare(Residuo a, Residuo b) {
        return Integer.compare(b.getPrioridadAmbiental(), a.getPrioridadAmbiental());
    }

    /**
     * Compara dos residuos por prioridad ambiental
     */
    public int comparar(Residuo a, Residuo b) {
        return compare(a, b);
    }

    /**
     * Obtiene la prioridad ambiental de un residuo
     */
    public int getPrioridadAmbiental(Residuo r) {
        return r.getPrioridadAmbiental();
    }
    
    
}
