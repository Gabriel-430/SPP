package spp.controlador;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 16/06/2026
 * Descripción: Controla la ventana  para redactar observaciones
 * a un reporte final.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import spp.utilidades.UtilidadesGUI;

public class AgregarObservacionReporteFinalController implements Initializable {

    @FXML
    private TextArea txtObservacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (VisualizarReporteFinalController.observacionTemporal != null) {
            txtObservacion.setText(VisualizarReporteFinalController.observacionTemporal);
        }
    }

    @FXML
    void clicGuardarObservacion(Event event) {
        VisualizarReporteFinalController.observacionTemporal = txtObservacion.getText();
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-VisualizarReporteFinal.fxml", event, "Visualizar Reporte Final");
    }

    @FXML
    void cerrarGUI(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-VisualizarReporteFinal.fxml", event, "Visualizar Reporte Final");
    }
}
