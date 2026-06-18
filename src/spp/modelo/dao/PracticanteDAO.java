package spp.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dto.Practicante;
import spp.modelo.dto.Usuario;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Proporciona operaciones de consulta y registro para
 * practicantes del Sistema de Prácticas Profesionales.
 */
public class PracticanteDAO {

    public List<Practicante> obtenerPracticantesPorCurso(int idCurso) throws SQLException {
        List<Practicante> practicantes = new ArrayList<>();
        // Consulta del caso de uso actual (Mostrar entregables)
        String consulta = "SELECT DISTINCT p.id_practicante, p.matricula, p.sexo, p.lenguaIndigena, "
                + "p.creditosAcumulados, p.seguroMedicoVigente, p.id_curso, u.id_usuario, u.nombreUsuario, "
                + "u.nombreCompleto, u.contrasenia, u.telefono, u.correoElectronico, u.estadoActivo "
                + "FROM Practicante p "
                + "INNER JOIN Usuario u ON p.id_usuario = u.id_usuario "
                + "INNER JOIN PracticaProfesional pp ON pp.id_practicante = p.id_practicante "
                + "INNER JOIN Documento d ON d.id_practica = pp.id_practica "
                + "WHERE p.id_curso = ? ORDER BY u.nombreCompleto";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idCurso);
            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    practicantes.add(mapearPracticante(resultado));
                }
            }
        }
        return practicantes;
    }

    public List<Practicante> obtenerPracticantes() throws SQLException {
        List<Practicante> practicantes = new ArrayList<>();
        String consulta = "SELECT p.id_practicante, p.matricula, p.sexo, p.lenguaIndigena, "
                + "p.creditosAcumulados, p.seguroMedicoVigente, p.id_curso, u.id_usuario, u.nombreUsuario, "
                + "u.nombreCompleto, u.contrasenia, u.telefono, u.correoElectronico, u.estadoActivo "
                + "FROM Practicante p "
                + "INNER JOIN Usuario u ON p.id_usuario = u.id_usuario "
                + "ORDER BY u.nombreCompleto";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement sentencia = conexion.prepareStatement(consulta); ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                practicantes.add(mapearPracticante(resultado));
            }
        }
        return practicantes;
    }

    public boolean existeMatricula(String matricula) throws SQLException {
        String consulta = "SELECT COUNT(*) AS total "
                + "FROM Practicante "
                + "WHERE matricula = ?";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setString(1, matricula);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() && resultado.getInt("total") > 0;
            }
        }
    }

    public void registrarPracticante(Practicante practicante)
            throws SQLException {
        Connection conexion = null;

        try {
            conexion = MySQLConnectionManager.obtenerConexion();
            conexion.setAutoCommit(false);

            int idUsuario = registrarUsuario(conexion,
                    practicante.getUsuario());
            practicante.getUsuario().setIdUsuario(idUsuario);

            insertarPracticante(conexion, practicante);

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
        String sentenciaSql = "INSERT INTO Usuario (nombreUsuario, "
                + "nombreCompleto, contrasenia, telefono, correoElectronico, "
                + "estadoActivo) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(
                sentenciaSql, Statement.RETURN_GENERATED_KEYS)) {

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

    private void insertarPracticante(Connection conexion,
            Practicante practicante) throws SQLException {
        String sentenciaSql = "INSERT INTO Practicante (matricula, sexo, "
                + "lenguaIndigena, creditosAcumulados, seguroMedicoVigente, "
                + "id_curso, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(
                sentenciaSql)) {

            sentencia.setString(1, practicante.getMatricula());
            sentencia.setString(2, practicante.getSexo());
            sentencia.setString(3, practicante.getLenguaIndigena());
            sentencia.setInt(4, practicante.getCreditosAcumulados());
            sentencia.setBoolean(5, practicante.isSeguroMedicoVigente());
            sentencia.setInt(6, practicante.getCurso().getIdCurso());
            sentencia.setInt(7, practicante.getUsuario().getIdUsuario());

            sentencia.executeUpdate();
        }
    }

    private Practicante mapearPracticante(ResultSet resultado)
            throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setIdUsuario(resultado.getInt("id_usuario"));
        usuario.setNombreUsuario(resultado.getString("nombreUsuario"));
        usuario.setNombreCompleto(resultado.getString("nombreCompleto"));
        usuario.setContrasenia(resultado.getString("contrasenia"));
        usuario.setTelefono(resultado.getString("telefono"));
        usuario.setCorreoElectronico(resultado.getString("correoElectronico"));
        usuario.setEstadoActivo(resultado.getBoolean("estadoActivo"));

        Practicante practicante = new Practicante();

        practicante.setIdPracticante(resultado.getInt("id_practicante"));
        practicante.setMatricula(resultado.getString("matricula"));
        practicante.setSexo(resultado.getString("sexo"));
        practicante.setLenguaIndigena(resultado.getString("lenguaIndigena"));
        practicante.setCreditosAcumulados(resultado.getInt(
                "creditosAcumulados"));
        practicante.setSeguroMedicoVigente(resultado.getBoolean(
                "seguroMedicoVigente"));
        practicante.setUsuario(usuario);

        return practicante;
    }

    public List<Practicante> recuperarPracticantesConDocumentos(int idProfesor) throws SQLException {
        List<Practicante> practicantes = new ArrayList<>();
        String consulta = "SELECT DISTINCT p.id_practicante, p.matricula, p.sexo, p.lenguaIndigena, "
                + "p.creditosAcumulados, p.seguroMedicoVigente, p.id_curso, u.id_usuario, u.nombreUsuario, "
                + "u.nombreCompleto, u.contrasenia, u.telefono, u.correoElectronico, u.estadoActivo "
                + "FROM Practicante p "
                + "INNER JOIN Usuario u ON p.id_usuario = u.id_usuario "
                + "INNER JOIN PracticaProfesional pp ON pp.id_practicante = p.id_practicante "
                + "INNER JOIN Documento d ON d.id_practica = pp.id_practica "
                + "INNER JOIN Curso c ON p.id_curso = c.id_curso "
                + "WHERE c.id_profesor = ? ORDER BY u.nombreCompleto";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idProfesor);
            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    practicantes.add(mapearPracticante(resultado));
                }
            }
        }
        return practicantes;
    }
}
