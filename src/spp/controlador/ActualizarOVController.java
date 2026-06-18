package spp.controlador;

import java.sql.SQLException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import spp.modelo.dto.OrganizacionVinculada;
import spp.service.ProyectoService;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la interfaz de actualización de organizaciones
 * vinculadas del Sistema de Prácticas Profesionales.
 */
public class ActualizarOVController {

    @FXML
    private TextField tfRazonSocial;

    @FXML
    private TextField tfNombreComercial;

    @FXML
    private TextField tfRfc;

    @FXML
    private TextField tfDireccion;

    @FXML
    private Label lblMensaje;

    private OrganizacionVinculada organizacion;
    private ProyectoService proyectoService;

    public ActualizarOVController() {
        proyectoService = new ProyectoService();
    }

    public void inicializarDatos(
            OrganizacionVinculada organizacion) {
        this.organizacion = organizacion;

        tfRazonSocial.setText(
                organizacion.getRazonSocial());

        tfNombreComercial.setText(
                organizacion.getNombreComercial());

        tfRfc.setText(
                organizacion.getRfc());

        tfDireccion.setText(
                organizacion.getDireccion());
    }

    @FXML
    private void actualizar(Event event) {
        try {
            organizacion.setRazonSocial(
                    tfRazonSocial.getText().trim());

            organizacion.setNombreComercial(
                    tfNombreComercial.getText().trim());

            organizacion.setRfc(
                    tfRfc.getText().trim().toUpperCase());

            organizacion.setDireccion(
                    tfDireccion.getText().trim());

            proyectoService.actualizarOrganizacion(
                    organizacion);

            mostrarInformacion(
                    "Actualización exitosa",
                    "Sección actualizada.");

            UtilidadesGUI.mostrarGUI(
            "/spp/vista/GUI-SeccionesActualizacionProyecto.fxml",
            event,
            "Actualización de Proyecto");

        } catch (IllegalArgumentException excepcion) {
            mostrarAdvertencia(
                    "Datos inválidos",
                    excepcion.getMessage());

        } catch (SQLException excepcion) {
            mostrarError(
                    "Error al acceder a la base de datos",
                    "No se pudo acceder a la base de datos. "
                    + "Por favor intente nuevamente en unos minutos.");

            excepcion.printStackTrace();
        }
    }

    @FXML
    private void regresar(Event event) {
        UtilidadesGUI.mostrarGUI(
                "/spp/vista/GUI-SeccionesActualizacionProyecto.fxml",
                event,
                "Actualización de Proyecto");
    }

    private void mostrarInformacion(
            String titulo,
            String mensaje) {

        Alert alerta =
                new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarAdvertencia(
            String titulo,
            String mensaje) {

        Alert alerta =
                new Alert(Alert.AlertType.WARNING);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarError(
            String titulo,
            String mensaje) {

        Alert alerta =
                new Alert(Alert.AlertType.ERROR);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}