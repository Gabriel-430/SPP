package spp.utilidades;

import org.mindrot.jbcrypt.BCrypt;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 16/06/2026
 * Descripción: Genera y verifica hashes de contraseñas para el inicio
 * de sesión del Sistema de Prácticas Profesionales.
 */
public class EncriptadorContrasenia {

  private static final int COSTO_HASH = 12;

  private EncriptadorContrasenia() {    
  }

  public static String generarHash(String contrasenia) {
    return BCrypt.hashpw(contrasenia, BCrypt.gensalt(COSTO_HASH));
  }

  public static boolean verificarContrasenia(String contrasenia, String hash) {
    if (contrasenia == null || hash == null || hash.trim().isEmpty()) {
      return false;
    }

    return BCrypt.checkpw(contrasenia, hash);
  }
}