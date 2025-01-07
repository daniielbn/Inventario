package controladores.producto;

import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.Producto;

public class CrearProductoController {
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtEAN13;
    @FXML
    private TextField txtRFID;

    @FXML // Importamos el botón de limpiar
    private Button buttonLimpiar;

    @FXML
    public void initialize() {
        buttonLimpiar.setOnAction(event ->
            limpiar(event)
        );
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
            alert.setTitle("Ayuda - Crear Producto");
            alert.setHeaderText("Ayuda para el módulo de crear Productos.");
            alert.setContentText("Desde este módulo podrás crear productos. Los campos a rellenar son:" +
                    "\n- Descripción del Producto: Ej.: 'Ordenador'." +
                    "\n- EAN13 del Producto: Ej.: 1234567890123 (número de 13 dígitos)" +
                    "\n- KeyRFID del Producto: Ej.: RFID00001 (Secuencia alfanúmerica de 4 caracteres 'RFID' y 5 dígitos '00001')" +
                    "\n- Desde el botón de 'Limpiar' puede vacíar todos los campos." +
                    "\n- Desde el botón de 'Volver' puede volver al listado de aulas." +
                    "\n- Por úlitmo, desde el botón de 'Crear' puede crear el producto con los datos introducidos.");
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

    public void crear(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            if (comprobarRFID(txtRFID.getText()) && comprobarEAN13(txtEAN13.getText())) {
                ProductoDAO productoDAO = new ProductoDAO();
                Producto nuevoProducto = new Producto(txtDescripcion.getText(), Long.parseLong(txtEAN13.getText()), txtRFID.getText());
                if (productoDAO.create(nuevoProducto)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Producto creado");
                    alert.setHeaderText("Producto creado correctamente.");
                    alert.setContentText("Descripción: " + txtDescripcion.getText() +
                            "\nEAN13: " + txtEAN13.getText() +
                            "\nKeyRFID: " + txtRFID.getText());
                    alert.showAndWait();
                    limpiar(null);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error al crear el producto");
                    alert.setHeaderText("Error al crear el producto.");
                    alert.setContentText("No se ha podido crear el producto. Por favor, inténtelo de nuevo.");
                    alert.showAndWait();
                }
            }
        }
    }

    public void limpiar(ActionEvent actionEvent) {
        txtDescripcion.clear();
        txtEAN13.clear();
        txtRFID.clear();
    }

    private boolean comprobarCampos() {
        return !txtDescripcion.getText().isEmpty() && !txtEAN13.getText().isEmpty() && !txtRFID.getText().isEmpty();
    }

    private boolean comprobarEAN13(String EAN13) {
        return EAN13.matches("[0-9]{13}");
    }

    private boolean comprobarRFID(String RFID) {
        return RFID.matches("RFID[0-9]{5}");
    }
}
