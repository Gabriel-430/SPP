package spp.modelo.dto;

import java.time.LocalDate;

public class PracticaProfesional {

    private int idPractica;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horasAcumuladas;
    private boolean estado;
    private Practicante practicante;
    private Proyecto proyecto;

    public PracticaProfesional() {
    }

    public PracticaProfesional(int idPractica, LocalDate fechaInicio, LocalDate fechaFin, int horasAcumuladas, boolean estado, Practicante practicante, Proyecto proyecto) {
        this.idPractica = idPractica;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horasAcumuladas = horasAcumuladas;
        this.estado = estado;
        this.practicante = practicante;
        this.proyecto = proyecto;
    }

    public int getIdPractica() {
        return idPractica;
    }

    public void setIdPractica(int idPractica) {
        this.idPractica = idPractica;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getHorasAcumuladas() {
        return horasAcumuladas;
    }

    public void setHorasAcumuladas(int horasAcumuladas) {
        this.horasAcumuladas = horasAcumuladas;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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
