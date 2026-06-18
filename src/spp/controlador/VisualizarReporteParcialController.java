package spp.controlador;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 18/06/2026
 * Descripción: Controla la visualización y evaluación de un reporte parcial.
 */
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import spp.modelo.dto.Reporte;
import spp.service.DocumentoService;
import spp.service.ReporteService;
import spp.utilidades.Alerta;
import spp.utilidades.UtilidadesGUI;

public class VisualizarReporteParcialController implements Initializable {

    @FXML
    private TextArea txtVisor;
    @FXML
    private TextField txtCalificacion;

    public static Reporte reporteEvaluar;
    public static String observacionTemporal = "";
    public static String calificacionTemporal = "";

    private final ReporteService reporteService = new ReporteService();
    private final DocumentoService documentoService = new DocumentoService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!calificacionTemporal.isEmpty()) {
            txtCalificacion.setText(calificacionTemporal);
        }
        cargarDocumento();
    }

    private void cargarDocumento() {
        if (reporteEvaluar != null && txtVisor != null) {
            try {
                File archivoFisico = documentoService.obtenerArchivoPorRuta(reporteEvaluar.getRutaArchivo());
                String contenidoPDF = documentoService.extraerDatosArchivo(archivoFisico);
                txtVisor.setText(contenidoPDF);
            } catch (IllegalArgumentException e) {
                txtVisor.setText("No se pudo cargar la vista previa del documento.");
            }
        }
    }

    @FXML
    void clicDescargar(Event event) {
        if (reporteEvaluar != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName(reporteEvaluar.getNombreDocumento());
            File destino = fileChooser.showSaveDialog(txtCalificacion.getScene().getWindow());

            if (destino != null) {
                try {
                    documentoService.descargarArchivo(reporteEvaluar, destino);
                    Alerta.mostrarAlertaInformacion("Éxito", "Archivo descargado exitosamente.");
                } catch (IOException e) {
                    Alerta.mostrarAlertaError("Error de descarga", "No se pudo descargar el archivo.");
                }
            }
        }
    }

    @FXML
    void clicAgregarObservacion(Event event) {
        calificacionTemporal = txtCalificacion.getText();
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-AgregarObservacion.fxml", event, "Agregar Observación");
    }

    @FXML
    void calificar(Event event) throws SQLException {
        String calificacionText = txtCalificacion.getText();

        if (calificacionText == null || calificacionText.trim().isEmpty()) {
            Alerta.mostrarAlertaAdvertencia("Faltan datos", "Por favor asigne una calificación.");
            return;
        }

        try {
            int calificacion = Integer.parseInt(calificacionText);
            if (calificacion < 0 || calificacion > 100) {
                Alerta.mostrarAlertaAdvertencia("Dato inválido", "La calificación debe estar entre 0 y 100.");
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Desea guardar la calificación?");
            ButtonType btnConfirmar = new ButtonType("Confirmar calificación");
            ButtonType btnSeguir = new ButtonType("Seguir editando");
            confirmacion.getButtonTypes().setAll(btnConfirmar, btnSeguir);

            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == btnConfirmar) {
                boolean exito = reporteService.evaluarReporte(reporteEvaluar, calificacion, observacionTemporal);
                if (exito) {
                    Alerta.mostrarAlertaInformacion("Éxito", "Reporte calificado exitosamente.");
                    observacionTemporal = "";
                    calificacionTemporal = "";
                    UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuProfesor.fxml", event, "Menú Profesor");
                } else {
                    Alerta.mostrarAlertaError("Error", "No se pudo acceder a la base de datos.");
                }
            }
        } catch (NumberFormatException e) {
            Alerta.mostrarAlertaAdvertencia("Dato inválido", "La calificación debe ser numérica.");
        }
    }

    @FXML
    void cerrarGUI(Event event) {
        observacionTemporal = "";
        calificacionTemporal = "";
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-EvaluarReporteParcial.fxml", event, "Evaluar Reporte Parcial");
    }
}
