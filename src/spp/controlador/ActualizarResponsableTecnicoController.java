package spp.controlador;

import java.sql.SQLException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import spp.modelo.dto.ResponsableTecnicoProyecto;
import spp.service.ProyectoService;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la interfaz de actualización de responsables
 * técnicos de proyectos del Sistema de Prácticas Profesionales.
 */
public class ActualizarResponsableTecnicoController {

    @FXML
    private TextField tfNombreCompleto;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TextField tfCorreo;

    @FXML
    private Label lblMensaje;

    private ResponsableTecnicoProyecto responsable;
    private ProyectoService proyectoService;

    public ActualizarResponsableTecnicoController() {
        proyectoService = new ProyectoService();
    }

    public void inicializarDatos(ResponsableTecnicoProyecto responsable) {
        this.responsable = responsable;

        tfNombreCompleto.setText(responsable.getNombreCompleto());
        tfTelefono.setText(responsable.getTelefono());
        tfCorreo.setText(responsable.getCorreo());
    }

    @FXML
    private void actualizar(Event event) {
        limpiarMensaje();

        try {
            responsable.setNombreCompleto(tfNombreCompleto.getText().trim());
            responsable.setTelefono(tfTelefono.getText().trim());
            responsable.setCorreo(tfCorreo.getText().trim());

            proyectoService.actualizarResponsable(responsable);

            mostrarInformacion("Actualización exitosa",
                    "Sección actualizada.");

            UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuCoordinador.fxml",
            event, "Menú Coordinador");

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