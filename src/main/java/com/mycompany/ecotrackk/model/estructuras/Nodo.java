package ecotrack.model.estructuras;

/**
 * Clase base para representar un nodo genérico dentro de una lista o estructura enlazada.
 */
public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;
    private Nodo<T> anterior;

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }

    // Getters
    public T getDato() {
        return dato;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public Nodo<T> getAnterior() {
        return anterior;
    }

    // Setters
    public void setDato(T dato) {
        this.dato = dato;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }

    public void setAnterior(Nodo<T> anterior) {
        this.anterior = anterior;
    }
}
