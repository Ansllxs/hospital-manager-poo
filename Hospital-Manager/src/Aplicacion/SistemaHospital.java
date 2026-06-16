package Aplicacion;

import Conceptos.Paciente;
import Conceptos.Medico;
import Conceptos.Cita;
import Util.ArchivoBinario;
import Util.ManejadorXML;
import java.util.ArrayList;
import java.util.List;

public class SistemaHospital {
    private static final String ARCHIVO_PACIENTES = "PACIENTES.DAT";
    private static final String ARCHIVO_MEDICOS = "MEDICOS.DAT";
    private static final String ARCHIVO_CITAS = "CITAS.DAT";

    private ArrayList<Paciente> pacientes;
    private ArrayList<Medico> medicos;
    private ArrayList<Cita> citas;

    /**
     * Constructor que inicializa las listas y carga los datos desde archivos .DAT
     */
    public SistemaHospital() {
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.citas = new ArrayList<>();
        cargarDatos();
    }

    /**
     * Carga pacientes, médicos y citas desde sus archivos .DAT
     */
    public void cargarDatos() {
        List<Paciente> pacientesCargados = ArchivoBinario.leer(ARCHIVO_PACIENTES);
        this.pacientes = new ArrayList<>(pacientesCargados);

        List<Medico> medicosCargados = ArchivoBinario.leer(ARCHIVO_MEDICOS);
        this.medicos = new ArrayList<>(medicosCargados);

        List<Cita> citasCargadas = ArchivoBinario.leer(ARCHIVO_CITAS);
        this.citas = new ArrayList<>(citasCargadas);
    }

    /**
     * Guarda pacientes, médicos y citas en sus archivos .DAT
     */
    public void guardarDatos() {
        ArchivoBinario.guardar(pacientes, ARCHIVO_PACIENTES);
        ArchivoBinario.guardar(medicos, ARCHIVO_MEDICOS);
        ArchivoBinario.guardar(citas, ARCHIVO_CITAS);
    }

    // ==================== MÉTODOS DE PACIENTES ====================

    /**
     * Obtiene la lista de pacientes
     * @return Lista de pacientes
     */
    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    /**
     * Agrega un nuevo paciente
     * @param paciente Paciente a agregar
     * @return true si se agregó correctamente, false si ya existe o es nulo
     */
    public boolean agregarPaciente(Paciente paciente) {
        if (paciente == null) {
            return false;
        }

        // Verificar si ya existe un paciente con la misma identificación
        if (buscarPacientePorId(paciente.getIdentificacion()) != null) {
            return false;
        }

        pacientes.add(paciente);
        guardarDatos();
        return true;
    }

    /**
     * Modifica un paciente existente
     * @param paciente Paciente con los datos actualizados
     * @return true si se modificó correctamente, false si no existe
     */
    public boolean modificarPaciente(Paciente paciente) {
        if (paciente == null) {
            return false;
        }

        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getIdentificacion().equals(paciente.getIdentificacion())) {
                pacientes.set(i, paciente);
                guardarDatos();
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina un paciente por su identificación
     * @param identificacion Identificación del paciente a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean eliminarPaciente(String identificacion) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getIdentificacion().equals(identificacion)) {
                pacientes.remove(i);
                guardarDatos();
                return true;
            }
        }
        return false;
    }

    /**
     * Busca un paciente por su identificación
     * @param identificacion Identificación del paciente
     * @return Paciente encontrado o null si no existe
     */
    public Paciente buscarPacientePorId(String identificacion) {
        for (Paciente paciente : pacientes) {
            if (paciente.getIdentificacion().equals(identificacion)) {
                return paciente;
            }
        }
        return null;
    }

    // ==================== MÉTODOS DE MÉDICOS ====================

    /**
     * Obtiene la lista de médicos
     * @return Lista de médicos
     */
    public ArrayList<Medico> getMedicos() {
        return medicos;
    }

    /**
     * Agrega un nuevo médico
     * @param medico Médico a agregar
     * @return true si se agregó correctamente, false si ya existe o es nulo
     */
    public boolean agregarMedico(Medico medico) {
        if (medico == null) {
            return false;
        }

        // Verificar si ya existe un médico con la misma identificación
        if (buscarMedicoPorId(medico.getIdentificacion()) != null) {
            return false;
        }

        medicos.add(medico);
        guardarDatos();
        return true;
    }

    /**
     * Modifica un médico existente
     * @param medico Médico con los datos actualizados
     * @return true si se modificó correctamente, false si no existe
     */
    public boolean modificarMedico(Medico medico) {
        if (medico == null) {
            return false;
        }

        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getIdentificacion().equals(medico.getIdentificacion())) {
                medicos.set(i, medico);
                guardarDatos();
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina un médico por su identificación
     * @param identificacion Identificación del médico a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean eliminarMedico(String identificacion) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getIdentificacion().equals(identificacion)) {
                medicos.remove(i);
                guardarDatos();
                return true;
            }
        }
        return false;
    }

    /**
     * Busca un médico por su identificación
     * @param identificacion Identificación del médico
     * @return Médico encontrado o null si no existe
     */
    public Medico buscarMedicoPorId(String identificacion) {
        for (Medico medico : medicos) {
            if (medico.getIdentificacion().equals(identificacion)) {
                return medico;
            }
        }
        return null;
    }

    // ==================== MÉTODOS DE CITAS ====================

    /**
     * Obtiene la lista de citas
     * @return Lista de citas
     */
    public ArrayList<Cita> getCitas() {
        return citas;
    }

    /**
     * Agrega una nueva cita
     * @param cita Cita a agregar
     * @return true si se agregó correctamente, false si ya existe o es nula
     */
    public boolean agregarCita(Cita cita) {
        if (cita == null) {
            return false;
        }

        // Verificar si ya existe una cita con la misma identificación
        if (buscarCitaPorId(cita.getIdentificacion()) != null) {
            return false;
        }

        citas.add(cita);
        guardarDatos();
        return true;
    }

    /**
     * Actualiza una cita existente
     * @param cita Cita con los datos actualizados
     * @return true si se actualizó correctamente, false si no existe
     */
    public boolean actualizarCita(Cita cita) {
        if (cita == null) {
            return false;
        }

        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getIdentificacion() == cita.getIdentificacion()) {
                citas.set(i, cita);
                guardarDatos();
                return true;
            }
        }
        return false;
    }

    /**
     * Busca una cita por su identificación
     * @param identificacion Identificación de la cita
     * @return Cita encontrada o null si no existe
     */
    public Cita buscarCitaPorId(int identificacion) {
        for (Cita cita : citas) {
            if (cita.getIdentificacion() == identificacion) {
                return cita;
            }
        }
        return null;
    }

    /**
     * Genera un consecutivo único para una nueva cita
     * @return Consecutivo para la cita (ID máximo + 1)
     */
    public int generarConsecutivoCita() {
        int maxId = 0;
        for (Cita cita : citas) {
            if (cita.getIdentificacion() > maxId) {
                maxId = cita.getIdentificacion();
            }
        }
        return maxId + 1;
    }

    /**
     * Busca citas aplicando filtros con lógica AND
     * @param pacienteId Identificación del paciente (vacío para no filtrar)
     * @param telefono Teléfono del paciente (vacío para no filtrar)
     * @param email Email del paciente (vacío para no filtrar)
     * @return Lista de citas que cumplen con los filtros
     */
    public ArrayList<Cita> buscarCitas(String pacienteId, String telefono, String email) {
        ArrayList<Cita> resultados = new ArrayList<>();

        boolean filtroPacienteId = pacienteId != null && !pacienteId.trim().isEmpty();
        boolean filtroTelefono = telefono != null && !telefono.trim().isEmpty();
        boolean filtroEmail = email != null && !email.trim().isEmpty();

        for (Cita cita : citas) {
            if (cita.getPaciente() == null) {
                continue;
            }

            boolean cumpleFiltros = true;

            // Aplicar filtro por identificación del paciente
            if (filtroPacienteId) {
                if (!cita.getPaciente().getIdentificacion().equals(pacienteId)) {
                    cumpleFiltros = false;
                }
            }

            // Aplicar filtro por teléfono del paciente
            if (filtroTelefono && cumpleFiltros) {
                if (!cita.getPaciente().getTelefono().equals(telefono)) {
                    cumpleFiltros = false;
                }
            }

            // Aplicar filtro por email del paciente
            if (filtroEmail && cumpleFiltros) {
                if (!cita.getPaciente().getEmail().equals(email)) {
                    cumpleFiltros = false;
                }
            }

            if (cumpleFiltros) {
                resultados.add(cita);
            }
        }

        return resultados;
    }

    // ==================== OPERACIONES DE IMPORTAR/EXPORTAR/LIMPIAR ====================

    /**
     * Exporta las listas actuales de pacientes, médicos y citas a archivos XML
     * Genera los archivos en Hospital-Manager/Export/
     * @return true si se exportó correctamente, false en caso de error
     */
    public boolean exportarDatos() {
        try {
            boolean exitoPacientes = ManejadorXML.exportarPacientes(pacientes);
            boolean exitoMedicos = ManejadorXML.exportarMedicos(medicos);
            boolean exitoCitas = ManejadorXML.exportarCitas(citas);

            if (!exitoPacientes || !exitoMedicos || !exitoCitas) {
                System.err.println("Error: No se pudieron exportar todos los datos a XML.");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.err.println("Error al exportar datos: " + e.getMessage());
            return false;
        }
    }

    /**
     * Importa datos desde archivos XML y reemplaza las listas actuales
     * Los datos importados se guardan inmediatamente en archivos .DAT
     * @return true si se importó correctamente, false en caso de error
     */
    public boolean importarDatos() {
        try {
            // Importar desde XML
            ArrayList<Paciente> pacientesImportados = ManejadorXML.importarPacientes();
            ArrayList<Medico> medicosImportados = ManejadorXML.importarMedicos();
            ArrayList<Cita> citasImportadas = ManejadorXML.importarCitas(pacientesImportados, medicosImportados);

            // Reemplazar listas internas
            this.pacientes = pacientesImportados;
            this.medicos = medicosImportados;
            this.citas = citasImportadas;

            // Guardar inmediatamente en archivos .DAT
            guardarDatos();

            return true;
        } catch (Exception e) {
            System.err.println("Error al importar datos: " + e.getMessage());
            return false;
        }
    }

    /**
     * Limpia todas las listas internas y vacía los archivos .DAT
     * No elimina los archivos XML
     */
    public void limpiarDatos() {
        try {
            // Vaciar listas internas
            this.pacientes.clear();
            this.medicos.clear();
            this.citas.clear();

            // Guardar listas vacías en archivos .DAT
            guardarDatos();

        } catch (Exception e) {
            System.err.println("Error al limpiar datos: " + e.getMessage());
        }
    }
}
