package spp.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dto.OrganizacionVinculada;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Proporciona operaciones de acceso a datos para las
 * organizaciones vinculadas del Sistema de Prácticas Profesionales.
 */
public class OrganizacionVinculadaDAO {

    public List<OrganizacionVinculada> obtenerOrganizaciones()
            throws SQLException {
        List<OrganizacionVinculada> organizaciones = new ArrayList<>();

        String consulta = "SELECT id_organizacion, razonSocial, "
                + "nombreComercial, RFC, direccion "
                + "FROM OrganizacionVinculada "
                + "ORDER BY nombreComercial";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(
                        consulta);
                ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                organizaciones.add(mapearOrganizacion(resultado));
            }
        }

        return organizaciones;
    }

    public boolean existeRfc(String rfc) throws SQLException {
        String consulta = "SELECT COUNT(*) AS total "
                + "FROM OrganizacionVinculada "
                + "WHERE RFC = ?";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(
                        consulta)) {

            sentencia.setString(1, rfc);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() && resultado.getInt("total") > 0;
            }
        }
    }

    private OrganizacionVinculada mapearOrganizacion(ResultSet resultado)
            throws SQLException {
        OrganizacionVinculada organizacion = new OrganizacionVinculada();

        organizacion.setIdOrganizacion(resultado.getInt("id_organizacion"));
        organizacion.setRazonSocial(resultado.getString("razonSocial"));
        organizacion.setNombreComercial(resultado.getString("nombreComercial"));
        organizacion.setRfc(resultado.getString("RFC"));
        organizacion.setDireccion(resultado.getString("direccion"));

        return organizacion;
    }
}