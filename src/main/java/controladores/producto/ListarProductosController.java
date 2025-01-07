package controladores.producto;

import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Producto;

import java.util.List;

public class ListarProductosController {
    private final ProductoDAO productoDAO = new ProductoDAO();

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
            limpiar(event);
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
            alert.setHeaderText("Ayuda");
            alert.setContentText("Esta es la ventana de listado de productos. Aquí se mostrarán todas los productos disponibles en el sistema.\n" +
                    "Existe la opción de filtrar los resultados mediante los campos de búsqueda.\n" +
                    "El primer campo pertenece a la descripción del Producto.\n" +
                    "El segundo campo pertenece a la keyRFID del Producto\n" +
                    "Para limpiar los campos, debe pulsar el botón 'Limpiar'");
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
        txtDescripcion.clear();
        txtRFID.clear();
        cargarProductos();
    }
}
