package spp.controlador;

import javafx.event.Event;
import javafx.fxml.FXML;
import spp.utilidades.UtilidadesGUI;

/**
 * @author Gabriel Hernández Martínez
 */
public class EntregablesController {

    @FXML
    void cerrarGUI(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuPracticante.fxml", event, "Menú Practicante");
    }
}
