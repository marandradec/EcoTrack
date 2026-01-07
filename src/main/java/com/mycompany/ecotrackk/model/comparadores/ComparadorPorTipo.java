package ecotrack.model.comparadores;

import ecotrack.model.Residuo;
import java.util.Comparator;

/**
 * Comparador para ordenar residuos por tipo
 */
public class ComparadorPorTipo implements Comparator<Residuo> {
    @Override
    public int compare(Residuo a, Residuo b) {
        return a.getTipo().compareToIgnoreCase(b.getTipo());
    }

    /**
     * Compara dos residuos por tipo
     */
    public int comparar(Residuo a, Residuo b) {
        return compare(a, b);
    }

    /**
     * Obtiene el tipo de un residuo
     */
    public String getTipo(Residuo r) {
        return r.getTipo();
    }
}
