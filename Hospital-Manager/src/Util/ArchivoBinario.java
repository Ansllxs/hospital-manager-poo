package Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoBinario {
    private static final String CARPETA_DATA = "Data";

    /**
     * Guarda una lista de objetos en un archivo .DAT
     * @param lista Lista de objetos a guardar
     * @param nombreArchivo Nombre del archivo (ej: "PACIENTES.DAT")
     * @param <T> Tipo de objetos en la lista (debe ser Serializable)
     * @return true si se guardó correctamente, false en caso de error
     */
    public static <T> boolean guardar(List<T> lista, String nombreArchivo) {
        crearCarpetaData();
        String rutaCompleta = CARPETA_DATA + File.separator + nombreArchivo;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaCompleta))) {
            oos.writeObject(lista);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar archivo " + nombreArchivo + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Lee una lista de objetos desde un archivo .DAT
     * @param nombreArchivo Nombre del archivo (ej: "PACIENTES.DAT")
     * @param <T> Tipo de objetos en la lista
     * @return Lista de objetos leídos, o lista vacía si el archivo no existe o está vacío
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> leer(String nombreArchivo) {
        crearCarpetaData();
        String rutaCompleta = CARPETA_DATA + File.separator + nombreArchivo;
        File archivo = new File(rutaCompleta);

        // Si el archivo no existe, devolver lista vacía
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        // Si el archivo existe pero está vacío, devolver lista vacía
        if (archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaCompleta))) {
            Object objeto = ois.readObject();
            if (objeto instanceof List) {
                return (List<T>) objeto;
            } else {
                return new ArrayList<>();
            }
        } catch (EOFException e) {
            // Archivo vacío o corrupto
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer archivo " + nombreArchivo + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Vacía un archivo .DAT (lo deja como lista vacía)
     * @param nombreArchivo Nombre del archivo (ej: "PACIENTES.DAT")
     * @return true si se vació correctamente, false en caso de error
     */
    public static boolean vaciar(String nombreArchivo) {
        return guardar(new ArrayList<>(), nombreArchivo);
    }

    /**
     * Crea la carpeta Data si no existe
     */
    private static void crearCarpetaData() {
        File carpeta = new File(CARPETA_DATA);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }

    /**
     * Verifica si un archivo .DAT existe
     * @param nombreArchivo Nombre del archivo (ej: "PACIENTES.DAT")
     * @return true si el archivo existe, false en caso contrario
     */
    public static boolean existe(String nombreArchivo) {
        String rutaCompleta = CARPETA_DATA + File.separator + nombreArchivo;
        File archivo = new File(rutaCompleta);
        return archivo.exists();
    }

    /**
     * Elimina un archivo .DAT
     * @param nombreArchivo Nombre del archivo (ej: "PACIENTES.DAT")
     * @return true si se eliminó correctamente, false en caso de error
     */
    public static boolean eliminar(String nombreArchivo) {
        String rutaCompleta = CARPETA_DATA + File.separator + nombreArchivo;
        File archivo = new File(rutaCompleta);

        if (archivo.exists()) {
            return archivo.delete();
        }
        return false;
    }
}
