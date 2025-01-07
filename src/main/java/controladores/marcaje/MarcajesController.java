package controladores.marcaje;

import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class MarcajesController {
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
            alert.setHeaderText("Ayuda - Marcajes");
            alert.setContentText("Desde este módulo podrás gestionar los marcajes. Podrás:" +
                    "\n- Crear marcajes." +
                    "\n- Listar marcajes." +
                    "\n- Modificar marcajes." +
                    "\n- Eliminar marcajes." +
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

    public void crearMarcaje(ActionEvent actionEvent) {
        try {
            App.setDirectory(3);
            App.setRoot("crearMarcaje", "Sistema de gestión de marcajes - Crear Marcaje.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarMarcajes(ActionEvent actionEvent) {
        try {
            App.setDirectory(3);
            App.setRoot("listarMarcajes", "Sistema de gestión de marcajes - Listar Marcajes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarMarcaje(ActionEvent actionEvent) {
        try {
            App.setDirectory(3);
            App.setRoot("modificarMarcaje", "Sistema de gestión de marcajes - Modificar Marcaje.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarMarcaje(ActionEvent actionEvent) {
        try {
            App.setDirectory(3);
            App.setRoot("eliminarMarcaje", "Sistema de gestión de marcajes - Eliminar Marcaje.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
