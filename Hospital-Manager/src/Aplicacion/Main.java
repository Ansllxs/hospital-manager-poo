package Aplicacion;

import Presentacion.VentanaSolicitarCita;
import Presentacion.VentanaConsultarCitas;
import Presentacion.VentanaAtenderCita;
import Conceptos.Paciente;
import Conceptos.Medico;
import javax.swing.*;
import java.awt.*;

/**
 * Main temporal para probar las funcionalidades del proyecto
 * NOTA: Este archivo será reemplazado cuando exista VentanaPrincipal
 */
public class Main extends JFrame {
    private SistemaHospital sistema;

    // Botones
    private JButton btnCrearDatos;
    private JButton btnSolicitarCita;
    private JButton btnConsultarCitas;
    private JButton btnAtenderCita;
    private JButton btnExportarXML;
    private JButton btnImportarXML;
    private JButton btnLimpiarDatos;
    private JButton btnSalir;

    /**
     * Constructor del Main
     */
    public Main() {
        this.sistema = new SistemaHospital();
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la ventana
     */
    private void inicializarComponentes() {
        // Configuración de la ventana
        setTitle("Hospital Manager - Pruebas");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel principal con los botones
        JPanel panelBotones = new JPanel(new GridLayout(8, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crear botones
        btnCrearDatos = new JButton("Crear datos de prueba");
        btnCrearDatos.addActionListener(e -> crearDatosPrueba());
        panelBotones.add(btnCrearDatos);

        btnSolicitarCita = new JButton("Solicitar cita");
        btnSolicitarCita.addActionListener(e -> abrirSolicitarCita());
        panelBotones.add(btnSolicitarCita);

        btnConsultarCitas = new JButton("Consultar citas");
        btnConsultarCitas.addActionListener(e -> abrirConsultarCitas());
        panelBotones.add(btnConsultarCitas);

        btnAtenderCita = new JButton("Atender cita");
        btnAtenderCita.addActionListener(e -> abrirAtenderCita());
        panelBotones.add(btnAtenderCita);

        btnExportarXML = new JButton("Exportar XML");
        btnExportarXML.addActionListener(e -> exportarXML());
        panelBotones.add(btnExportarXML);

        btnImportarXML = new JButton("Importar XML");
        btnImportarXML.addActionListener(e -> importarXML());
        panelBotones.add(btnImportarXML);

        btnLimpiarDatos = new JButton("Limpiar datos");
        btnLimpiarDatos.addActionListener(e -> limpiarDatos());
        panelBotones.add(btnLimpiarDatos);

        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> salir());
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        // Panel de información
        JPanel panelInfo = new JPanel();
        JLabel lblInfo = new JLabel("<html><center>Main temporal para pruebas<br>Proyecto Hospital Manager</center></html>");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setForeground(Color.GRAY);
        panelInfo.add(lblInfo);
        add(panelInfo, BorderLayout.SOUTH);
    }

    /**
     * Crea datos de prueba si no existen
     */
    private void crearDatosPrueba() {
        try {
            // Crear pacientes si no existen
            Paciente p1 = new Paciente("101", "Ana Mora", "8888-1111", "ana@email.com");
            Paciente p2 = new Paciente("102", "Luis Rojas", "8888-2222", "luis@email.com");

            boolean agregadoP1 = sistema.agregarPaciente(p1);
            boolean agregadoP2 = sistema.agregarPaciente(p2);

            // Crear médicos si no existen
            Medico m1 = new Medico("M01", "Dr. Carlos Pérez", "2222-1111", "Médico general");
            Medico m2 = new Medico("M02", "Dra. María Soto", "2222-2222", "Pediatra");

            boolean agregadoM1 = sistema.agregarMedico(m1);
            boolean agregadoM2 = sistema.agregarMedico(m2);

            // Construir mensaje
            StringBuilder mensaje = new StringBuilder("Datos de prueba creados:\n\n");

            if (agregadoP1) {
                mensaje.append("✓ Paciente: Ana Mora\n");
            } else {
                mensaje.append("○ Paciente Ana Mora ya existe\n");
            }

            if (agregadoP2) {
                mensaje.append("✓ Paciente: Luis Rojas\n");
            } else {
                mensaje.append("○ Paciente Luis Rojas ya existe\n");
            }

            if (agregadoM1) {
                mensaje.append("✓ Médico: Dr. Carlos Pérez\n");
            } else {
                mensaje.append("○ Médico Dr. Carlos Pérez ya existe\n");
            }

            if (agregadoM2) {
                mensaje.append("✓ Médico: Dra. María Soto\n");
            } else {
                mensaje.append("○ Médico Dra. María Soto ya existe\n");
            }

            mensaje.append("\nTotal en sistema:\n");
            mensaje.append("Pacientes: ").append(sistema.getPacientes().size()).append("\n");
            mensaje.append("Médicos: ").append(sistema.getMedicos().size()).append("\n");
            mensaje.append("Citas: ").append(sistema.getCitas().size());

            JOptionPane.showMessageDialog(
                this,
                mensaje.toString(),
                "Datos de prueba",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al crear datos de prueba: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Abre la ventana para solicitar cita
     */
    private void abrirSolicitarCita() {
        VentanaSolicitarCita ventana = new VentanaSolicitarCita(sistema);
        ventana.setVisible(true);
    }

    /**
     * Abre la ventana para consultar citas
     */
    private void abrirConsultarCitas() {
        VentanaConsultarCitas ventana = new VentanaConsultarCitas(sistema);
        ventana.setVisible(true);
    }

    /**
     * Abre la ventana para atender cita
     */
    private void abrirAtenderCita() {
        VentanaAtenderCita ventana = new VentanaAtenderCita(sistema);
        ventana.setVisible(true);
    }

    /**
     * Exporta los datos a XML
     */
    private void exportarXML() {
        try {
            boolean exito = sistema.exportarDatos();

            if (exito) {
                JOptionPane.showMessageDialog(
                    this,
                    "Datos exportados exitosamente a:\n\n" +
                    "- Export/pacientes.xml\n" +
                    "- Export/medicos.xml\n" +
                    "- Export/citas.xml",
                    "Exportación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al exportar los datos.\nRevise la consola para más detalles.",
                    "Error de exportación",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al exportar: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Importa los datos desde XML
     */
    private void importarXML() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea importar datos desde XML?\n\n" +
            "Esto reemplazará todos los datos actuales en memoria\n" +
            "y sobrescribirá los archivos .DAT",
            "Confirmar importación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                boolean exito = sistema.importarDatos();

                if (exito) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Datos importados exitosamente desde:\n\n" +
                        "- Export/pacientes.xml\n" +
                        "- Export/medicos.xml\n" +
                        "- Export/citas.xml\n\n" +
                        "Los archivos .DAT fueron actualizados.\n\n" +
                        "Total importado:\n" +
                        "Pacientes: " + sistema.getPacientes().size() + "\n" +
                        "Médicos: " + sistema.getMedicos().size() + "\n" +
                        "Citas: " + sistema.getCitas().size(),
                        "Importación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "Error al importar los datos.\n" +
                        "Verifique que existan los archivos XML en la carpeta Export/",
                        "Error de importación",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al importar: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Limpia todos los datos del sistema
     */
    private void limpiarDatos() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea limpiar todos los datos?\n\n" +
            "Esto eliminará:\n" +
            "- Todos los pacientes\n" +
            "- Todos los médicos\n" +
            "- Todas las citas\n\n" +
            "Los archivos .DAT serán vaciados.\n" +
            "Los archivos XML NO serán eliminados.",
            "Confirmar limpieza",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                sistema.limpiarDatos();

                JOptionPane.showMessageDialog(
                    this,
                    "Todos los datos fueron eliminados.\n\n" +
                    "Los archivos .DAT han sido vaciados.\n" +
                    "Los archivos XML permanecen intactos.",
                    "Datos limpiados",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al limpiar datos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Sale del programa
     */
    private void salir() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea salir?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Método main
     */
    public static void main(String[] args) {
        // Usar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, usar el Look and Feel por defecto
        }

        // Crear y mostrar la ventana en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            Main ventanaPrincipal = new Main();
            ventanaPrincipal.setVisible(true);
        });
    }
}
