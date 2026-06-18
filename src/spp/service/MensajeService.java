package spp.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import spp.modelo.dao.MensajeDAO;
import spp.modelo.dao.UsuarioDAO;
import spp.modelo.dto.Mensaje;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 18/06/2026
 * Descripción: Controla la lógica de negocio para los mensajes.
 */
public class MensajeService {

    private final MensajeDAO mensajeDAO;
    private final UsuarioDAO usuarioDAO;

    public MensajeService() {
        this.mensajeDAO = new MensajeDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean verificarDestinatarioExiste(String nombreDestinatario) {
        int idDestinatario = usuarioDAO.obtenerIdDestinatarioPorNombreUsuario(nombreDestinatario);
        return idDestinatario > 0;
    }

    public Mensaje crearMensaje(String asunto, String contenido) {
        Mensaje mensaje = new Mensaje();
        mensaje.setAsunto(asunto != null ? asunto : "");
        mensaje.setContenido(contenido != null ? contenido : "");
        mensaje.setFecha(LocalDate.now());
        return mensaje;
    }

    public void vincularMensajeConDestinatarioYRemitente(Mensaje mensaje, String nombreDestinatario, int idRemitente) {
        int idDestinatario = usuarioDAO.obtenerIdDestinatarioPorNombreUsuario(nombreDestinatario);
        mensaje.setIdUsuarioRemitente(idRemitente);

        if (idDestinatario > 0) {
            mensaje.setIdUsuarioDestinatario(idDestinatario);
        } else {
            mensaje.setIdUsuarioDestinatario(idRemitente);
        }
    }

    public boolean enviarMensaje(Mensaje mensaje) {
        mensaje.setEstado("Enviado");
        return mensajeDAO.guardarMensaje(mensaje);
    }

    public boolean guardarBorrador(Mensaje mensaje) {
        mensaje.setEstado("EsBorrador");
        return mensajeDAO.guardarMensaje(mensaje);
    }

    public List<Mensaje> recuperarMensajes(int idUsuario) {
        try {
            return mensajeDAO.recuperarMensajes(idUsuario);
        } catch (SQLException e) {
            return null;
        }
    }

    public Mensaje recuperarDetalles(int idMensaje) {
        try {
            return mensajeDAO.recuperarDetalles(idMensaje);
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean eliminarMensaje(int idMensaje) {
        try {
            return mensajeDAO.eliminarMensaje(idMensaje);
        } catch (SQLException e) {
            return false;
        }
    }
}
