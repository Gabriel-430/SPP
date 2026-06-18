package spp.modelo.dto;

import java.time.LocalDate;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 16/06/2026
 * Descripción: Representa un documento, que puede ser Horario, Cronograma,
 * Oficio, PSP o Reporte. Contiene únicamente los atributos compartidos.
 */
public class Documento {

    private int idDocumento;
    private String nombreDocumento;
    private LocalDate fechaEntrega;
    private LocalDate fechaEmision;
    private String estado;
    private String rutaArchivo;
    private int idPractica;
    private int idCatalogoDocumento;

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public int getIdPractica() {
        return idPractica;
    }

    public void setIdPractica(int idPractica) {
        this.idPractica = idPractica;
    }

    public int getIdCatalogoDocumento() {
        return idCatalogoDocumento;
    }

    public void setIdCatalogoDocumento(int idCatalogoDocumento) {
        this.idCatalogoDocumento = idCatalogoDocumento;
    }
}
