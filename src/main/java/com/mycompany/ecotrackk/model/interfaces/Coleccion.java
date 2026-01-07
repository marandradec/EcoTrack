package ecotrack.model.interfaces;

/**
 * Define el comportamiento básico que deben seguir todas las estructuras del proyecto,
 * asegurando consistencia y facilidad para intercambiarlas sin modificar el resto del sistema.
 */
public interface Coleccion<T> {
    /**
     * Agrega un elemento a la colección
     */
    void agregar(T elemento);

    /**
     * Elimina un elemento de la colección
     */
    boolean eliminar(T elemento);

    /**
     * Verifica si la colección está vacía
     */
    boolean estaVacia();

    /**
     * Obtiene el tamaño de la colección
     */
    int obtenerTamano();

    /**
     * Verifica si la colección contiene un elemento específico
     */
    boolean contiene(T elemento);
}
