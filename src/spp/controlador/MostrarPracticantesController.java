package spp.controlador;

import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import spp.modelo.dto.Practicante;
import spp.service.PracticanteService;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la vista que muestra la lista de practicantes
 * registrados en el Sistema de Prácticas Profesionales.
 */
public class MostrarPracticantesController {

  @FXML
  private TableView<Practicante> tvPracticantes;

  @FXML
  private TableColumn<Practicante, String> tcMatricula;

  @FXML
  private TableColumn<Practicante, String> tcNombreCompleto;

  @FXML
  private TableColumn<Practicante, String> tcSexo;

  @FXML
  private TableColumn<Practicante, Number> tcCreditos;

  @FXML
  private TableColumn<Practicante, String> tcSeguro;

  @FXML
  private TableColumn<Practicante, String> tcEstado;

  @FXML
  private Label lblMensaje;

  private PracticanteService practicanteService;

  public MostrarPracticantesController() {
    practicanteService = new PracticanteService();
  }

  @FXML
  private void initialize() {
    configurarTabla();
    cargarPracticantes();
  }

  @FXML
  private void regresar(ActionEvent evento) {
    UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuCoordinador.fxml", evento,
        "Menú Coordinador");
  }

  private void configurarTabla() {
    tcMatricula.setCellValueFactory(celda -> new SimpleStringProperty(
        celda.getValue().getMatricula()));

    tcNombreCompleto.setCellValueFactory(celda -> new SimpleStringProperty(
        celda.getValue().getUsuario().getNombreCompleto()));

    tcSexo.setCellValueFactory(celda -> new SimpleStringProperty(
        celda.getValue().getSexo()));

    tcCreditos.setCellValueFactory(celda -> new SimpleIntegerProperty(
        celda.getValue().getCreditosAcumulados()));

    tcSeguro.setCellValueFactory(celda -> new SimpleStringProperty(
        celda.getValue().isSeguroMedicoVigente() ? "Vigente" : "No vigente"));

    tcEstado.setCellValueFactory(celda -> new SimpleStringProperty(
        celda.getValue().getUsuario().isEstadoActivo()
            ? "Activo" : "No activo"));
  }

  private void cargarPracticantes() {
    try {
      List<Practicante> practicantes =
          practicanteService.obtenerPracticantes();

      tvPracticantes.getItems().setAll(practicantes);

      if (practicantes.isEmpty()) {
        lblMensaje.setText("No hay practicantes registrados.");
      } else {
        lblMensaje.setText("Practicantes registrados: "
            + practicantes.size());
      }

    } catch (SQLException excepcion) {
      lblMensaje.setText("No se pudo cargar la lista de practicantes.");
      excepcion.printStackTrace();
    }
  }
}
