package spp.controlador;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import spp.modelo.dto.TipoUsuario;
import spp.modelo.dto.Usuario;
import spp.service.LoginService;
import spp.utilidades.SesionUsuario;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 16/06/2026
 * Descripción: Controla la vista de inicio de sesión del Sistema de
 * Prácticas Profesionales.
 */
public class LoginController {

    @FXML
    private TextField tfNombreUsuario;

    @FXML
    private PasswordField pfContrasenia;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnSalir;

    @FXML
    private Label lblMensajeError;

    private LoginService loginService;

    public LoginController() {
        loginService = new LoginService();
    }

    @FXML
    private void iniciarSesion(ActionEvent evento) {
        limpiarMensajeError();

        try {
            Usuario usuario = loginService.iniciarSesion(
                    tfNombreUsuario.getText(),
                    pfContrasenia.getText());

            if (usuario == null) {
                mostrarMensajeError("Usuario, contraseña incorrecta o usuario inactivo.");
                return;
            }

            TipoUsuario tipoUsuario = loginService.obtenerTipoUsuario(
                    usuario.getIdUsuario());

            SesionUsuario.setUsuarioActual(usuario);
            SesionUsuario.setTipoUsuarioActual(tipoUsuario);

            abrirMenu(evento, tipoUsuario);

        } catch (SQLException excepcion) {
            mostrarMensajeError("No se pudo acceder a la base de datos.");
            excepcion.printStackTrace();
        } catch (IOException excepcion) {
            mostrarMensajeError("No se pudo abrir el menú correspondiente.");
            excepcion.printStackTrace();
        }
    }

    @FXML
    private void salir(ActionEvent evento) {
        Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        stage.close();
    }

    private void abrirMenu(ActionEvent evento, TipoUsuario tipoUsuario)
            throws IOException {
        String rutaVista = obtenerRutaMenu(tipoUsuario);

        Parent raiz = FXMLLoader.load(getClass().getResource(rutaVista));
        Scene sceneMenu = new Scene(raiz);

        Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
        stage.setScene(sceneMenu);
        stage.centerOnScreen();
        stage.show();
    }

    private String obtenerRutaMenu(TipoUsuario tipoUsuario) {
        switch (tipoUsuario) {
            case ADMINISTRADOR:
                return "/spp/vista/GUI-MenuAdministrador.fxml";

            case COORDINADOR:
                return "/spp/vista/GUI-MenuCoordinador.fxml";

            case PROFESOR:
                return "/spp/vista/GUI-MenuProfesor.fxml";

            case PRACTICANTE:
                return "/spp/vista/GUI-MenuPracticante.fxml";

            default:
                throw new IllegalArgumentException("Tipo de usuario no valido.");
        }
    }

    private void limpiarMensajeError() {
        lblMensajeError.setText("");
    }

    private void mostrarMensajeError(String mensaje) {
        lblMensajeError.setText(mensaje);
    }
}
