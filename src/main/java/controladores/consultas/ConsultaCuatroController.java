package controladores.consultas;

import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Producto;

import java.util.List;

public class ConsultaCuatroController {
    private static final ProductoDAO productoDAO = new ProductoDAO();

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

    @FXML
    public void initialize() {
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEAN13.setCellValueFactory(new PropertyValueFactory<>("EAN13"));
        colKeyRFID.setCellValueFactory(new PropertyValueFactory<>("keyRFID"));
        cargarProductos();
    }

    private void cargarProductos() {
        List<Producto> productos = productoDAO.getProductosSinCategorias();
        tablaProductos.getItems().setAll(productos);
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
            alert.setHeaderText("Ayuda - Consulta 4");
            alert.setContentText("En esta ventana puedes consultar los productos que no pertenecen a ninguna categoría." +
                    "\nSi lo deseas, puedes pulsar el botón de 'Volver' para volver a la página inicial.");
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
}
