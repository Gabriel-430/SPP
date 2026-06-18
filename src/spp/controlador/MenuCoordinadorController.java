package spp.controlador;

import javafx.event.Event;
import javafx.fxml.FXML;
import spp.utilidades.UtilidadesGUI;

public class MenuCoordinadorController {

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
    void mostrarPracticantes(Event event) {
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
