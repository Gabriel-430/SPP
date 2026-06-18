package spp.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dto.PeriodoEscolar;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Proporciona operaciones de acceso a datos para los periodos
 * escolares del Sistema de Prácticas Profesionales.
 */
public class PeriodoEscolarDAO {

    public PeriodoEscolar obtenerPeriodoActivo() throws SQLException {
        String consulta = "SELECT id_periodo, fechaInicio, fechaFin "
                + "FROM PeriodoEscolar "
                + "WHERE CURDATE() BETWEEN fechaInicio AND fechaFin "
                + "LIMIT 1";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(
                        consulta);
                ResultSet resultado = sentencia.executeQuery()) {

            if (resultado.next()) {
                return mapearPeriodo(resultado);
            }
        }

        return null;
    }

    private PeriodoEscolar mapearPeriodo(ResultSet resultado)
            throws SQLException {
        PeriodoEscolar periodo = new PeriodoEscolar();

        periodo.setIdPeriodo(resultado.getInt("id_periodo"));
        periodo.setFechaInicio(resultado.getDate("fechaInicio").toLocalDate());
        periodo.setFechaFin(resultado.getDate("fechaFin").toLocalDate());

        return periodo;
    }
}
