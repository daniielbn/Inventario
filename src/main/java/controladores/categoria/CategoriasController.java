package controladores.categoria;

import DAO.CategoriaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Categoria;

import java.util.List;

public class CategoriasController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private Categoria categoriaSeleccionada;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;

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

        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarNombre();
        });

        txtDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarDescripcion();
        });

        buttonLimpiar.setOnAction(event -> {
            limpiar();
        });

        tablaCategorias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categoriaSeleccionada = newValue;
        });
    }

    private void cargarCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        tablaCategorias.getItems().setAll(categorias);
    }

    private void filtrarNombre() {
        if (!txtNombre.getText().isEmpty()) {
            List<Categoria> categorias = categoriaDAO.getPorNombre(txtNombre.getText());
            tablaCategorias.getItems().clear();
            tablaCategorias.getItems().setAll(categorias);
        } else {
            cargarCategorias();
        }
    }

    private void filtrarDescripcion() {
        if (!txtDescripcion.getText().isEmpty()) {
            List<Categoria> categorias = categoriaDAO.getPorDescripcion(txtDescripcion.getText());
            tablaCategorias.getItems().clear();
            tablaCategorias.getItems().setAll(categorias);
        } else {
            cargarCategorias();
        }
    }

    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setDirectory(0);
            App.setRoot("accesibilidad", "Accesibilidad");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setHeaderText("Ayuda - Categorías");
            alert.setContentText("Desde este módulo podrás gestionar las categorías. Podrás:" +
                    "\n- Crear categorías." +
                    "\n- Modificar categorías." +
                    "\n- Eliminar categorías." +
                    "\nPara poder modificar o eliminar una categoría, primero deberás seleccionarla de la tabla." +
                    "\n\nAdemás, podrás acceder a la accesibilidad de la aplicación y a la ayuda.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setDirectory(0);
            App.setRoot("principal", "Sistema de gestión de productos mediante RFID - Daniel Brito Negrín.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearCategoria(MouseEvent mouseEvent) {
        try {
            App.setDirectory(2);
            App.setRoot("crearCategoria", "Sistema de gestión de Categorías - Crear categorías.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarCategoria(MouseEvent mouseEvent) {
        try {
            if (categoriaSeleccionada != null) {
                App.setUserData(categoriaSeleccionada);
                App.setDirectory(2);
                App.setRoot("modificarCategoria", "Sistema de gestión de Categorías - Modificar categorías.");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Debes seleccionar una categoria para poder modificarla.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarCategoria(MouseEvent mouseEvent) {
        try {
            if (categoriaSeleccionada != null) {
                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setTitle("Eliminar categoría.");
                alertConfirmation.setHeaderText("¿Estás seguro de que deseas eliminar la categoría?");
                alertConfirmation.setContentText("Se eliminará la categoría seleccionada.");
                alertConfirmation.showAndWait();
                if (alertConfirmation.getResult() != ButtonType.OK) {
                    return;
                } else {
                    if (categoriaDAO.delete(categoriaSeleccionada)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito");
                        alert.setHeaderText("Categoría eliminada.");
                        alert.setContentText("La categoría seleccionada ha sido eliminada correctamente.");
                        alert.showAndWait();
                        cargarCategorias();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error al eliminar la categoría.");
                        alert.setContentText("Ha ocurrido un error al intentar eliminar la categoría seleccionada.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Debes seleccionar una categoría para poder modificarla.");
                alert.showAndWait();
            }
            App.setDirectory(2);
            App.setRoot("eliminarCategoria", "Sistema de gestión de Categorías - Eliminar categorías.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiar() {
        txtNombre.clear();
        txtDescripcion.clear();
        cargarCategorias();
    }
}
