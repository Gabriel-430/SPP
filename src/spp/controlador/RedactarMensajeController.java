package spp.controlador;

import java.util.Optional;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import spp.modelo.dto.Mensaje;
import spp.service.MensajeService;
import spp.utilidades.Alerta;
import spp.utilidades.SesionUsuario;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 18/06/2026
 * Descripción: Controla la GUI de redacción de mensajes.
 */
public class RedactarMensajeController {

    @FXML
    private TextField txtDestinatario;
    @FXML
    private TextField txtAsunto;
    @FXML
    private TextArea txtContenido;

    private final MensajeService mensajeService = new MensajeService();

    private String obtenerRutaGUIMenu() {
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
    void clicEnviar(Event event) {
        String destinatario = txtDestinatario.getText();
        String asunto = txtAsunto.getText();
        String contenido = txtContenido.getText();
        int idRemitente = SesionUsuario.getUsuarioActual().getIdUsuario();

        if (asunto == null || asunto.trim().isEmpty() || contenido == null || contenido.trim().isEmpty()) {
            Alerta.mostrarAlertaAdvertencia("Campos faltantes", "Faltan campos por llenar, verifique que los campos estén completos e inténtelo de nuevo.");
            return;
        }

        if (!mensajeService.verificarDestinatarioExiste(destinatario)) {
            Alerta.mostrarAlertaAdvertencia("Destinatario Inválido", "No se encontró al destinatario ingresado, verifique que existe e inténtelo de nuevo.");
            return;
        }

        Mensaje nuevoMensaje = mensajeService.crearMensaje(asunto, contenido);
        mensajeService.vincularMensajeConDestinatarioYRemitente(nuevoMensaje, destinatario, idRemitente);

        boolean exito = mensajeService.enviarMensaje(nuevoMensaje);

        if (exito) {
            Alerta.mostrarAlertaInformacion("Mensaje enviado", "Mensaje enviado con éxito");
            UtilidadesGUI.mostrarGUI(obtenerRutaGUIMenu(), event, "Menú Principal");
        } else {
            Alert alertaErrorBD = new Alert(Alert.AlertType.ERROR);
            alertaErrorBD.setHeaderText(null);
            alertaErrorBD.setContentText("Ocurrió un error al enviar el mensaje. Verifique su conexión a internet e inténtelo de nuevo.");

            ButtonType btnReintentar = new ButtonType("Volver a intentar");
            ButtonType btnBorrador = new ButtonType("Guardar en borradores");

            alertaErrorBD.getButtonTypes().setAll(btnReintentar, btnBorrador);
            Optional<ButtonType> respuestaError = alertaErrorBD.showAndWait();

            if (respuestaError.isPresent() && respuestaError.get() == btnBorrador) {
                guardarComoBorrador(nuevoMensaje, event);
            }
        }
    }

    @FXML
    void clicCancelar(Event event) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Desea guardar este mensaje en borradores?");

        ButtonType btnGuardar = new ButtonType("Guardar en borradores");
        ButtonType btnCancelar = new ButtonType("Cancelar");

        confirmacion.getButtonTypes().setAll(btnGuardar, btnCancelar);

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == btnGuardar) {
            String destinatario = txtDestinatario.getText();
            String asunto = txtAsunto.getText();
            String contenido = txtContenido.getText();
            int idRemitente = SesionUsuario.getUsuarioActual().getIdUsuario();

            Mensaje borrador = mensajeService.crearMensaje(asunto, contenido);
            mensajeService.vincularMensajeConDestinatarioYRemitente(borrador, destinatario, idRemitente);

            guardarComoBorrador(borrador, event);
        } else {
            UtilidadesGUI.mostrarGUI(obtenerRutaGUIMenu(), event, "Menú Principal");
        }
    }

    private void guardarComoBorrador(Mensaje borrador, Event event) {
        boolean guardado = mensajeService.guardarBorrador(borrador);
        if (guardado) {
            Alerta.mostrarAlertaInformacion("Borrador guardado", "El mensaje ha sido guardado en borradores exitosamente.");
            UtilidadesGUI.mostrarGUI(obtenerRutaGUIMenu(), event, "Menú Principal");
        } else {
            Alerta.mostrarAlertaError("Error", "No se pudo guardar el borrador en el sistema.");
        }
    }
}
