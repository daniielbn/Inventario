package controladores.categoria;

import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class CategoriasController {
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
                    "\n- Listar categorías." +
                    "\n- Modificar categorías." +
                    "\n- Eliminar categorías." +
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

    public void crearCategoria(ActionEvent actionEvent) {
        try {
            App.setDirectory(2);
            App.setRoot("crearCategoria", "Sistema de gestión de Categorías - Crear categorías.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarCategorias(ActionEvent actionEvent) {
        try {
            App.setDirectory(2);
            App.setRoot("listarCategorias", "Sistema de gestión de Categorías - Listar categorías.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarCategoria(ActionEvent actionEvent) {
        try {
            App.setDirectory(2);
            App.setRoot("modificarCategoria", "Sistema de gestión de Categorías - Modificar categorías.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarCategoria(ActionEvent actionEvent) {
        try {
            App.setDirectory(2);
            App.setRoot("eliminarCategoria", "Sistema de gestión de Categorías - Eliminar categorías.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
