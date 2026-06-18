package spp.modelo.dto;

public class Profesor {

    private int idProfesor;
    private int numeroPersonal;
    private String turno;
    private Usuario usuario;

    public Profesor() {
    }

    public Profesor(int idProfesor, int numeroPersonal, String turno, Usuario usuario) {
        this.idProfesor = idProfesor;
        this.numeroPersonal = numeroPersonal;
        this.turno = turno;
        this.usuario = usuario;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(int numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
