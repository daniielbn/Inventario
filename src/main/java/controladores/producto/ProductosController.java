package controladores.producto;

import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class ProductosController {
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

    public void crearProducto(ActionEvent actionEvent) {
        try {
            App.setDirectory(4);
            App.setRoot("crearProducto", "Sistema de gestión de Productos - Crear Producto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarProductos(ActionEvent actionEvent) {
        try {
            App.setDirectory(4);
            App.setRoot("listarProductos", "Sistema de gestión de Productos - Listar Productos.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarProducto(ActionEvent actionEvent) {
        try {
            App.setDirectory(4);
            App.setRoot("modificarProducto", "Sistema de gestión de Productos - Modificar Producto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarProducto(ActionEvent actionEvent) {
        try {
            App.setDirectory(4);
            App.setRoot("eliminarProducto", "Sistema de gestión de Productos - Eliminar Producto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
