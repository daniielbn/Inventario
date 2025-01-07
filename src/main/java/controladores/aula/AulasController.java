package controladores.aula;

import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class AulasController {
    public void crearAula(ActionEvent actionEvent) {
        try {
            App.setDirectory(1);
            App.setRoot("crearAula", "Sistema de gestión de Aulas - Crear aulas.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listarAulas(ActionEvent actionEvent) {
        try {
            App.setDirectory(1);
            App.setRoot("listarAulas", "Sistema de gestión de Aulas - Listar aulas.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void modificarAula(ActionEvent actionEvent) {
        try {
            App.setDirectory(1);
            App.setRoot("modificarAula", "Sistema de gestión de Aulas - Modificar aulas.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminarAula(ActionEvent actionEvent) {
        try {
            App.setDirectory(1);
            App.setRoot("eliminarAula", "Sistema de gestión de Aulas - Eliminar aulas.");
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            alert.setTitle("Ayuda - Aulas");
            alert.setHeaderText("Ayuda para el módulo de Aulas.");
            alert.setContentText("Desde este módulo podrás gestionar las aulas. Podrás:" +
                    "\n- Crear aulas." +
                    "\n- Listar aulas." +
                    "\n- Modificar aulas." +
                    "\n- Eliminar aulas." +
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
}
