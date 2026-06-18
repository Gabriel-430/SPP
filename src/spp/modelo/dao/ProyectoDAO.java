package spp.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dto.OrganizacionVinculada;
import spp.modelo.dto.Proyecto;
import spp.modelo.dto.ResponsableTecnicoProyecto;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Proporciona operaciones de acceso a datos para el registro
 * y actualización de proyectos del Sistema de Prácticas Profesionales.
 */
public class ProyectoDAO {

    public List<Proyecto> obtenerProyectosActivos() throws SQLException {
        List<Proyecto> proyectos = new ArrayList<>();

        String consulta = "SELECT p.id_proyecto, p.nombreProyecto, "
                + "p.cupoDisponible, p.estadoProyecto, "
                + "p.descripcionActividades, "
                + "o.id_organizacion, o.razonSocial, o.nombreComercial, "
                + "o.RFC, o.direccion, "
                + "r.id_responsable, r.nombreCompleto, r.telefono, r.correo "
                + "FROM Proyecto p "
                + "INNER JOIN OrganizacionVinculada o "
                + "ON p.idOrganizacion = o.id_organizacion "
                + "INNER JOIN ResponsableTecnicoProyecto r "
                + "ON p.idResponsable = r.id_responsable "
                + "WHERE p.estadoProyecto = 'Activo' "
                + "ORDER BY p.nombreProyecto";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(
                        consulta);
                ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                proyectos.add(mapearProyecto(resultado));
            }
        }

        return proyectos;
    }

    public void actualizarProyecto(Proyecto proyecto) throws SQLException {
        String sentenciaSql = "UPDATE Proyecto "
                + "SET nombreProyecto = ?, cupoDisponible = ?, "
                + "descripcionActividades = ? "
                + "WHERE id_proyecto = ?";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(
                        sentenciaSql)) {

            sentencia.setString(1, proyecto.getNombreProyecto());
            sentencia.setInt(2, proyecto.getCupoDisponible());
            sentencia.setString(3, proyecto.getDescripcionActividades());
            sentencia.setInt(4, proyecto.getIdProyecto());

            sentencia.executeUpdate();
        }
    }

    public void actualizarOrganizacion(OrganizacionVinculada organizacion)
            throws SQLException {
        String sentenciaSql = "UPDATE OrganizacionVinculada "
                + "SET razonSocial = ?, nombreComercial = ?, RFC = ?, "
                + "direccion = ? "
                + "WHERE id_organizacion = ?";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(
                        sentenciaSql)) {

            sentencia.setString(1, organizacion.getRazonSocial());
            sentencia.setString(2, organizacion.getNombreComercial());
            sentencia.setString(3, organizacion.getRfc());
            sentencia.setString(4, organizacion.getDireccion());
            sentencia.setInt(5, organizacion.getIdOrganizacion());

            sentencia.executeUpdate();
        }
    }

    public void actualizarResponsable(ResponsableTecnicoProyecto responsable)
            throws SQLException {
        String sentenciaSql = "UPDATE ResponsableTecnicoProyecto "
                + "SET nombreCompleto = ?, telefono = ?, correo = ? "
                + "WHERE id_responsable = ?";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(
                        sentenciaSql)) {

            sentencia.setString(1, responsable.getNombreCompleto());
            sentencia.setString(2, responsable.getTelefono());
            sentencia.setString(3, responsable.getCorreo());
            sentencia.setInt(4, responsable.getIdResponsable());

            sentencia.executeUpdate();
        }
    }

    public void registrarProyectoCompleto(Proyecto proyecto)
            throws SQLException {
        Connection conexion = null;

        try {
            conexion = MySQLConnectionManager.obtenerConexion();
            conexion.setAutoCommit(false);

            OrganizacionVinculada organizacion = proyecto.getOrganizacion();

            if (organizacion.getIdOrganizacion() == 0) {
                int idOrganizacion = registrarOrganizacion(conexion,
                        organizacion);
                organizacion.setIdOrganizacion(idOrganizacion);
            }

            ResponsableTecnicoProyecto responsable =
                    proyecto.getResponsable();
            responsable.setOrganizacion(organizacion);

            if (responsable.getIdResponsable() == 0) {
                int idResponsable = registrarResponsable(conexion,
                        responsable);
                responsable.setIdResponsable(idResponsable);
            }

            registrarProyecto(conexion, proyecto);

            conexion.commit();

        } catch (SQLException excepcion) {
            if (conexion != null) {
                conexion.rollback();
            }

            throw excepcion;

        } finally {
            if (conexion != null) {
                conexion.setAutoCommit(true);
                conexion.close();
            }
        }
    }

    private int registrarOrganizacion(Connection conexion,
            OrganizacionVinculada organizacion) throws SQLException {
        String sentenciaSql = "INSERT INTO OrganizacionVinculada "
                + "(razonSocial, nombreComercial, RFC, direccion) "
                + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(
                sentenciaSql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, organizacion.getRazonSocial());
            sentencia.setString(2, organizacion.getNombreComercial());
            sentencia.setString(3, organizacion.getRfc());
            sentencia.setString(4, organizacion.getDireccion());

            sentencia.executeUpdate();

            try (ResultSet resultado = sentencia.getGeneratedKeys()) {
                if (resultado.next()) {
                    return resultado.getInt(1);
                }
            }
        }

        throw new SQLException("No fue posible registrar la organización.");
    }

    private int registrarResponsable(Connection conexion,
            ResponsableTecnicoProyecto responsable) throws SQLException {
        String sentenciaSql = "INSERT INTO ResponsableTecnicoProyecto "
                + "(nombreCompleto, telefono, correo, id_organizacion) "
                + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(
                sentenciaSql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, responsable.getNombreCompleto());
            sentencia.setString(2, responsable.getTelefono());
            sentencia.setString(3, responsable.getCorreo());
            sentencia.setInt(4, responsable.getOrganizacion()
                    .getIdOrganizacion());

            sentencia.executeUpdate();

            try (ResultSet resultado = sentencia.getGeneratedKeys()) {
                if (resultado.next()) {
                    return resultado.getInt(1);
                }
            }
        }

        throw new SQLException("No fue posible registrar el responsable.");
    }

    private void registrarProyecto(Connection conexion, Proyecto proyecto)
            throws SQLException {
        String sentenciaSql = "INSERT INTO Proyecto (nombreProyecto, "
                + "cupoDisponible, estadoProyecto, descripcionActividades, "
                + "idOrganizacion, idResponsable) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement sentencia = conexion.prepareStatement(
                sentenciaSql)) {

            sentencia.setString(1, proyecto.getNombreProyecto());
            sentencia.setInt(2, proyecto.getCupoDisponible());
            sentencia.setString(3, proyecto.getEstadoProyecto());
            sentencia.setString(4, proyecto.getDescripcionActividades());
            sentencia.setInt(5, proyecto.getOrganizacion()
                    .getIdOrganizacion());
            sentencia.setInt(6, proyecto.getResponsable().getIdResponsable());

            sentencia.executeUpdate();
        }
    }

    private Proyecto mapearProyecto(ResultSet resultado) throws SQLException {
        OrganizacionVinculada organizacion = new OrganizacionVinculada();

        organizacion.setIdOrganizacion(resultado.getInt("id_organizacion"));
        organizacion.setRazonSocial(resultado.getString("razonSocial"));
        organizacion.setNombreComercial(resultado.getString("nombreComercial"));
        organizacion.setRfc(resultado.getString("RFC"));
        organizacion.setDireccion(resultado.getString("direccion"));

        ResponsableTecnicoProyecto responsable =
                new ResponsableTecnicoProyecto();

        responsable.setIdResponsable(resultado.getInt("id_responsable"));
        responsable.setNombreCompleto(resultado.getString("nombreCompleto"));
        responsable.setTelefono(resultado.getString("telefono"));
        responsable.setCorreo(resultado.getString("correo"));
        responsable.setOrganizacion(organizacion);

        Proyecto proyecto = new Proyecto();

        proyecto.setIdProyecto(resultado.getInt("id_proyecto"));
        proyecto.setNombreProyecto(resultado.getString("nombreProyecto"));
        proyecto.setCupoDisponible(resultado.getInt("cupoDisponible"));
        proyecto.setEstadoProyecto(resultado.getString("estadoProyecto"));
        proyecto.setDescripcionActividades(resultado.getString(
                "descripcionActividades"));
        proyecto.setOrganizacion(organizacion);
        proyecto.setResponsable(responsable);

        return proyecto;
    }
}
