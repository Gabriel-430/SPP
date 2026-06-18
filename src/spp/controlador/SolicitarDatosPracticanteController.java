package spp.controlador;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import spp.modelo.dto.Curso;
import spp.service.CursoService;
import spp.service.PracticanteService;
import spp.utilidades.GeneradorContrasenia;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la interfaz de solicitud de datos para registrar
 * practicantes en el Sistema de Prácticas Profesionales.
 */
public class SolicitarDatosPracticanteController {

    @FXML
    private TextField tfMatricula;

    @FXML
    private TextField tfNombreCompleto;

    @FXML
    private ComboBox<String> cmbSexo;

    @FXML
    private TextField tfCreditosAcumulados;

    @FXML
    private CheckBox chkSeguroMedicoVigente;

    @FXML
    private ComboBox<Curso> cmbCurso;

    @FXML
    private Label lblMensaje;

    private CursoService cursoService;
    private PracticanteService practicanteService;

    public SolicitarDatosPracticanteController() {
        cursoService = new CursoService();
        practicanteService = new PracticanteService();
    }

    @FXML
    private void initialize() {
        cargarSexos();
        cargarCursosActivos();
    }

    @FXML
    private void continuarRegistro(Event evento) {
        limpiarMensaje();

        try {
            String lenguaIndigena = solicitarLenguaIndigena();
            String contraseniaGenerada = GeneradorContrasenia
                    .generarContrasenia();

            practicanteService.registrarPracticante(
                    tfMatricula.getText(),
                    tfNombreCompleto.getText(),
                    cmbSexo.getValue(),
                    tfCreditosAcumulados.getText(),
                    chkSeguroMedicoVigente.isSelected(),
                    lenguaIndigena,
                    cmbCurso.getValue(),
                    contraseniaGenerada);

            mostrarInformacion("Registro exitoso",
                    "El registro del practicante fue exitoso.\n\n"
                    + "Usuario: " + tfMatricula.getText().trim() + "\n"
                    + "Contraseña generada: " + contraseniaGenerada);

            UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuCoordinador.fxml",
                    evento, "Menú Coordinador");

        } catch (IllegalArgumentException excepcion) {
            mostrarAdvertencia("Datos inválidos", excepcion.getMessage());

        } catch (SQLException excepcion) {
            mostrarError("Error al acceder a la base de datos",
                    "No se pudo acceder a la base de datos. Por favor intente "
                    + "nuevamente en unos minutos.");
            excepcion.printStackTrace();
        }
    }

    @FXML
    private void cancelarRegistro(Event evento) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cancelar registro");
        alerta.setHeaderText("¿Estás seguro de cancelar el registro?");
        alerta.setContentText("Los datos ingresados no serán guardados.");

        Optional<ButtonType> respuesta = alerta.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuCoordinador.fxml",
                    evento, "Menú Coordinador");
        }
    }

    private void cargarSexos() {
        cmbSexo.getItems().setAll("Masculino", "Femenino", "Otro");
    }

    private void cargarCursosActivos() {
        try {
            List<Curso> cursos = cursoService.obtenerCursosActivos();
            cmbCurso.getItems().setAll(cursos);

            if (cursos.isEmpty()) {
                lblMensaje.setText("No hay cursos activos disponibles.");
            }

        } catch (SQLException excepcion) {
            mostrarError("Error al acceder a la base de datos",
                    "No se pudo acceder a la base de datos. Por favor intente "
                    + "nuevamente en unos minutos.");
            excepcion.printStackTrace();
        }
    }

    private String solicitarLenguaIndigena() {
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Lengua indígena");
        alerta.setHeaderText("¿El practicante habla alguna lengua indígena?");
        alerta.setContentText(null);
        alerta.getButtonTypes().setAll(botonSi, botonNo);

        Optional<ButtonType> respuesta = alerta.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == botonSi) {
            return solicitarDetalleLengua();
        }

        return "Ninguna";
    }

    private String solicitarDetalleLengua() {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Detalle de lengua indígena");
        dialogo.setHeaderText("Ingresa la lengua indígena correspondiente");
        dialogo.setContentText("Lengua:");

        Optional<String> respuesta = dialogo.showAndWait();

        if (!respuesta.isPresent() || respuesta.get().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Por favor, complete todos los campos obligatorios.");
        }

        return respuesta.get().trim();
    }

    private void limpiarMensaje() {
        lblMensaje.setText("");
    }

    private void mostrarInformacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}