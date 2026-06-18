package spp.service;

import java.sql.SQLException;
import java.util.List;
import spp.modelo.dao.OrganizacionVinculadaDAO;
import spp.modelo.dao.ProyectoDAO;
import spp.modelo.dto.OrganizacionVinculada;
import spp.modelo.dto.Proyecto;
import spp.modelo.dto.ResponsableTecnicoProyecto;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Gestiona las operaciones relacionadas con el registro y
 * actualización de proyectos del Sistema de Prácticas Profesionales.
 */
public class ProyectoService {

    private OrganizacionVinculadaDAO organizacionVinculadaDAO;
    private ProyectoDAO proyectoDAO;

    public ProyectoService() {
        organizacionVinculadaDAO = new OrganizacionVinculadaDAO();
        proyectoDAO = new ProyectoDAO();
    }

    public List<Proyecto> obtenerProyectosActivos() throws SQLException {
        return proyectoDAO.obtenerProyectosActivos();
    }

    public boolean hayProyectosActivos() throws SQLException {
        return !proyectoDAO.obtenerProyectosActivos().isEmpty();
    }

    public void registrarProyecto(OrganizacionVinculada organizacion,
            ResponsableTecnicoProyecto responsable, Proyecto proyecto,
            boolean esNuevaOrganizacion) throws SQLException {
        validarDatosRegistro(organizacion, responsable, proyecto);

        if (esNuevaOrganizacion && organizacionVinculadaDAO.existeRfc(
                organizacion.getRfc().trim())) {
            throw new IllegalArgumentException(
                    "Ya existe una empresa registrada con el mismo RFC.");
        }

        proyecto.setOrganizacion(organizacion);
        proyecto.setResponsable(responsable);
        proyecto.setEstadoProyecto("Activo");

        proyectoDAO.registrarProyectoCompleto(proyecto);
    }

    public void actualizarProyecto(Proyecto proyecto) throws SQLException {
        validarDatosProyecto(proyecto);
        proyectoDAO.actualizarProyecto(proyecto);
    }

    public void actualizarOrganizacion(OrganizacionVinculada organizacion)
            throws SQLException {
        validarDatosOrganizacion(organizacion);
        proyectoDAO.actualizarOrganizacion(organizacion);
    }

    public void actualizarResponsable(ResponsableTecnicoProyecto responsable)
            throws SQLException {
        validarDatosResponsable(responsable);
        proyectoDAO.actualizarResponsable(responsable);
    }

    private void validarDatosRegistro(OrganizacionVinculada organizacion,
            ResponsableTecnicoProyecto responsable, Proyecto proyecto) {
        validarDatosOrganizacion(organizacion);
        validarDatosResponsable(responsable);
        validarDatosProyecto(proyecto);
    }

    private void validarDatosProyecto(Proyecto proyecto) {
        if (proyecto == null
                || estaVacio(proyecto.getNombreProyecto())
                || estaVacio(proyecto.getDescripcionActividades())) {
            throw new IllegalArgumentException(
                    "Por favor, complete todos los campos obligatorios.");
        }

        if (proyecto.getCupoDisponible() <= 0) {
            throw new IllegalArgumentException(
                    "El cupo disponible debe ser mayor que cero.");
        }
    }

    private void validarDatosOrganizacion(
            OrganizacionVinculada organizacion) {
        if (organizacion == null
                || estaVacio(organizacion.getRazonSocial())
                || estaVacio(organizacion.getNombreComercial())
                || estaVacio(organizacion.getRfc())
                || estaVacio(organizacion.getDireccion())) {
            throw new IllegalArgumentException(
                    "Por favor, complete todos los campos obligatorios.");
        }

        if (!esRfcPersonaMoralValido(organizacion.getRfc().trim())) {
            throw new IllegalArgumentException(
                    "El RFC debe tener formato de persona moral: "
                    + "3 letras, 6 números y 3 caracteres alfanuméricos.");
        }
    }

    private void validarDatosResponsable(
            ResponsableTecnicoProyecto responsable) {
        if (responsable == null
                || estaVacio(responsable.getNombreCompleto())
                || estaVacio(responsable.getTelefono())
                || estaVacio(responsable.getCorreo())) {
            throw new IllegalArgumentException(
                    "Por favor, complete todos los campos obligatorios.");
        }

        if (!esTelefonoValido(responsable.getTelefono().trim())) {
            throw new IllegalArgumentException(
                    "El teléfono debe contener exactamente 10 dígitos.");
        }

        if (!esCorreoValido(responsable.getCorreo().trim())) {
            throw new IllegalArgumentException(
                    "El correo electrónico no tiene un formato válido.");
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private boolean esTelefonoValido(String telefono) {
        return telefono.matches("\\d{10}");
    }

    private boolean esCorreoValido(String correo) {
        return correo.matches(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean esRfcPersonaMoralValido(String rfc) {
        return rfc.matches("[A-ZÑ&]{3}\\d{6}[A-Z0-9]{3}");
    }
}
