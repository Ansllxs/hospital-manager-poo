package Presentacion;

import Aplicacion.SistemaHospital;
import Conceptos.Cita;
import Conceptos.Paciente;
import Conceptos.Medico;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaAtenderCita extends JFrame {
    private SistemaHospital sistema;
    private Cita citaSeleccionada;

    // Componentes
    private JComboBox<Cita> cmbCitas;
    private JTextField txtPacienteId;
    private JTextField txtPacienteNombre;
    private JComboBox<Medico> cmbMedicos;
    private JTextArea txtObservaciones;
    private JScrollPane scrollObservaciones;

    // Diagnósticos
    private JTextField txtDiagnostico;
    private JButton btnAgregarDiagnostico;
    private DefaultListModel<String> modeloDiagnosticos;
    private JList<String> listaDiagnosticos;
    private JScrollPane scrollDiagnosticos;

    // Tratamientos
    private JTextField txtTratamiento;
    private JButton btnAgregarTratamiento;
    private DefaultListModel<String> modeloTratamientos;
    private JList<String> listaTratamientos;
    private JScrollPane scrollTratamientos;

    // Botones finales
    private JButton btnSalvar;
    private JButton btnCancelar;

    /**
     * Constructor que recibe el sistema hospital
     * @param sistema Instancia de SistemaHospital
     */
    public VentanaAtenderCita(SistemaHospital sistema) {
        this.sistema = sistema;
        inicializarComponentes();
        cargarCitas();
        cargarMedicos();
    }

    /**
     * Inicializa todos los componentes de la ventana
     */
    private void inicializarComponentes() {
        // Configuración de la ventana
        setTitle("Atender cita");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel principal con scroll
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // ==================== Fila 0: Seleccionar Cita ====================
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panelPrincipal.add(new JLabel("Cita:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        cmbCitas = new JComboBox<>();
        cmbCitas.addActionListener(e -> cargarDatosCita());
        panelPrincipal.add(cmbCitas, gbc);

        // ==================== Fila 1: ID Paciente ====================
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        panelPrincipal.add(new JLabel("ID Paciente:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtPacienteId = new JTextField();
        txtPacienteId.setEditable(false);
        txtPacienteId.setBackground(Color.LIGHT_GRAY);
        panelPrincipal.add(txtPacienteId, gbc);

        // ==================== Fila 2: Nombre Paciente ====================
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        panelPrincipal.add(new JLabel("Nombre Paciente:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtPacienteNombre = new JTextField();
        txtPacienteNombre.setEditable(false);
        txtPacienteNombre.setBackground(Color.LIGHT_GRAY);
        panelPrincipal.add(txtPacienteNombre, gbc);

        // ==================== Fila 3: Médico ====================
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        panelPrincipal.add(new JLabel("Médico:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        cmbMedicos = new JComboBox<>();
        panelPrincipal.add(cmbMedicos, gbc);

        // ==================== Fila 4: Observaciones ====================
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTH;
        panelPrincipal.add(new JLabel("Observaciones:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0.7;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        txtObservaciones = new JTextArea(3, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        scrollObservaciones = new JScrollPane(txtObservaciones);
        panelPrincipal.add(scrollObservaciones, gbc);

        // ==================== Fila 5: Panel Diagnósticos ====================
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel panelDiagnosticos = new JPanel(new BorderLayout(5, 5));
        panelDiagnosticos.setBorder(BorderFactory.createTitledBorder("Diagnósticos"));

        JPanel panelEntradaDiagnostico = new JPanel(new BorderLayout(5, 5));
        txtDiagnostico = new JTextField();
        btnAgregarDiagnostico = new JButton("+");
        btnAgregarDiagnostico.addActionListener(e -> agregarDiagnostico());
        panelEntradaDiagnostico.add(txtDiagnostico, BorderLayout.CENTER);
        panelEntradaDiagnostico.add(btnAgregarDiagnostico, BorderLayout.EAST);

        modeloDiagnosticos = new DefaultListModel<>();
        listaDiagnosticos = new JList<>(modeloDiagnosticos);
        scrollDiagnosticos = new JScrollPane(listaDiagnosticos);

        panelDiagnosticos.add(panelEntradaDiagnostico, BorderLayout.NORTH);
        panelDiagnosticos.add(scrollDiagnosticos, BorderLayout.CENTER);

        panelPrincipal.add(panelDiagnosticos, gbc);

        // ==================== Fila 6: Panel Tratamientos ====================
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel panelTratamientos = new JPanel(new BorderLayout(5, 5));
        panelTratamientos.setBorder(BorderFactory.createTitledBorder("Tratamientos"));

        JPanel panelEntradaTratamiento = new JPanel(new BorderLayout(5, 5));
        txtTratamiento = new JTextField();
        btnAgregarTratamiento = new JButton("+");
        btnAgregarTratamiento.addActionListener(e -> agregarTratamiento());
        panelEntradaTratamiento.add(txtTratamiento, BorderLayout.CENTER);
        panelEntradaTratamiento.add(btnAgregarTratamiento, BorderLayout.EAST);

        modeloTratamientos = new DefaultListModel<>();
        listaTratamientos = new JList<>(modeloTratamientos);
        scrollTratamientos = new JScrollPane(listaTratamientos);

        panelTratamientos.add(panelEntradaTratamiento, BorderLayout.NORTH);
        panelTratamientos.add(scrollTratamientos, BorderLayout.CENTER);

        panelPrincipal.add(panelTratamientos, gbc);

        // Agregar panel principal al centro
        add(panelPrincipal, BorderLayout.CENTER);

        // ==================== Panel de botones ====================
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> guardarAtencion());
        panelBotones.add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Carga las citas existentes en el combo
     */
    private void cargarCitas() {
        cmbCitas.removeAllItems();

        for (Cita cita : sistema.getCitas()) {
            cmbCitas.addItem(cita);
        }

        if (cmbCitas.getItemCount() == 0) {
            JOptionPane.showMessageDialog(
                this,
                "No hay citas registradas.\nPor favor, cree una cita primero.",
                "Sin citas",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Carga los médicos existentes en el combo
     */
    private void cargarMedicos() {
        cmbMedicos.removeAllItems();

        for (Medico medico : sistema.getMedicos()) {
            cmbMedicos.addItem(medico);
        }

        if (cmbMedicos.getItemCount() == 0) {
            JOptionPane.showMessageDialog(
                this,
                "No hay médicos registrados.\nPor favor, registre médicos primero.",
                "Sin médicos",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Carga los datos de la cita seleccionada
     */
    private void cargarDatosCita() {
        citaSeleccionada = (Cita) cmbCitas.getSelectedItem();

        if (citaSeleccionada == null) {
            limpiarCampos();
            return;
        }

        // Cargar datos del paciente
        Paciente paciente = citaSeleccionada.getPaciente();
        if (paciente != null) {
            txtPacienteId.setText(paciente.getIdentificacion());
            txtPacienteNombre.setText(paciente.getNombre());
        } else {
            txtPacienteId.setText("");
            txtPacienteNombre.setText("");
        }

        // Seleccionar médico si existe
        if (citaSeleccionada.getMedico() != null) {
            cmbMedicos.setSelectedItem(citaSeleccionada.getMedico());
        } else {
            cmbMedicos.setSelectedIndex(-1);
        }

        // Cargar observaciones
        txtObservaciones.setText(citaSeleccionada.getObservaciones() != null ? citaSeleccionada.getObservaciones() : "");

        // Cargar diagnósticos
        modeloDiagnosticos.clear();
        if (citaSeleccionada.getDiagnosticos() != null) {
            for (String diagnostico : citaSeleccionada.getDiagnosticos()) {
                modeloDiagnosticos.addElement(diagnostico);
            }
        }

        // Cargar tratamientos
        modeloTratamientos.clear();
        if (citaSeleccionada.getTratamientos() != null) {
            for (String tratamiento : citaSeleccionada.getTratamientos()) {
                modeloTratamientos.addElement(tratamiento);
            }
        }
    }

    /**
     * Agrega un diagnóstico a la lista
     */
    private void agregarDiagnostico() {
        String diagnostico = txtDiagnostico.getText().trim();

        if (diagnostico.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Debe ingresar un diagnóstico.",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        modeloDiagnosticos.addElement(diagnostico);
        txtDiagnostico.setText("");
    }

    /**
     * Agrega un tratamiento a la lista
     */
    private void agregarTratamiento() {
        String tratamiento = txtTratamiento.getText().trim();

        if (tratamiento.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Debe ingresar un tratamiento.",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        modeloTratamientos.addElement(tratamiento);
        txtTratamiento.setText("");
    }

    /**
     * Guarda la atención de la cita
     */
    private void guardarAtencion() {
        // Validar que hay una cita seleccionada
        if (citaSeleccionada == null) {
            JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar una cita.",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Validar que hay un médico seleccionado
        if (cmbMedicos.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar un médico.",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            // Actualizar médico
            Medico medicoSeleccionado = (Medico) cmbMedicos.getSelectedItem();
            citaSeleccionada.setMedico(medicoSeleccionado);

            // Actualizar observaciones
            citaSeleccionada.setObservaciones(txtObservaciones.getText().trim());

            // Actualizar diagnósticos
            ArrayList<String> diagnosticos = new ArrayList<>();
            for (int i = 0; i < modeloDiagnosticos.getSize(); i++) {
                diagnosticos.add(modeloDiagnosticos.getElementAt(i));
            }
            citaSeleccionada.setDiagnosticos(diagnosticos);

            // Actualizar tratamientos
            ArrayList<String> tratamientos = new ArrayList<>();
            for (int i = 0; i < modeloTratamientos.getSize(); i++) {
                tratamientos.add(modeloTratamientos.getElementAt(i));
            }
            citaSeleccionada.setTratamientos(tratamientos);

            // Actualizar la cita en el sistema (esto llama automáticamente a guardarDatos())
            boolean exito = sistema.actualizarCita(citaSeleccionada);

            if (exito) {
                JOptionPane.showMessageDialog(
                    this,
                    "Cita atendida y actualizada exitosamente.\nID: " + citaSeleccionada.getIdentificacion(),
                    "Cita actualizada",
                    JOptionPane.INFORMATION_MESSAGE
                );

                // Cerrar la ventana después de guardar
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar la cita.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al guardar la atención: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        txtPacienteId.setText("");
        txtPacienteNombre.setText("");
        cmbMedicos.setSelectedIndex(-1);
        txtObservaciones.setText("");
        modeloDiagnosticos.clear();
        modeloTratamientos.clear();
        txtDiagnostico.setText("");
        txtTratamiento.setText("");
    }
}
