package spp.service;

import java.sql.SQLException;
import java.util.List;
import spp.modelo.dao.ProfesorDAO;
import spp.modelo.dto.Curso;
import spp.modelo.dto.Profesor;
import spp.modelo.dto.Usuario;
import spp.utilidades.EncriptadorContrasenia;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Gestiona las operaciones de profesores del Sistema de
 * Prácticas Profesionales.
 */
public class ProfesorService {

  private ProfesorDAO profesorDAO;

  public ProfesorService() {
    profesorDAO = new ProfesorDAO();
  }

  public List<Profesor> obtenerProfesores() throws SQLException {
    return profesorDAO.obtenerProfesores();
  }

  public void registrarProfesor(String nombres, String apellidos,
      String correoElectronico, String numeroPersonalTexto, String telefono,
      String turno, String contrasenia, Curso curso) throws SQLException {
    validarDatos(nombres, apellidos, correoElectronico, numeroPersonalTexto,
        telefono, turno, contrasenia, curso);

    int numeroPersonal = Integer.parseInt(numeroPersonalTexto.trim());

    if (profesorDAO.existeNumeroPersonal(numeroPersonal)) {
      throw new IllegalArgumentException(
          "Ya existe un profesor con ese número personal.");
    }

    Usuario usuario = crearUsuario(nombres, apellidos, correoElectronico,
        telefono, contrasenia);

    Profesor profesor = new Profesor();

    profesor.setNumeroPersonal(numeroPersonal);
    profesor.setTurno(turno.trim());
    profesor.setUsuario(usuario);

    profesorDAO.registrarProfesorConCurso(profesor, curso.getIdCurso());
  }

  private Usuario crearUsuario(String nombres, String apellidos,
      String correoElectronico, String telefono, String contrasenia) {
    Usuario usuario = new Usuario();

    usuario.setNombreUsuario(correoElectronico.trim());
    usuario.setNombreCompleto(nombres.trim() + " " + apellidos.trim());
    usuario.setCorreoElectronico(correoElectronico.trim());
    usuario.setTelefono(telefono.trim());
    usuario.setContrasenia(EncriptadorContrasenia.generarHash(contrasenia));
    usuario.setEstadoActivo(true);

    return usuario;
  }

  private void validarDatos(String nombres, String apellidos,
    String correoElectronico, String numeroPersonalTexto,
    String telefono, String turno,
    String contrasenia, Curso curso) {

  if (estaVacio(nombres)
      || estaVacio(apellidos)
      || estaVacio(correoElectronico)
      || estaVacio(numeroPersonalTexto)
      || estaVacio(telefono)
      || estaVacio(turno)
      || estaVacio(contrasenia)
      || curso == null) {

    throw new IllegalArgumentException(
        "Por favor, complete todos los campos obligatorios.");
    }

    // Validación de correo
    if (!esCorreoValido(correoElectronico.trim())) {
    throw new IllegalArgumentException(
        "El correo electrónico no tiene un formato válido.");
  }

    // Validación de teléfono
    if (!esTelefonoValido(telefono.trim())) {
    throw new IllegalArgumentException(
        "El teléfono debe contener exactamente 10 dígitos.");
    }

    try {
        Integer.parseInt(numeroPersonalTexto.trim());
    } catch (NumberFormatException excepcion) {
    throw new IllegalArgumentException(
        "El número personal debe ser numérico.");
    }
 }

  private boolean estaVacio(String texto) {
    return texto == null || texto.trim().isEmpty();
  }
  
  private boolean esCorreoValido(String correo) {
    return correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
  
  private boolean esTelefonoValido(String telefono) {
  return telefono.matches("\\d{10}");
  }
}