package spp.controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import spp.modelo.dto.Profesor;
import spp.service.ProfesorService;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la vista que muestra la lista de profesores
 * registrados en el Sistema de Prácticas Profesionales.
 */
public class MostrarProfesoresController {

  @FXML
  private TableView<Profesor> tvProfesores;

  @FXML
  private TableColumn<Profesor, Number> tcNumeroPersonal;

  @FXML
  private TableColumn<Profesor, String> tcNombreCompleto;

  @FXML
  private TableColumn<Profesor, String> tcTurno;

  @FXML
  private TableColumn<Profesor, String> tcCorreoElectronico;

  @FXML
  private TableColumn<Profesor, String> tcEstado;

  @FXML
  private Label lblMensaje;

  private ProfesorService profesorService;

  public MostrarProfesoresController() {
    profesorService = new ProfesorService();
  }

  @FXML
  private void initialize() {
    configurarTabla();
    cargarProfesores();
  }

  @FXML
  private void regresar(ActionEvent evento) {
    try {
      Parent raiz = FXMLLoader.load(getClass().getResource(
          "/spp/vista/GUI-MenuAdministrador.fxml"));

      Scene sceneMenu = new Scene(raiz);
      Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();

      stage.setScene(sceneMenu);
      stage.centerOnScreen();
      stage.show();

    } catch (IOException excepcion) {
      lblMensaje.setText("No se pudo regresar al menú.");
      excepcion.printStackTrace();
    }
  }

  private void configurarTabla() {
    tcNumeroPersonal.setCellValueFactory(celda -> new SimpleIntegerProperty(
        celda.getValue().getNumeroPersonal()));

    tcNombreCompleto.setCellValueFactory(celda -> new SimpleStringProperty(
        celda.getValue().getUsuario().getNombreCompleto()));

    tcTurno.setCellValueFactory(celda -> new SimpleStringProperty(
        celda.getValue().getTurno()));

    tcCorreoElectronico.setCellValueFactory(celda -> new SimpleStringProperty(
        celda.getValue().getUsuario().getCorreoElectronico()));

    tcEstado.setCellValueFactory(celda -> new SimpleStringProperty(
        celda.getValue().getUsuario().isEstadoActivo()
            ? "Activo" : "No activo"));
  }

  private void cargarProfesores() {
    try {
      List<Profesor> profesores = profesorService.obtenerProfesores();
      tvProfesores.getItems().setAll(profesores);

      if (profesores.isEmpty()) {
        lblMensaje.setText("No hay profesores registrados.");
      } else {
        lblMensaje.setText("Profesores registrados: " + profesores.size());
      }

    } catch (SQLException excepcion) {
      lblMensaje.setText("No se pudo cargar la lista de profesores.");
      excepcion.printStackTrace();
    }
  }
}
