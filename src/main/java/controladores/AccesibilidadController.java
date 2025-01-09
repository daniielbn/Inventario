package controladores;

import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class AccesibilidadController {
    public void abrirAyuda(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setHeaderText("Ayuda - Accesibilidad");
            alert.setContentText("En esta ventana puedes consultar la accesibilidad del sistema." +
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

    public void proximamente(MouseEvent mouseEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Próximamente");
            alert.setHeaderText("Próximamente");
            alert.setContentText("Esta funcionalidad estará disponible en futuras versiones del sistema.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
