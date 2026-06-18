package spp.modelo.dto;

import junit.framework.TestCase;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 18/06/2026
 * Descripción: Pruebas unitarias de DTO relacionados con proyectos sin usar Hamcrest.
 */
public class DTOProyectoTest extends TestCase {

  public void testResponsableTecnicoGettersSetters() {
    OrganizacionVinculada organizacion = new OrganizacionVinculada();
    ResponsableTecnicoProyecto responsable = new ResponsableTecnicoProyecto();

    responsable.setIdResponsable(1);
    responsable.setNombreCompleto("Ana Responsable");
    responsable.setTelefono("2281234567");
    responsable.setCorreo("ana@empresa.com");
    responsable.setOrganizacion(organizacion);

    assertEquals(1, responsable.getIdResponsable());
    assertEquals("Ana Responsable", responsable.getNombreCompleto());
    assertEquals("2281234567", responsable.getTelefono());
    assertEquals("ana@empresa.com", responsable.getCorreo());
    assertSame(organizacion, responsable.getOrganizacion());
  }

  public void testResponsableTecnicoConstructorCompleto() {
    OrganizacionVinculada organizacion = new OrganizacionVinculada();
    ResponsableTecnicoProyecto responsable = new ResponsableTecnicoProyecto(3, "Luis Técnico", "2281112233", "luis@empresa.com", organizacion);

    assertEquals(3, responsable.getIdResponsable());
    assertEquals("Luis Técnico", responsable.getNombreCompleto());
    assertEquals("2281112233", responsable.getTelefono());
    assertEquals("luis@empresa.com", responsable.getCorreo());
    assertSame(organizacion, responsable.getOrganizacion());
  }

  public void testResponsableTecnicoToString() {
    ResponsableTecnicoProyecto responsable = new ResponsableTecnicoProyecto();

    responsable.setNombreCompleto("Ana Responsable");
    responsable.setCorreo("ana@empresa.com");

    assertEquals("Ana Responsable - ana@empresa.com", responsable.toString());
  }

  public void testProyectoGettersSetters() {
    OrganizacionVinculada organizacion = new OrganizacionVinculada();
    ResponsableTecnicoProyecto responsable = new ResponsableTecnicoProyecto();
    Proyecto proyecto = new Proyecto();

    proyecto.setIdProyecto(1);
    proyecto.setNombreProyecto("Proyecto Web");
    proyecto.setCupoDisponible(3);
    proyecto.setEstadoProyecto("Disponible");
    proyecto.setDescripcionActividades("Desarrollo de sistema");
    proyecto.setOrganizacion(organizacion);
    proyecto.setResponsable(responsable);

    assertEquals(1, proyecto.getIdProyecto());
    assertEquals("Proyecto Web", proyecto.getNombreProyecto());
    assertEquals(3, proyecto.getCupoDisponible());
    assertEquals("Disponible", proyecto.getEstadoProyecto());
    assertEquals("Desarrollo de sistema", proyecto.getDescripcionActividades());
    assertSame(organizacion, proyecto.getOrganizacion());
    assertSame(responsable, proyecto.getResponsable());
  }

  public void testProyectoConstructorCompleto() {
    OrganizacionVinculada organizacion = new OrganizacionVinculada();
    ResponsableTecnicoProyecto responsable = new ResponsableTecnicoProyecto();
    Proyecto proyecto = new Proyecto(2, 5, "Activo", organizacion, responsable);

    assertEquals(2, proyecto.getIdProyecto());
    assertEquals(5, proyecto.getCupoDisponible());
    assertEquals("Activo", proyecto.getEstadoProyecto());
    assertSame(organizacion, proyecto.getOrganizacion());
    assertSame(responsable, proyecto.getResponsable());
  }

  public void testProyectoPermiteCambiarEstadoADisponible() {
    Proyecto proyecto = new Proyecto();

    proyecto.setEstadoProyecto("No Disponible");
    proyecto.setEstadoProyecto("Disponible");

    assertEquals("Disponible", proyecto.getEstadoProyecto());
  }

  public void testProyectoPermiteActualizarCupo() {
    Proyecto proyecto = new Proyecto();

    proyecto.setCupoDisponible(0);
    proyecto.setCupoDisponible(proyecto.getCupoDisponible() + 1);

    assertEquals(1, proyecto.getCupoDisponible());
  }

  public void testProyectoPermiteActualizarDescripcion() {
    Proyecto proyecto = new Proyecto();

    proyecto.setDescripcionActividades("Actividades iniciales");
    proyecto.setDescripcionActividades("Actividades actualizadas");

    assertEquals("Actividades actualizadas", proyecto.getDescripcionActividades());
  }
}
