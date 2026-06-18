package spp.modelo.dao;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 17/06/2026
 * Descripción: Maneja el acceso a datos de la entidad Reporte.
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dto.Reporte;

public class ReporteDAO {

  public boolean guardarReporte(Reporte reporte) throws SQLException {
    boolean exito = false;

    String updateDocumento = "UPDATE documento SET estado = ? "
        + "WHERE id_documento = ?";

    String insertEvaluacion = "INSERT INTO evaluacion "
        + "(calificacionNumerica, observaciones, fechaEvaluacion, id_practica) "
        + "VALUES (?, ?, ?, ?)";

    try (Connection conexion = MySQLConnectionManager.obtenerConexion()) {
      conexion.setAutoCommit(false);

      try (PreparedStatement sentenciaDocumento =
          conexion.prepareStatement(updateDocumento);
          PreparedStatement sentenciaEvaluacion =
          conexion.prepareStatement(insertEvaluacion)) {

        sentenciaDocumento.setString(1, reporte.getEstado());
        sentenciaDocumento.setInt(2, reporte.getIdDocumento());

        int filasDocumento = sentenciaDocumento.executeUpdate();

        sentenciaEvaluacion.setInt(1, reporte.getCalificacion());
        sentenciaEvaluacion.setString(2, reporte.getObservaciones());
        sentenciaEvaluacion.setDate(3, Date.valueOf(LocalDate.now()));
        sentenciaEvaluacion.setInt(4, reporte.getIdPractica());

        int filasEvaluacion = sentenciaEvaluacion.executeUpdate();

        if (filasDocumento > 0 && filasEvaluacion > 0) {
          conexion.commit();
          exito = true;
        } else {
          conexion.rollback();
        }

      } catch (SQLException excepcion) {
        conexion.rollback();
        throw excepcion;

      } finally {
        conexion.setAutoCommit(true);
      }
    }

    return exito;
  }

  public List<Reporte> recuperarReportesPendientes(int idUsuarioProfesor,
      String tipoReporte) throws SQLException {
    return obtenerReportesPendientes(idUsuarioProfesor, tipoReporte);
  }

  public List<Reporte> obtenerReportesPendientes(int idUsuarioProfesor,
      String tipoReporte) throws SQLException {
    List<Reporte> reportes = new ArrayList<>();

    String consulta = "SELECT d.id_documento, d.nombreDocumento, "
        + "d.fechaEntrega, d.fechaEmision, d.estado, d.rutaArchivo, "
        + "d.id_practica "
        + "FROM documento d "
        + "INNER JOIN catalogodocumento c "
        + "ON d.id_catalogoDocumento = c.id_catalogoDocumento "
        + "INNER JOIN practicaprofesional pp "
        + "ON d.id_practica = pp.id_practica "
        + "INNER JOIN practicante pr "
        + "ON pp.id_practicante = pr.id_practicante "
        + "INNER JOIN curso cu "
        + "ON pr.id_curso = cu.id_curso "
        + "INNER JOIN profesor prof "
        + "ON cu.id_profesor = prof.id_profesor "
        + "WHERE prof.id_usuario = ? "
        + "AND c.tipoDocumento = ? "
        + "AND d.estado = 'Pendiente'";

    try (Connection conexion = MySQLConnectionManager.obtenerConexion();
        PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

      sentencia.setInt(1, idUsuarioProfesor);
      sentencia.setString(2, tipoReporte);

      try (ResultSet resultado = sentencia.executeQuery()) {
        while (resultado.next()) {
          reportes.add(mapearReporte(resultado, tipoReporte));
        }
      }
    }

    return reportes;
  }

  public boolean evaluarReporteParcial(int idDocumento, int calificacion,
      String observacion) throws SQLException {
    Reporte reporte = new Reporte();

    reporte.setIdDocumento(idDocumento);
    reporte.setCalificacion(calificacion);
    reporte.setObservaciones(observacion);
    reporte.setEstado("Evaluado");
    reporte.setIdPractica(obtenerIdPracticaDocumento(idDocumento));

    return guardarReporte(reporte);
  }

  private int obtenerIdPracticaDocumento(int idDocumento) throws SQLException {
    String consulta = "SELECT id_practica FROM documento "
        + "WHERE id_documento = ?";

    try (Connection conexion = MySQLConnectionManager.obtenerConexion();
        PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

      sentencia.setInt(1, idDocumento);

      try (ResultSet resultado = sentencia.executeQuery()) {
        if (resultado.next()) {
          return resultado.getInt("id_practica");
        }
      }
    }

    throw new SQLException("No se encontró la práctica del documento.");
  }

  private Reporte mapearReporte(ResultSet resultado, String tipoReporte)
      throws SQLException {
    Reporte reporte = new Reporte();

    reporte.setIdDocumento(resultado.getInt("id_documento"));
    reporte.setNombreDocumento(resultado.getString("nombreDocumento"));

    Date fechaEntrega = resultado.getDate("fechaEntrega");

    if (fechaEntrega != null) {
      reporte.setFechaEntrega(fechaEntrega.toLocalDate());
    }

    Date fechaEmision = resultado.getDate("fechaEmision");

    if (fechaEmision != null) {
      reporte.setFechaEmision(fechaEmision.toLocalDate());
    }

    reporte.setEstado(resultado.getString("estado"));
    reporte.setRutaArchivo(resultado.getString("rutaArchivo"));
    reporte.setIdPractica(resultado.getInt("id_practica"));
    reporte.setTipoReporte(tipoReporte);

    return reporte;
  }
}