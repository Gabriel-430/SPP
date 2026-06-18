package spp.service;

import java.sql.SQLException;
import spp.modelo.dao.UsuarioDAO;
import spp.modelo.dto.TipoUsuario;
import spp.modelo.dto.Usuario;
import spp.utilidades.EncriptadorContrasenia;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 16/06/2026
 * Descripción: Valida las credenciales de acceso y determina el tipo de
 * usuario del Sistema de Prácticas Profesionales.
 */
public class LoginService {

  private UsuarioDAO usuarioDAO;

  public LoginService() {
    usuarioDAO = new UsuarioDAO();
  }

  public Usuario iniciarSesion(String nombreUsuario, String contrasenia)
      throws SQLException {
    if (nombreUsuario == null || nombreUsuario.trim().isEmpty()
        || contrasenia == null || contrasenia.trim().isEmpty()) {
      return null;
    }

    Usuario usuario = usuarioDAO.obtenerUsuarioPorNombreUsuario(
        nombreUsuario.trim());

    if (usuario == null) {
      return null;
    }

    if (!usuario.isEstadoActivo()) {
      return null;
    }

    boolean contraseniaCorrecta = EncriptadorContrasenia.verificarContrasenia(
        contrasenia,
        usuario.getContrasenia());

    if (!contraseniaCorrecta) {
      return null;
    }

    return usuario;
  }

  public TipoUsuario obtenerTipoUsuario(int idUsuario) throws SQLException {
    if (usuarioDAO.esCoordinador(idUsuario)) {
      return TipoUsuario.COORDINADOR;
    }

    if (usuarioDAO.esProfesor(idUsuario)) {
      return TipoUsuario.PROFESOR;
    }

    if (usuarioDAO.esPracticante(idUsuario)) {
      return TipoUsuario.PRACTICANTE;
    }

    return TipoUsuario.ADMINISTRADOR;
  }
}