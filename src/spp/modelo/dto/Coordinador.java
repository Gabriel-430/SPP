package spp.modelo.dto;

public class Coordinador {

    private int idCoordinador;
    private int numeroPersonal;
    private Usuario usuario;

    public Coordinador() {
    }

    public Coordinador(int idCoordinador, int numeroPersonal, Usuario usuario) {
        this.idCoordinador = idCoordinador;
        this.numeroPersonal = numeroPersonal;
        this.usuario = usuario;
    }

    public int getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(int idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public int getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(int numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
