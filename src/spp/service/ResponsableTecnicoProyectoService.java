package spp.service;

import java.sql.SQLException;
import java.util.List;
import spp.modelo.dao.ResponsableTecnicoProyectoDAO;
import spp.modelo.dto.ResponsableTecnicoProyecto;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Gestiona las operaciones relacionadas con responsables
 * técnicos de proyectos del Sistema de Prácticas Profesionales.
 */
public class ResponsableTecnicoProyectoService {

    private ResponsableTecnicoProyectoDAO responsableTecnicoProyectoDAO;

    public ResponsableTecnicoProyectoService() {
        responsableTecnicoProyectoDAO = new ResponsableTecnicoProyectoDAO();
    }

    public List<ResponsableTecnicoProyecto> obtenerResponsablesPorOrganizacion(
            int idOrganizacion) throws SQLException {
        return responsableTecnicoProyectoDAO.obtenerResponsablesPorOrganizacion(
                idOrganizacion);
    }

    public void validarNuevoResponsable(
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

    public void validarResponsableExistente(
            ResponsableTecnicoProyecto responsable) {
        if (responsable == null) {
            throw new IllegalArgumentException(
                    "Seleccione un responsable técnico.");
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private boolean esTelefonoValido(String telefono) {
        return telefono.matches("\\d{10}");
    }

    private boolean esCorreoValido(String correo) {
        return correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}