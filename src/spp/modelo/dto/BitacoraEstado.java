package spp.modelo.dto;

import java.time.LocalDate;

public class BitacoraEstado {

    private int idBitacora;
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDate fechaCambio;
    private String motivo;
    private PracticaProfesional practica;
    private Usuario usuario;

    public BitacoraEstado() {
    }

    public BitacoraEstado(int idBitacora, String estadoAnterior, String estadoNuevo, LocalDate fechaCambio, String motivo, PracticaProfesional practica, Usuario usuario) {
        this.idBitacora = idBitacora;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.fechaCambio = fechaCambio;
        this.motivo = motivo;
        this.practica = practica;
        this.usuario = usuario;
    }

    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public LocalDate getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDate fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public PracticaProfesional getPractica() {
        return practica;
    }

    public void setPractica(PracticaProfesional practica) {
        this.practica = practica;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
