package spp.controlador;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 16/06/2026
 * Descripción: Controla la vista del menú de entregables, el cual
 * es accesible a través del menú principal del estudiante al pulsar
 * el botón "Subir entregables".
 */
import javafx.event.Event;
import javafx.fxml.FXML;
import spp.utilidades.UtilidadesGUI;

public class MenuEntregablesController {

    @FXML
    void mostrarGUISubirReporteMensual(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-SubirReporteMensual.fxml", event, "Añadir Reporte Final");
    }

    @FXML
    void mostrarGUISubirAutoevaluacion(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-SubirAutoevaluacion.fxml", event, "Añadir Autoevaluación");
    }

    @FXML
    void volverMenuPrincipal(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuPracticante.fxml", event, "Menú Principal - Practicante");
    }
}
