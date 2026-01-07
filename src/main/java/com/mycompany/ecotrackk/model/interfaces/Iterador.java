package ecotrack.model.interfaces;

/**
 * Permite recorrer las estructuras de datos creadas de forma controlada,
 * tanto hacia adelante como hacia atrás, sin exponer su implementación interna.
 * Mantiene el código limpio y flexible.
 */
public interface Iterador<T> {
    /**
     * Verifica si hay un siguiente elemento
     */
    boolean tieneSiguiente();

    /**
     * Verifica si hay un elemento anterior
     */
    boolean tieneAnterior();

    /**
     * Obtiene el siguiente elemento y avanza
     */
    T siguiente();

    /**
     * Obtiene el elemento anterior y retrocede
     */
    T anterior();

    /**
     * Reinicia el iterador al inicio
     */
    void reiniciar();
}
