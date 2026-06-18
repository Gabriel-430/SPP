package spp.controlador;

import java.sql.SQLException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import spp.modelo.dto.Proyecto;
import spp.service.ProyectoService;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la interfaz de actualización de datos generales
 * de proyectos del Sistema de Prácticas Profesionales.
 */
public class ActualizarProyectoController {

    @FXML
    private TextField tfNombreProyecto;

    @FXML
    private TextField tfCupoDisponible;

    @FXML
    private TextArea taDescripcionActividades;

    @FXML
    private Label lblMensaje;

    private Proyecto proyecto;
    private ProyectoService proyectoService;

    public ActualizarProyectoController() {
        proyectoService = new ProyectoService();
    }

    public void inicializarDatos(Proyecto proyecto) {
        this.proyecto = proyecto;

        tfNombreProyecto.setText(proyecto.getNombreProyecto());
        tfCupoDisponible.setText(String.valueOf(proyecto.getCupoDisponible()));
        taDescripcionActividades.setText(
                proyecto.getDescripcionActividades());
    }

    @FXML
    private void actualizar(Event event) {
        limpiarMensaje();

        try {
            proyecto.setNombreProyecto(tfNombreProyecto.getText().trim());
            proyecto.setCupoDisponible(convertirCupoDisponible());
            proyecto.setDescripcionActividades(taDescripcionActividades
                    .getText().trim());

            proyectoService.actualizarProyecto(proyecto);

            mostrarInformacion("Actualización exitosa",
                    "Sección actualizada.");

            UtilidadesGUI.mostrarGUI(
            "/spp/vista/GUI-SeccionesActualizacionProyecto.fxml",
            event,
            "Actualización de Proyecto");

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
    private void regresar(Event event) {
        UtilidadesGUI.mostrarGUI(
                "/spp/vista/GUI-SeccionesActualizacionProyecto.fxml",
                event, "Actualización de Proyecto");
    }

    private int convertirCupoDisponible() {
        try {
            return Integer.parseInt(tfCupoDisponible.getText().trim());
        } catch (NumberFormatException excepcion) {
            throw new IllegalArgumentException(
                    "El cupo disponible debe ser numérico.");
        }
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