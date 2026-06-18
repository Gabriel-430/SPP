package spp.controlador;

import java.sql.SQLException;
import java.util.Optional;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import spp.modelo.dto.OrganizacionVinculada;
import spp.modelo.dto.Proyecto;
import spp.modelo.dto.ResponsableTecnicoProyecto;
import spp.service.ProyectoService;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la interfaz de solicitud de datos del proyecto
 * para registrar proyectos en el Sistema de Prácticas Profesionales.
 */
public class SolicitarDatosProyectoController {

    @FXML
    private TextField tfNombreProyecto;

    @FXML
    private TextField tfCupoDisponible;

    @FXML
    private TextArea taDescripcionActividades;

    @FXML
    private Label lblMensaje;

    private OrganizacionVinculada organizacion;
    private ResponsableTecnicoProyecto responsable;
    private boolean nuevaOrganizacion;
    private ProyectoService proyectoService;

    public SolicitarDatosProyectoController() {
        proyectoService = new ProyectoService();
    }

    public void inicializarDatos(OrganizacionVinculada organizacion,
            ResponsableTecnicoProyecto responsable,
            boolean nuevaOrganizacion) {
        this.organizacion = organizacion;
        this.responsable = responsable;
        this.nuevaOrganizacion = nuevaOrganizacion;
    }

    @FXML
    private void confirmarDatos(Event event) {
        limpiarMensaje();

        try {
            Proyecto proyecto = crearProyecto();

            proyectoService.registrarProyecto(organizacion, responsable,
                    proyecto, nuevaOrganizacion);

            mostrarInformacion("Registro exitoso",
                    "Registro del Proyecto Exitoso.");

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
    private void cancelarRegistro(Event event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Cancelar registro");
        alerta.setHeaderText("¿Estás seguro de cancelar la operación?");
        alerta.setContentText("Los datos ingresados no serán guardados.");

        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");
        alerta.getButtonTypes().setAll(botonSi, botonNo);

        Optional<ButtonType> respuesta = alerta.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == botonSi) {
            UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuCoordinador.fxml",
                    event, "Menú Coordinador");
        }
    }

    private Proyecto crearProyecto() {
        if (estaVacio(tfNombreProyecto.getText())
                || estaVacio(tfCupoDisponible.getText())
                || estaVacio(taDescripcionActividades.getText())) {
            throw new IllegalArgumentException(
                    "Por favor, complete todos los campos obligatorios.");
        }

        int cupoDisponible = convertirCupoDisponible();

        Proyecto proyecto = new Proyecto();

        proyecto.setNombreProyecto(tfNombreProyecto.getText().trim());
        proyecto.setCupoDisponible(cupoDisponible);
        proyecto.setDescripcionActividades(taDescripcionActividades
                .getText().trim());
        proyecto.setEstadoProyecto("Activo");

        return proyecto;
    }

    private int convertirCupoDisponible() {
        try {
            return Integer.parseInt(tfCupoDisponible.getText().trim());
        } catch (NumberFormatException excepcion) {
            throw new IllegalArgumentException(
                    "El cupo disponible debe ser numérico.");
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
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