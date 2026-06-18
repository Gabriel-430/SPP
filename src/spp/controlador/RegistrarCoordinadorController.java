package spp.controlador;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 18/06/2026
 * Descripción: Controlador para la vista de registro de Coordinador.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import spp.utilidades.Alerta;
import spp.utilidades.UtilidadesGUI;

public class RegistrarCoordinadorController implements Initializable {

    @FXML
    private TextField txtNombreCompleto;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtNoPersonal;
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtContrasena;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    void clicRegistrar(Event event) {
        String nombre = txtNombreCompleto.getText();
        String usuario = txtNombreUsuario.getText();
        String noPersonal = txtNoPersonal.getText();
        String correo = txtCorreo.getText();
        String contrasena = txtContrasena.getText();

        if (nombre.isEmpty() || usuario.isEmpty() || noPersonal.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            Alerta.mostrarAlertaAdvertencia("Campos vacíos", "Por favor, llene todos los campos requeridos.");
            return;
        }

        Alerta.mostrarAlertaInformacion("Éxito", "Coordinador registrado correctamente en el sistema.");
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuAdministrador.fxml", event, "Menú Administrador");
    }

    @FXML
    void clicCancelar(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuAdministrador.fxml", event, "Menú Administrador");
    }
}
