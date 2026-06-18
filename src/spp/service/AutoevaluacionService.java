package spp.service;

import java.io.File;
import spp.modelo.dto.Documento;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 17/06/2026
 * Descripción: Lógica de negocio específica para validar y subir Autoevaluaciones.
 */
public class AutoevaluacionService {

    private final DocumentoService documentoService;

    public AutoevaluacionService() {
        this.documentoService = new DocumentoService();
    }

    public boolean archivoYaExiste(String rutaArchivo, String matricula) {
        return documentoService.archivoYaExiste(rutaArchivo, matricula);
    }

    public boolean subirAutoevaluacion(Documento documento, String matricula, File archivoFisico) {
        return documentoService.subirDocumento(documento, matricula, archivoFisico);
    }
}
