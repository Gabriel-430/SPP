package spp.service;

import java.sql.SQLException;
import spp.modelo.dao.PeriodoEscolarDAO;
import spp.modelo.dto.PeriodoEscolar;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Gestiona las operaciones relacionadas con los periodos
 * escolares del Sistema de Prácticas Profesionales.
 */
public class PeriodoEscolarService {

    private PeriodoEscolarDAO periodoEscolarDAO;

    public PeriodoEscolarService() {
        periodoEscolarDAO = new PeriodoEscolarDAO();
    }

    public PeriodoEscolar obtenerPeriodoActivo() throws SQLException {
        return periodoEscolarDAO.obtenerPeriodoActivo();
    }

    public boolean existePeriodoActivo() throws SQLException {
        return obtenerPeriodoActivo() != null;
    }
}
