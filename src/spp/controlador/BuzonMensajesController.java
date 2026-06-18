package spp.controlador;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import spp.modelo.dto.Mensaje;
import spp.service.MensajeService;
import spp.utilidades.SesionUsuario;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 18/06/2026
 * Descripción: Controlador para visualizar y gestionar la bandeja de entrada de mensajes.
 */
public class BuzonMensajesController {

    @FXML
    private TableView<Mensaje> tblMensajes;
    @FXML
    private TableColumn<Mensaje, Integer> colRemitente;
    @FXML
    private TableColumn<Mensaje, String> colAsunto;
    @FXML
    private TableColumn<Mensaje, String> colFecha;
    @FXML
    private Button btnRegresar;

    private final MensajeService mensajeService = new MensajeService();
    private ObservableList<Mensaje> listaMensajes;

    @FXML
    public void initialize() {
        colRemitente.setCellValueFactory(new PropertyValueFactory<>("idUsuarioRemitente"));
        colAsunto.setCellValueFactory(new PropertyValueFactory<>("asunto"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        Platform.runLater(this::cargarMensajes);
    }

    private void cargarMensajes() {
        int idUsuario = SesionUsuario.getUsuarioActual().getIdUsuario();
        List<Mensaje> mensajesRecuperados = mensajeService.recuperarMensajes(idUsuario);

        if (mensajesRecuperados == null) {
            mostrarErrorBaseDatos();
            return;
        }

        listaMensajes = FXCollections.observableArrayList(mensajesRecuperados);
        tblMensajes.setItems(listaMensajes);
    }

    private String obtenerRutaMenu() {
        if (SesionUsuario.getTipoUsuarioActual() == null) {
            return "/spp/vista/GUI-Login.fxml";
        }
        switch (SesionUsuario.getTipoUsuarioActual()) {
            case PRACTICANTE:
                return "/spp/vista/GUI-MenuPracticante.fxml";
            case PROFESOR:
                return "/spp/vista/GUI-MenuProfesor.fxml";
            case COORDINADOR:
                return "/spp/vista/GUI-MenuCoordinador.fxml";
            case ADMINISTRADOR:
                return "/spp/vista/GUI-MenuAdministrador.fxml";
            default:
                return "/spp/vista/GUI-Login.fxml";
        }
    }

    @FXML
    void clicVerDetalles(Event event) {
        Mensaje mensajeSeleccionado = tblMensajes.getSelectionModel().getSelectedItem();

        if (mensajeSeleccionado != null) {
            DetalleMensajeController.idMensajeSeleccionado = mensajeSeleccionado.getIdMensaje();
            UtilidadesGUI.mostrarGUI("/spp/vista/GUI-DetalleMensaje.fxml", event, "Detalle del Mensaje");
        }
    }

    @FXML
    void clicEliminarMensaje(Event event) {
        Mensaje mensajeSeleccionado = tblMensajes.getSelectionModel().getSelectedItem();

        if (mensajeSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Seguro que desea eliminar el mensaje?");

            ButtonType btnEliminar = new ButtonType("Eliminar mensaje");
            ButtonType btnCancelar = new ButtonType("Cancelar eliminación de mensaje");

            confirmacion.getButtonTypes().setAll(btnEliminar, btnCancelar);

            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == btnEliminar) {
                boolean exito = mensajeService.eliminarMensaje(mensajeSeleccionado.getIdMensaje());

                if (exito) {
                    listaMensajes.remove(mensajeSeleccionado);
                } else {
                    Alert alertaErrorEliminacion = new Alert(Alert.AlertType.ERROR);
                    alertaErrorEliminacion.setHeaderText(null);
                    alertaErrorEliminacion.setContentText("Error, no se pudo eliminar el mensaje. Intentelo de nuevo más tarde.");

                    ButtonType btnAceptar = new ButtonType("Aceptar");
                    alertaErrorEliminacion.getButtonTypes().setAll(btnAceptar);
                    alertaErrorEliminacion.showAndWait();
                }
            }
        }
    }

    @FXML
    void clicCerrarBuzon(Event event) {
        UtilidadesGUI.mostrarGUI(obtenerRutaMenu(), event, "Menú Principal");
    }

    private void mostrarErrorBaseDatos() {
        Alert alertaErrorBD = new Alert(Alert.AlertType.ERROR);
        alertaErrorBD.setHeaderText(null);
        alertaErrorBD.setContentText("No se pudo acceder a la base de datos. Por favor intente nuevamente en unos minutos.");

        ButtonType btnCerrar = new ButtonType("Cerrar");
        alertaErrorBD.getButtonTypes().setAll(btnCerrar);
        alertaErrorBD.showAndWait();

        if (btnRegresar.getScene() != null && btnRegresar.getScene().getWindow() != null) {
            try {
                Stage stage = (Stage) btnRegresar.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(obtenerRutaMenu()));
                stage.setScene(new Scene(root));
                stage.setTitle("Menú Principal");
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
