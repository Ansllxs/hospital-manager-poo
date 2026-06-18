/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Aplicacion.SistemaHospital;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author matar
 */

public class VentanaPrincipal extends JFrame {

    private SistemaHospital sistema;

    public VentanaPrincipal(SistemaHospital sistema) {
        this.sistema = sistema;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Hospital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ── Barra de menú ──
        JMenuBar menuBar = new JMenuBar();
        JMenu menuUtil = new JMenu("Util");

        JMenuItem itemExportar = new JMenuItem("Exportar");
        JMenuItem itemImportar = new JMenuItem("Importar");
        JMenuItem itemLimpiar = new JMenuItem("Limpiar");

        itemExportar.addActionListener(e -> accionExportar());
        itemImportar.addActionListener(e -> accionImportar());
        itemLimpiar.addActionListener(e -> accionLimpiar());

        menuUtil.add(itemExportar);
        menuUtil.add(itemImportar);
        menuUtil.add(itemLimpiar);
        menuBar.add(menuUtil);
        setJMenuBar(menuBar);

        // ── Panel de citas (fila superior) ──
        JPanel panelCitas = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelCitas.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        panelCitas.setBackground(new Color(230, 240, 255));

        panelCitas.add(crearBotonIcono("Consultar", "Imagenes/consultar.png", e -> abrirConsultar()));
        panelCitas.add(crearBotonIcono("Solicitar",  "Imagenes/solicitar.png",  e -> abrirSolicitar()));
        panelCitas.add(crearBotonIcono("Atender",    "Imagenes/atender.png",    e -> abrirAtender()));

        // ── Panel de personas (fila inferior) ──
        JPanel panelPersonas = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panelPersonas.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        panelPersonas.setBackground(new Color(230, 240, 255));

        panelPersonas.add(crearBotonIcono("Pacientes", "Imagenes/pacientes.png", e -> abrirPacientes()));
        panelPersonas.add(crearBotonIcono("Medicos",   "Imagenes/medicos.png",   e -> abrirMedicos()));

        // ── Panel central que agrupa ambas filas ──
        JPanel panelCentral = new JPanel(new GridLayout(2, 1));
        panelCentral.setBackground(new Color(230, 240, 255));
        panelCentral.add(panelCitas);
        panelCentral.add(panelPersonas);

        add(panelCentral, BorderLayout.CENTER);

        pack();
        setMinimumSize(new Dimension(600, 350));
        setLocationRelativeTo(null);
    }

    // ── Crea un botón con imagen encima y texto debajo ──
    private JPanel crearBotonIcono(String texto, String rutaImagen,
                                    java.awt.event.ActionListener listener) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setOpaque(false);

        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(120, 100));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Intentar cargar imagen desde archivo
        try {
            java.io.File imgFile = new java.io.File(rutaImagen);
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(rutaImagen);
                Image img = icon.getImage().getScaledInstance(100, 90, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(img));
            } else {
                // Imagen de respaldo: texto en el botón
                btn.setText("[ " + texto + " ]");
                btn.setFont(new Font("Arial", Font.PLAIN, 11));
                btn.setBorderPainted(true);
                btn.setContentAreaFilled(true);
            }
        } catch (Exception ex) {
            btn.setText("[ " + texto + " ]");
        }

        btn.addActionListener(listener);

        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(btn, BorderLayout.CENTER);
        panel.add(lbl, BorderLayout.SOUTH);
        return panel;
    }

    // ── Acciones de menú ──
    private void accionExportar() {
        boolean ok = sistema.exportarDatos();
        if (ok) {
            JOptionPane.showMessageDialog(this,
                "Datos exportados exitosamente a:\n- Export/pacientes.xml\n- Export/medicos.xml\n- Export/citas.xml",
                "Exportar", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al exportar los datos.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionImportar() {
        int conf = JOptionPane.showConfirmDialog(this,
            "¿Importar desde XML? Esto reemplazará todos los datos actuales.",
            "Importar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (conf == JOptionPane.YES_OPTION) {
            boolean ok = sistema.importarDatos();
            if (ok) {
                JOptionPane.showMessageDialog(this,
                    "Datos importados correctamente.\nPacientes: " + sistema.getPacientes().size() +
                    "\nMédicos: " + sistema.getMedicos().size() +
                    "\nCitas: " + sistema.getCitas().size(),
                    "Importar", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al importar. Verifique que existan los XML en Export/",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void accionLimpiar() {
        int conf = JOptionPane.showConfirmDialog(this,
            "¿Limpiar todos los datos? Los archivos .DAT serán vaciados.",
            "Limpiar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (conf == JOptionPane.YES_OPTION) {
            sistema.limpiarDatos();
            JOptionPane.showMessageDialog(this,
                "Todos los datos fueron eliminados.",
                "Limpiar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ── Apertura de ventanas ──
    private void abrirConsultar() {
        new VentanaConsultarCitas(sistema).setVisible(true);
    }

    private void abrirSolicitar() {
        new VentanaSolicitarCita(sistema).setVisible(true);
    }

    private void abrirAtender() {
        new VentanaAtenderCita(sistema).setVisible(true);
    }

    private void abrirPacientes() {
        new PacientesFrame(sistema).setVisible(true);
    }

    private void abrirMedicos() {
        new MedicosFrame(sistema).setVisible(true);
    }
}
