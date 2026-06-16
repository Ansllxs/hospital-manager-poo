package Conceptos;

import java.io.Serializable;

public class Medico implements Serializable {
    private static final long serialVersionUID = 1L;

    private String identificacion;
    private String nombre;
    private String telefono;
    private String puesto;

    public Medico() {
    }

    public Medico(String identificacion, String nombre, String telefono, String puesto) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
        this.puesto = puesto;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    @Override
    public String toString() {
        return identificacion + " - " + nombre + " (" + puesto + ")";
    }
}
