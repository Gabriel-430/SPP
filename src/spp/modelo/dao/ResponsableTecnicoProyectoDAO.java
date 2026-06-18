package spp.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dto.OrganizacionVinculada;
import spp.modelo.dto.ResponsableTecnicoProyecto;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Proporciona operaciones de acceso a datos para los
 * responsables técnicos de proyectos del Sistema de Prácticas Profesionales.
 */
public class ResponsableTecnicoProyectoDAO {

    public List<ResponsableTecnicoProyecto> obtenerResponsablesPorOrganizacion(
            int idOrganizacion) throws SQLException {
        List<ResponsableTecnicoProyecto> responsables = new ArrayList<>();

        String consulta = "SELECT id_responsable, nombreCompleto, telefono, "
                + "correo, id_organizacion "
                + "FROM ResponsableTecnicoProyecto "
                + "WHERE id_organizacion = ? "
                + "ORDER BY nombreCompleto";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(
                        consulta)) {

            sentencia.setInt(1, idOrganizacion);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    responsables.add(mapearResponsable(resultado));
                }
            }
        }

        return responsables;
    }

    private ResponsableTecnicoProyecto mapearResponsable(ResultSet resultado)
            throws SQLException {
        OrganizacionVinculada organizacion = new OrganizacionVinculada();
        organizacion.setIdOrganizacion(resultado.getInt("id_organizacion"));

        ResponsableTecnicoProyecto responsable =
                new ResponsableTecnicoProyecto();

        responsable.setIdResponsable(resultado.getInt("id_responsable"));
        responsable.setNombreCompleto(resultado.getString("nombreCompleto"));
        responsable.setTelefono(resultado.getString("telefono"));
        responsable.setCorreo(resultado.getString("correo"));
        responsable.setOrganizacion(organizacion);

        return responsable;
    }
}