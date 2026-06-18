package spp.service;

import junit.framework.TestCase;
import spp.modelo.dto.Curso;
import spp.modelo.dto.OrganizacionVinculada;
import spp.modelo.dto.Proyecto;
import spp.modelo.dto.ResponsableTecnicoProyecto;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 18/06/2026
 * Descripción: Pruebas unitarias de validaciones de servicios sin depender de base de datos.
 */
public class ValidacionesSinBaseDatosTest extends TestCase {

  public void testResponsableValidoNoLanzaExcepcion() {
    ResponsableTecnicoProyecto responsable = new ResponsableTecnicoProyecto();
    ResponsableTecnicoProyectoService service = new ResponsableTecnicoProyectoService();

    responsable.setNombreCompleto("Ana Responsable");
    responsable.setTelefono("2281234567");
    responsable.setCorreo("ana@empresa.com");

    service.validarNuevoResponsable(responsable);
  }

  public void testResponsableNuloLanzaExcepcion() {
    ResponsableTecnicoProyectoService service = new ResponsableTecnicoProyectoService();

    try {
      service.validarNuevoResponsable(null);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().length() > 0);
    }
  }

  public void testResponsableSinNombreLanzaExcepcion() {
    ResponsableTecnicoProyecto responsable = crearResponsableValido();
    ResponsableTecnicoProyectoService service = new ResponsableTecnicoProyectoService();

    responsable.setNombreCompleto(" ");

    try {
      service.validarNuevoResponsable(responsable);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("campos"));
    }
  }

  public void testResponsableTelefonoCortoLanzaExcepcion() {
    ResponsableTecnicoProyecto responsable = crearResponsableValido();
    ResponsableTecnicoProyectoService service = new ResponsableTecnicoProyectoService();

    responsable.setTelefono("228123");

    try {
      service.validarNuevoResponsable(responsable);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("10"));
    }
  }

  public void testResponsableTelefonoConLetrasLanzaExcepcion() {
    ResponsableTecnicoProyecto responsable = crearResponsableValido();
    ResponsableTecnicoProyectoService service = new ResponsableTecnicoProyectoService();

    responsable.setTelefono("228ABC4567");

    try {
      service.validarNuevoResponsable(responsable);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("10"));
    }
  }

  public void testResponsableCorreoInvalidoLanzaExcepcion() {
    ResponsableTecnicoProyecto responsable = crearResponsableValido();
    ResponsableTecnicoProyectoService service = new ResponsableTecnicoProyectoService();

    responsable.setCorreo("correo-invalido");

    try {
      service.validarNuevoResponsable(responsable);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("correo"));
    }
  }

  public void testResponsableExistenteNuloLanzaExcepcion() {
    ResponsableTecnicoProyectoService service = new ResponsableTecnicoProyectoService();

    try {
      service.validarResponsableExistente(null);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("Seleccione"));
    }
  }

  public void testResponsableExistenteValidoNoLanzaExcepcion() {
    ResponsableTecnicoProyectoService service = new ResponsableTecnicoProyectoService();

    service.validarResponsableExistente(new ResponsableTecnicoProyecto());
  }

  public void testOrganizacionNulaLanzaExcepcion() throws Exception {
    OrganizacionVinculadaService service = new OrganizacionVinculadaService();

    try {
      service.validarNuevaOrganizacion(null);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("campos"));
    }
  }

  public void testOrganizacionSinRfcLanzaExcepcion() throws Exception {
    OrganizacionVinculada organizacion = crearOrganizacionValida();
    OrganizacionVinculadaService service = new OrganizacionVinculadaService();

    organizacion.setRfc(" ");

    try {
      service.validarNuevaOrganizacion(organizacion);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("campos"));
    }
  }

  public void testOrganizacionRfcPersonaFisicaLanzaExcepcion() throws Exception {
    OrganizacionVinculada organizacion = crearOrganizacionValida();
    OrganizacionVinculadaService service = new OrganizacionVinculadaService();

    organizacion.setRfc("MOBE010101AA1");

    try {
      service.validarNuevaOrganizacion(organizacion);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("RFC"));
    }
  }

  public void testOrganizacionRfcCortoLanzaExcepcion() throws Exception {
    OrganizacionVinculada organizacion = crearOrganizacionValida();
    OrganizacionVinculadaService service = new OrganizacionVinculadaService();

    organizacion.setRfc("ABC240101");

    try {
      service.validarNuevaOrganizacion(organizacion);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("RFC"));
    }
  }

  public void testProfesorConCorreoInvalidoLanzaExcepcion() throws Exception {
    ProfesorService service = new ProfesorService();
    Curso curso = new Curso();

    try {
      service.registrarProfesor("Ana", "López", "correo", "123", "2281234567", "Matutino", "Password1*", curso);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("correo"));
    }
  }

  public void testProfesorConTelefonoInvalidoLanzaExcepcion() throws Exception {
    ProfesorService service = new ProfesorService();
    Curso curso = new Curso();

    try {
      service.registrarProfesor("Ana", "López", "ana@uv.mx", "123", "228ABC4567", "Matutino", "Password1*", curso);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("10"));
    }
  }

  public void testProfesorConNumeroPersonalNoNumericoLanzaExcepcion() throws Exception {
    ProfesorService service = new ProfesorService();
    Curso curso = new Curso();

    try {
      service.registrarProfesor("Ana", "López", "ana@uv.mx", "ABC", "2281234567", "Matutino", "Password1*", curso);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("numérico"));
    }
  }

  public void testPracticanteMatriculaSinSLanzaExcepcion() throws Exception {
    PracticanteService service = new PracticanteService();
    Curso curso = new Curso();

    try {
      service.registrarPracticante("24001234", "Naruto Uzumaki", "Masculino", "300", true, "No", curso, "Password1*");
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("matrícula"));
    }
  }

  public void testPracticanteMatriculaCortaLanzaExcepcion() throws Exception {
    PracticanteService service = new PracticanteService();
    Curso curso = new Curso();

    try {
      service.registrarPracticante("S2400", "Naruto Uzumaki", "Masculino", "300", true, "No", curso, "Password1*");
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("matrícula"));
    }
  }

  public void testPracticanteCreditosNoNumericosLanzaExcepcion() throws Exception {
    PracticanteService service = new PracticanteService();
    Curso curso = new Curso();

    try {
      service.registrarPracticante("S24001234", "Naruto Uzumaki", "Masculino", "ABC", true, "No", curso, "Password1*");
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("créditos"));
    }
  }

  public void testPracticanteCreditosInsuficientesLanzaExcepcion() throws Exception {
    PracticanteService service = new PracticanteService();
    Curso curso = new Curso();

    try {
      service.registrarPracticante("S24001234", "Naruto Uzumaki", "Masculino", "100", true, "No", curso, "Password1*");
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("créditos"));
    }
  }

  public void testActualizarProyectoSinNombreLanzaExcepcion() throws Exception {
    ProyectoService service = new ProyectoService();
    Proyecto proyecto = crearProyectoValido();

    proyecto.setNombreProyecto(" ");

    try {
      service.actualizarProyecto(proyecto);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("campos"));
    }
  }

  public void testActualizarProyectoConCupoCeroLanzaExcepcion() throws Exception {
    ProyectoService service = new ProyectoService();
    Proyecto proyecto = crearProyectoValido();

    proyecto.setCupoDisponible(0);

    try {
      service.actualizarProyecto(proyecto);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("cupo"));
    }
  }

  public void testActualizarResponsableConCorreoInvalidoLanzaExcepcion() throws Exception {
    ProyectoService service = new ProyectoService();
    ResponsableTecnicoProyecto responsable = crearResponsableValido();

    responsable.setCorreo("correo");

    try {
      service.actualizarResponsable(responsable);
      fail("Se esperaba IllegalArgumentException.");
    } catch (IllegalArgumentException excepcion) {
      assertTrue(excepcion.getMessage().contains("correo"));
    }
  }

  private ResponsableTecnicoProyecto crearResponsableValido() {
    ResponsableTecnicoProyecto responsable = new ResponsableTecnicoProyecto();

    responsable.setNombreCompleto("Ana Responsable");
    responsable.setTelefono("2281234567");
    responsable.setCorreo("ana@empresa.com");

    return responsable;
  }

  private OrganizacionVinculada crearOrganizacionValida() {
    OrganizacionVinculada organizacion = new OrganizacionVinculada();

    organizacion.setRazonSocial("Empresa SA de CV");
    organizacion.setNombreComercial("Empresa");
    organizacion.setRfc("ABC2401011A1");
    organizacion.setDireccion("Xalapa");

    return organizacion;
  }

  private Proyecto crearProyectoValido() {
    Proyecto proyecto = new Proyecto();

    proyecto.setNombreProyecto("Proyecto Web");
    proyecto.setCupoDisponible(3);
    proyecto.setDescripcionActividades("Actividades de desarrollo");

    return proyecto;
  }
}
