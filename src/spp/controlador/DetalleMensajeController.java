package spp.controlador;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import spp.modelo.dto.Mensaje;
import spp.service.MensajeService;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 18/06/2026
 * Descripción: Controlador para visualizar el contenido completo de un mensaje.
 */
public class DetalleMensajeController {

    @FXML
    private Label lblAsunto;
    @FXML
    private Label lblFecha;
    @FXML
    private TextArea txtContenido;
    @FXML
    private Button btnRegresar;

    public static int idMensajeSeleccionado;
    private final MensajeService mensajeService = new MensajeService();

    @FXML
    public void initialize() {
        Platform.runLater(this::cargarDetallesMensaje);
    }

    private void cargarDetallesMensaje() {
        Mensaje mensaje = mensajeService.recuperarDetalles(idMensajeSeleccionado);

        if (mensaje == null) {
            mostrarErrorBaseDatos();
            return;
        }

        lblAsunto.setText(mensaje.getAsunto());
        lblFecha.setText(mensaje.getFecha().toString());
        txtContenido.setText(mensaje.getContenido());
    }

    @FXML
    void clicRegresar(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-BuzonMensajes.fxml", event, "Buzón de Mensajes");
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
                Parent root = FXMLLoader.load(getClass().getResource("/spp/vista/GUI-BuzonMensajes.fxml"));
                stage.setScene(new Scene(root));
                stage.setTitle("Buzón de Mensajes");
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
