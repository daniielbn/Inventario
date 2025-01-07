package controladores;

import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class PrincipalController {

    public void abrirAulas(MouseEvent mouseEvent) {
        try {
            App.setDirectory(1);
            App.setRoot("aulas", "Sistema de gestión de Aulas.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirProductos(MouseEvent mouseEvent) {
        try {
            App.setDirectory(4);
            App.setRoot("productos", "Sistema de gestión de Productos.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirMarcajes(MouseEvent mouseEvent) {
        try {
            App.setDirectory(3);
            App.setRoot("marcajes", "Sistema de gestión de Marcajes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirCategorias(MouseEvent mouseEvent) {
        try {
            App.setDirectory(2);
            App.setRoot("categorias", "Sistema de gestión de Categorías.");
        } catch (Exception e) {
            e.printStackTrace();
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
            alert.setContentText("Para cualquier duda o sugerencia, contacte con el desarrollador del sistema: Daniel Brito Negrín.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salir(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void abrirConsulta1(MouseEvent mouseEvent) {
    }

    public void abrirConsulta2(MouseEvent mouseEvent) {
    }

    public void abrirConsulta3(MouseEvent mouseEvent) {
    }

    public void abrirConsulta4(MouseEvent mouseEvent) {
    }

    public void abrirConsulta5(MouseEvent mouseEvent) {
    }

    public void abrirProductoCategoria(MouseEvent mouseEvent) {
    }

    public void abirConsulta1(MouseEvent mouseEvent) {
    }

    public void abirConsulta2(MouseEvent mouseEvent) {
    }

    public void abirConsulta3(MouseEvent mouseEvent) {
    }

    public void abirConsulta4(MouseEvent mouseEvent) {
    }

    public void abirConsulta5(MouseEvent mouseEvent) {
    }
}
