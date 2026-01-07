package ecotrack.util;

import java.util.Arrays;
import java.util.List;

/**
 * Guarda valores fijos usados en todo el sistema para mantener orden y
 * facilitar modificaciones futuras.
 */
public class Constantes {
    
    /**
     * Tipos de residuos permitidos en el sistema
     */
    public static final List<String> TIPOS_RESIDUO = Arrays.asList(
        "orgánico",
        "plástico",
        "vidrio",
        "metal",
        "electrónico",
        "papel",
        "textil"
    );

    /**
     * Capacidad máxima de un vehículo recolector (en kilogramos)
     */
    public static final double MAX_CAPACIDAD_VEHICULO = 5000.0;

    /**
     * Umbral de urgencia para alertas (nivel de prioridad ambiental)
     */
    public static final int UMBRAL_URGENCIA = 7;

    /**
     * Nombre del archivo de persistencia de datos
     */
    public static final String ARCHIVO_DATOS = "ecotrack_datos.dat";

    /**
     * Nombre del archivo de estadísticas
     */
    public static final String ARCHIVO_ESTADISTICAS = "ecotrack_estadisticas.txt";

    /**
     * Zonas predefinidas de la ciudad
     */
    public static final List<String> ZONAS_PREDEFINIDAS = Arrays.asList(
        "Centro",
        "Norte",
        "Sur",
        "Este",
        "Oeste",
        "Industrial",
        "Residencial"
    );

    /**
     * Constructor privado para evitar instanciación
     */
    private Constantes() {
        // Clase de utilidad, no debe instanciarse
    }
}
