package spp.controlador;

import java.io.File;
import java.time.LocalDate;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import spp.modelo.dto.Documento;
import spp.service.AutoevaluacionService;
import spp.utilidades.Alerta;
import spp.utilidades.SesionUsuario;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 16/06/2026
 * Descripción: Controla la vista donde el practicante selecciona y sube
 * su archivo de autoevaluación al sistema.
 */
public class SubirAutoevaluacionController {

    @FXML
    private TextField txtNombreArchivo;
    @FXML
    private Label lblTamano;
    @FXML
    private Label lblFecha;
    @FXML
    private Button btnSeleccionarArchivo;
    @FXML
    private Button btnSubirDocumento;

    private File archivo;
    private final AutoevaluacionService autoevaluacionService = new AutoevaluacionService();

    @FXML
    public void initialize() {
        btnSubirDocumento.setDisable(true);
        btnSeleccionarArchivo.setText("Seleccionar archivo");
    }

    @FXML
    void clicSeleccionarArchivo(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        Window ventanaActual = txtNombreArchivo.getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(ventanaActual);

        if (archivoSeleccionado != null) {
            this.archivo = archivoSeleccionado;
            txtNombreArchivo.setText(archivo.getName());
            lblTamano.setText("Tamaño: " + (archivo.length() / 1024) + " KB");
            lblFecha.setText("Fecha: " + LocalDate.now().toString());

            btnSubirDocumento.setDisable(false);
            btnSeleccionarArchivo.setText("Cambiar archivo");
        }
    }

    @FXML
    void clicGuardar(Event event) {
        if (archivo == null) {
            Alerta.mostrarAlertaAdvertencia("Archivo faltante", "Debe seleccionar un archivo PDF.");
            return;
        }

        String matricula = SesionUsuario.getUsuarioActual().getNombreUsuario();
        String rutaRelativa = "archivos_subidos/" + matricula + "_" + archivo.getName();

        if (autoevaluacionService.archivoYaExiste(rutaRelativa, matricula)) {
            Alerta.mostrarAlertaAdvertencia("Archivo existente", "El archivo ya ha sido subido anteriormente.");
            return;
        }

        Documento documento = new Documento();
        documento.setNombreDocumento(archivo.getName());
        documento.setRutaArchivo(rutaRelativa);

        boolean exito = autoevaluacionService.subirAutoevaluacion(documento, matricula, this.archivo);

        if (exito) {
            Alerta.mostrarAlertaInformacion("Éxito", "La autoevaluación ha sido guardada exitosamente.");
            UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuEntregables.fxml", event, "Menu Entregables");
        } else {
            Alerta.mostrarAlertaError("Error", "No se pudo guardar la autoevaluación. Verifique que el alumno tenga una Práctica Profesional asignada en el sistema.");
        }
    }

    @FXML
    void cerrarGUI(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuEntregables.fxml", event, "Menu Entregables");
    }
}
