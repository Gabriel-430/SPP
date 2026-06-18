package spp.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dto.Profesor;
import spp.modelo.dto.Usuario;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Proporciona operaciones de consulta para profesores del
 * Sistema de Prácticas Profesionales.
 */
public class ProfesorDAO {

  public List<Profesor> obtenerProfesores() throws SQLException {
    List<Profesor> profesores = new ArrayList<>();

    String consulta = "SELECT p.id_profesor, p.numeroPersonal, p.turno, "
        + "u.id_usuario, u.nombreUsuario, u.nombreCompleto, u.contrasenia, "
        + "u.telefono, u.correoElectronico, u.estadoActivo "
        + "FROM Profesor p "
        + "INNER JOIN Usuario u ON p.id_usuario = u.id_usuario "
        + "ORDER BY u.nombreCompleto";

    try (Connection conexion = MySQLConnectionManager.obtenerConexion();
        PreparedStatement sentencia = conexion.prepareStatement(consulta);
        ResultSet resultado = sentencia.executeQuery()) {

      while (resultado.next()) {
        profesores.add(mapearProfesor(resultado));
      }
    }

    return profesores;
  }

  public boolean existeNumeroPersonal(int numeroPersonal) throws SQLException {
    String consulta = "SELECT COUNT(*) AS total "
        + "FROM Profesor "
        + "WHERE numeroPersonal = ?";

    try (Connection conexion = MySQLConnectionManager.obtenerConexion();
        PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

      sentencia.setInt(1, numeroPersonal);

      try (ResultSet resultado = sentencia.executeQuery()) {
        return resultado.next() && resultado.getInt("total") > 0;
      }
    }
  }

  public void registrarProfesorConCurso(Profesor profesor, int idCurso)
      throws SQLException {
    Connection conexion = null;

    try {
      conexion = MySQLConnectionManager.obtenerConexion();
      conexion.setAutoCommit(false);

      int idUsuario = registrarUsuario(conexion, profesor.getUsuario());
      profesor.getUsuario().setIdUsuario(idUsuario);

      int idProfesor = registrarProfesor(conexion, profesor);
      profesor.setIdProfesor(idProfesor);

      asignarProfesorACurso(conexion, idProfesor, idCurso);

      conexion.commit();

    } catch (SQLException excepcion) {
      if (conexion != null) {
        conexion.rollback();
      }

      throw excepcion;

    } finally {
      if (conexion != null) {
        conexion.setAutoCommit(true);
        conexion.close();
      }
    }
  }

  private int registrarUsuario(Connection conexion, Usuario usuario)
      throws SQLException {
    String sentenciaSql = "INSERT INTO Usuario (nombreUsuario, nombreCompleto, "
        + "contrasenia, telefono, correoElectronico, estadoActivo) "
        + "VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql,
        Statement.RETURN_GENERATED_KEYS)) {

      sentencia.setString(1, usuario.getNombreUsuario());
      sentencia.setString(2, usuario.getNombreCompleto());
      sentencia.setString(3, usuario.getContrasenia());
      sentencia.setString(4, usuario.getTelefono());
      sentencia.setString(5, usuario.getCorreoElectronico());
      sentencia.setBoolean(6, usuario.isEstadoActivo());

      sentencia.executeUpdate();

      try (ResultSet resultado = sentencia.getGeneratedKeys()) {
        if (resultado.next()) {
          return resultado.getInt(1);
        }
      }
    }

    throw new SQLException("No fue posible registrar el usuario.");
  }

  private int registrarProfesor(Connection conexion, Profesor profesor)
      throws SQLException {
    String sentenciaSql = "INSERT INTO Profesor (numeroPersonal, turno, "
        + "id_usuario) VALUES (?, ?, ?)";

    try (PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql,
        Statement.RETURN_GENERATED_KEYS)) {

      sentencia.setInt(1, profesor.getNumeroPersonal());
      sentencia.setString(2, profesor.getTurno());
      sentencia.setInt(3, profesor.getUsuario().getIdUsuario());

      sentencia.executeUpdate();

      try (ResultSet resultado = sentencia.getGeneratedKeys()) {
        if (resultado.next()) {
          return resultado.getInt(1);
        }
      }
    }

    throw new SQLException("No fue posible registrar el profesor.");
  }

  private void asignarProfesorACurso(Connection conexion, int idProfesor,
      int idCurso) throws SQLException {
    String sentenciaSql = "UPDATE Curso "
        + "SET id_profesor = ? "
        + "WHERE id_curso = ?";

    try (PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql)) {
      sentencia.setInt(1, idProfesor);
      sentencia.setInt(2, idCurso);
      sentencia.executeUpdate();
    }
  }

  private Profesor mapearProfesor(ResultSet resultado) throws SQLException {
    Usuario usuario = new Usuario();

    usuario.setIdUsuario(resultado.getInt("id_usuario"));
    usuario.setNombreUsuario(resultado.getString("nombreUsuario"));
    usuario.setNombreCompleto(resultado.getString("nombreCompleto"));
    usuario.setContrasenia(resultado.getString("contrasenia"));
    usuario.setTelefono(resultado.getString("telefono"));
    usuario.setCorreoElectronico(resultado.getString("correoElectronico"));
    usuario.setEstadoActivo(resultado.getBoolean("estadoActivo"));

    Profesor profesor = new Profesor();

    profesor.setIdProfesor(resultado.getInt("id_profesor"));
    profesor.setNumeroPersonal(resultado.getInt("numeroPersonal"));
    profesor.setTurno(resultado.getString("turno"));
    profesor.setUsuario(usuario);

    return profesor;
  }
}