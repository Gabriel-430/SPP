package spp.modelo.dto;

import java.time.LocalDate;

public class Evaluacion {

    private int idEvaluacion;
    private int calificacionNumerica;
    private String observaciones;
    private LocalDate fechaEvaluacion;
    private PracticaProfesional practica;
    private CatalogoEvaluacion catalogo;

    public Evaluacion() {
    }

    public Evaluacion(int idEvaluacion, int calificacionNumerica, String observaciones, LocalDate fechaEvaluacion,
            PracticaProfesional practica, CatalogoEvaluacion catalogo) {
        this.idEvaluacion = idEvaluacion;
        this.calificacionNumerica = calificacionNumerica;
        this.observaciones = observaciones;
        this.fechaEvaluacion = fechaEvaluacion;
        this.practica = practica;
        this.catalogo = catalogo;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public int getCalificacionNumerica() {
        return calificacionNumerica;
    }

    public void setCalificacionNumerica(int calificacionNumerica) {
        this.calificacionNumerica = calificacionNumerica;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(LocalDate fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public PracticaProfesional getPractica() {
        return practica;
    }

    public void setPractica(PracticaProfesional practica) {
        this.practica = practica;
    }

    public CatalogoEvaluacion getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(CatalogoEvaluacion catalogo) {
        this.catalogo = catalogo;
    }
}
