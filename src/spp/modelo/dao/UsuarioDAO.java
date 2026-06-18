package spp.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dto.Usuario;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 16/06/2026
 * Descripción: Proporciona operaciones de acceso a datos para los
 * usuarios del Sistema de Prácticas Profesionales.
 */
public class UsuarioDAO {

    public Usuario obtenerUsuarioPorNombreUsuario(String nombreUsuario)
            throws SQLException {
        String consulta = "SELECT id_usuario, nombreUsuario, nombreCompleto, "
                + "contrasenia, telefono, correoElectronico, estadoActivo "
                + "FROM Usuario WHERE nombreUsuario = ?";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setString(1, nombreUsuario);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearUsuario(resultado);
                }
            }
        }

        return null;
    }

    public int obtenerIdDestinatarioPorNombreUsuario(String nombreUsuario) {
        int idDestinatario = -1;
        String query = "SELECT id_usuario FROM usuario WHERE nombreUsuario = ? AND estadoActivo = 1";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement declaracion = conexion.prepareStatement(query)) {

            declaracion.setString(1, nombreUsuario);
            ResultSet resultado = declaracion.executeQuery();

            if (resultado.next()) {
                idDestinatario = resultado.getInt("id_usuario");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idDestinatario;
    }

    public boolean esCoordinador(int idUsuario) throws SQLException {
        return existeUsuarioEnTabla("Coordinador", "id_usuario", idUsuario);
    }

    public boolean esProfesor(int idUsuario) throws SQLException {
        return existeUsuarioEnTabla("Profesor", "id_usuario", idUsuario);
    }

    public boolean esPracticante(int idUsuario) throws SQLException {
        return existeUsuarioEnTabla("Practicante", "id_usuario", idUsuario);
    }

    private boolean existeUsuarioEnTabla(String nombreTabla, String nombreColumna,
            int idUsuario) throws SQLException {
        String consulta = "SELECT COUNT(*) AS total FROM " + nombreTabla
                + " WHERE " + nombreColumna + " = ?";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idUsuario);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() && resultado.getInt("total") > 0;
            }
        }
    }

    private Usuario mapearUsuario(ResultSet resultado) throws SQLException {
        Usuario usuario = new Usuario();

        usuario.setIdUsuario(resultado.getInt("id_usuario"));
        usuario.setNombreUsuario(resultado.getString("nombreUsuario"));
        usuario.setNombreCompleto(resultado.getString("nombreCompleto"));
        usuario.setContrasenia(resultado.getString("contrasenia"));
        usuario.setTelefono(resultado.getString("telefono"));
        usuario.setCorreoElectronico(resultado.getString("correoElectronico"));
        usuario.setEstadoActivo(resultado.getBoolean("estadoActivo"));

        return usuario;
    }
}
