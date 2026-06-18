package spp.service;

import java.sql.SQLException;
import java.util.List;
import spp.modelo.dao.OrganizacionVinculadaDAO;
import spp.modelo.dto.OrganizacionVinculada;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Gestiona las operaciones relacionadas con organizaciones
 * vinculadas del Sistema de Prácticas Profesionales.
 */
public class OrganizacionVinculadaService {

    private OrganizacionVinculadaDAO organizacionVinculadaDAO;

    public OrganizacionVinculadaService() {
        organizacionVinculadaDAO = new OrganizacionVinculadaDAO();
    }

    public List<OrganizacionVinculada> obtenerOrganizaciones()
            throws SQLException {
        return organizacionVinculadaDAO.obtenerOrganizaciones();
    }

    public boolean existeRfc(String rfc) throws SQLException {
        return organizacionVinculadaDAO.existeRfc(rfc);
    }
    
    public void validarNuevaOrganizacion(OrganizacionVinculada organizacion)
        throws SQLException {
        if (organizacion == null
            || estaVacio(organizacion.getRazonSocial())
            || estaVacio(organizacion.getNombreComercial())
            || estaVacio(organizacion.getRfc())
            || estaVacio(organizacion.getDireccion())) {
            throw new IllegalArgumentException(
                "Por favor, complete todos los campos obligatorios.");
            }

        String rfc = organizacion.getRfc().trim().toUpperCase();

        if (!esRfcPersonaMoralValido(rfc)) {
            throw new IllegalArgumentException(
                "El RFC debe tener formato de persona moral: "
                + "3 letras, 6 números y 3 caracteres alfanuméricos.");
        }

        if (organizacionVinculadaDAO.existeRfc(rfc)) {
            throw new IllegalArgumentException(
                "Ya existe una empresa registrada con el mismo RFC.");
        }

        organizacion.setRfc(rfc);
    }
    
    private boolean esRfcPersonaMoralValido(String rfc) {
        return rfc.matches("[A-ZÑ&]{3}\\d{6}[A-Z0-9]{3}");
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
    
}