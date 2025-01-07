package controladores.producto;

import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Aula;
import modelo.Producto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EliminarProductoController {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private Producto productoSeleccionado;

    @FXML
    private ComboBox<Long> cbIdProducto;

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
        colIdProducto.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("idProducto"));
        colDescripcion.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("descripcion"));
        colEAN13.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("EAN13"));
        colKeyRFID.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("keyRFID"));
        cargarProductos();

        cbIdProducto.getItems().setAll(obtenerIdProductos());

        tablaProductos.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Producto>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                productoSeleccionado = newValue;
                cbIdProducto.setValue(newValue.getIdProducto());
            }
        });

        cbIdProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                productoSeleccionado = productoDAO.obtenerPorId(newValue);
                tablaProductos.getSelectionModel().select(productoSeleccionado);
            }
        });

        buttonLimpiar.setOnAction(event ->
                limpiar(event)
        );
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
            alert.setHeaderText("Ayuda - Eliminar Producto");
            alert.setContentText("Esta es la ventana de eliminar productos. Aquí se mostrarán todas los productos disponibles en el sistema.\n" +
                    "Solo debes seleccionar, ya puede ser en la tabla o en la lista desplegable, el producto que se quiere eliminar.\n" +
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
            App.setDirectory(4);
            App.setRoot("productos", "Sistema de gestión de Productos.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiar(ActionEvent actionEvent) {
        productoSeleccionado = null;
        cbIdProducto.setValue(null);
        tablaProductos.getSelectionModel().clearSelection();
    }

    private void cargarProductos() {
        List<Producto> productos = productoDAO.getTodos();
        tablaProductos.getItems().clear();
        tablaProductos.getItems().setAll(productos);
    }

    private List<Long> obtenerIdProductos() {
        List<Producto> productos = productoDAO.getTodos();
        List<Long> idProductos = new ArrayList<>();
        for (Producto producto : productos) {
            idProductos.add(producto.getIdProducto());
        }
        return idProductos;
    }

    public void eliminar(ActionEvent actionEvent) {
        if (productoSeleccionado != null) {
            Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmacion.setTitle("Confirmación");
            alertConfirmacion.setHeaderText("¿Estás seguro de que deseas eliminar el producto?");
            alertConfirmacion.setContentText("Se eliminará el producto seleccionado.");
            if (alertConfirmacion.showAndWait().get() != ButtonType.OK) {
                return;
            } else {
                if (productoDAO.delete(productoSeleccionado)) {
                    cargarProductos();
                    cbIdProducto.getItems().setAll(obtenerIdProductos());
                    productoSeleccionado = null;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito");
                    alert.setHeaderText("Producto eliminado");
                    alert.setContentText("El producto ha sido eliminado correctamente.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al eliminar el producto");
                    alert.setContentText("No se ha podido eliminar el producto seleccionado.");
                    alert.showAndWait();
                }
            }
        }
    }
}
