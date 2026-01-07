package ecotrack.util;

import ecotrack.model.Zona;

/**
 * Se encarga de calcular la utilidad ambiental de cada zona a partir de los residuos
 * recolectados y pendientes. Centraliza la lógica matemática para mantener el código
 * más limpio y reutilizable.
 */
public class CalculadoraUtilidad {
    
    /**
     * Calcula la utilidad ambiental de una zona
     * u = P_recolectado - P_pendiente
     * 
     * @param z La zona para calcular la utilidad
     * @return La utilidad ambiental calculada
     */
    public static double calcularUtilidad(Zona z) {
        return z.getPRecolectado() - z.getPPendiente();
    }

    /**
     * Actualiza la utilidad de una zona y la recalcula
     */
    public static void actualizarUtilidad(Zona z) {
        double utilidad = calcularUtilidad(z);
        z.setUtilidad(utilidad);
        z.calcularUtilidad(); // Esto también actualiza la prioridad
    }
}
