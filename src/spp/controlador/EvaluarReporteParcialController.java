package spp.controlador;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 18/06/2026
 * Descripción: Controla la vista para evaluar reportes parciales entregados
 * por practicantes.
 */
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import spp.modelo.dto.Reporte;
import spp.service.DocumentoService;
import spp.service.ReporteService;
import spp.utilidades.Alerta;
import spp.utilidades.SesionUsuario;
import spp.utilidades.UtilidadesGUI;

public class EvaluarReporteParcialController {

  public static String observacionTemporal = "";

  @FXML
  private ComboBox<Reporte> cmbReportes;

  @FXML
  private TextField tfCalificacion;

  @FXML
  private Button btnAbrir;

  @FXML
  private Button btnDescargar;

  private ReporteService reporteService;
  private DocumentoService documentoService;

  public EvaluarReporteParcialController() {
    reporteService = new ReporteService();
    documentoService = new DocumentoService();
  }

  @FXML
  private void initialize() {
    btnAbrir.setDisable(true);
    btnDescargar.setDisable(true);
    configurarComboBox();
    cargarReportesPendientes();
    limitarCalificacion();
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

  @FXML
  void seleccionarReporte(Event event) {
    Reporte reporte = cmbReportes.getSelectionModel().getSelectedItem();

    if (reporte == null) {
      btnAbrir.setDisable(true);
      btnDescargar.setDisable(true);
      return;
    }

    try {
      File archivoFisico = documentoService.obtenerArchivoPorRuta(
          reporte.getRutaArchivo());

      boolean existeArchivo = archivoFisico != null && archivoFisico.exists();

      btnAbrir.setDisable(!existeArchivo);
      btnDescargar.setDisable(!existeArchivo);

      if (!existeArchivo) {
        Alerta.mostrarAlertaAdvertencia("Archivo no encontrado",
            "El reporte existe en el sistema, pero no se encontró el archivo físico.");
      }

    } catch (Exception excepcion) {
      btnAbrir.setDisable(true);
      btnDescargar.setDisable(true);
      Alerta.mostrarAlertaError("Error", "No se pudo cargar el archivo.");
    }
  }

  @FXML
  void clicAbrirArchivo(Event event) {
    Reporte reporte = cmbReportes.getSelectionModel().getSelectedItem();

    if (reporte == null) {
      Alerta.mostrarAlertaAdvertencia("Reporte no seleccionado",
          "Seleccione un reporte.");
      return;
    }

    try {
      File archivoFisico = documentoService.obtenerArchivoPorRuta(
          reporte.getRutaArchivo());

      if (archivoFisico == null || !archivoFisico.exists()) {
        Alerta.mostrarAlertaAdvertencia("Archivo no encontrado",
            "No se encontró el archivo del reporte.");
        return;
      }

      Desktop.getDesktop().open(archivoFisico);

    } catch (IOException excepcion) {
      Alerta.mostrarAlertaError("Error",
          "No se pudo abrir el archivo.");
    }
  }

  @FXML
  void clicDescargarArchivo(Event event) {
    Reporte reporte = cmbReportes.getSelectionModel().getSelectedItem();

    if (reporte == null) {
      Alerta.mostrarAlertaAdvertencia("Reporte no seleccionado",
          "Seleccione un reporte.");
      return;
    }

    try {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setInitialFileName(reporte.getNombreDocumento());
      File destino = fileChooser.showSaveDialog(cmbReportes.getScene().getWindow());

      if (destino == null) {
        return;
      }

      documentoService.descargarArchivo(reporte, destino);
      Alerta.mostrarAlertaInformacion("Descarga completada",
          "El archivo fue descargado correctamente.");

    } catch (Exception excepcion) {
      Alerta.mostrarAlertaError("Error",
          "No se pudo descargar el archivo.");
    }
  }

  @FXML
  void clicGuardarCalificacion(Event event) throws SQLException {
    Reporte reporte = cmbReportes.getSelectionModel().getSelectedItem();

    if (reporte == null) {
      Alerta.mostrarAlertaAdvertencia("Reporte no seleccionado",
          "Seleccione un reporte.");
      return;
    }

    try {
      int calificacion = convertirCalificacion();

      reporteService.evaluarReporteParcial(reporte, calificacion,
          observacionTemporal);

      Alerta.mostrarAlertaInformacion("Evaluación exitosa",
          "La calificación fue guardada correctamente.");

      observacionTemporal = "";

      UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuProfesor.fxml",
          event, "Menú Profesor");

    } catch (IllegalArgumentException excepcion) {
      Alerta.mostrarAlertaAdvertencia("Datos inválidos",
          excepcion.getMessage());
    }
  }

  @FXML
  void clicAgregarObservacion(Event event) {
    AgregarObservacionController.reporteOrigen =
        AgregarObservacionController.REPORTE_PARCIAL;
    UtilidadesGUI.mostrarGUI("/spp/vista/GUI-AgregarObservacion.fxml",
        event, "Agregar Observación");
  }

  @FXML
  void clicCancelarRegresar(Event event) {
    UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuProfesor.fxml",
        event, "Menú Profesor");
  }

  private void cargarReportesPendientes() {
    try {
      int idUsuario = SesionUsuario.getUsuarioActual().getIdUsuario();

      List<Reporte> reportes = reporteService.recuperarReportesPendientes(
          idUsuario, "Reporte Parcial");

      cmbReportes.getItems().setAll(reportes);

      if (!reportes.isEmpty()) {
        cmbReportes.getSelectionModel().selectFirst();
        seleccionarReporte(null);
      }

    } catch (SQLException excepcion) {
      Alerta.mostrarAlertaError("Error",
          "No se pudo acceder a la base de datos.");
    }
  }

  private int convertirCalificacion() {
    String textoCalificacion = tfCalificacion.getText();

    if (textoCalificacion == null || textoCalificacion.trim().isEmpty()) {
      throw new IllegalArgumentException("Ingrese una calificación.");
    }

    try {
      int calificacion = Integer.parseInt(textoCalificacion.trim());

      if (calificacion < 0 || calificacion > 100) {
        throw new IllegalArgumentException(
            "La calificación debe estar entre 0 y 100.");
      }

      return calificacion;

    } catch (NumberFormatException excepcion) {
      throw new IllegalArgumentException(
          "La calificación debe ser numérica.");
    }
  }
  
  private void limitarCalificacion() {
      tfCalificacion.textProperty().addListener((observable, valorAnterior,
      valorNuevo) -> {
      if (valorNuevo == null || valorNuevo.isEmpty()) {
          return;
      }

      if (!valorNuevo.matches("\\d*")) {
          tfCalificacion.setText(valorAnterior);
          return;
      }

      int calificacion = Integer.parseInt(valorNuevo);

      if (calificacion > 100) {
          tfCalificacion.setText(valorAnterior);
      }
    });
  }
}
