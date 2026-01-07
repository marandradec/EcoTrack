package ecotrack.model.interfaces;

import java.util.Comparator;

/**
 * Define el comportamiento necesario para ordenar elementos dentro de una estructura de datos,
 * permitiendo usar distintos criterios sin modificar la lógica principal.
 */
public interface EstructuraOrdenable<T> {
    /**
     * Ordena los elementos usando un comparador específico
     */
    void ordenar(Comparator<T> comparador);

    /**
     * Compara dos elementos usando un criterio específico
     */
    int comparar(T a, T b);
}
