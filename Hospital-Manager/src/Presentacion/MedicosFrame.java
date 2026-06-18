/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Aplicacion.SistemaHospital;
import Conceptos.Medico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author matar
 */

public class MedicosFrame extends JFrame {

    private SistemaHospital sistema;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtPuesto;
    private JTextField txtTelefono;

    private JButton btnNuevo;
    private JButton btnModificar;
    private JButton btnBorrar;
    private JButton btnSalir;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public MedicosFrame(SistemaHospital sistema) {
        this.sistema = sistema;
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        setTitle("Medicos");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ── Panel superior (campos + botones) ──
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 5));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JPanel panelCampos = new JPanel(new GridLayout(2, 4, 8, 5));

        panelCampos.add(new JLabel("ID"));
        txtId = new JTextField();
        panelCampos.add(txtId);

        panelCampos.add(new JLabel("Nombre"));
        txtNombre = new JTextField();
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Puesto"));
        txtPuesto = new JTextField();
        panelCampos.add(txtPuesto);

        panelCampos.add(new JLabel("Telefono"));
        txtTelefono = new JTextField();
        panelCampos.add(txtTelefono);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 5, 5));
        btnNuevo     = new JButton("Nuevo");
        btnModificar = new JButton("Modificar");
        btnBorrar    = new JButton("Borrar");

        btnNuevo.addActionListener(e -> accionNuevo());
        btnModificar.addActionListener(e -> accionModificar());
        btnBorrar.addActionListener(e -> accionBorrar());

        panelBotones.add(btnNuevo);
        panelBotones.add(btnModificar);
        panelBotones.add(btnBorrar);

        panelSuperior.add(panelCampos, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.EAST);

        // ── Tabla ──
        String[] columnas = {"ID", "Nombre", "Puesto", "Telefono"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarFilaSeleccionada();
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // ── Botón Salir ──
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> dispose());
        panelInferior.add(btnSalir);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Medico m : sistema.getMedicos()) {
            modeloTabla.addRow(new Object[]{
                m.getIdentificacion(),
                m.getNombre(),
                m.getPuesto(),
                m.getTelefono()
            });
        }
    }

    private void cargarFilaSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;

        txtId.setText((String) modeloTabla.getValueAt(fila, 0));
        txtNombre.setText((String) modeloTabla.getValueAt(fila, 1));
        txtPuesto.setText((String) modeloTabla.getValueAt(fila, 2));
        txtTelefono.setText((String) modeloTabla.getValueAt(fila, 3));

        txtId.setEditable(false);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtPuesto.setText("");
        txtTelefono.setText("");
        txtId.setEditable(true);
        tabla.clearSelection();
    }

    private void accionNuevo() {
        limpiarCampos();

        int opcion = JOptionPane.showConfirmDialog(this,
            buildFormPanel(), "Nuevo Médico", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String id     = txtId.getText().trim();
            String nombre = txtNombre.getText().trim();
            String puesto = txtPuesto.getText().trim();
            String tel    = txtTelefono.getText().trim();

            if (id.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "ID y Nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Medico m = new Medico(id, nombre, tel, puesto);
            boolean ok = sistema.agregarMedico(m);
            if (ok) {
                cargarTabla();
                JOptionPane.showMessageDialog(this,
                    "Médico guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Ya existe un médico con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        limpiarCampos();
    }

    private void accionModificar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un médico de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this,
            buildFormPanel(), "Modificar Médico", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String id     = txtId.getText().trim();
            String nombre = txtNombre.getText().trim();
            String puesto = txtPuesto.getText().trim();
            String tel    = txtTelefono.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Medico m = new Medico(id, nombre, tel, puesto);
            boolean ok = sistema.modificarMedico(m);
            if (ok) {
                cargarTabla();
                JOptionPane.showMessageDialog(this,
                    "Médico modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se pudo modificar el médico.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        limpiarCampos();
    }

    private void accionBorrar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un médico de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id     = (String) modeloTabla.getValueAt(fila, 0);
        String nombre = (String) modeloTabla.getValueAt(fila, 1);

        int conf = JOptionPane.showConfirmDialog(this,
            "¿Eliminar al médico " + nombre + " (ID: " + id + ")?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (conf == JOptionPane.YES_OPTION) {
            boolean ok = sistema.eliminarMedico(id);
            if (ok) {
                cargarTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(this,
                    "Médico eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se pudo eliminar el médico.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel buildFormPanel() {
        JPanel p = new JPanel(new GridLayout(4, 2, 8, 5));
        p.add(new JLabel("ID:"));       p.add(txtId);
        p.add(new JLabel("Nombre:"));   p.add(txtNombre);
        p.add(new JLabel("Puesto:"));   p.add(txtPuesto);
        p.add(new JLabel("Telefono:")); p.add(txtTelefono);
        return p;
    }
}
