package spp.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import spp.modelo.dto.Reporte;
import spp.service.DocumentoService;
import spp.service.ReporteService;
import spp.utilidades.Alerta;
import spp.utilidades.SesionUsuario;
import spp.utilidades.UtilidadesGUI;

public class EvaluarReporteMensualController implements Initializable {

    @FXML
    private ComboBox<Reporte> cmbReportes;
    @FXML
    private TextArea txtVisor;
    @FXML
    private TextField txtCalificacion;
    @FXML
    private Button btnDescargar;

    public static String observacionTemporal = "";
    private final ReporteService reporteService = new ReporteService();
    private final DocumentoService documentoService = new DocumentoService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnDescargar.setDisable(true);
        configurarComboBox();
        cargarReportes();
    }

    private void configurarComboBox() {
        cmbReportes.setConverter(new StringConverter<Reporte>() {
            @Override
            public String toString(Reporte reporte) {
                return reporte != null ? reporte.getNombreDocumento() : "";
            }

            @Override
            public Reporte fromString(String texto) {
                return null;
            }
        });
    }

    private void cargarReportes() {
        try {
            int idUsuario = SesionUsuario.getUsuarioActual().getIdUsuario();
            List<Reporte> lista = reporteService.recuperarReportesPendientes(idUsuario, "Reporte Mensual");

            cmbReportes.setItems(FXCollections.observableArrayList(lista));

            if (lista.isEmpty()) {
                Alerta.mostrarAlertaInformacion("Bandeja vacia", "No hay reportes mensuales pendientes por evaluar.");
            } else {
                cmbReportes.getSelectionModel().selectFirst();
                seleccionarReporte(null);
            }
        } catch (SQLException e) {
            Alerta.mostrarAlertaError("Error", "No se pudo acceder a la base de datos.");
        }
    }

    @FXML
    void seleccionarReporte(Event event) {
        Reporte reporte = cmbReportes.getSelectionModel().getSelectedItem();

        if (reporte == null) {
            txtVisor.clear();
            btnDescargar.setDisable(true);
            return;
        }

        try {
            File archivoFisico = documentoService.obtenerArchivoPorRuta(reporte.getRutaArchivo());

            if (archivoFisico != null && archivoFisico.exists()) {
                txtVisor.setText(documentoService.extraerDatosArchivo(archivoFisico));
                btnDescargar.setDisable(false);
            } else {
                txtVisor.setText("No se encontro el archivo fisico del reporte.");
                btnDescargar.setDisable(true);
            }
        } catch (Exception e) {
            txtVisor.setText("No se pudo cargar la vista previa del documento.");
            btnDescargar.setDisable(true);
        }
    }

    @FXML
    void clicDescargar(Event event) {
        Reporte reporte = cmbReportes.getSelectionModel().getSelectedItem();

        if (reporte == null) {
            Alerta.mostrarAlertaAdvertencia("Reporte no seleccionado", "Seleccione un reporte.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(reporte.getNombreDocumento());
        File destino = fileChooser.showSaveDialog(cmbReportes.getScene().getWindow());

        if (destino == null) {
            return;
        }

        try {
            documentoService.descargarArchivo(reporte, destino);
            Alerta.mostrarAlertaInformacion("Exito", "Archivo descargado.");
        } catch (IOException e) {
            Alerta.mostrarAlertaError("Error", "No se pudo descargar.");
        }
    }

    @FXML
    void clicAgregarObservacion(Event event) {
        AgregarObservacionController.reporteOrigen = AgregarObservacionController.REPORTE_MENSUAL;
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-AgregarObservacion.fxml", event, "Agregar Observacion");
    }

    @FXML
    void calificar(Event event) throws SQLException {
        Reporte reporteSeleccionado = cmbReportes.getSelectionModel().getSelectedItem();

        if (reporteSeleccionado == null) {
            Alerta.mostrarAlertaAdvertencia("Falta seleccion", "Seleccione un reporte.");
            return;
        }

        try {
            int calificacion = convertirCalificacion();

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setContentText("Confirmar calificacion?");
            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                if (reporteService.evaluarReporte(reporteSeleccionado, calificacion, observacionTemporal)) {
                    Alerta.mostrarAlertaInformacion("Exito", "Reporte calificado.");
                    limpiarFormulario();
                    cargarReportes();
                } else {
                    Alerta.mostrarAlertaError("Error", "Error en BD.");
                }
            }
        } catch (IllegalArgumentException e) {
            Alerta.mostrarAlertaAdvertencia("Dato invalido", e.getMessage());
        }
    }

    @FXML
    void cerrarGUI(Event event) {
        observacionTemporal = "";
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuProfesor.fxml", event, "Menu Profesor");
    }

    private int convertirCalificacion() {
        String textoCalificacion = txtCalificacion.getText();

        if (textoCalificacion == null || textoCalificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("Asigne una calificacion.");
        }

        try {
            int calificacion = Integer.parseInt(textoCalificacion.trim());

            if (calificacion < 0 || calificacion > 100) {
                throw new IllegalArgumentException("La calificacion debe ser entre 0 y 100.");
            }

            return calificacion;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La calificacion debe ser numerica.");
        }
    }

    private void limpiarFormulario() {
        observacionTemporal = "";
        txtCalificacion.clear();
        txtVisor.clear();
        btnDescargar.setDisable(true);
        cmbReportes.getItems().clear();
    }
}
