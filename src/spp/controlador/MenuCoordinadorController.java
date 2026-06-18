package spp.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import spp.utilidades.UtilidadesGUI;

public class MenuCoordinadorController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    void mostrarRegistrarProyecto(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-SolicitarDatosOV.fxml", event, "Registrar Proyecto");
    }

    @FXML
    void mostrarActualizarProyecto(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-SeccionesActualizacionProyecto.fxml", event, "Actualizar Proyecto");
    }

    @FXML
    void mostrarRegistrarPracticante(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-SolicitarDatosPracticante.fxml", event, "Registrar Practicante");
    }

    @FXML
    void mostrarMostrarPracticantes(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MostrarPracticantes.fxml", event, "Mostrar Practicantes");
    }

    @FXML
    void mostrarBuzon(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-BuzonMensajes.fxml", event, "Buzón de Mensajes");
    }

    @FXML
    void mostrarEnviarMensaje(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-RedactarMensaje.fxml", event, "Redactar Mensaje");
    }

    @FXML
    void cerrarSesion(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-Login.fxml", event, "Iniciar Sesión");
    }
}
