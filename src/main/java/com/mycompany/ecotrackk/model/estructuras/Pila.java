package ecotrack.model.estructuras;

/**
 * Estructura tipo Last In, First Out (LIFO), utilizada para simular el centro de reciclaje,
 * donde los residuos se procesan en orden inverso al de llegada.
 */
public class Pila<T> {
    private Nodo<T> cima;
    private int tamaño;

    public Pila() {
        this.cima = null;
        this.tamaño = 0;
    }

    /**
     * Apila un elemento en la cima de la pila
     */
    public void apilar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        nuevoNodo.setSiguiente(cima);
        cima = nuevoNodo;
        tamaño++;
    }

    /**
     * Desapila y retorna el elemento de la cima
     */
    public T desapilar() {
        if (estaVacia()) {
            return null;
        }
        T dato = cima.getDato();
        cima = cima.getSiguiente();
        tamaño--;
        return dato;
    }

    /**
     * Retorna el elemento de la cima sin desapilarlo
     */
    public T cima() {
        if (estaVacia()) {
            return null;
        }
        return cima.getDato();
    }

    /**
     * Verifica si la pila está vacía
     */
    public boolean estaVacia() {
        return cima == null;
    }

    /**
     * Obtiene el tamaño de la pila
     */
    public int obtenerTamano() {
        return tamaño;
    }
    
    /**
 * Obtiene todos los elementos de la pila sin modificar la pila.
 * Los elementos se devuelven en el orden en que están en la pila (de la cima al fondo).
 */
    public List<T> obtenerTodos() {
        List<T> elementos = new ArrayList<>();
        Nodo<T> actual = cima;
        while (actual != null) {
            elementos.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return elementos;
    }
    
    public boolean contiene(T elemento) {
    Nodo<T> actual = cima;
    while (actual != null) {
        if (actual.getDato().equals(elemento)) {
            return true;
        }
        actual = actual.getSiguiente();
    }
    return false;
}
}
