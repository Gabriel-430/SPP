package spp.service;

import java.sql.SQLException;
import java.util.List;
import spp.modelo.dao.CursoDAO;
import spp.modelo.dto.Curso;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Gestiona las operaciones relacionadas con los cursos del
 * Sistema de Prácticas Profesionales.
 */
public class CursoService {

   private CursoDAO cursoDAO;

   public CursoService() {
      cursoDAO = new CursoDAO();
   }

   public List<Curso> obtenerCursosSinProfesor() throws SQLException {
      return cursoDAO.obtenerCursosSinProfesor();
   }

   public boolean hayCursosSinProfesor() throws SQLException {
      return !cursoDAO.obtenerCursosSinProfesor().isEmpty();
   }
  
   public List<Curso> obtenerCursosActivos() throws SQLException {
     return cursoDAO.obtenerCursosActivos();
   }

   public boolean hayCursosActivos() throws SQLException {
     return !cursoDAO.obtenerCursosActivos().isEmpty();
   }
}
