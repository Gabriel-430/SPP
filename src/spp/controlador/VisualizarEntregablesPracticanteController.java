package spp.controlador;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import spp.modelo.dto.Documento;
import spp.modelo.dto.Practicante;
import spp.service.DocumentoService;
import spp.utilidades.Alerta;
import spp.utilidades.UtilidadesGUI;

public class VisualizarEntregablesPracticanteController {

    @FXML
    private ComboBox<Documento> cmbDocumentos;
    @FXML
    private ImageView imgVisor;
    @FXML
    private Button btnDescargar;

    public static Practicante practicanteSeleccionado;
    private final DocumentoService documentoService = new DocumentoService();

    @FXML
    public void initialize() {
        btnDescargar.setDisable(true);
        cargarDocumentos();
    }

    private void cargarDocumentos() {
        try {
            List<Documento> lista = documentoService.recuperarDocumentosPorPracticaProfesional(practicanteSeleccionado.getIdPracticante());
            ObservableList<Documento> documentosObs = FXCollections.observableArrayList(lista);
            cmbDocumentos.setItems(documentosObs);
            cmbDocumentos.setCellFactory(param -> new javafx.scene.control.ListCell<Documento>() {
                @Override
                protected void updateItem(Documento item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getNombreDocumento());
                }
            });
            cmbDocumentos.setButtonCell(cmbDocumentos.getCellFactory().call(null));
        } catch (SQLException e) {
            Alerta.mostrarAlertaError("Error", "No se pudo acceder a la base de datos.");
        }
    }

    @FXML
    void seleccionarDocumento(Event event) {
        Documento doc = cmbDocumentos.getSelectionModel().getSelectedItem();
        if (doc != null) {
            try {
                File archivoFisico = documentoService.obtenerArchivoPorRuta(doc.getRutaArchivo());
                if (archivoFisico.exists()) {
                    Image image = new Image(archivoFisico.toURI().toString());
                    imgVisor.setImage(image);
                    btnDescargar.setDisable(false);
                } else {
                    Alerta.mostrarAlertaError("Error", "El archivo físico no existe.");
                }
            } catch (Exception e) {
                Alerta.mostrarAlertaError("Error", "No se pudo cargar la imagen.");
                btnDescargar.setDisable(true);
            }
        }
    }

    @FXML
    void clicDescargar(Event event) {
        Documento doc = cmbDocumentos.getSelectionModel().getSelectedItem();
        if (doc != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName(doc.getNombreDocumento());
            File destino = fileChooser.showSaveDialog(cmbDocumentos.getScene().getWindow());
            if (destino != null) {
                try {
                    documentoService.descargarArchivo(doc, destino);
                    Alerta.mostrarAlertaInformacion("Éxito", "Archivo descargado exitosamente.");
                } catch (IOException e) {
                    Alerta.mostrarAlertaError("Error de descarga", "No se pudo descargar.");
                }
            }
        }
    }

    @FXML
    void clicRegresar(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-ListaPracticantesConEntregables.fxml", event, "Entregables de Practicantes");
    }
}
