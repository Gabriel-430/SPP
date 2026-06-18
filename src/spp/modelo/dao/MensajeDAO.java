package spp.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dto.Mensaje;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 18/06/2026
 * Descripción: Maneja el acceso a datos para la persistencia y recuperación de mensajes.
 */
public class MensajeDAO {

    public boolean guardarMensaje(Mensaje mensaje) {
        boolean exito = false;
        String query = "INSERT INTO mensaje (asunto, cuerpoMensaje, esBorrador, fechaHoraEnvio, fueLeido, id_remitente, id_destinatario) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement declaracion = conexion.prepareStatement(query)) {

            declaracion.setString(1, mensaje.getAsunto());
            declaracion.setString(2, mensaje.getContenido());

            boolean esBorrador = "EsBorrador".equals(mensaje.getEstado());
            declaracion.setBoolean(3, esBorrador);

            declaracion.setDate(4, mensaje.getFecha() != null ? Date.valueOf(mensaje.getFecha()) : null);
            declaracion.setBoolean(5, false);
            declaracion.setInt(6, mensaje.getIdUsuarioRemitente());
            declaracion.setInt(7, mensaje.getIdUsuarioDestinatario());

            int filasAfectadas = declaracion.executeUpdate();
            if (filasAfectadas > 0) {
                exito = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exito;
    }

    public List<Mensaje> recuperarMensajes(int idUsuarioDestinatario) throws SQLException {
        List<Mensaje> mensajes = new ArrayList<>();
        String query = "SELECT * FROM mensaje WHERE id_destinatario = ?";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement declaracion = conexion.prepareStatement(query)) {

            declaracion.setInt(1, idUsuarioDestinatario);
            try (ResultSet resultado = declaracion.executeQuery()) {
                while (resultado.next()) {
                    Mensaje mensaje = new Mensaje();
                    mensaje.setIdMensaje(resultado.getInt("id_mensaje"));
                    mensaje.setAsunto(resultado.getString("asunto"));
                    mensaje.setContenido(resultado.getString("cuerpoMensaje"));

                    Date sqlDate = resultado.getDate("fechaHoraEnvio");
                    if (sqlDate != null) {
                        mensaje.setFecha(sqlDate.toLocalDate());
                    }

                    boolean esBorrador = resultado.getBoolean("esBorrador");
                    mensaje.setEstado(esBorrador ? "EsBorrador" : "Enviado");

                    mensaje.setIdUsuarioRemitente(resultado.getInt("id_remitente"));
                    mensaje.setIdUsuarioDestinatario(resultado.getInt("id_destinatario"));

                    mensajes.add(mensaje);
                }
            }
        }
        return mensajes;
    }

    public Mensaje recuperarDetalles(int idMensaje) throws SQLException {
        Mensaje mensaje = null;
        String query = "SELECT * FROM mensaje WHERE id_mensaje = ?";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement declaracion = conexion.prepareStatement(query)) {

            declaracion.setInt(1, idMensaje);
            try (ResultSet resultado = declaracion.executeQuery()) {
                if (resultado.next()) {
                    mensaje = new Mensaje();
                    mensaje.setIdMensaje(resultado.getInt("id_mensaje"));
                    mensaje.setAsunto(resultado.getString("asunto"));
                    mensaje.setContenido(resultado.getString("cuerpoMensaje"));

                    Date sqlDate = resultado.getDate("fechaHoraEnvio");
                    if (sqlDate != null) {
                        mensaje.setFecha(sqlDate.toLocalDate());
                    }

                    boolean esBorrador = resultado.getBoolean("esBorrador");
                    mensaje.setEstado(esBorrador ? "EsBorrador" : "Enviado");

                    mensaje.setIdUsuarioRemitente(resultado.getInt("id_remitente"));
                    mensaje.setIdUsuarioDestinatario(resultado.getInt("id_destinatario"));
                }
            }
        }
        return mensaje;
    }

    public boolean eliminarMensaje(int idMensaje) throws SQLException {
        boolean exito = false;
        String query = "DELETE FROM mensaje WHERE id_mensaje = ?";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement declaracion = conexion.prepareStatement(query)) {

            declaracion.setInt(1, idMensaje);
            int filasAfectadas = declaracion.executeUpdate();
            if (filasAfectadas > 0) {
                exito = true;
            }
        }
        return exito;
    }
}
