package Presentacion;

import Aplicacion.SistemaHospital;
import Conceptos.Paciente;
import Conceptos.Cita;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaSolicitarCita extends JFrame {
    private SistemaHospital sistema;

    // Componentes
    private JTextField txtIdentificacion;
    private JComboBox<Paciente> cmbPacientes;
    private JButton btnAgregarPaciente;
    private JTextField txtFecha;
    private JTextArea txtObservaciones;
    private JScrollPane scrollObservaciones;
    private JButton btnSalvar;
    private JButton btnCancelar;

    /**
     * Constructor que recibe el sistema hospital
     * @param sistema Instancia de SistemaHospital
     */
    public VentanaSolicitarCita(SistemaHospital sistema) {
        this.sistema = sistema;
        inicializarComponentes();
        cargarPacientes();
    }

    /**
     * Inicializa todos los componentes de la ventana
     */
    private void inicializarComponentes() {
        // Configuración de la ventana
        setTitle("Solicitar cita");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel principal con GridBagLayout para mejor control
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Fila 0: ID de la cita
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panelPrincipal.add(new JLabel("ID Cita:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.gridwidth = 2;
        txtIdentificacion = new JTextField();
        txtIdentificacion.setEditable(false);
        txtIdentificacion.setBackground(Color.LIGHT_GRAY);
        txtIdentificacion.setText(String.valueOf(sistema.generarConsecutivoCita()));
        panelPrincipal.add(txtIdentificacion, gbc);

        // Fila 1: Paciente
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.gridwidth = 1;
        panelPrincipal.add(new JLabel("Paciente:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        cmbPacientes = new JComboBox<>();
        panelPrincipal.add(cmbPacientes, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.1;
        btnAgregarPaciente = new JButton("+");
        btnAgregarPaciente.setToolTipText("Agregar nuevo paciente");
        btnAgregarPaciente.addActionListener(e -> mostrarMensajePacientes());
        panelPrincipal.add(btnAgregarPaciente, gbc);

        // Fila 2: Fecha
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        gbc.gridwidth = 1;
        panelPrincipal.add(new JLabel("Fecha (dd/MM/yyyy):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.gridwidth = 2;
        txtFecha = new JTextField();
        panelPrincipal.add(txtFecha, gbc);

        // Fila 3: Observaciones
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        panelPrincipal.add(new JLabel("Observaciones:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        txtObservaciones = new JTextArea(5, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        scrollObservaciones = new JScrollPane(txtObservaciones);
        scrollObservaciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelPrincipal.add(scrollObservaciones, gbc);

        // Agregar panel principal al centro
        add(panelPrincipal, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> guardarCita());
        panelBotones.add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Carga los pacientes existentes en el combo
     */
    private void cargarPacientes() {
        cmbPacientes.removeAllItems();

        for (Paciente paciente : sistema.getPacientes()) {
            cmbPacientes.addItem(paciente);
        }

        // Si no hay pacientes, mostrar mensaje
        if (cmbPacientes.getItemCount() == 0) {
            JOptionPane.showMessageDialog(
                this,
                "No hay pacientes registrados.\nPor favor, cree un paciente primero desde la ventana principal.",
                "Sin pacientes",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Guarda la cita en el sistema
     */
    private void guardarCita() {
        // Validar que hay un paciente seleccionado
        if (cmbPacientes.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar un paciente.",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Validar que la fecha no esté vacía
        String fecha = txtFecha.getText().trim();
        if (fecha.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Debe ingresar una fecha.",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE
            );
            txtFecha.requestFocus();
            return;
        }

        try {
            // Obtener datos
            int identificacion = Integer.parseInt(txtIdentificacion.getText());
            Paciente pacienteSeleccionado = (Paciente) cmbPacientes.getSelectedItem();
            String observaciones = txtObservaciones.getText().trim();

            // Crear la cita con médico null, diagnósticos y tratamientos vacíos
            Cita nuevaCita = new Cita(
                identificacion,
                pacienteSeleccionado,
                fecha,
                observaciones,
                null  // médico null por ahora
            );

            // Agregar la cita al sistema (esto llama automáticamente a guardarDatos())
            boolean exito = sistema.agregarCita(nuevaCita);

            if (exito) {
                JOptionPane.showMessageDialog(
                    this,
                    "Cita guardada exitosamente.\nConsecutivo: " + identificacion,
                    "Cita guardada",
                    JOptionPane.INFORMATION_MESSAGE
                );

                // Cerrar la ventana después de guardar
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "No se pudo guardar la cita.\nEs posible que el ID ya exista.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al procesar el ID de la cita.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al guardar la cita: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void mostrarMensajePacientes() {
    PacientesFrame ventanaPacientes = new PacientesFrame(sistema);
    ventanaPacientes.setVisible(true);
    ventanaPacientes.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            cargarPacientes();
        }
    });
}
    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        txtIdentificacion.setText(String.valueOf(sistema.generarConsecutivoCita()));
        cmbPacientes.setSelectedIndex(-1);
        txtFecha.setText("");
        txtObservaciones.setText("");
    }
}
