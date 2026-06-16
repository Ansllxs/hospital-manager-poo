package Conceptos;

import java.io.Serializable;
import java.util.ArrayList;

public class Cita implements Serializable {
    private static final long serialVersionUID = 1L;

    private int identificacion;
    private Paciente paciente;
    private String fecha;
    private String observaciones;
    private Medico medico;
    private ArrayList<String> diagnosticos;
    private ArrayList<String> tratamientos;

    public Cita() {
        this.diagnosticos = new ArrayList<>();
        this.tratamientos = new ArrayList<>();
    }

    public Cita(int identificacion, Paciente paciente, String fecha, String observaciones, Medico medico) {
        this.identificacion = identificacion;
        this.paciente = paciente;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.medico = medico;
        this.diagnosticos = new ArrayList<>();
        this.tratamientos = new ArrayList<>();
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public ArrayList<String> getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(ArrayList<String> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public ArrayList<String> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(ArrayList<String> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public void agregarDiagnostico(String diagnostico) {
        if (diagnostico != null && !diagnostico.trim().isEmpty()) {
            this.diagnosticos.add(diagnostico);
        }
    }

    public void agregarTratamiento(String tratamiento) {
        if (tratamiento != null && !tratamiento.trim().isEmpty()) {
            this.tratamientos.add(tratamiento);
        }
    }

    @Override
    public String toString() {
        String nombrePaciente = (paciente != null) ? paciente.getNombre() : "Sin paciente";
        return "Cita #" + identificacion + " - " + nombrePaciente + " - " + fecha;
    }
}
