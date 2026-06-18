package spp.modelo.dto;

import java.time.LocalDate;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 18/06/2026
 * Descripción: Representa la entidad Mensaje que puede ser enviada entre Usuarios.
 */
public class Mensaje {

    private int idMensaje;
    private String asunto;
    private String contenido;
    private LocalDate fecha;
    private String estado;
    private int idUsuarioRemitente;
    private int idUsuarioDestinatario;

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdUsuarioRemitente() {
        return idUsuarioRemitente;
    }

    public void setIdUsuarioRemitente(int idUsuarioRemitente) {
        this.idUsuarioRemitente = idUsuarioRemitente;
    }

    public int getIdUsuarioDestinatario() {
        return idUsuarioDestinatario;
    }

    public void setIdUsuarioDestinatario(int idUsuarioDestinatario) {
        this.idUsuarioDestinatario = idUsuarioDestinatario;
    }
}
