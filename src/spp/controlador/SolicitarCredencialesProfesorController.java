package spp.controlador;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import spp.modelo.dto.Curso;
import spp.service.CursoService;
import spp.service.ProfesorService;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la interfaz de solicitud de credenciales para
 * registrar profesores en el Sistema de Prácticas Profesionales.
 */
public class SolicitarCredencialesProfesorController {

  @FXML
  private TextField tfNombres;

  @FXML
  private TextField tfApellidos;

  @FXML
  private TextField tfCorreoElectronico;

  @FXML
  private TextField tfNumeroPersonal;

  @FXML
  private TextField tfTelefono;

  @FXML
  private ComboBox<String> cmbTurno;

  @FXML
  private PasswordField pfContrasenia;

  @FXML
  private ComboBox<Curso> cmbCurso;

  @FXML
  private Label lblMensaje;

  private CursoService cursoService;
  private ProfesorService profesorService;

  public SolicitarCredencialesProfesorController() {
    cursoService = new CursoService();
    profesorService = new ProfesorService();
  }

  @FXML
  private void initialize() {
      cargarTurnos();
      cargarCursosSinProfesor();
      limitarNumerosYLongitud(tfTelefono, 10);
      limitarNumerosYLongitud(tfNumeroPersonal, 10);
      limitarLongitud(tfCorreoElectronico, 40);
      limitarLongitud(tfNombres, 20);
      limitarLongitud(tfApellidos, 19);
  }

  @FXML
  private void continuarRegistro(Event evento) {
      limpiarMensaje();

      if (!confirmarRegistro()) {
      return;
      }

      try {
          profesorService.registrarProfesor(
              tfNombres.getText(),
              tfApellidos.getText(),
              tfCorreoElectronico.getText(),
              tfNumeroPersonal.getText(),
              tfTelefono.getText(),
              cmbTurno.getValue(),
              pfContrasenia.getText(),
              cmbCurso.getValue());

      mostrarInformacion("Registro exitoso",
          "El profesor fue exitosamente registrado en el sistema.");

      UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuAdministrador.fxml",
          evento, "Menú Administrador");

      } catch (IllegalArgumentException excepcion) {
          mostrarAdvertencia("Datos inválidos", excepcion.getMessage());

      } catch (SQLException excepcion) {
          mostrarError("Error al acceder a la base de datos",
          "No se pudo acceder a la base de datos. Por favor intente "
          + "nuevamente en unos minutos.");
          excepcion.printStackTrace();
      }
  }

  @FXML
  private void cancelarRegistro(Event evento) {
      Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
      alerta.setTitle("Cancelar registro");
      alerta.setHeaderText("¿Está seguro de que desea cancelar el registro "
      + "del profesor?");
      alerta.setContentText("Los datos ingresados no serán guardados.");

      Optional<ButtonType> respuesta = alerta.showAndWait();

      if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
          UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuAdministrador.fxml",
          evento, "Menú Administrador");
      }
  }

  private void cargarTurnos() {
      cmbTurno.getItems().setAll("Matutino", "Vespertino");
  }

  private void cargarCursosSinProfesor() {
      try {
          List<Curso> cursos = cursoService.obtenerCursosSinProfesor();
          cmbCurso.getItems().setAll(cursos);

          if (cursos.isEmpty()) {
              lblMensaje.setText("No hay cursos disponibles para asignar.");
          }

      } catch (SQLException excepcion) {
          mostrarError("Error al acceder a la base de datos",
          "No se pudo acceder a la base de datos. Por favor intente "
          + "nuevamente en unos minutos.");
          excepcion.printStackTrace();
      }
  }

  private boolean confirmarRegistro() {
      Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
     alerta.setTitle("Confirmación de registro");
      alerta.setHeaderText("¿Seguro que desea ingresar al profesor?");
      alerta.setContentText("Seleccione Aceptar para registrar al profesor.");

      Optional<ButtonType> respuesta = alerta.showAndWait();

      return respuesta.isPresent() && respuesta.get() == ButtonType.OK;
  }

  private void limpiarMensaje() {
      lblMensaje.setText("");
  }

  private void mostrarInformacion(String titulo, String mensaje) {
      Alert alerta = new Alert(Alert.AlertType.INFORMATION);
      alerta.setTitle(titulo);
      alerta.setHeaderText(null);
      alerta.setContentText(mensaje);
      alerta.showAndWait();
  }

  private void mostrarAdvertencia(String titulo, String mensaje) {
      Alert alerta = new Alert(Alert.AlertType.WARNING);
      alerta.setTitle(titulo);
      alerta.setHeaderText(null);
      alerta.setContentText(mensaje);
      alerta.showAndWait();
   }

  private void mostrarError(String titulo, String mensaje) {
      Alert alerta = new Alert(Alert.AlertType.ERROR);
      alerta.setTitle(titulo);
      alerta.setHeaderText(null);
      alerta.setContentText(mensaje);
      alerta.showAndWait();
   }
  
  private void limitarLongitud(TextField campo, int longitudMaxima) {
      campo.textProperty().addListener((observable, valorAnterior, valorNuevo) -> {
          if (valorNuevo != null && valorNuevo.length() > longitudMaxima) {
              campo.setText(valorAnterior);
          }
       });
  }

    private void limitarNumerosYLongitud(TextField campo, int longitudMaxima) {
        campo.textProperty().addListener((observable, valorAnterior, valorNuevo) -> {
            if (valorNuevo == null) {
                return;
            }

            if (!valorNuevo.matches("\\d*") || valorNuevo.length() > longitudMaxima) {
                campo.setText(valorAnterior);
            }
        });
    }
    
}
