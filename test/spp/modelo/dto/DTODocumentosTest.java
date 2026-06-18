package spp.modelo.dto;

import java.time.LocalDate;
import junit.framework.TestCase;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 18/06/2026
 * Descripción: Pruebas unitarias para DTO de documentos, reportes y prácticas profesionales.
 */
public class DTODocumentosTest extends TestCase {

  public void testDocumentoGettersSetters() {
    LocalDate fechaEntrega = LocalDate.of(2026, 6, 18);
    LocalDate fechaEmision = LocalDate.of(2026, 6, 1);
    Documento documento = new Documento();

    documento.setIdDocumento(1);
    documento.setNombreDocumento("Reporte mensual");
    documento.setFechaEntrega(fechaEntrega);
    documento.setFechaEmision(fechaEmision);
    documento.setEstado("Entregado");
    documento.setRutaArchivo("C:/reportes/reporte.pdf");
    documento.setIdPractica(10);
    documento.setIdCatalogoDocumento(2);

    assertEquals(1, documento.getIdDocumento());
    assertEquals("Reporte mensual", documento.getNombreDocumento());
    assertEquals(fechaEntrega, documento.getFechaEntrega());
    assertEquals(fechaEmision, documento.getFechaEmision());
    assertEquals("Entregado", documento.getEstado());
    assertEquals("C:/reportes/reporte.pdf", documento.getRutaArchivo());
    assertEquals(10, documento.getIdPractica());
    assertEquals(2, documento.getIdCatalogoDocumento());
  }

  public void testReporteGettersSetters() {
    Reporte reporte = new Reporte();

    reporte.setIdDocumento(5);
    reporte.setNombreDocumento("Reporte parcial");
    reporte.setCalificacion(90);
    reporte.setObservaciones("Buen trabajo");
    reporte.setTipoReporte("Parcial");
    reporte.setEstado("Evaluado");

    assertEquals(5, reporte.getIdDocumento());
    assertEquals("Reporte parcial", reporte.getNombreDocumento());
    assertEquals(90, reporte.getCalificacion());
    assertEquals("Buen trabajo", reporte.getObservaciones());
    assertEquals("Parcial", reporte.getTipoReporte());
    assertEquals("Evaluado", reporte.getEstado());
  }

  public void testCatalogoDocumentoGettersSetters() {
    CatalogoDocumento catalogo = new CatalogoDocumento();

    catalogo.setIdCatalogoDocumento(1);
    catalogo.setTipoDocumento("Horario");
    catalogo.setDescripcion("Documento de horario");
    catalogo.setActivo(true);

    assertEquals(1, catalogo.getIdCatalogoDocumento());
    assertEquals("Horario", catalogo.getTipoDocumento());
    assertEquals("Documento de horario", catalogo.getDescripcion());
    assertTrue(catalogo.isActivo());
  }

  public void testCatalogoDocumentoConstructorCompleto() {
    CatalogoDocumento catalogo = new CatalogoDocumento(2, "PSP", "Plan de seguimiento", true);

    assertEquals(2, catalogo.getIdCatalogoDocumento());
    assertEquals("PSP", catalogo.getTipoDocumento());
    assertEquals("Plan de seguimiento", catalogo.getDescripcion());
    assertTrue(catalogo.isActivo());
  }

  public void testCatalogoEvaluacionGettersSetters() {
    CatalogoEvaluacion catalogo = new CatalogoEvaluacion();

    catalogo.setIdCatalogo(1);
    catalogo.setTipoEvaluacion("Parcial");

    assertEquals(1, catalogo.getIdCatalogo());
    assertEquals("Parcial", catalogo.getTipoEvaluacion());
  }

  public void testCatalogoEvaluacionConstructorCompleto() {
    CatalogoEvaluacion catalogo = new CatalogoEvaluacion(2, "Final");

    assertEquals(2, catalogo.getIdCatalogo());
    assertEquals("Final", catalogo.getTipoEvaluacion());
  }

  public void testPracticaProfesionalGettersSetters() {
    LocalDate inicio = LocalDate.of(2026, 1, 1);
    LocalDate fin = LocalDate.of(2026, 6, 1);
    Practicante practicante = new Practicante();
    Proyecto proyecto = new Proyecto();
    PracticaProfesional practica = new PracticaProfesional();

    practica.setIdPractica(1);
    practica.setFechaInicio(inicio);
    practica.setFechaFin(fin);
    practica.setHorasAcumuladas(420);
    practica.setEstado(true);
    practica.setPracticante(practicante);
    practica.setProyecto(proyecto);

    assertEquals(1, practica.getIdPractica());
    assertEquals(inicio, practica.getFechaInicio());
    assertEquals(fin, practica.getFechaFin());
    assertEquals(420, practica.getHorasAcumuladas());
    assertTrue(practica.isEstado());
    assertSame(practicante, practica.getPracticante());
    assertSame(proyecto, practica.getProyecto());
  }

  public void testPracticaProfesionalConstructorCompleto() {
    Practicante practicante = new Practicante();
    Proyecto proyecto = new Proyecto();
    LocalDate inicio = LocalDate.of(2026, 2, 1);
    LocalDate fin = LocalDate.of(2026, 7, 1);
    PracticaProfesional practica = new PracticaProfesional(2, inicio, fin, 210, true, practicante, proyecto);

    assertEquals(2, practica.getIdPractica());
    assertEquals(inicio, practica.getFechaInicio());
    assertEquals(fin, practica.getFechaFin());
    assertEquals(210, practica.getHorasAcumuladas());
    assertTrue(practica.isEstado());
    assertSame(practicante, practica.getPracticante());
    assertSame(proyecto, practica.getProyecto());
  }

  public void testEvaluacionGettersSetters() {
    LocalDate fecha = LocalDate.of(2026, 6, 18);
    PracticaProfesional practica = new PracticaProfesional();
    CatalogoEvaluacion catalogo = new CatalogoEvaluacion();
    Evaluacion evaluacion = new Evaluacion();

    evaluacion.setIdEvaluacion(1);
    evaluacion.setCalificacionNumerica(95);
    evaluacion.setObservaciones("Excelente");
    evaluacion.setFechaEvaluacion(fecha);
    evaluacion.setPractica(practica);
    evaluacion.setCatalogo(catalogo);

    assertEquals(1, evaluacion.getIdEvaluacion());
    assertEquals(95, evaluacion.getCalificacionNumerica());
    assertEquals("Excelente", evaluacion.getObservaciones());
    assertEquals(fecha, evaluacion.getFechaEvaluacion());
    assertSame(practica, evaluacion.getPractica());
    assertSame(catalogo, evaluacion.getCatalogo());
  }

  public void testMensajeGettersSetters() {
    LocalDate fecha = LocalDate.of(2026, 6, 18);
    Mensaje mensaje = new Mensaje();

    mensaje.setIdMensaje(1);
    mensaje.setAsunto("Aviso");
    mensaje.setContenido("Contenido del mensaje");
    mensaje.setFecha(fecha);
    mensaje.setEstado("Enviado");
    mensaje.setIdUsuarioRemitente(2);
    mensaje.setIdUsuarioDestinatario(3);

    assertEquals(1, mensaje.getIdMensaje());
    assertEquals("Aviso", mensaje.getAsunto());
    assertEquals("Contenido del mensaje", mensaje.getContenido());
    assertEquals(fecha, mensaje.getFecha());
    assertEquals("Enviado", mensaje.getEstado());
    assertEquals(2, mensaje.getIdUsuarioRemitente());
    assertEquals(3, mensaje.getIdUsuarioDestinatario());
  }
}
