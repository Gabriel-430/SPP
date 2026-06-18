package spp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 16/06/2026
 * Descripción: Inicia la aplicación del Sistema de Prácticas Profesionales.
 */
public class SPP extends Application {

  @Override
  public void start(Stage stagePrincipal) throws Exception {
    Parent raiz = FXMLLoader.load(getClass().getResource(
        "/spp/vista/GUI-Login.fxml"));

    Scene sceneLogin = new Scene(raiz);

    stagePrincipal.setTitle("Sistema de Prácticas Profesionales");
    stagePrincipal.setScene(sceneLogin);
    stagePrincipal.centerOnScreen();
    stagePrincipal.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}