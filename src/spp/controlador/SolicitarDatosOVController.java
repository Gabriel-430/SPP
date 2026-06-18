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
import spp.service.OrganizacionVinculadaService;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la interfaz de solicitud de datos de organización
 * vinculada para registrar proyectos en el Sistema de Prácticas Profesionales.
 */
public class SolicitarDatosOVController {

    @FXML
    private RadioButton rbNuevaEmpresa;

    @FXML
    private RadioButton rbEmpresaExistente;

    @FXML
    private ComboBox<OrganizacionVinculada> cmbOrganizacionExistente;

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

    private ToggleGroup grupoTipoRegistro;
    private OrganizacionVinculadaService organizacionVinculadaService;

    public SolicitarDatosOVController() {
        organizacionVinculadaService = new OrganizacionVinculadaService();
    }

    @FXML
    private void initialize() {
        configurarRadioButtons();
        cargarOrganizaciones();
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
            OrganizacionVinculada organizacion = obtenerOrganizacion();
            boolean nuevaOrganizacion = rbNuevaEmpresa.isSelected();

            abrirSolicitudResponsable(event, organizacion, nuevaOrganizacion);

        } catch (IllegalArgumentException excepcion) {
            mostrarAdvertencia("Datos inválidos", excepcion.getMessage());

        } catch (SQLException excepcion) {
            mostrarError("Error al acceder a la base de datos",
                    "No se pudo acceder a la base de datos. Por favor intente "
                    + "nuevamente en unos minutos.");
            excepcion.printStackTrace();

        } catch (IOException excepcion) {
            mostrarError("Error al abrir la pantalla",
                    "No se pudo abrir la pantalla de responsable técnico.");
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

        rbNuevaEmpresa.setToggleGroup(grupoTipoRegistro);
        rbEmpresaExistente.setToggleGroup(grupoTipoRegistro);
        rbNuevaEmpresa.setSelected(true);
    }

    private void cargarOrganizaciones() {
        try {
            List<OrganizacionVinculada> organizaciones =
                    organizacionVinculadaService.obtenerOrganizaciones();

            cmbOrganizacionExistente.getItems().setAll(organizaciones);

        } catch (SQLException excepcion) {
            mostrarError("Error al acceder a la base de datos",
                    "No se pudo acceder a la base de datos. Por favor intente "
                    + "nuevamente en unos minutos.");
            excepcion.printStackTrace();
        }
    }

    private void actualizarCampos() {
        boolean esNuevaEmpresa = rbNuevaEmpresa.isSelected();

        cmbOrganizacionExistente.setDisable(esNuevaEmpresa);

        tfRazonSocial.setDisable(!esNuevaEmpresa);
        tfNombreComercial.setDisable(!esNuevaEmpresa);
        tfRfc.setDisable(!esNuevaEmpresa);
        tfDireccion.setDisable(!esNuevaEmpresa);

        if (!esNuevaEmpresa) {
            limpiarCamposOrganizacion();
        }
    }

    private OrganizacionVinculada obtenerOrganizacion()
            throws SQLException {
        if (rbEmpresaExistente.isSelected()) {
            return obtenerOrganizacionExistente();
        }

        return crearNuevaOrganizacion();
    }

    private OrganizacionVinculada obtenerOrganizacionExistente() {
        OrganizacionVinculada organizacion =
                cmbOrganizacionExistente.getValue();

        if (organizacion == null) {
            throw new IllegalArgumentException(
                    "Seleccione una organización vinculada.");
        }

        return organizacion;
    }

    private OrganizacionVinculada crearNuevaOrganizacion()
            throws SQLException {
        OrganizacionVinculada organizacion = new OrganizacionVinculada();

        organizacion.setRazonSocial(tfRazonSocial.getText());
        organizacion.setNombreComercial(tfNombreComercial.getText());
        organizacion.setRfc(tfRfc.getText());
        organizacion.setDireccion(tfDireccion.getText());

        organizacionVinculadaService.validarNuevaOrganizacion(organizacion);

        return organizacion;
    }

    private void abrirSolicitudResponsable(Event event,
            OrganizacionVinculada organizacion, boolean nuevaOrganizacion)
            throws IOException {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                "/spp/vista/GUI-SolicitarDatosResponsable.fxml"));

        Parent raiz = cargador.load();

        SolicitarDatosResponsableController controlador =
                cargador.getController();
        controlador.inicializarDatos(organizacion, nuevaOrganizacion);

        Scene escena = new Scene(raiz);
        Stage escenario = (Stage) ((Node) event.getSource()).getScene()
                .getWindow();

        escenario.setScene(escena);
        escenario.centerOnScreen();
        escenario.show();
    }

    private void limpiarCamposOrganizacion() {
        tfRazonSocial.clear();
        tfNombreComercial.clear();
        tfRfc.clear();
        tfDireccion.clear();
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