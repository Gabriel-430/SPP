package spp.controlador;

import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import spp.modelo.dto.Practicante;
import spp.service.PracticanteService;
import spp.utilidades.Alerta;
import spp.utilidades.UtilidadesGUI;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 17/06/2026
 * Descripción: Controla la vista que muestra los practicantes que han
 * entregado al menos un documento como autoevaluación, cronograma u
 * horario y que pertenece al mismo curso que el profesor.
 */
public class ListaPracticantesConEntregablesController {

    @FXML
    private TableView<Practicante> tblPracticantes;
    @FXML
    private TableColumn<Practicante, String> colMatricula;
    @FXML
    private TableColumn<Practicante, String> colNombre;
    @FXML
    private TableColumn<Practicante, String> colDocumentos;

    private final PracticanteService practicanteService = new PracticanteService();

    @FXML
    public void initialize() {
        colMatricula.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMatricula()));
        colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getUsuario().getNombreCompleto()));
        colDocumentos.setCellValueFactory(cell -> new SimpleStringProperty("Sí")); // Tienen al menos 1 por la consulta SQL

        cargarPracticantes();
    }

    private void cargarPracticantes() {
        try {
            int idProfesor = MenuProfesorController.idProfesorLogueado;
            List<Practicante> lista = practicanteService.recuperarListaPracticantesConDocumentos(idProfesor);
            tblPracticantes.setItems(FXCollections.observableArrayList(lista));
        } catch (SQLException e) {
            Alerta.mostrarAlertaError("Error de acceso", "No se pudo acceder a la base de datos.");
        }
    }

    @FXML
    void clicVerDocumentos(Event event) {
        Practicante seleccionado = tblPracticantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            VisualizarEntregablesPracticanteController.practicanteSeleccionado = seleccionado;
            UtilidadesGUI.mostrarGUI("/spp/vista/GUI-VisualizarEntregablesPracticante.fxml", event, "Expediente del Practicante");
        } else {
            Alerta.mostrarAlertaAdvertencia("Selección requerida", "Por favor seleccione un practicante de la tabla.");
        }
    }

    @FXML
    void clicRegresar(Event event) {
        UtilidadesGUI.mostrarGUI("/spp/vista/GUI-MenuProfesor.fxml", event, "Menú Profesor");
    }
}
