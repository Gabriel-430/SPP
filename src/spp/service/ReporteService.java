package spp.service;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import spp.modelo.dao.ReporteDAO;
import spp.modelo.dto.Reporte;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 18/06/2026
 * Descripción: Gestiona las operaciones relacionadas con la recuperación y
 * evaluación de reportes del Sistema de Prácticas Profesionales.
 */
public class ReporteService {

  private ReporteDAO reporteDAO;
  private DocumentoService documentoService;

  public ReporteService() {
    reporteDAO = new ReporteDAO();
    documentoService = new DocumentoService();
  }

  public boolean archivoYaExiste(String rutaArchivo, String matricula) {
    return documentoService.archivoYaExiste(rutaArchivo, matricula);
  }

  public boolean subirReporte(Reporte reporte, String matricula,
      File archivoFisico) {
    return documentoService.subirDocumento(reporte, matricula, archivoFisico);
  }

  public List<Reporte> recuperarReportesPendientes(int idUsuarioProfesor,
      String tipoReporte) throws SQLException {
    return reporteDAO.obtenerReportesPendientes(idUsuarioProfesor, tipoReporte);
  }

  public boolean evaluarReporte(Reporte reporte) throws SQLException {
    validarReporteSeleccionado(reporte);
    validarCalificacion(reporte.getCalificacion());

    if (reporte.getEstado() == null || reporte.getEstado().trim().isEmpty()) {
      reporte.setEstado("Evaluado");
    }

    return reporteDAO.guardarReporte(reporte);
  }

  public boolean evaluarReporte(Reporte reporte, int calificacion,
      String observacion) throws SQLException {
    validarReporteSeleccionado(reporte);
    validarCalificacion(calificacion);
    validarObservacion(observacion);

    reporte.setCalificacion(calificacion);
    reporte.setObservaciones(observacion);
    reporte.setEstado("Evaluado");

    return reporteDAO.guardarReporte(reporte);
  }

  public boolean evaluarReporteParcial(Reporte reporte, int calificacion,
      String observacion) throws SQLException {
    return evaluarReporte(reporte, calificacion, observacion);
  }

  private void validarReporteSeleccionado(Reporte reporte) {
    if (reporte == null) {
      throw new IllegalArgumentException("Seleccione un reporte.");
    }
  }

  private void validarCalificacion(int calificacion) {
    if (calificacion < 0 || calificacion > 100) {
      throw new IllegalArgumentException(
          "La calificación debe estar entre 0 y 100.");
    }
  }

  private void validarObservacion(String observacion) {
    if (observacion == null || observacion.trim().isEmpty()) {
      throw new IllegalArgumentException(
        "Debe agregar una observación antes de evaluar el reporte.");
    }

    if (observacion.trim().length() > 100) {
        throw new IllegalArgumentException(
        "La observación no puede superar los 100 caracteres.");
    }
  }
  
  
}
