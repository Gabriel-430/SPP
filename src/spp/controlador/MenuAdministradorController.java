package spp.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import spp.utilidades.UtilidadesGUI;

public class MenuAdministradorController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    void mostrarRegistrarCoordinador(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-RegistrarCoordinador.fxml", event, "Registrar Coordinador");
    }

    @FXML
    void mostrarRegistrarProfesor(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-SolicitarCredencialesProfesor.fxml", event, "Registrar Profesor");
    }

    @FXML
    void mostrarMostrarProfesores(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MostrarProfesores.fxml", event, "Mostrar Profesores");
    }

    @FXML
    void cerrarSesion(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-Login.fxml", event, "Iniciar Sesión");
    }
}
