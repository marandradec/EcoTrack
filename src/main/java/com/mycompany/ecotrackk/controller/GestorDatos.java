package ecotrack.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda y carga la información en archivos.
 */
public class GestorDatos {
    
    /**
     * Guarda un objeto en un archivo binario
     */
    public void guardar(Object objeto, String nombreArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(nombreArchivo))) {
            oos.writeObject(objeto);
            System.out.println("Datos guardados exitosamente en " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    /**
     * Carga un objeto desde un archivo binario
     */
    public Object cargar(String nombreArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(nombreArchivo))) {
            Object objeto = ois.readObject();
            System.out.println("Datos cargados exitosamente desde " + nombreArchivo);
            return objeto;
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + nombreArchivo);
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            return null;
        }
    }

    /**
     * Guarda datos en formato de texto
     */
    public void guardarTexto(String contenido, String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.print(contenido);
            System.out.println("Datos guardados en texto: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar texto: " + e.getMessage());
        }
    }

    /**
     * Carga datos desde un archivo de texto
     */
    public List<String> cargarTexto(String nombreArchivo) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
            System.out.println("Datos cargados desde texto: " + nombreArchivo);
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al cargar texto: " + e.getMessage());
        }
        return lineas;
    }
}
