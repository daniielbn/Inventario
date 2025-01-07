package controladores.categoria;

import DAO.CategoriaDAO;
import com.example.inventario_hib.App;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Categoria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EliminarCategoriaController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private Categoria categoriaSeleccionada;

    @FXML
    private ComboBox<Long> cbIdCategorias;

    @FXML
    private TableView<Categoria> tablaCategorias;
    @FXML
    private TableColumn<Categoria, Long> colIdCategoria;
    @FXML
    private TableColumn<Categoria, String> colNombre;
    @FXML
    private TableColumn<Categoria, String> colDescripcion;
    @FXML
    private TableColumn<Categoria, Boolean> colEstado;

    @FXML // Importamos el botón de limpiar
    private Button buttonLimpiar;

    @FXML
    public void initialize() {
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        cargarCategorias();

        tablaCategorias.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Categoria>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                categoriaSeleccionada = newValue;
                cbIdCategorias.setValue(categoriaSeleccionada.getIdCategoria());
            }
        });

        cbIdCategorias.getItems().setAll(obtenerIdCategorias());

        cbIdCategorias.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                categoriaSeleccionada = categoriaDAO.obtenerPorId(newValue);
                tablaCategorias.getSelectionModel().select(categoriaSeleccionada);
            }
        });

        buttonLimpiar.setOnAction(event ->
                limpiar(event)
        );
    }

    private void cargarCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        tablaCategorias.getItems().clear();
        tablaCategorias.getItems().setAll(categorias);
    }

    private List<Long> obtenerIdCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        List<Long> idCategorias = new ArrayList<>();
        for (Categoria categoria : categorias) {
            idCategorias.add(categoria.getIdCategoria());
        }
        return idCategorias;
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
            App.setDirectory(2);
            App.setRoot("categorias", "Sistema de gestión de Categorías.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(ActionEvent actionEvent) {
        if (categoriaSeleccionada != null) {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setTitle("Eliminar categoría");
            alertConfirmation.setHeaderText("¿Estás seguro de que deseas eliminar la categoría?");
            alertConfirmation.setContentText("Se eliminará la categoría seleccionada.");
            alertConfirmation.showAndWait();
            if (alertConfirmation.getResult() != ButtonType.OK) {
                return;
            } else {
                if (categoriaDAO.delete(categoriaSeleccionada)) {
                    cargarCategorias();
                    cbIdCategorias.getItems().setAll(obtenerIdCategorias());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito");
                    alert.setHeaderText("Categoría eliminada.");
                    alert.setContentText("La categoría seleccionada ha sido eliminada correctamente.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al eliminar la categoría.");
                    alert.setContentText("Ha ocurrido un error al intentar eliminar la categoría seleccionada.");
                    alert.showAndWait();
                }
            }
        }
    }

    public void limpiar(ActionEvent actionEvent) {
        categoriaSeleccionada = null;
        cbIdCategorias.setValue(null);
        tablaCategorias.getSelectionModel().clearSelection();
    }
}
