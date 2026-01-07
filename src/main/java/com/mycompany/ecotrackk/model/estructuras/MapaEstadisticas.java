package ecotrack.model.estructuras;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementa un mapa personalizado para guardar estadísticas globales del sistema.
 * Utiliza una tabla hash con buckets para almacenar pares clave-valor.
 */
public class MapaEstadisticas<K, V> {
    private List<NodoMapa<K, V>> buckets;
    private int capacidad;
    private int tamaño;

    /**
     * Clase interna para representar un nodo del mapa
     */
    private static class NodoMapa<K, V> {
        private K clave;
        private V valor;
        private NodoMapa<K, V> siguiente;

        public NodoMapa(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = null;
        }

        public K getClave() {
            return clave;
        }

        public V getValor() {
            return valor;
        }

        public void setValor(V valor) {
            this.valor = valor;
        }

        public NodoMapa<K, V> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(NodoMapa<K, V> siguiente) {
            this.siguiente = siguiente;
        }
    }

    public MapaEstadisticas() {
        this.capacidad = 16;
        this.buckets = new ArrayList<>();
        this.tamaño = 0;
        
        // Inicializar buckets
        for (int i = 0; i < capacidad; i++) {
            buckets.add(null);
        }
    }

    /**
     * Calcula el índice del bucket para una clave
     */
    private int obtenerIndice(K clave) {
        return Math.abs(clave.hashCode()) % capacidad;
    }

    /**
     * Inserta o actualiza un par clave-valor
     */
    public void put(K clave, V valor) {
        int indice = obtenerIndice(clave);
        NodoMapa<K, V> nodo = buckets.get(indice);

        // Buscar si la clave ya existe
        while (nodo != null) {
            if (nodo.getClave().equals(clave)) {
                nodo.setValor(valor);
                return;
            }
            nodo = nodo.getSiguiente();
        }

        // Insertar nuevo nodo al inicio del bucket
        NodoMapa<K, V> nuevoNodo = new NodoMapa<>(clave, valor);
        nuevoNodo.setSiguiente(buckets.get(indice));
        buckets.set(indice, nuevoNodo);
        tamaño++;
    }

    /**
     * Obtiene el valor asociado a una clave
     */
    public V get(K clave) {
        int indice = obtenerIndice(clave);
        NodoMapa<K, V> nodo = buckets.get(indice);

        while (nodo != null) {
            if (nodo.getClave().equals(clave)) {
                return nodo.getValor();
            }
            nodo = nodo.getSiguiente();
        }

        return null;
    }

    /**
     * Verifica si el mapa contiene una clave
     */
    public boolean contieneClave(K clave) {
        return get(clave) != null;
    }

    /**
     * Elimina un par clave-valor
     */
    public void eliminar(K clave) {
        int indice = obtenerIndice(clave);
        NodoMapa<K, V> nodo = buckets.get(indice);
        NodoMapa<K, V> anterior = null;

        while (nodo != null) {
            if (nodo.getClave().equals(clave)) {
                if (anterior == null) {
                    buckets.set(indice, nodo.getSiguiente());
                } else {
                    anterior.setSiguiente(nodo.getSiguiente());
                }
                tamaño--;
                return;
            }
            anterior = nodo;
            nodo = nodo.getSiguiente();
        }
    }

    /**
     * Retorna un conjunto con todas las claves
     */
    public Set<K> claves() {
        Set<K> conjuntoClaves = new HashSet<>();
        for (NodoMapa<K, V> nodo : buckets) {
            while (nodo != null) {
                conjuntoClaves.add(nodo.getClave());
                nodo = nodo.getSiguiente();
            }
        }
        return conjuntoClaves;
    }

    /**
     * Retorna una colección con todos los valores
     */
    public Collection<V> valores() {
        List<V> listaValores = new ArrayList<>();
        for (NodoMapa<K, V> nodo : buckets) {
            while (nodo != null) {
                listaValores.add(nodo.getValor());
                nodo = nodo.getSiguiente();
            }
        }
        return listaValores;
    }

    /**
     * Obtiene el tamaño del mapa
     */
    public int obtenerTamano() {
        return tamaño;
    }

    /**
     * Verifica si el mapa está vacío
     */
    public boolean estaVacio() {
        return tamaño == 0;
    }
}
