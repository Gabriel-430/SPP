package spp.controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import spp.modelo.dto.Proyecto;
import spp.service.ProyectoService;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la interfaz de selección de secciones para la
 * actualización de proyectos del Sistema de Prácticas Profesionales.
 */
public class SeccionesActualizacionProyectoController {

    @FXML
    private RadioButton rbNombreProyecto;

    @FXML
    private RadioButton rbOrganizacion;

    @FXML
    private RadioButton rbResponsable;

    @FXML
    private TextField tfBusqueda;

    @FXML
    private TableView<Proyecto> tvProyectos;

    @FXML
    private TableColumn<Proyecto, String> tcProyecto;

    @FXML
    private TableColumn<Proyecto, String> tcOrganizacion;

    @FXML
    private TableColumn<Proyecto, String> tcResponsable;

    @FXML
    private TableColumn<Proyecto, Number> tcCupo;

    @FXML
    private TableColumn<Proyecto, String> tcEstado;

    @FXML
    private Label lblMensaje;

    private ToggleGroup grupoBusqueda;
    private ProyectoService proyectoService;
    private List<Proyecto> proyectos;

    public SeccionesActualizacionProyectoController() {
        proyectoService = new ProyectoService();
    }

    @FXML
    private void initialize() {
        configurarRadioButtons();
        configurarTabla();
        cargarProyectos();
    }

    @FXML
    private void buscarProyecto(Event event) {
        limpiarMensaje();

        String criterio = tfBusqueda.getText();

        if (criterio == null || criterio.trim().isEmpty()) {
            tvProyectos.getItems().setAll(proyectos);
            return;
        }

        String criterioBusqueda = criterio.trim().toLowerCase();

        List<Proyecto> proyectosFiltrados = proyectos.stream()
                .filter(proyecto -> cumpleCriterioBusqueda(proyecto,
                        criterioBusqueda))
                .collect(Collectors.toList());

        tvProyectos.getItems().setAll(proyectosFiltrados);

        if (proyectosFiltrados.isEmpty()) {
            lblMensaje.setText("No se encontraron proyectos con ese criterio.");
        }
    }

    @FXML
    private void actualizarProyecto(Event event) {
        Proyecto proyecto = obtenerProyectoSeleccionado();

        if (proyecto == null) {
            return;
        }

        abrirActualizarProyecto(event, proyecto);
    }

    @FXML
    private void actualizarOrganizacion(Event event) {
        Proyecto proyecto = obtenerProyectoSeleccionado();

        if (proyecto == null) {
            return;
        }

        abrirActualizarOrganizacion(event, proyecto);
    }

    @FXML
    private void actualizarResponsable(Event event) {
        Proyecto proyecto = obtenerProyectoSeleccionado();

        if (proyecto == null) {
            return;
        }

        abrirActualizarResponsable(event, proyecto);
    }

    @FXML
    private void regresar(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuCoordinador.fxml",
                event, "Menú Coordinador");
    }

    private void configurarRadioButtons() {
        grupoBusqueda = new ToggleGroup();

        rbNombreProyecto.setToggleGroup(grupoBusqueda);
        rbOrganizacion.setToggleGroup(grupoBusqueda);
        rbResponsable.setToggleGroup(grupoBusqueda);
        rbNombreProyecto.setSelected(true);
    }

    private void configurarTabla() {
        tcProyecto.setCellValueFactory(celda -> new SimpleStringProperty(
                celda.getValue().getNombreProyecto()));

        tcOrganizacion.setCellValueFactory(celda -> new SimpleStringProperty(
                celda.getValue().getOrganizacion().getNombreComercial()));

        tcResponsable.setCellValueFactory(celda -> new SimpleStringProperty(
                celda.getValue().getResponsable().getNombreCompleto()));

        tcCupo.setCellValueFactory(celda -> new SimpleIntegerProperty(
                celda.getValue().getCupoDisponible()));

        tcEstado.setCellValueFactory(celda -> new SimpleStringProperty(
                celda.getValue().getEstadoProyecto()));
    }

    private void cargarProyectos() {
        try {
            proyectos = proyectoService.obtenerProyectosActivos();
            tvProyectos.getItems().setAll(proyectos);

            if (proyectos.isEmpty()) {
                lblMensaje.setText("No existe ningún Proyecto Actualizable.");
            }

        } catch (SQLException excepcion) {
            mostrarError("Error al acceder a la base de datos",
                "No se pudo acceder a la base de datos. Por favor intente "
                + "nuevamente en unos minutos.");
            excepcion.printStackTrace();
        }
    }
    
    private boolean cumpleCriterioBusqueda(Proyecto proyecto,
            String criterioBusqueda) {
        if (rbOrganizacion.isSelected()) {
            return proyecto.getOrganizacion().getNombreComercial()
                    .toLowerCase().contains(criterioBusqueda);
        }

        if (rbResponsable.isSelected()) {
            return proyecto.getResponsable().getNombreCompleto()
                    .toLowerCase().contains(criterioBusqueda);
        }

        return proyecto.getNombreProyecto().toLowerCase()
                .contains(criterioBusqueda);
    }

    private Proyecto obtenerProyectoSeleccionado() {
        Proyecto proyecto = tvProyectos.getSelectionModel().getSelectedItem();

        if (proyecto == null) {
            mostrarAdvertencia("Proyecto no seleccionado",
                    "Seleccione un proyecto de la tabla.");
        }

        return proyecto;
    }

    private void abrirActualizarProyecto(Event event, Proyecto proyecto) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                    "/spp/vista/GUI-ActualizarProyecto.fxml"));

            Parent raiz = cargador.load();

            ActualizarProyectoController controlador = cargador.getController();
            controlador.inicializarDatos(proyecto);

            cambiarEscena(event, raiz);

        } catch (IOException excepcion) {
            mostrarError("Error al abrir la pantalla",
                    "No se pudo abrir la pantalla de actualización.");
            excepcion.printStackTrace();
        }
    }

    private void abrirActualizarOrganizacion(Event event, Proyecto proyecto) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                    "/spp/vista/GUI-ActualizarOV.fxml"));

            Parent raiz = cargador.load();

            ActualizarOVController controlador = cargador.getController();
            controlador.inicializarDatos(proyecto.getOrganizacion());

            cambiarEscena(event, raiz);

        } catch (IOException excepcion) {
            mostrarError("Error al abrir la pantalla",
                    "No se pudo abrir la pantalla de actualización.");
            excepcion.printStackTrace();
        }
    }

    private void abrirActualizarResponsable(Event event, Proyecto proyecto) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                    "/spp/vista/GUI-ActualizarResponsableTecnico.fxml"));

            Parent raiz = cargador.load();

            ActualizarResponsableTecnicoController controlador =
                    cargador.getController();
            controlador.inicializarDatos(proyecto.getResponsable());

            cambiarEscena(event, raiz);

        } catch (IOException excepcion) {
            mostrarError("Error al abrir la pantalla",
                    "No se pudo abrir la pantalla de actualización.");
            excepcion.printStackTrace();
        }
    }

    private void cambiarEscena(Event event, Parent raiz) {
        Scene escena = new Scene(raiz);
        Stage escenario = (Stage) ((Node) event.getSource()).getScene()
                .getWindow();

        escenario.setScene(escena);
        escenario.centerOnScreen();
        escenario.show();
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
