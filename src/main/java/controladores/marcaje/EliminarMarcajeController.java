package controladores.marcaje;

import DAO.MarcajeDAO;
import com.example.inventario_hib.App;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Marcaje;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EliminarMarcajeController {
    private final MarcajeDAO marcajeDAO = new MarcajeDAO();
    private Marcaje marcajeSeleccionado;

    @FXML
    private TableView<Marcaje> tablaMarcajes;
    @FXML
    private TableColumn<Marcaje, Long> colIdMarcaje;
    @FXML
    private TableColumn<Marcaje, Long> colIdAula;
    @FXML
    private TableColumn<Marcaje, Long> colIdProducto;
    @FXML
    private TableColumn<Marcaje, Date> colFecha;
    @FXML
    private TableColumn<Marcaje, Boolean> colTipo;

    @FXML
    private ComboBox<Long> cbIdMarcajes;

    @FXML // Importamos el botón de limpiar
    private Button buttonLimpiar;

    @FXML
    public void initialize() {
        colIdMarcaje.setCellValueFactory(new PropertyValueFactory<>("idMarcaje"));
        colIdAula.setCellValueFactory(new PropertyValueFactory<>("idAula"));
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cargarMarcajes();

        tablaMarcajes.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Marcaje>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                marcajeSeleccionado = newValue;
                cbIdMarcajes.setValue(marcajeSeleccionado.getIdMarcaje());
            }
        });

        cbIdMarcajes.getItems().setAll(obtenerIdMarcajes());

        cbIdMarcajes.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                marcajeSeleccionado = marcajeDAO.obtenerPorId(newValue);
                tablaMarcajes.getSelectionModel().select(marcajeSeleccionado);
            }
        });

        buttonLimpiar.setOnAction(actionEvent ->
                limpiar()
        );
    }

    private void cargarMarcajes() {
        List<Marcaje> marcajes = marcajeDAO.getTodos();
        tablaMarcajes.getItems().clear();
        tablaMarcajes.getItems().setAll(marcajes);
    }

    private List<Long> obtenerIdMarcajes() {
        List<Marcaje> marcajes = marcajeDAO.getTodos();
        List<Long> idMarcajes = new ArrayList<>();
        for (Marcaje marcaje : marcajes) {
            idMarcajes.add(marcaje.getIdMarcaje());
        }
        return idMarcajes;
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setDirectory(0);
            App.setRoot("accesibilidad", "Accesibilidad.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setHeaderText("Ayuda - Eliminar categoría");
            alert.setContentText("Esta es la ventana de eliminar categorías. Aquí se mostrarán todas las categorías disponibles en el sistema.\n" +
                    "Solo debes seleccionar, ya puede ser en la tabla o en la lista desplegable, la categoría que se quiera eliminar.\n" +
                    "Deberás darle al botón de eliminar, y volver a confirmar una segunda vez para evitar eliminados accidentales.\n" +
                    "Para limpiar los campos, debe pulsar el botón 'Limpiar'.\n" +
                    "Por último, si desea volver solo debe pulsar en 'Volver'.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setDirectory(3);
            App.setRoot("marcajes", "Sistema de gestión de Marcajes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(ActionEvent actionEvent) {
        if (marcajeSeleccionado != null) {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setTitle("Eliminar marcaje");
            alertConfirmation.setHeaderText("¿Estás seguro de que deseas eliminar el marcaje?");
            alertConfirmation.setContentText("Se eliminará el marcaje seleccionado.");
            alertConfirmation.showAndWait();
            if (alertConfirmation.getResult() != ButtonType.OK) {
                return;
            } else {
                if (marcajeDAO.delete(marcajeSeleccionado)) {
                    cargarMarcajes();
                    cbIdMarcajes.getItems().setAll(obtenerIdMarcajes());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito");
                    alert.setHeaderText("Marcaje eliminado.");
                    alert.setContentText("El marcaje seleccionado ha sido eliminado correctamente.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al eliminar el maracje.");
                    alert.setContentText("Ha ocurrido un error al intentar eliminar el marcaje seleccionado.");
                    alert.showAndWait();
                }
            }
        }

    }

    private void limpiar() {
        marcajeSeleccionado = null;
        cbIdMarcajes.setValue(null);
        tablaMarcajes.getSelectionModel().clearSelection();
    }
}
