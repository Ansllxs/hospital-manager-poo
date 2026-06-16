package Presentacion;

import Aplicacion.SistemaHospital;
import Conceptos.Cita;
import Conceptos.Paciente;
import Conceptos.Medico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VentanaConsultarCitas extends JFrame {
    private SistemaHospital sistema;

    // Componentes de filtros
    private JTextField txtPacienteId;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JButton btnCerrar;

    // Componentes de tabla
    private JTable tablaCitas;
    private JScrollPane scrollTabla;
    private DefaultTableModel modeloTabla;

    // Nombres de columnas
    private final String[] COLUMNAS = {
        "ID Cita",
        "ID Paciente",
        "Nombre Paciente",
        "Teléfono",
        "Email",
        "Fecha",
        "Médico",
        "Observaciones",
        "Diagnósticos",
        "Tratamientos"
    };

    /**
     * Constructor que recibe el sistema hospital
     * @param sistema Instancia de SistemaHospital
     */
    public VentanaConsultarCitas(SistemaHospital sistema) {
        this.sistema = sistema;
        inicializarComponentes();
        cargarTodasLasCitas();
    }

    /**
     * Inicializa todos los componentes de la ventana
     */
    private void inicializarComponentes() {
        // Configuración de la ventana
        setTitle("Consultar citas");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ==================== PANEL SUPERIOR: FILTROS ====================
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros de búsqueda"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 0: ID Paciente
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        panelFiltros.add(new JLabel("ID Paciente:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        txtPacienteId = new JTextField(15);
        panelFiltros.add(txtPacienteId, gbc);

        // Teléfono
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        panelFiltros.add(new JLabel("Teléfono:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.3;
        txtTelefono = new JTextField(15);
        panelFiltros.add(txtTelefono, gbc);

        // Fila 1: Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        panelFiltros.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        gbc.gridwidth = 3;
        txtEmail = new JTextField(15);
        panelFiltros.add(txtEmail, gbc);

        // Fila 2: Botones
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JPanel panelBotonesFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarCitas());
        panelBotonesFiltros.add(btnBuscar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFiltros());
        panelBotonesFiltros.add(btnLimpiar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelBotonesFiltros.add(btnCerrar);

        panelFiltros.add(panelBotonesFiltros, gbc);

        add(panelFiltros, BorderLayout.NORTH);

        // ==================== PANEL CENTRAL: TABLA ====================
        modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };

        tablaCitas = new JTable(modeloTabla);
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCitas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Configurar anchos de columnas
        tablaCitas.getColumnModel().getColumn(0).setPreferredWidth(70);  // ID Cita
        tablaCitas.getColumnModel().getColumn(1).setPreferredWidth(100); // ID Paciente
        tablaCitas.getColumnModel().getColumn(2).setPreferredWidth(150); // Nombre Paciente
        tablaCitas.getColumnModel().getColumn(3).setPreferredWidth(100); // Teléfono
        tablaCitas.getColumnModel().getColumn(4).setPreferredWidth(150); // Email
        tablaCitas.getColumnModel().getColumn(5).setPreferredWidth(100); // Fecha
        tablaCitas.getColumnModel().getColumn(6).setPreferredWidth(150); // Médico
        tablaCitas.getColumnModel().getColumn(7).setPreferredWidth(200); // Observaciones
        tablaCitas.getColumnModel().getColumn(8).setPreferredWidth(200); // Diagnósticos
        tablaCitas.getColumnModel().getColumn(9).setPreferredWidth(200); // Tratamientos

        scrollTabla = new JScrollPane(tablaCitas);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Resultados"));

        add(scrollTabla, BorderLayout.CENTER);
    }

    /**
     * Carga todas las citas del sistema en la tabla
     */
    private void cargarTodasLasCitas() {
        ArrayList<Cita> todasLasCitas = sistema.getCitas();
        actualizarTabla(todasLasCitas);
    }

    /**
     * Busca citas aplicando los filtros especificados
     * Lógica AND: todos los filtros no vacíos deben cumplirse
     */
    private void buscarCitas() {
        String pacienteId = txtPacienteId.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        // Llamar al método de búsqueda del sistema
        ArrayList<Cita> citasEncontradas = sistema.buscarCitas(pacienteId, telefono, email);

        // Actualizar tabla con resultados
        actualizarTabla(citasEncontradas);

        // Mostrar mensaje si no hay resultados
        if (citasEncontradas.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No se encontraron citas con los filtros especificados.",
                "Sin resultados",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * Actualiza la tabla con la lista de citas proporcionada
     * @param citas Lista de citas a mostrar
     */
    private void actualizarTabla(ArrayList<Cita> citas) {
        // Limpiar tabla
        modeloTabla.setRowCount(0);

        // Agregar cada cita como fila
        for (Cita cita : citas) {
            Object[] fila = new Object[10];

            // ID Cita
            fila[0] = cita.getIdentificacion();

            // Datos del paciente
            Paciente paciente = cita.getPaciente();
            if (paciente != null) {
                fila[1] = paciente.getIdentificacion();
                fila[2] = paciente.getNombre();
                fila[3] = paciente.getTelefono();
                fila[4] = paciente.getEmail();
            } else {
                fila[1] = "";
                fila[2] = "";
                fila[3] = "";
                fila[4] = "";
            }

            // Fecha
            fila[5] = cita.getFecha() != null ? cita.getFecha() : "";

            // Médico
            Medico medico = cita.getMedico();
            if (medico != null) {
                fila[6] = medico.getNombre();
            } else {
                fila[6] = "Sin asignar";
            }

            // Observaciones
            fila[7] = cita.getObservaciones() != null ? cita.getObservaciones() : "";

            // Diagnósticos
            fila[8] = convertirListaATexto(cita.getDiagnosticos());

            // Tratamientos
            fila[9] = convertirListaATexto(cita.getTratamientos());

            modeloTabla.addRow(fila);
        }
    }

    /**
     * Limpia los filtros y muestra todas las citas
     */
    private void limpiarFiltros() {
        txtPacienteId.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        cargarTodasLasCitas();
    }

    /**
     * Convierte una lista de strings en un texto separado por comas
     * @param lista Lista de strings
     * @return Texto con elementos separados por comas
     */
    private String convertirListaATexto(ArrayList<String> lista) {
        if (lista == null || lista.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i));
            if (i < lista.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
