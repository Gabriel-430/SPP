package spp.controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import spp.modelo.dto.OrganizacionVinculada;
import spp.modelo.dto.ResponsableTecnicoProyecto;
import spp.service.ResponsableTecnicoProyectoService;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la interfaz de solicitud de datos del responsable
 * técnico para registrar proyectos en el Sistema de Prácticas Profesionales.
 */
public class SolicitarDatosResponsableController {

    @FXML
    private RadioButton rbNuevoResponsable;

    @FXML
    private RadioButton rbResponsableExistente;

    @FXML
    private ComboBox<ResponsableTecnicoProyecto> cmbResponsableExistente;

    @FXML
    private TextField tfNombreCompleto;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TextField tfCorreo;

    @FXML
    private Label lblMensaje;

    private ToggleGroup grupoTipoRegistro;
    private OrganizacionVinculada organizacion;
    private boolean nuevaOrganizacion;
    private ResponsableTecnicoProyectoService responsableService;

    public SolicitarDatosResponsableController() {
        responsableService = new ResponsableTecnicoProyectoService();
    }

    public void inicializarDatos(OrganizacionVinculada organizacion,
            boolean nuevaOrganizacion) {
        this.organizacion = organizacion;
        this.nuevaOrganizacion = nuevaOrganizacion;

        configurarRadioButtons();
        cargarResponsablesExistentes();
        actualizarCampos();
    }

    @FXML
    private void cambiarTipoRegistro(Event event) {
        actualizarCampos();
    }

    @FXML
    private void continuarRegistro(Event event) {
        limpiarMensaje();

        try {
            ResponsableTecnicoProyecto responsable = obtenerResponsable();

            abrirSolicitudProyecto(event, responsable);

        } catch (IllegalArgumentException excepcion) {
            mostrarAdvertencia("Datos inválidos", excepcion.getMessage());

        } catch (IOException excepcion) {
            mostrarError("Error al abrir la pantalla",
                    "No se pudo abrir la pantalla de datos del proyecto.");
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

    private void configurarRadioButtons() {
        grupoTipoRegistro = new ToggleGroup();

        rbNuevoResponsable.setToggleGroup(grupoTipoRegistro);
        rbResponsableExistente.setToggleGroup(grupoTipoRegistro);
        rbNuevoResponsable.setSelected(true);

        if (nuevaOrganizacion) {
            rbResponsableExistente.setDisable(true);
        }
    }

    private void cargarResponsablesExistentes() {
        if (nuevaOrganizacion || organizacion == null
                || organizacion.getIdOrganizacion() == 0) {
            return;
        }

        try {
            List<ResponsableTecnicoProyecto> responsables =
                    responsableService.obtenerResponsablesPorOrganizacion(
                            organizacion.getIdOrganizacion());

            cmbResponsableExistente.getItems().setAll(responsables);

        } catch (SQLException excepcion) {
            mostrarError("Error al acceder a la base de datos",
                    "No se pudo acceder a la base de datos. Por favor intente "
                    + "nuevamente en unos minutos.");
            excepcion.printStackTrace();
        }
    }

    private void actualizarCampos() {
        boolean esNuevoResponsable = rbNuevoResponsable.isSelected();

        cmbResponsableExistente.setDisable(esNuevoResponsable);

        tfNombreCompleto.setDisable(!esNuevoResponsable);
        tfTelefono.setDisable(!esNuevoResponsable);
        tfCorreo.setDisable(!esNuevoResponsable);

        if (!esNuevoResponsable) {
            limpiarCamposResponsable();
        }
    }

    private ResponsableTecnicoProyecto obtenerResponsable() {
        if (rbResponsableExistente.isSelected()) {
            return obtenerResponsableExistente();
        }

        return crearNuevoResponsable();
    }

    private ResponsableTecnicoProyecto obtenerResponsableExistente() {
        ResponsableTecnicoProyecto responsable =
                cmbResponsableExistente.getValue();

        responsableService.validarResponsableExistente(responsable);

        return responsable;
    }

    private ResponsableTecnicoProyecto crearNuevoResponsable() {
        ResponsableTecnicoProyecto responsable =
                new ResponsableTecnicoProyecto();

        responsable.setNombreCompleto(tfNombreCompleto.getText());
        responsable.setTelefono(tfTelefono.getText());
        responsable.setCorreo(tfCorreo.getText());
        responsable.setOrganizacion(organizacion);

        responsableService.validarNuevoResponsable(responsable);

        return responsable;
    }

    private void abrirSolicitudProyecto(Event event,
            ResponsableTecnicoProyecto responsable) throws IOException {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                "/spp/vista/GUI-SolicitarDatosProyecto.fxml"));

        Parent raiz = cargador.load();

        SolicitarDatosProyectoController controlador =
                cargador.getController();

        controlador.inicializarDatos(organizacion, responsable,
                nuevaOrganizacion);

        Scene escena = new Scene(raiz);
        Stage escenario = (Stage) ((Node) event.getSource()).getScene()
                .getWindow();

        escenario.setScene(escena);
        escenario.centerOnScreen();
        escenario.show();
    }

    private void limpiarCamposResponsable() {
        tfNombreCompleto.clear();
        tfTelefono.clear();
        tfCorreo.clear();
    }

    private void limpiarMensaje() {
        lblMensaje.setText("");
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