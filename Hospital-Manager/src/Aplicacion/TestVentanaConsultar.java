package Aplicacion;

import Presentacion.VentanaConsultarCitas;
import Conceptos.Paciente;
import Conceptos.Medico;
import Conceptos.Cita;

/**
 * Clase temporal para probar VentanaConsultarCitas
 * NOTA: Este archivo es solo para pruebas y puede eliminarse después
 */
public class TestVentanaConsultar {
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

        sistema.agregarMedico(m1);
        sistema.agregarMedico(m2);

        // Agregar citas de prueba
        Cita cita1 = new Cita(sistema.generarConsecutivoCita(), p1, "15/06/2024", "Primera consulta", m1);
        cita1.agregarDiagnostico("Hipertensión");
        cita1.agregarTratamiento("Medicamento XYZ 10mg");
        cita1.agregarTratamiento("Dieta baja en sodio");
        sistema.agregarCita(cita1);

        Cita cita2 = new Cita(sistema.generarConsecutivoCita(), p2, "16/06/2024", "Control mensual", m2);
        cita2.agregarDiagnostico("Diabetes tipo 2");
        cita2.agregarTratamiento("Insulina");
        sistema.agregarCita(cita2);

        Cita cita3 = new Cita(sistema.generarConsecutivoCita(), p3, "17/06/2024", "Consulta general", null);
        cita3.agregarDiagnostico("Gripe común");
        sistema.agregarCita(cita3);

        Cita cita4 = new Cita(sistema.generarConsecutivoCita(), p1, "18/06/2024", "Seguimiento", m1);
        sistema.agregarCita(cita4);

        // Abrir la ventana de consultar citas
        VentanaConsultarCitas ventana = new VentanaConsultarCitas(sistema);
        ventana.setVisible(true);
    }
}
