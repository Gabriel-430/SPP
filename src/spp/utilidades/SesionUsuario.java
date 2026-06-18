package spp.utilidades;

import spp.modelo.dto.TipoUsuario;
import spp.modelo.dto.Usuario;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 16/06/2026
 * Descripción: Mantiene la información del usuario que inició sesión
 * durante la ejecución del Sistema de Prácticas Profesionales.
 */
public class SesionUsuario {

  private static Usuario usuarioActual;
  private static TipoUsuario tipoUsuarioActual;

  private SesionUsuario() {
  }

  public static Usuario getUsuarioActual() {
    return usuarioActual;
  }

  public static void setUsuarioActual(Usuario usuarioActual) {
    SesionUsuario.usuarioActual = usuarioActual;
  }

  public static TipoUsuario getTipoUsuarioActual() {
    return tipoUsuarioActual;
  }

  public static void setTipoUsuarioActual(TipoUsuario tipoUsuarioActual) {
    SesionUsuario.tipoUsuarioActual = tipoUsuarioActual;
  }

  public static boolean haySesionActiva() {
    return usuarioActual != null && tipoUsuarioActual != null;
  }

  public static void cerrarSesion() {
    usuarioActual = null;
    tipoUsuarioActual = null;
  }
}