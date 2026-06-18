package spp.modelo.dto;

public class CalificacionFinal {

    private String idCalificacionFinal;
    private String tipo;
    private float promedioTotal;
    private String estado;
    private PracticaProfesional practica;

    public CalificacionFinal() {
    }

    public CalificacionFinal(String idCalificacionFinal, String tipo, float promedioTotal, String estado, PracticaProfesional practica) {
        this.idCalificacionFinal = idCalificacionFinal;
        this.tipo = tipo;
        this.promedioTotal = promedioTotal;
        this.estado = estado;
        this.practica = practica;
    }

    public String getIdCalificacionFinal() {
        return idCalificacionFinal;
    }

    public void setIdCalificacionFinal(String idCalificacionFinal) {
        this.idCalificacionFinal = idCalificacionFinal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPromedioTotal() {
        return promedioTotal;
    }

    public void setPromedioTotal(float promedioTotal) {
        this.promedioTotal = promedioTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public PracticaProfesional getPractica() {
        return practica;
    }

    public void setPractica(PracticaProfesional practica) {
        this.practica = practica;
    }
}
