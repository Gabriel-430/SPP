package spp.controlador;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la consulta y selección de reportes finales pendientes
 * de evaluación.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import spp.modelo.dto.Reporte;
import spp.utilidades.UtilidadesGUI;

public class ListaReportesFinalesController implements Initializable {

    @FXML
    private TableView<Reporte> tblReportes;

    @FXML
    private TableColumn<Reporte, String> colMatricula;

    @FXML
    private TableColumn<Reporte, String> colFecha;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarReportes();
    }

    private void cargarReportes() {
        try {
        } catch (Exception e) {
            mostrarErrorBaseDatos();
        }
    }

    @FXML
    void clicSeleccionarReporte(Event event) {
        Reporte seleccionado = tblReportes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            VisualizarReporteFinalController.reporteEvaluar = seleccionado;
            UtilidadesGUI.mostrarGUI(
                    "/spp/vista/GUI-VisualizarReporteFinal.fxml",
                    event,
                    "Visualizar Reporte Final"
            );
        }
    }

    @FXML
    void clicCancelarEvaluacion(Event event) {
        UtilidadesGUI.mostrarGUI(
                "/spp/vista/GUI-MenuProfesor.fxml",
                event,
                "Menú Profesor"
        );
    }

    private void mostrarErrorBaseDatos() {
        Alert alertaErrorBD = new Alert(Alert.AlertType.ERROR);
        alertaErrorBD.setHeaderText(null);
        alertaErrorBD.setContentText(
                "No se pudo acceder a la base de datos. Por favor intente nuevamente en unos minutos."
        );

        ButtonType btnCerrar = new ButtonType("Cerrar");
        alertaErrorBD.getButtonTypes().setAll(btnCerrar);
        alertaErrorBD.showAndWait();
    }
}
