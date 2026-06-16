package Aplicacion;

import Presentacion.VentanaAtenderCita;
import Conceptos.Paciente;
import Conceptos.Medico;
import Conceptos.Cita;

/**
 * Clase temporal para probar VentanaAtenderCita
 * NOTA: Este archivo es solo para pruebas y puede eliminarse después
 */
public class TestVentanaAtender {
    public static void main(String[] args) {
        // Crear el sistema
        SistemaHospital sistema = new SistemaHospital();

        // Agregar pacientes de prueba
        Paciente p1 = new Paciente("123456789", "Juan Pérez", "88887777", "juan@email.com");
        Paciente p2 = new Paciente("987654321", "María López", "88889999", "maria@email.com");
        Paciente p3 = new Paciente("555666777", "Carlos Gómez", "88886666", "carlos@email.com");

        sistema.agregarPaciente(p1);
        sistema.agregarPaciente(p2);
        sistema.agregarPaciente(p3);

        // Agregar médicos de prueba
        Medico m1 = new Medico("001", "Dr. López", "88881111", "Cardiólogo");
        Medico m2 = new Medico("002", "Dra. Rodríguez", "88882222", "Pediatra");
        Medico m3 = new Medico("003", "Dr. Martínez", "88883333", "Medicina General");

        sistema.agregarMedico(m1);
        sistema.agregarMedico(m2);
        sistema.agregarMedico(m3);

        // Agregar citas de prueba (algunas sin médico, sin diagnósticos ni tratamientos)
        Cita cita1 = new Cita(sistema.generarConsecutivoCita(), p1, "15/06/2024", "Primera consulta", null);
        sistema.agregarCita(cita1);

        Cita cita2 = new Cita(sistema.generarConsecutivoCita(), p2, "16/06/2024", "Control mensual", m2);
        cita2.agregarDiagnostico("Diabetes tipo 2");
        sistema.agregarCita(cita2);

        Cita cita3 = new Cita(sistema.generarConsecutivoCita(), p3, "17/06/2024", "Consulta general", null);
        sistema.agregarCita(cita3);

        // Abrir la ventana de atender cita
        VentanaAtenderCita ventana = new VentanaAtenderCita(sistema);
        ventana.setVisible(true);
    }
}
