package spp.controlador;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 16/06/2026
 * Descripción: Controla la vista del menú principal del practicante.
 */
import javafx.event.Event;
import javafx.fxml.FXML;
import spp.utilidades.UtilidadesGUI;

public class MenuPracticanteController {

    @FXML
    void mostrarProyectos(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MiProyectoAsignado.fxml", event, "Proyectos");
    }

    @FXML
    void mostrarMenuEntregables(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuEntregables.fxml", event, "Subir Entregables");
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
