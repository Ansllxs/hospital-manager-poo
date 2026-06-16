package Aplicacion;

import Presentacion.VentanaSolicitarCita;
import Conceptos.Paciente;

/**
 * Clase temporal para probar VentanaSolicitarCita
 * NOTA: Este archivo es solo para pruebas y puede eliminarse después
 */
public class TestVentanaCita {
    public static void main(String[] args) {
        // Crear el sistema
        SistemaHospital sistema = new SistemaHospital();

        // Agregar algunos pacientes de prueba
        Paciente p1 = new Paciente("123456789", "Juan Pérez", "88887777", "juan@email.com");
        Paciente p2 = new Paciente("987654321", "María López", "88889999", "maria@email.com");
        Paciente p3 = new Paciente("555666777", "Carlos Gómez", "88886666", "carlos@email.com");

        sistema.agregarPaciente(p1);
        sistema.agregarPaciente(p2);
        sistema.agregarPaciente(p3);

        // Abrir la ventana de solicitar cita
        VentanaSolicitarCita ventana = new VentanaSolicitarCita(sistema);
        ventana.setVisible(true);
    }
}
