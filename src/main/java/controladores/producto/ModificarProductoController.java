package controladores.producto;

import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Producto;
import java.io.IOException;

public class ModificarProductoController {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private Producto productoSeleccionado;

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
        productoSeleccionado = (Producto) App.getUserData();

        limpiar();

        buttonLimpiar.setOnAction(event ->
                limpiar()
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
            alert.setHeaderText("Ayuda - Modificar producto");
            alert.setContentText("Esta es la ventana de modificado de productos. Aquí se mostrarán todos las productos disponibles en el sistema para las cuales podrás modificar cualquier dato (menos su ID).\n" +
                    "Puedes seleccionar el registro en la tabla que deseas modificar, o eligiendo si ID en la caja desplegable de 'Id de Producto'.\n" +
                    "Una vez selecciones el registro que deseas modificar, se autocompletarán los campos con los datos del mismo. Solo tienes que modificar lo que desees y pulsar 'Modificar'.\n" +
                    "Para el EAN13, debes introducir una secuencia de 13 dígitos." +
                    "Para la KeyRFID, debes introducir una secuencia alfanúmerica con 4 caracteres fijos (RFID), y 5 dígitos variables (00001)" +
                    "Para limpiar los campos, debe pulsar el botón 'Limpiar'." +
                    "Para volver al menú principal, pulse 'Volver'.");
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

    private void limpiar() {
        txtDescripcion.setText(productoSeleccionado.getDescripcion());
        txtEAN13.setText(productoSeleccionado.getEAN13().toString());
        txtRFID.setText(productoSeleccionado.getKeyRFID());
    }

    public void modificar(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            if (comprobarEAN13(txtEAN13.getText()) && comprobarRFID(txtRFID.getText()) && comprobarCamposModificados()) {
                productoSeleccionado.setDescripcion(txtDescripcion.getText());
                productoSeleccionado.setEAN13(Long.parseLong(txtEAN13.getText()));
                productoSeleccionado.setKeyRFID(txtRFID.getText());
                if (productoDAO.update(productoSeleccionado)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Producto modificado");
                    alert.setHeaderText("Producto modificado correctamente.");
                    alert.setContentText("Descripción: " + txtDescripcion.getText() +
                            "\nEAN13: " + txtEAN13.getText() +
                            "\nKeyRFID: " + txtRFID.getText());
                    alert.showAndWait();
                    volver(actionEvent);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Modificar Aula");
                    alert.setHeaderText("Error al modificar el aula.");
                    alert.showAndWait();
                }
            }
        }
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

    private boolean comprobarCamposModificados() {
        if (txtRFID.getText().equals(productoSeleccionado.getKeyRFID()) && txtEAN13.getText().equals(productoSeleccionado.getEAN13().toString()) && txtDescripcion.getText().equals(productoSeleccionado.getDescripcion())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Producto no modificado");
            alert.setHeaderText("No se ha modificado ningún campo.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
