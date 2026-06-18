package spp.controlador;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 18/06/2026
 * Descripción: Controlador del menú principal del Profesor.
 */
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import spp.modelo.MySQLConnectionManager;
import spp.utilidades.SesionUsuario;
import spp.utilidades.UtilidadesGUI;

public class MenuProfesorController implements Initializable {

    public static int idProfesorLogueado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (SesionUsuario.getUsuarioActual() != null) {
            idProfesorLogueado = SesionUsuario.getUsuarioActual().getIdUsuario();
        }
    }
     
    @FXML
    void mostrarEvaluarReporteParcial(Event event) {
        try {
            int idUsuario = SesionUsuario.getUsuarioActual().getIdUsuario();

            if (new spp.service.ReporteService()
                .recuperarReportesPendientes(idUsuario, "Reporte Parcial")
                .isEmpty()) {
                spp.utilidades.Alerta.mostrarAlertaInformacion(
                    "Bandeja Vacía",
                    "No hay reportes parciales pendientes por evaluar.");

            return;
        }

        UtilidadesGUI.mostrarGUI(
                "/spp/vista/GUI-EvaluarReporteParcial.fxml",
                event,
                "Evaluar Reporte Parcial");

    } catch (Exception excepcion) {
        spp.utilidades.Alerta.mostrarAlertaError(
                "Error",
                "No se pudo acceder a la base de datos.");
    }
}

    @FXML
    void mostrarEvaluarReporteMensual(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-EvaluarReporteMensual.fxml", event, "Evaluar Reporte Mensual");
    }

    @FXML
    void mostrarEvaluarReporteFinal(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-EvaluarReporteFinal.fxml", event, "Evaluar Reporte Final");
    }

    @FXML
    void mostrarEntregablesPracticante(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-ListaPracticantesConEntregables.fxml", event, "Entregables de Practicantes");
    }

    @FXML
    void mostrarBuzon(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-BuzonMensajes.fxml", event, "Buzón de Mensajes");
    }

    @FXML
    void mostrarEnviarMensaje(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-RedactarMensaje.fxml", event, "Redactar Mensaje");
    }

    @FXML
    void cerrarSesion(Event event) {
        idProfesorLogueado = 0;
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-Login.fxml", event, "Iniciar Sesión");
    }
}
