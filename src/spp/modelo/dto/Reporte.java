package spp.modelo.dto;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 17/06/2026
 * Descripción: Representa un Reporte e incluye información como calificación,
 * observaciones, tipo de reporte y el estado en que se encuentra.
 */
public class Reporte extends Documento {

    private int calificacion;
    private String observaciones;
    private String tipoReporte;
    private String estado;

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
