package spp.modelo.dto;

import java.time.LocalDate;
import junit.framework.TestCase;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 18/06/2026
 * Descripción: Pruebas unitarias de DTO académicos y de seguimiento sin usar Hamcrest.
 */
public class DTOAcademicoTest extends TestCase {

  public void testPeriodoEscolarGettersSetters() {
    LocalDate inicio = LocalDate.of(2026, 1, 1);
    LocalDate fin = LocalDate.of(2026, 6, 30);
    PeriodoEscolar periodo = new PeriodoEscolar();

    periodo.setIdPeriodo(1);
    periodo.setFechaInicio(inicio);
    periodo.setFechaFin(fin);

    assertEquals(1, periodo.getIdPeriodo());
    assertEquals(inicio, periodo.getFechaInicio());
    assertEquals(fin, periodo.getFechaFin());
  }

  public void testPeriodoEscolarConstructorCompleto() {
    LocalDate inicio = LocalDate.of(2026, 2, 1);
    LocalDate fin = LocalDate.of(2026, 7, 1);
    PeriodoEscolar periodo = new PeriodoEscolar(2, inicio, fin);

    assertEquals(2, periodo.getIdPeriodo());
    assertEquals(inicio, periodo.getFechaInicio());
    assertEquals(fin, periodo.getFechaFin());
  }

  public void testCalificacionFinalGettersSetters() {
    PracticaProfesional practica = new PracticaProfesional();
    CalificacionFinal calificacion = new CalificacionFinal();

    calificacion.setIdCalificacionFinal("CF-01");
    calificacion.setTipo("Final");
    calificacion.setPromedioTotal(95.5f);
    calificacion.setEstado("Aprobado");
    calificacion.setPractica(practica);

    assertEquals("CF-01", calificacion.getIdCalificacionFinal());
    assertEquals("Final", calificacion.getTipo());
    assertEquals(95.5f, calificacion.getPromedioTotal(), 0.001f);
    assertEquals("Aprobado", calificacion.getEstado());
    assertSame(practica, calificacion.getPractica());
  }

  public void testCalificacionFinalConstructorCompleto() {
    PracticaProfesional practica = new PracticaProfesional();
    CalificacionFinal calificacion = new CalificacionFinal("CF-02", "Parcial", 88.0f, "Aprobado", practica);

    assertEquals("CF-02", calificacion.getIdCalificacionFinal());
    assertEquals("Parcial", calificacion.getTipo());
    assertEquals(88.0f, calificacion.getPromedioTotal(), 0.001f);
    assertEquals("Aprobado", calificacion.getEstado());
    assertSame(practica, calificacion.getPractica());
  }

  public void testBitacoraEstadoGettersSetters() {
    LocalDate fecha = LocalDate.of(2026, 6, 18);
    PracticaProfesional practica = new PracticaProfesional();
    Usuario usuario = new Usuario();
    BitacoraEstado bitacora = new BitacoraEstado();

    bitacora.setIdBitacora(1);
    bitacora.setEstadoAnterior("Disponible");
    bitacora.setEstadoNuevo("No Disponible");
    bitacora.setFechaCambio(fecha);
    bitacora.setMotivo("Asignación de practicante");
    bitacora.setPractica(practica);
    bitacora.setUsuario(usuario);

    assertEquals(1, bitacora.getIdBitacora());
    assertEquals("Disponible", bitacora.getEstadoAnterior());
    assertEquals("No Disponible", bitacora.getEstadoNuevo());
    assertEquals(fecha, bitacora.getFechaCambio());
    assertEquals("Asignación de practicante", bitacora.getMotivo());
    assertSame(practica, bitacora.getPractica());
    assertSame(usuario, bitacora.getUsuario());
  }

  public void testListaProyectosSeleccionadosGettersSetters() {
    LocalDate fecha = LocalDate.of(2026, 6, 18);
    Practicante practicante = new Practicante();
    Proyecto proyecto = new Proyecto();
    ListaProyectosSeleccionados lista = new ListaProyectosSeleccionados();

    lista.setIdLista(1);
    lista.setFechaSeleccion(fecha);
    lista.setPrioridad(1);
    lista.setEstado("Seleccionado");
    lista.setMotivoRechazo("Sin rechazo");
    lista.setPracticante(practicante);
    lista.setProyecto(proyecto);

    assertEquals(1, lista.getIdLista());
    assertEquals(fecha, lista.getFechaSeleccion());
    assertEquals(1, lista.getPrioridad());
    assertEquals("Seleccionado", lista.getEstado());
    assertEquals("Sin rechazo", lista.getMotivoRechazo());
    assertSame(practicante, lista.getPracticante());
    assertSame(proyecto, lista.getProyecto());
  }

  public void testTipoUsuarioValores() {
    assertEquals(TipoUsuario.ADMINISTRADOR, TipoUsuario.valueOf("ADMINISTRADOR"));
    assertEquals(TipoUsuario.COORDINADOR, TipoUsuario.valueOf("COORDINADOR"));
    assertEquals(TipoUsuario.PROFESOR, TipoUsuario.valueOf("PROFESOR"));
    assertEquals(TipoUsuario.PRACTICANTE, TipoUsuario.valueOf("PRACTICANTE"));
  }
}
