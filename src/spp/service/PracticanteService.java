package spp.service;

import java.sql.SQLException;
import java.util.List;
import spp.modelo.dao.PracticanteDAO;
import spp.modelo.dto.Curso;
import spp.modelo.dto.Practicante;
import spp.modelo.dto.Usuario;
import spp.utilidades.EncriptadorContrasenia;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Gestiona las operaciones de practicantes del Sistema de
 * Prácticas Profesionales.
 */
public class PracticanteService {

    private static final int CREDITOS_TOTALES = 365;
    private static final double PORCENTAJE_MINIMO_CREDITOS = 0.70;

    private PracticanteDAO practicanteDAO;

    public PracticanteService() {
        practicanteDAO = new PracticanteDAO();
    }

    public List<Practicante> obtenerPracticantes() throws SQLException {
        return practicanteDAO.obtenerPracticantes();
    }

    public void registrarPracticante(String matricula, String nombreCompleto,
            String sexo, String creditosAcumuladosTexto,
            boolean seguroMedicoVigente, String lenguaIndigena, Curso curso,
            String contraseniaGenerada) throws SQLException {
        validarDatos(matricula, nombreCompleto, sexo, creditosAcumuladosTexto,
                curso, contraseniaGenerada);

        int creditosAcumulados = Integer.parseInt(
                creditosAcumuladosTexto.trim());

        validarRequisitoCreditos(creditosAcumulados);

        if (practicanteDAO.existeMatricula(matricula.trim())) {
            throw new IllegalArgumentException(
                    "Ya existe un practicante con esa matrícula.");
        }

        Usuario usuario = crearUsuario(matricula, nombreCompleto,
                contraseniaGenerada);

        Practicante practicante = new Practicante();

        practicante.setMatricula(matricula.trim());
        practicante.setSexo(sexo.trim());
        practicante.setLenguaIndigena(lenguaIndigena);
        practicante.setCreditosAcumulados(creditosAcumulados);
        practicante.setSeguroMedicoVigente(seguroMedicoVigente);
        practicante.setCurso(curso);
        practicante.setUsuario(usuario);

        practicanteDAO.registrarPracticante(practicante);
    }

    private Usuario crearUsuario(String matricula, String nombreCompleto,
            String contraseniaGenerada) {
        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(matricula.trim());
        usuario.setNombreCompleto(nombreCompleto.trim());
        usuario.setCorreoElectronico("");
        usuario.setTelefono("");
        usuario.setContrasenia(EncriptadorContrasenia.generarHash(
                contraseniaGenerada));
        usuario.setEstadoActivo(true);

        return usuario;
    }

    private void validarDatos(String matricula, String nombreCompleto,
            String sexo, String creditosAcumuladosTexto, Curso curso,
            String contraseniaGenerada) {
        if (estaVacio(matricula) || estaVacio(nombreCompleto)
                || estaVacio(sexo) || estaVacio(creditosAcumuladosTexto)
                || curso == null || estaVacio(contraseniaGenerada)) {
            throw new IllegalArgumentException(
                    "Por favor, complete todos los campos obligatorios.");
        }
        if (!esMatriculaValida(matricula.trim())) {
            throw new IllegalArgumentException(
                    "La matrícula debe iniciar con S y contener 8 números.");
        }

        try {
            Integer.parseInt(creditosAcumuladosTexto.trim());
        } catch (NumberFormatException excepcion) {
            throw new IllegalArgumentException(
                    "Los créditos acumulados deben ser numéricos.");
        }
    }

    private void validarRequisitoCreditos(int creditosAcumulados) {
        int creditosMinimos = (int) Math.ceil(CREDITOS_TOTALES
                * PORCENTAJE_MINIMO_CREDITOS);

        if (creditosAcumulados < creditosMinimos) {
            throw new IllegalArgumentException(
                    "El practicante no cumple con el mínimo de créditos "
                    + "requeridos.");
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private boolean esMatriculaValida(String matricula) {
        return matricula.matches("[sS]\\d{8}");
    }

    public List<Practicante> recuperarListaPracticantesConDocumentos(int idProfesor) throws SQLException {
        return practicanteDAO.recuperarPracticantesConDocumentos(idProfesor);
    }
}
