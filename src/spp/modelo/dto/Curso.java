package spp.modelo.dto;

public class Curso {

    private int idCurso;
    private String nrc;
    private String bloque;
    private int cupoAlumnos;
    private String nombre;
    private int creditos;
    private Profesor profesor;
    private PeriodoEscolar periodo;
    private Coordinador coordinador;
    private String estado;
    
    public Curso() {
    }

    public Curso(int idCurso, String nrc, String bloque, int cupoAlumnos, String nombre, int creditos, Profesor profesor, PeriodoEscolar periodo, Coordinador coordinador) {
        this.idCurso = idCurso;
        this.nrc = nrc;
        this.bloque = bloque;
        this.cupoAlumnos = cupoAlumnos;
        this.nombre = nombre;
        this.creditos = creditos;
        this.profesor = profesor;
        this.periodo = periodo;
        this.coordinador = coordinador;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public int getCupoAlumnos() {
        return cupoAlumnos;
    }

    public void setCupoAlumnos(int cupoAlumnos) {
        this.cupoAlumnos = cupoAlumnos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public PeriodoEscolar getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoEscolar periodo) {
        this.periodo = periodo;
    }

    public Coordinador getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
    return nrc + " - " + nombre + " - Bloque " + bloque;
    }
}
