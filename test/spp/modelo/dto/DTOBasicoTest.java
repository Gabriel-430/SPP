package spp.modelo.dto;

import junit.framework.TestCase;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 18/06/2026
 * Descripción: Pruebas unitarias básicas para DTO principales del SPP sin usar Hamcrest.
 */
public class DTOBasicoTest extends TestCase {

  public void testUsuarioGettersSetters() {
    Usuario usuario = new Usuario();

    usuario.setIdUsuario(1);
    usuario.setNombreUsuario("admin");
    usuario.setNombreCompleto("Emiliano Morales");
    usuario.setContrasenia("12345");
    usuario.setTelefono("2281234567");
    usuario.setCorreoElectronico("admin@spp.com");
    usuario.setEstadoActivo(true);

    assertEquals(1, usuario.getIdUsuario());
    assertEquals("admin", usuario.getNombreUsuario());
    assertEquals("Emiliano Morales", usuario.getNombreCompleto());
    assertEquals("12345", usuario.getContrasenia());
    assertEquals("2281234567", usuario.getTelefono());
    assertEquals("admin@spp.com", usuario.getCorreoElectronico());
    assertTrue(usuario.isEstadoActivo());
  }

  public void testUsuarioConstructorCompleto() {
    Usuario usuario = new Usuario(2, "coord", "Gabriel Martinez", "abc", "2280000000", "coord@spp.com", true);

    assertEquals(2, usuario.getIdUsuario());
    assertEquals("coord", usuario.getNombreUsuario());
    assertEquals("Gabriel Martinez", usuario.getNombreCompleto());
    assertEquals("abc", usuario.getContrasenia());
    assertEquals("2280000000", usuario.getTelefono());
    assertEquals("coord@spp.com", usuario.getCorreoElectronico());
    assertTrue(usuario.isEstadoActivo());
  }

  public void testProfesorGettersSetters() {
    Usuario usuario = new Usuario();
    Profesor profesor = new Profesor();

    profesor.setIdProfesor(10);
    profesor.setNumeroPersonal(20001);
    profesor.setTurno("Matutino");
    profesor.setUsuario(usuario);

    assertEquals(10, profesor.getIdProfesor());
    assertEquals(20001, profesor.getNumeroPersonal());
    assertEquals("Matutino", profesor.getTurno());
    assertSame(usuario, profesor.getUsuario());
  }

  public void testProfesorConstructorCompleto() {
    Usuario usuario = new Usuario();
    Profesor profesor = new Profesor(1, 9000, "Vespertino", usuario);

    assertEquals(1, profesor.getIdProfesor());
    assertEquals(9000, profesor.getNumeroPersonal());
    assertEquals("Vespertino", profesor.getTurno());
    assertSame(usuario, profesor.getUsuario());
  }

  public void testCoordinadorGettersSetters() {
    Usuario usuario = new Usuario();
    Coordinador coordinador = new Coordinador();

    coordinador.setIdCoordinador(5);
    coordinador.setNumeroPersonal(10001);
    coordinador.setUsuario(usuario);

    assertEquals(5, coordinador.getIdCoordinador());
    assertEquals(10001, coordinador.getNumeroPersonal());
    assertSame(usuario, coordinador.getUsuario());
  }

  public void testCoordinadorConstructorCompleto() {
    Usuario usuario = new Usuario();
    Coordinador coordinador = new Coordinador(1, 10002, usuario);

    assertEquals(1, coordinador.getIdCoordinador());
    assertEquals(10002, coordinador.getNumeroPersonal());
    assertSame(usuario, coordinador.getUsuario());
  }

  public void testCursoGettersSetters() {
    Profesor profesor = new Profesor();
    PeriodoEscolar periodo = new PeriodoEscolar();
    Coordinador coordinador = new Coordinador();
    Curso curso = new Curso();

    curso.setIdCurso(1);
    curso.setNrc("12345");
    curso.setBloque("1");
    curso.setCupoAlumnos(30);
    curso.setNombre("Experiencia Recepcional");
    curso.setCreditos(8);
    curso.setProfesor(profesor);
    curso.setPeriodo(periodo);
    curso.setCoordinador(coordinador);
    curso.setEstado("Activo");

    assertEquals(1, curso.getIdCurso());
    assertEquals("12345", curso.getNrc());
    assertEquals("1", curso.getBloque());
    assertEquals(30, curso.getCupoAlumnos());
    assertEquals("Experiencia Recepcional", curso.getNombre());
    assertEquals(8, curso.getCreditos());
    assertSame(profesor, curso.getProfesor());
    assertSame(periodo, curso.getPeriodo());
    assertSame(coordinador, curso.getCoordinador());
    assertEquals("Activo", curso.getEstado());
  }

  public void testCursoToString() {
    Curso curso = new Curso();

    curso.setNrc("12345");
    curso.setNombre("Experiencia Recepcional");
    curso.setBloque("2");

    assertEquals("12345 - Experiencia Recepcional - Bloque 2", curso.toString());
  }

  public void testPracticanteGettersSetters() {
    Curso curso = new Curso();
    Usuario usuario = new Usuario();
    Practicante practicante = new Practicante();

    practicante.setIdPracticante(1);
    practicante.setMatricula("S24001234");
    practicante.setSexo("Masculino");
    practicante.setLenguaIndigena("Náhuatl");
    practicante.setCreditosAcumulados(300);
    practicante.setSeguroMedicoVigente(true);
    practicante.setCurso(curso);
    practicante.setUsuario(usuario);

    assertEquals(1, practicante.getIdPracticante());
    assertEquals("S24001234", practicante.getMatricula());
    assertEquals("Masculino", practicante.getSexo());
    assertEquals("Náhuatl", practicante.getLenguaIndigena());
    assertEquals(300, practicante.getCreditosAcumulados());
    assertTrue(practicante.isSeguroMedicoVigente());
    assertSame(curso, practicante.getCurso());
    assertSame(usuario, practicante.getUsuario());
  }

  public void testPracticanteConstructorCompleto() {
    Curso curso = new Curso();
    Usuario usuario = new Usuario();
    Practicante practicante = new Practicante(2, "S24000001", "Femenino", "No", 280, true, curso, usuario);

    assertEquals(2, practicante.getIdPracticante());
    assertEquals("S24000001", practicante.getMatricula());
    assertEquals("Femenino", practicante.getSexo());
    assertEquals("No", practicante.getLenguaIndigena());
    assertEquals(280, practicante.getCreditosAcumulados());
    assertTrue(practicante.isSeguroMedicoVigente());
    assertSame(curso, practicante.getCurso());
    assertSame(usuario, practicante.getUsuario());
  }

  public void testOrganizacionVinculadaGettersSetters() {
    OrganizacionVinculada organizacion = new OrganizacionVinculada();

    organizacion.setIdOrganizacion(1);
    organizacion.setRazonSocial("Empresa SA de CV");
    organizacion.setNombreComercial("Empresa");
    organizacion.setRfc("ABC2401011A1");
    organizacion.setDireccion("Xalapa");

    assertEquals(1, organizacion.getIdOrganizacion());
    assertEquals("Empresa SA de CV", organizacion.getRazonSocial());
    assertEquals("Empresa", organizacion.getNombreComercial());
    assertEquals("ABC2401011A1", organizacion.getRfc());
    assertEquals("Xalapa", organizacion.getDireccion());
  }

  public void testOrganizacionVinculadaConstructorCompleto() {
    OrganizacionVinculada organizacion = new OrganizacionVinculada(2, "UV SA de CV", "UV", "UVW240101AB1", "Xalapa");

    assertEquals(2, organizacion.getIdOrganizacion());
    assertEquals("UV SA de CV", organizacion.getRazonSocial());
    assertEquals("UV", organizacion.getNombreComercial());
    assertEquals("UVW240101AB1", organizacion.getRfc());
    assertEquals("Xalapa", organizacion.getDireccion());
  }
}
