package ecotrack.model.estructuras;

import ecotrack.model.interfaces.Coleccion;
import ecotrack.model.interfaces.Iterador;
import java.io.Serializable;
import java.util.function.Predicate;

/**
 * Implementa una lista enlazada circular genérica que permite recorrer
 * los elementos hacia adelante y hacia atrás.
 */
public class ListaCircular<T> implements Coleccion<T>, Serializable {
    private static final long serialVersionUID = 1L;
    private Nodo<T> cabeza;
    private int tamaño;
    private Nodo<T> ultimo;

    public ListaCircular() {
        this.cabeza = null;
        this.ultimo = null;
        this.tamaño = 0;
    }

    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        
        if (cabeza == null) {
            cabeza = nuevoNodo;
            ultimo = nuevoNodo;
            cabeza.setSiguiente(cabeza);
            cabeza.setAnterior(cabeza);
        } else {
            ultimo.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(ultimo);
            nuevoNodo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevoNodo);
            ultimo = nuevoNodo;
        }
        tamaño++;
    }

    @Override
    public boolean eliminar(T elemento) {
        if (cabeza == null) {
            return false;
        }

        Nodo<T> actual = cabeza;
        do {
            if (actual.getDato().equals(elemento)) {
                if (tamaño == 1) {
                    cabeza = null;
                    ultimo = null;
                } else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                    
                    if (actual == cabeza) {
                        cabeza = actual.getSiguiente();
                    }
                    if (actual == ultimo) {
                        ultimo = actual.getAnterior();
                    }
                }
                tamaño--;
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != cabeza);

        return false;
    }

    @Override
    public boolean estaVacia() {
        return tamaño == 0;
    }

    @Override
    public int obtenerTamano() {
        return tamaño;
    }

    @Override
    public boolean contiene(T elemento) {
        if (cabeza == null) {
            return false;
        }

        Nodo<T> actual = cabeza;
        do {
            if (actual.getDato().equals(elemento)) {
                return true;
            }
            actual = actual.getSiguiente();
        } while (actual != cabeza);

        return false;
    }

    /**
     * Busca un elemento usando un predicado
     */
    public T buscar(Predicate<T> criterio) {
        if (cabeza == null) {
            return null;
        }

        Nodo<T> actual = cabeza;
        do {
            if (criterio.test(actual.getDato())) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        } while (actual != cabeza);

        return null;
    }
    
    /**
     * Limpiar
     */
    
    public void limpiar() {
           cabeza = null;
           ultimo = null;
           tamaño = 0;
       }

    /**
     * Muestra los elementos hacia adelante
     */
    public void mostrarAdelante() {
        if (cabeza == null) {
            System.out.println("Lista vacía");
            return;
        }

        Nodo<T> actual = cabeza;
        do {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();
        } while (actual != cabeza);
    }

    /**
     * Obtiene un iterador para recorrer la lista
     */
    public IteradorCircular iterador() {
        return new IteradorCircular();
    }

    /**
     * Obtiene el nodo cabeza
     */
    public Nodo<T> getCabeza() {
        return cabeza;
    }

    /**
     * Clase interna para el iterador circular
     */
    public class IteradorCircular implements Iterador<T> {
        private ListaCircular<T> lista;
        private Nodo<T> actual;
        private boolean iniciado;

        public IteradorCircular() {
            this.lista = ListaCircular.this;
            this.actual = lista.cabeza;
            this.iniciado = false;
        }

        @Override
        public boolean tieneSiguiente() {
            if (lista.cabeza == null) {
                return false;
            }
            return !iniciado || actual != lista.cabeza;
        }

        @Override
        public boolean tieneAnterior() {
            if (lista.cabeza == null) {
                return false;
            }
            return !iniciado || actual != lista.ultimo;
        }

        @Override
        public T siguiente() {
            if (lista.cabeza == null) {
                return null;
            }
            T dato = actual.getDato();
            actual = actual.getSiguiente();
            iniciado = true;
            return dato;
        }

        @Override
        public T anterior() {
            if (lista.cabeza == null) {
                return null;
            }
            actual = actual.getAnterior();
            iniciado = true;
            return actual.getDato();
        }

        @Override
        public void reiniciar() {
            this.actual = lista.cabeza;
            this.iniciado = false;
        }
        
        /**
        * Limpia la lista, eliminando todos los elementos.
        */
       
    }
}
