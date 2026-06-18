package spp.controlador;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 18/06/2026
 * Descripción: Controla la captura temporal de observaciones para la
 * evaluación de reportes parciales.
 */
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import spp.utilidades.UtilidadesGUI;

public class AgregarObservacionController {

  public static final String REPORTE_MENSUAL = "Reporte Mensual";
  public static final String REPORTE_PARCIAL = "Reporte Parcial";
  public static final String REPORTE_FINAL = "Reporte Final";

  public static String reporteOrigen = REPORTE_PARCIAL;

  @FXML
  private TextArea taObservacion;

  @FXML
  private void initialize() {
    taObservacion.setText(obtenerObservacionTemporal());
    limitarObservacion();
  }

  @FXML
  void clicGuardarObservacion(Event event) {
    guardarObservacionTemporal(taObservacion.getText());

    regresarAEvaluacion(event);
  }

  @FXML
  void cerrarGUI(Event event) {
    regresarAEvaluacion(event);
  }

  private String obtenerObservacionTemporal() {
    if (REPORTE_MENSUAL.equals(reporteOrigen)) {
      return EvaluarReporteMensualController.observacionTemporal;
    }

    if (REPORTE_FINAL.equals(reporteOrigen)) {
      return EvaluarReporteFinalController.observacionTemporal;
    }

    return EvaluarReporteParcialController.observacionTemporal;
  }

  private void guardarObservacionTemporal(String observacion) {
    if (REPORTE_MENSUAL.equals(reporteOrigen)) {
      EvaluarReporteMensualController.observacionTemporal = observacion;
      return;
    }

    if (REPORTE_FINAL.equals(reporteOrigen)) {
      EvaluarReporteFinalController.observacionTemporal = observacion;
      return;
    }

    EvaluarReporteParcialController.observacionTemporal = observacion;
  }

  private void regresarAEvaluacion(Event event) {
    if (REPORTE_MENSUAL.equals(reporteOrigen)) {
      UtilidadesGUI.mostrarGUI("/spp/vista/GUI-EvaluarReporteMensual.fxml",
          event, "Evaluar Reporte Mensual");
      return;
    }

    if (REPORTE_FINAL.equals(reporteOrigen)) {
      UtilidadesGUI.mostrarGUI("/spp/vista/GUI-EvaluarReporteFinal.fxml",
          event, "Evaluar Reporte Final");
      return;
    }

    UtilidadesGUI.mostrarGUI("/spp/vista/GUI-EvaluarReporteParcial.fxml",
        event, "Evaluar Reporte Parcial");
  }
  
  private void limitarObservacion() {
      taObservacion.textProperty().addListener((observable, valorAnterior,
      valorNuevo) -> {
      if (valorNuevo != null && valorNuevo.length() > 100) {
          taObservacion.setText(valorAnterior);
      }
      });
  }
}
