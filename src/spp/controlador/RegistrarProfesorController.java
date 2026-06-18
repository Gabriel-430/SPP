package spp.controlador;

import javafx.event.Event;
import javafx.fxml.FXML;
import spp.utilidades.UtilidadesGUI;

/**
 * @author Gabriel Hernández Martínez
 */
public class RegistrarProfesorController {

    @FXML
    void registrar(Event event) {
    }

    @FXML
    void cerrarGUI(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuCoordinador.fxml", event, "Menú Coordinador");
    }
}
