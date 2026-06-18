package spp.modelo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 16/06/2026
 * Descripción: Administra la creación de conexiones hacia la base de datos
 * MySQL del Sistema de Prácticas Profesionales.
 */
public class MySQLConnectionManager {

  private static final String RUTA_PROPIEDADES = "/config/database.properties";

  private static String url;
  private static String usuario;
  private static String contrasenia;
  private static String driver;

  static {
    cargarCredenciales();
    cargarDriver();
  }

  private MySQLConnectionManager() {
  }

  public static Connection obtenerConexion() throws SQLException {
    return DriverManager.getConnection(url, usuario, contrasenia);
  }

  private static void cargarCredenciales() {
    try (InputStream entrada = MySQLConnectionManager.class.getResourceAsStream(
        RUTA_PROPIEDADES)) {

      if (entrada == null) {
        throw new IllegalStateException("No fue posible acceder a database.properties");
      }

      Properties propiedades = new Properties();
      propiedades.load(entrada);

      url = propiedades.getProperty("db.url");
      usuario = propiedades.getProperty("db.user");
      contrasenia = propiedades.getProperty("db.password");
      driver = propiedades.getProperty("db.driver");

      validarPropiedades();

    } catch (IOException excepcion) {
      throw new IllegalStateException("No fue posible cargar database.properties",
          excepcion);
    }
  }

  private static void cargarDriver() {
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException excepcion) {
      throw new IllegalStateException("No fue posible cargar el driver de MySQL",
          excepcion);
    }
  }

  private static void validarPropiedades() {
    if (url == null || url.trim().isEmpty()) {
      throw new IllegalStateException("No se encontro la propiedad db.url");
    }

    if (usuario == null || usuario.trim().isEmpty()) {
      throw new IllegalStateException("No se encontro la propiedad db.user");
    }

    if (contrasenia == null) {
      throw new IllegalStateException("No se encontro la propiedad db.password");
    }

    if (driver == null || driver.trim().isEmpty()) {
      throw new IllegalStateException("No se encontro la propiedad db.driver");
    }
  }
}