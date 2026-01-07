package ecotrack.model.estructuras;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Cola en la que los elementos se ordenan según el nivel de urgencia o
 * impacto ambiental de las zonas. Los elementos con mayor prioridad salen primero.
 */
public class ColaPrioridad<T> {
    private List<Nodo<T>> elementos;
    private Comparator<T> comparador;

    public ColaPrioridad(Comparator<T> comparador) {
        this.elementos = new ArrayList<>();
        this.comparador = comparador;
    }

    /**
     * Encola un elemento manteniendo el orden de prioridad
     */
    public void encolar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        elementos.add(nuevoNodo);
        
        // Ordenar usando el comparador (mayor prioridad primero)
        elementos.sort((n1, n2) -> comparador.compare(n2.getDato(), n1.getDato()));
    }

    /**
     * Desencola y retorna el elemento con mayor prioridad
     */
    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        return elementos.remove(0).getDato();
    }

    /**
     * Retorna el elemento con mayor prioridad sin desencolarlo
     */
    public T frente() {
        if (estaVacia()) {
            return null;
        }
        return elementos.get(0).getDato();
    }

    /**
     * Verifica si la cola está vacía
     */
    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    /**
     * Obtiene el tamaño de la cola
     */
    public int obtenerTamano() {
        return elementos.size();
    }

    /**
     * Establece un nuevo comparador
     */
    public void setComparador(Comparator<T> comparador) {
        this.comparador = comparador;
        // Reordenar con el nuevo comparador
        elementos.sort((n1, n2) -> comparador.compare(n2.getDato(), n1.getDato()));
    }
    
    /**
    * Obtiene todos los elementos de la cola en orden de prioridad (de mayor a menor)
    */
   public List<T> obtenerTodos() {
       List<T> todos = new ArrayList<>();
       for (Nodo<T> nodo : elementos) {
           todos.add(nodo.getDato());
       }
       return todos;
    }
   
   // En ColaPrioridad.java, agrega este método:
    public Comparator<T> getComparador() {
        return comparador;
    }
}
