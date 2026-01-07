package ecotrack.model.comparadores;

import ecotrack.model.Residuo;
import java.util.Comparator;

/**
 * Comparador para ordenar residuos por peso
 */
public class ComparadorPorPeso implements Comparator<Residuo> {
    @Override
    public int compare(Residuo a, Residuo b) {
        return Double.compare(a.getPeso(), b.getPeso());
    }

    /**
     * Compara dos residuos por peso
     */
    public int comparar(Residuo a, Residuo b) {
        return compare(a, b);
    }

    /**
     * Obtiene el peso de un residuo
     */
    public double getPeso(Residuo r) {
        return r.getPeso();
    }
}
