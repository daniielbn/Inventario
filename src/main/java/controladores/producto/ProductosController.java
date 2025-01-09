package controladores.producto;

import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Producto;

import java.util.List;

public class ProductosController {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private Producto productoSeleccionado;

    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtRFID;

    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, Long> colIdProducto;
    @FXML
    private TableColumn<Producto, String> colDescripcion;
    @FXML
    private TableColumn<Producto, String> colEAN13;
    @FXML
    private TableColumn<Producto, Boolean> colKeyRFID;

    @FXML // Importamos el botón de limpiar
    private Button buttonLimpiar;

    @FXML
    public void initialize() {
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEAN13.setCellValueFactory(new PropertyValueFactory<>("EAN13"));
        colKeyRFID.setCellValueFactory(new PropertyValueFactory<>("keyRFID"));
        cargarProductos();

        txtDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarDescripcion();
        });

        txtRFID.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarRFID();
        });

        buttonLimpiar.setOnAction(event -> {
            limpiar();
        });

        tablaProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            productoSeleccionado = newValue;
        });
    }

    private void cargarProductos() {
        List<Producto> productos = productoDAO.getTodos();
        tablaProductos.getItems().setAll(productos);
    }

    private void filtrarRFID() {
        if (!txtRFID.getText().isEmpty()) {
            List<Producto> productos = productoDAO.getPorRFID(txtRFID.getText());
            tablaProductos.getItems().clear();
            tablaProductos.getItems().setAll(productos);
        } else {
            cargarProductos();
        }
    }

    private void filtrarDescripcion() {
        if (!txtDescripcion.getText().isEmpty()) {
            List<Producto> productos = productoDAO.getPorDescripcion(txtDescripcion.getText());
            tablaProductos.getItems().clear();
            tablaProductos.getItems().setAll(productos);
        } else {
            cargarProductos();
        }
    }
    public void abrirAccesibilidad(ActionEvent actionEvent) {
        try {
            App.setDirectory(0);
            App.setRoot("accesibilidad", "Accesibilidad.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setHeaderText("Ayuda - Productos");
            alert.setContentText("Desde este módulo podrás gestionar los productos. Podrás:" +
                    "\n- Crear productos." +
                    "\n- Listar productos." +
                    "\n- Modificar productos." +
                    "\n- Eliminar productos." +
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

    public void crearProducto(MouseEvent mouseEvent) {
        try {
            App.setDirectory(4);
            App.setRoot("crearProducto", "Sistema de gestión de Productos - Crear Producto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarProducto(MouseEvent mouseEvent) {
        try {
            if (productoSeleccionado != null) {
                App.setUserData(productoSeleccionado);
                App.setDirectory(4);
                App.setRoot("modificarProducto", "Sistema de gestión de Productos - Modificar Producto.");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al modificar producto");
                alert.setContentText("Debes seleccionar un producto para poder modificarlo.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarProducto(MouseEvent mouseEvent) {
        try {
            if (productoSeleccionado != null) {
                Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmacion.setTitle("Confirmación");
                alertConfirmacion.setHeaderText("¿Estás seguro de que deseas eliminar el producto?");
                alertConfirmacion.setContentText("Se eliminará el producto seleccionado.");
                if (alertConfirmacion.showAndWait().get() != ButtonType.OK) {
                    return;
                } else {
                    if (productoDAO.delete(productoSeleccionado)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito");
                        alert.setHeaderText("Producto eliminado");
                        alert.setContentText("El producto ha sido eliminado correctamente.");
                        alert.showAndWait();
                        cargarProductos();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error al eliminar el producto");
                        alert.setContentText("No se ha podido eliminar el producto seleccionado.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al modificar producto");
                alert.setContentText("Debes seleccionar un producto para poder modificarlo.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiar() {
        txtDescripcion.clear();
        txtRFID.clear();
        cargarProductos();
    }
}
