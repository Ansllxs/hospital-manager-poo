package Util;

import Conceptos.Paciente;
import Conceptos.Medico;
import Conceptos.Cita;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class ManejadorXML {
    private static final String CARPETA_EXPORT = "Export";
    private static final String ARCHIVO_PACIENTES = "pacientes.xml";
    private static final String ARCHIVO_MEDICOS = "medicos.xml";
    private static final String ARCHIVO_CITAS = "citas.xml";

    /**
     * Exporta la lista de pacientes a un archivo XML
     * @param pacientes Lista de pacientes a exportar
     * @return true si se exportó correctamente, false en caso de error
     */
    public static boolean exportarPacientes(ArrayList<Paciente> pacientes) {
        try {
            crearCarpetaExport();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Elemento raíz
            Element root = doc.createElement("pacientes");
            doc.appendChild(root);

            // Agregar cada paciente
            for (Paciente paciente : pacientes) {
                Element pacienteElement = doc.createElement("paciente");
                pacienteElement.setAttribute("id", paciente.getIdentificacion());

                Element nombre = doc.createElement("nombre");
                nombre.setTextContent(paciente.getNombre() != null ? paciente.getNombre() : "");
                pacienteElement.appendChild(nombre);

                Element telefono = doc.createElement("telefono");
                telefono.setTextContent(paciente.getTelefono() != null ? paciente.getTelefono() : "");
                pacienteElement.appendChild(telefono);

                Element email = doc.createElement("email");
                email.setTextContent(paciente.getEmail() != null ? paciente.getEmail() : "");
                pacienteElement.appendChild(email);

                root.appendChild(pacienteElement);
            }

            // Guardar el archivo
            return guardarDocumento(doc, ARCHIVO_PACIENTES);

        } catch (ParserConfigurationException e) {
            System.err.println("Error al exportar pacientes: " + e.getMessage());
            return false;
        }
    }

    /**
     * Exporta la lista de médicos a un archivo XML
     * @param medicos Lista de médicos a exportar
     * @return true si se exportó correctamente, false en caso de error
     */
    public static boolean exportarMedicos(ArrayList<Medico> medicos) {
        try {
            crearCarpetaExport();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Elemento raíz
            Element root = doc.createElement("medicos");
            doc.appendChild(root);

            // Agregar cada médico
            for (Medico medico : medicos) {
                Element medicoElement = doc.createElement("medico");
                medicoElement.setAttribute("id", medico.getIdentificacion());

                Element nombre = doc.createElement("nombre");
                nombre.setTextContent(medico.getNombre() != null ? medico.getNombre() : "");
                medicoElement.appendChild(nombre);

                Element telefono = doc.createElement("telefono");
                telefono.setTextContent(medico.getTelefono() != null ? medico.getTelefono() : "");
                medicoElement.appendChild(telefono);

                Element puesto = doc.createElement("puesto");
                puesto.setTextContent(medico.getPuesto() != null ? medico.getPuesto() : "");
                medicoElement.appendChild(puesto);

                root.appendChild(medicoElement);
            }

            // Guardar el archivo
            return guardarDocumento(doc, ARCHIVO_MEDICOS);

        } catch (ParserConfigurationException e) {
            System.err.println("Error al exportar médicos: " + e.getMessage());
            return false;
        }
    }

    /**
     * Exporta la lista de citas a un archivo XML
     * @param citas Lista de citas a exportar
     * @return true si se exportó correctamente, false en caso de error
     */
    public static boolean exportarCitas(ArrayList<Cita> citas) {
        try {
            crearCarpetaExport();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Elemento raíz
            Element root = doc.createElement("citas");
            doc.appendChild(root);

            // Agregar cada cita
            for (Cita cita : citas) {
                Element citaElement = doc.createElement("cita");
                citaElement.setAttribute("id", String.valueOf(cita.getIdentificacion()));

                // Paciente ID
                Element pacienteId = doc.createElement("paciente_id");
                if (cita.getPaciente() != null) {
                    pacienteId.setTextContent(cita.getPaciente().getIdentificacion());
                } else {
                    pacienteId.setTextContent("");
                }
                citaElement.appendChild(pacienteId);

                // Fecha
                Element fecha = doc.createElement("fecha");
                fecha.setTextContent(cita.getFecha() != null ? cita.getFecha() : "");
                citaElement.appendChild(fecha);

                // Médico ID
                Element medicoId = doc.createElement("medico_id");
                if (cita.getMedico() != null) {
                    medicoId.setTextContent(cita.getMedico().getIdentificacion());
                } else {
                    medicoId.setTextContent("");
                }
                citaElement.appendChild(medicoId);

                // Observaciones
                Element observaciones = doc.createElement("observaciones");
                observaciones.setTextContent(cita.getObservaciones() != null ? cita.getObservaciones() : "");
                citaElement.appendChild(observaciones);

                // Diagnósticos
                Element diagnosticosElement = doc.createElement("diagnosticos");
                if (cita.getDiagnosticos() != null) {
                    for (String diagnostico : cita.getDiagnosticos()) {
                        Element diagnosticoElement = doc.createElement("diagnostico");
                        diagnosticoElement.setTextContent(diagnostico != null ? diagnostico : "");
                        diagnosticosElement.appendChild(diagnosticoElement);
                    }
                }
                citaElement.appendChild(diagnosticosElement);

                // Tratamientos
                Element tratamientosElement = doc.createElement("tratamientos");
                if (cita.getTratamientos() != null) {
                    for (String tratamiento : cita.getTratamientos()) {
                        Element tratamientoElement = doc.createElement("tratamiento");
                        tratamientoElement.setTextContent(tratamiento != null ? tratamiento : "");
                        tratamientosElement.appendChild(tratamientoElement);
                    }
                }
                citaElement.appendChild(tratamientosElement);

                root.appendChild(citaElement);
            }

            // Guardar el archivo
            return guardarDocumento(doc, ARCHIVO_CITAS);

        } catch (ParserConfigurationException e) {
            System.err.println("Error al exportar citas: " + e.getMessage());
            return false;
        }
    }

    /**
     * Importa pacientes desde un archivo XML
     * @return Lista de pacientes importados, o lista vacía si hay error
     */
    public static ArrayList<Paciente> importarPacientes() {
        ArrayList<Paciente> pacientes = new ArrayList<>();

        try {
            String rutaCompleta = CARPETA_EXPORT + File.separator + ARCHIVO_PACIENTES;
            File archivo = new File(rutaCompleta);

            if (!archivo.exists()) {
                return pacientes;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList listaPacientes = doc.getElementsByTagName("paciente");

            for (int i = 0; i < listaPacientes.getLength(); i++) {
                Node nodo = listaPacientes.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;

                    String id = elemento.getAttribute("id");
                    String nombre = obtenerTextoElemento(elemento, "nombre");
                    String telefono = obtenerTextoElemento(elemento, "telefono");
                    String email = obtenerTextoElemento(elemento, "email");

                    Paciente paciente = new Paciente(id, nombre, telefono, email);
                    pacientes.add(paciente);
                }
            }

        } catch (Exception e) {
            System.err.println("Error al importar pacientes: " + e.getMessage());
        }

        return pacientes;
    }

    /**
     * Importa médicos desde un archivo XML
     * @return Lista de médicos importados, o lista vacía si hay error
     */
    public static ArrayList<Medico> importarMedicos() {
        ArrayList<Medico> medicos = new ArrayList<>();

        try {
            String rutaCompleta = CARPETA_EXPORT + File.separator + ARCHIVO_MEDICOS;
            File archivo = new File(rutaCompleta);

            if (!archivo.exists()) {
                return medicos;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList listaMedicos = doc.getElementsByTagName("medico");

            for (int i = 0; i < listaMedicos.getLength(); i++) {
                Node nodo = listaMedicos.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;

                    String id = elemento.getAttribute("id");
                    String nombre = obtenerTextoElemento(elemento, "nombre");
                    String telefono = obtenerTextoElemento(elemento, "telefono");
                    String puesto = obtenerTextoElemento(elemento, "puesto");

                    Medico medico = new Medico(id, nombre, telefono, puesto);
                    medicos.add(medico);
                }
            }

        } catch (Exception e) {
            System.err.println("Error al importar médicos: " + e.getMessage());
        }

        return medicos;
    }

    /**
     * Importa citas desde un archivo XML
     * @param pacientes Lista de pacientes para buscar referencias
     * @param medicos Lista de médicos para buscar referencias
     * @return Lista de citas importadas, o lista vacía si hay error
     */
    public static ArrayList<Cita> importarCitas(ArrayList<Paciente> pacientes, ArrayList<Medico> medicos) {
        ArrayList<Cita> citas = new ArrayList<>();

        try {
            String rutaCompleta = CARPETA_EXPORT + File.separator + ARCHIVO_CITAS;
            File archivo = new File(rutaCompleta);

            if (!archivo.exists()) {
                return citas;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList listaCitas = doc.getElementsByTagName("cita");

            for (int i = 0; i < listaCitas.getLength(); i++) {
                Node nodo = listaCitas.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;

                    int id = Integer.parseInt(elemento.getAttribute("id"));
                    String pacienteId = obtenerTextoElemento(elemento, "paciente_id");
                    String fecha = obtenerTextoElemento(elemento, "fecha");
                    String medicoId = obtenerTextoElemento(elemento, "medico_id");
                    String observaciones = obtenerTextoElemento(elemento, "observaciones");

                    // Buscar paciente
                    Paciente paciente = buscarPacientePorId(pacientes, pacienteId);

                    // Buscar médico
                    Medico medico = null;
                    if (medicoId != null && !medicoId.trim().isEmpty()) {
                        medico = buscarMedicoPorId(medicos, medicoId);
                    }

                    // Crear cita
                    Cita cita = new Cita(id, paciente, fecha, observaciones, medico);

                    // Importar diagnósticos
                    NodeList diagnosticosList = elemento.getElementsByTagName("diagnostico");
                    for (int j = 0; j < diagnosticosList.getLength(); j++) {
                        String diagnostico = diagnosticosList.item(j).getTextContent();
                        cita.agregarDiagnostico(diagnostico);
                    }

                    // Importar tratamientos
                    NodeList tratamientosList = elemento.getElementsByTagName("tratamiento");
                    for (int j = 0; j < tratamientosList.getLength(); j++) {
                        String tratamiento = tratamientosList.item(j).getTextContent();
                        cita.agregarTratamiento(tratamiento);
                    }

                    citas.add(cita);
                }
            }

        } catch (Exception e) {
            System.err.println("Error al importar citas: " + e.getMessage());
        }

        return citas;
    }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Crea la carpeta Export si no existe
     */
    private static void crearCarpetaExport() {
        File carpeta = new File(CARPETA_EXPORT);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }

    /**
     * Guarda un documento XML en un archivo
     * @param doc Documento XML a guardar
     * @param nombreArchivo Nombre del archivo
     * @return true si se guardó correctamente, false en caso de error
     */
    private static boolean guardarDocumento(Document doc, String nombreArchivo) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            String rutaCompleta = CARPETA_EXPORT + File.separator + nombreArchivo;
            StreamResult result = new StreamResult(new File(rutaCompleta));

            transformer.transform(source, result);
            return true;

        } catch (TransformerException e) {
            System.err.println("Error al guardar documento XML: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el texto de un elemento hijo
     * @param padre Elemento padre
     * @param nombreTag Nombre del tag hijo
     * @return Texto del elemento o cadena vacía si no existe
     */
    private static String obtenerTextoElemento(Element padre, String nombreTag) {
        NodeList lista = padre.getElementsByTagName(nombreTag);
        if (lista.getLength() > 0) {
            return lista.item(0).getTextContent();
        }
        return "";
    }

    /**
     * Busca un paciente por su identificación
     * @param pacientes Lista de pacientes
     * @param id Identificación a buscar
     * @return Paciente encontrado o null
     */
    private static Paciente buscarPacientePorId(ArrayList<Paciente> pacientes, String id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getIdentificacion().equals(id)) {
                return paciente;
            }
        }
        return null;
    }

    /**
     * Busca un médico por su identificación
     * @param medicos Lista de médicos
     * @param id Identificación a buscar
     * @return Médico encontrado o null
     */
    private static Medico buscarMedicoPorId(ArrayList<Medico> medicos, String id) {
        for (Medico medico : medicos) {
            if (medico.getIdentificacion().equals(id)) {
                return medico;
            }
        }
        return null;
    }
}
