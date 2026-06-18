package spp.utilidades;

import junit.framework.TestCase;
import spp.modelo.dto.TipoUsuario;
import spp.modelo.dto.Usuario;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 18/06/2026
 * Descripción: Pruebas unitarias de utilidades sin usar Hamcrest ni anotaciones JUnit 4.
 */
public class UtilidadesSinHamcrestTest extends TestCase {

  protected void tearDown() {
    SesionUsuario.cerrarSesion();
  }

  public void testGenerarContraseniaNoNula() {
    String contrasenia = GeneradorContrasenia.generarContrasenia();

    assertNotNull(contrasenia);
  }

  public void testGenerarContraseniaNoVacia() {
    String contrasenia = GeneradorContrasenia.generarContrasenia();

    assertTrue(contrasenia.length() > 0);
  }

  public void testGenerarContraseniaLongitudDiez() {
    String contrasenia = GeneradorContrasenia.generarContrasenia();

    assertEquals(10, contrasenia.length());
  }

  public void testGenerarContraseniaContieneMayuscula() {
    String contrasenia = GeneradorContrasenia.generarContrasenia();

    assertTrue(contrasenia.matches(".*[A-Z].*"));
  }

  public void testGenerarContraseniaContieneMinuscula() {
    String contrasenia = GeneradorContrasenia.generarContrasenia();

    assertTrue(contrasenia.matches(".*[a-z].*"));
  }

  public void testGenerarContraseniaContieneNumero() {
    String contrasenia = GeneradorContrasenia.generarContrasenia();

    assertTrue(contrasenia.matches(".*[0-9].*"));
  }

  public void testGenerarContraseniaContieneSimbolo() {
    String contrasenia = GeneradorContrasenia.generarContrasenia();

    assertTrue(contrasenia.matches(".*[*@$%].*"));
  }

  public void testSesionIniciaSinUsuario() {
    SesionUsuario.cerrarSesion();

    assertNull(SesionUsuario.getUsuarioActual());
    assertNull(SesionUsuario.getTipoUsuarioActual());
    assertFalse(SesionUsuario.haySesionActiva());
  }

  public void testSesionActivaConUsuarioYTipo() {
    Usuario usuario = new Usuario();

    SesionUsuario.setUsuarioActual(usuario);
    SesionUsuario.setTipoUsuarioActual(TipoUsuario.ADMINISTRADOR);

    assertSame(usuario, SesionUsuario.getUsuarioActual());
    assertEquals(TipoUsuario.ADMINISTRADOR, SesionUsuario.getTipoUsuarioActual());
    assertTrue(SesionUsuario.haySesionActiva());
  }

  public void testSesionNoActivaSinTipoUsuario() {
    Usuario usuario = new Usuario();

    SesionUsuario.setUsuarioActual(usuario);
    SesionUsuario.setTipoUsuarioActual(null);

    assertFalse(SesionUsuario.haySesionActiva());
  }

  public void testSesionNoActivaSinUsuario() {
    SesionUsuario.setUsuarioActual(null);
    SesionUsuario.setTipoUsuarioActual(TipoUsuario.COORDINADOR);

    assertFalse(SesionUsuario.haySesionActiva());
  }

  public void testCerrarSesionLimpiaDatos() {
    Usuario usuario = new Usuario();

    SesionUsuario.setUsuarioActual(usuario);
    SesionUsuario.setTipoUsuarioActual(TipoUsuario.PROFESOR);
    SesionUsuario.cerrarSesion();

    assertNull(SesionUsuario.getUsuarioActual());
    assertNull(SesionUsuario.getTipoUsuarioActual());
    assertFalse(SesionUsuario.haySesionActiva());
  }
}
