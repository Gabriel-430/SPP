package spp.utilidades;

import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 16/06/2026
 * Descripción: Contiene utilidades de GUI como mostrar o cerrar.
 */
public class UtilidadesGUI {

    public static void mostrarGUI(String rutaFxml, Event event, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(UtilidadesGUI.class.getResource(rutaFxml));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cerrarGUI(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
