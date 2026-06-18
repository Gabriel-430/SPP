package spp.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dto.Curso;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Proporciona operaciones de acceso a datos para la gestión
 * de cursos dentro del Sistema de Prácticas Profesionales.
 */
public class CursoDAO {

  public List<Curso> obtenerCursosSinProfesor() throws SQLException {
    List<Curso> cursos = new ArrayList<>();

    String consulta = "SELECT id_curso, nrc, bloque, cupoAlumnos, nombre, "
        + "creditos, estado "
        + "FROM Curso "
        + "WHERE id_profesor IS NULL "
        + "ORDER BY nombre, bloque";

    try (Connection conexion = MySQLConnectionManager.obtenerConexion();
        PreparedStatement sentencia = conexion.prepareStatement(consulta);
        ResultSet resultado = sentencia.executeQuery()) {

      while (resultado.next()) {
        cursos.add(mapearCurso(resultado));
      }
    }

    return cursos;
  }

  public List<Curso> obtenerCursosActivos() throws SQLException {
    List<Curso> cursos = new ArrayList<>();

    String consulta = "SELECT id_curso, nrc, bloque, cupoAlumnos, nombre, "
        + "creditos, estado "
        + "FROM Curso "
        + "WHERE estado = 'Activo' "
        + "ORDER BY nombre, bloque";

    try (Connection conexion = MySQLConnectionManager.obtenerConexion();
        PreparedStatement sentencia = conexion.prepareStatement(consulta);
        ResultSet resultado = sentencia.executeQuery()) {

      while (resultado.next()) {
        cursos.add(mapearCurso(resultado));
      }
    }

    return cursos;
  }

  private Curso mapearCurso(ResultSet resultado) throws SQLException {
    Curso curso = new Curso();

    curso.setIdCurso(resultado.getInt("id_curso"));
    curso.setNrc(resultado.getString("nrc"));
    curso.setBloque(resultado.getString("bloque"));
    curso.setCupoAlumnos(resultado.getInt("cupoAlumnos"));
    curso.setNombre(resultado.getString("nombre"));
    curso.setCreditos(resultado.getInt("creditos"));
    curso.setEstado(resultado.getString("estado"));

    return curso;
  }
}

