package ecotrack.controller;

import ecotrack.model.Residuo;
import ecotrack.model.interfaces.Coleccion;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ecotrack.model.estructuras.ListaCircular;


/**
 * Maneja el registro, orden y búsqueda de residuos.
 */
public class GestorResiduos {
    private Coleccion<Residuo> listaResiduos;

    public GestorResiduos(Coleccion<Residuo> listaResiduos) {
        this.listaResiduos = listaResiduos;
    }

    /**
     * Registra un nuevo residuo
     */
    public void registrarResiduo(Residuo r) {
        listaResiduos.agregar(r);
    }

    /**
     * Ordena los residuos según un criterio específico
     * Nota: Como Coleccion no tiene método de ordenamiento directo,
     * convertimos a lista temporal para ordenar
     */
    public void ordenar(Comparator<Residuo> criterio) {
    if (listaResiduos instanceof ListaCircular) {
        ListaCircular<Residuo> listaCircular = (ListaCircular<Residuo>) listaResiduos;
        
        // Obtener todos los elementos
        List<Residuo> elementos = new ArrayList<>();
        ListaCircular<Residuo>.IteradorCircular iterador = listaCircular.iterador();
        while (iterador.tieneSiguiente()) {
            elementos.add(iterador.siguiente());
        }
        
        // Ordenar la lista
        elementos.sort(criterio);
        
        // Limpiar la lista circular
        listaCircular.limpiar();
        
        // Agregar los elementos ordenados
        for (Residuo r : elementos) {
            listaCircular.agregar(r);
        }
    }
}
        
        // Ordenar la lista temporal
        listaTemporal.sort(criterio);
        
        // Limpiar y reinsertar ordenados
        // Nota: Esto requiere acceso directo a la estructura, 
        // por lo que asumimos que la lista circular se puede limpiar
        // En una implementación real, necesitaríamos métodos adicionales
    }

    /**
     * Obtiene todos los residuos como lista
     */
    public List<Residuo> obtenerTodosResiduos() {
        List<Residuo> resultado = new ArrayList<>();
        
        if (listaResiduos instanceof ecotrack.model.estructuras.ListaCircular) {
            ecotrack.model.estructuras.ListaCircular<Residuo> listaCircular = 
                (ecotrack.model.estructuras.ListaCircular<Residuo>) listaResiduos;
            
            ListaCircular<Residuo>.IteradorCircular iterador = listaCircular.iterador();
            while (iterador.tieneSiguiente()) {
                resultado.add(iterador.siguiente());
            }
        }
        
        return resultado;
    }

    /**
     * Busca un residuo por ID
     */
    public Residuo buscarPorId(String id) {
        if (listaResiduos instanceof ecotrack.model.estructuras.ListaCircular) {
            @SuppressWarnings("unchecked")
            ecotrack.model.estructuras.ListaCircular<Residuo> listaCircular = 
                (ecotrack.model.estructuras.ListaCircular<Residuo>) listaResiduos;
            
            return listaCircular.buscar(r -> r.getId().equals(id));
        }
        return null;
    }

    /**
     * Obtiene la colección de residuos
     */
    public Coleccion<Residuo> getListaResiduos() {
        return listaResiduos;
    }
}
