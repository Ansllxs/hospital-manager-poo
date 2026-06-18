package Aplicacion;

import Presentacion.VentanaPrincipal;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }

        SwingUtilities.invokeLater(() -> {
            SistemaHospital sistema = new SistemaHospital();
            VentanaPrincipal ventana = new VentanaPrincipal(sistema);
            ventana.setVisible(true);
        });
    }
}