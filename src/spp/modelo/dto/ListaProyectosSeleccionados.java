package spp.modelo.dto;

import java.time.LocalDate;

public class ListaProyectosSeleccionados {

    private int idLista;
    private LocalDate fechaSeleccion;
    private int prioridad;
    private String estado;
    private String motivoRechazo;
    private Practicante practicante;
    private Proyecto proyecto;

    public ListaProyectosSeleccionados() {
    }

    public ListaProyectosSeleccionados(int idLista, LocalDate fechaSeleccion, int prioridad, String estado,
            String motivoRechazo, Practicante practicante, Proyecto proyecto) {
        this.idLista = idLista;
        this.fechaSeleccion = fechaSeleccion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.motivoRechazo = motivoRechazo;
        this.practicante = practicante;
        this.proyecto = proyecto;
    }

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }

    public LocalDate getFechaSeleccion() {
        return fechaSeleccion;
    }

    public void setFechaSeleccion(LocalDate fechaSeleccion) {
        this.fechaSeleccion = fechaSeleccion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public Practicante getPracticante() {
        return practicante;
    }

    public void setPracticante(Practicante practicante) {
        this.practicante = practicante;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}
