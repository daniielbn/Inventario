package controladores.prodCat;

import DAO.CategoriaDAO;
import DAO.ProductoCategoriaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.ProductoCategoria;
import java.util.List;

public class ProdCatController {
    private static final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private static final ProductoCategoriaDAO productoCategoriaDAO = new ProductoCategoriaDAO();
    private ProductoCategoria productoCategoriaSeleccionado;

    @FXML
    private TextField txtIdCategoria;
    @FXML
    private TextField txtIdProducto;

    @FXML
    private TableView<ProductoCategoria> tablaProductoCategoria;
    @FXML
    private TableColumn<ProductoCategoria, Long> colId;
    @FXML
    private TableColumn<ProductoCategoria, Long> colIdCategoria;
    @FXML
    private TableColumn<ProductoCategoria, Long> colIdProducto;

    @FXML
    private Button buttonLimpiar; // Importamos el botón de limpiar

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idProductoCategoria"));
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
        cargarProductoCategorias();

        txtIdCategoria.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                cargarProductoCategorias();
            } else {
                filtrarIdCategoria();
            }
        });

        txtIdProducto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                cargarProductoCategorias();
            } else {
                filtrarIdProducto();
            }
        });

        buttonLimpiar.setOnAction(event -> {
            limpiar();
        });

        tablaProductoCategoria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            productoCategoriaSeleccionado = newValue;
        });
    }

    private void cargarProductoCategorias() {
        List<ProductoCategoria> productoCategorias = productoCategoriaDAO.getTodos();
        tablaProductoCategoria.getItems().clear();
        tablaProductoCategoria.getItems().setAll(productoCategorias);

    }

    private void filtrarIdCategoria() {
        List<ProductoCategoria> productoCategorias = productoCategoriaDAO.getPorIdCategoria(Long.parseLong(txtIdCategoria.getText()));
        tablaProductoCategoria.getItems().clear();
        tablaProductoCategoria.getItems().setAll(productoCategorias);

    }

    private void filtrarIdProducto() {
        List<ProductoCategoria> productoCategorias = productoCategoriaDAO.getPorIdProducto(Long.parseLong(txtIdProducto.getText()));
        tablaProductoCategoria.getItems().clear();
        tablaProductoCategoria.getItems().setAll(productoCategorias);

    }

    private void limpiar() {
        productoCategoriaSeleccionado = null;
        tablaProductoCategoria.getItems().clear();
        txtIdCategoria.clear();
        txtIdProducto.clear();
        cargarProductoCategorias();
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
            App.setDirectory(0);
            App.setRoot("ayuda", "Ayuda");
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

    public void crearProductoCategoria(MouseEvent mouseEvent) {
        try {
            App.setDirectory(5);
            App.setRoot("crearProdCat", "Relación Producto-Categoría - Crear relación");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarProductoCategoria(MouseEvent mouseEvent) {
        try {
            App.setDirectory(5);
            App.setRoot("modificarProdCat", "Relación Producto-Categoría - Modificar relación");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarProductoCategoria(MouseEvent mouseEvent) {
        try {
            if (productoCategoriaSeleccionado != null) {
                Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmacion.setTitle("Eliminar relación");
                alertConfirmacion.setHeaderText("¿Está seguro de que desea eliminar la relación seleccionada?");
                alertConfirmacion.setContentText("Esta acción no se puede deshacer.");
                if (alertConfirmacion.showAndWait().get() != ButtonType.OK) {
                    return;
                } else {
                    if (productoCategoriaDAO.delete(productoCategoriaSeleccionado)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito");
                        alert.setHeaderText("Relación eliminada");
                        alert.setContentText("La relación ha sido eliminada correctamente.");
                        alert.showAndWait();
                        cargarProductoCategorias();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error al eliminar la relación");
                        alert.setContentText("Ha ocurrido un error al eliminar la relación.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al eliminar la relación");
                alert.setContentText("Debes seleccionar una relación para poder eliminarla.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
