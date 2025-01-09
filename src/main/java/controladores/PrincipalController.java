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

    public void abrirProductoCategoria(MouseEvent mouseEvent) {
        try {
            App.setDirectory(5);
            App.setRoot("prodCat", "Sistema de gestión de la relación entre Producto-Categoría.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta1(MouseEvent mouseEvent) {
        try {
            App.setDirectory(6);
            App.setRoot("uno", "Sistema de gestión de productos mediante RFID - Marcajes de un Producto entre dos fechas.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta2(MouseEvent mouseEvent) {
        try {
            App.setDirectory(6);
            App.setRoot("dos", "Sistema de gestión de productos mediante RFID - Marcajes de un Aula entre dos fechas.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta3(MouseEvent mouseEvent) {
        try {
            App.setDirectory(6);
            App.setRoot("tres", "Sistema de gestión de productos mediante RFID - Marcajes de un Aula y de un Producto.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta4(MouseEvent mouseEvent) {
        try {
            App.setDirectory(6);
            App.setRoot("cuatro", "Sistema de gestión de productos mediante RFID - Productos sin categoría.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abirConsulta5(MouseEvent mouseEvent) {
        try {
            App.setDirectory(6);
            App.setRoot("cinco", "Sistema de gestión de productos mediante RFID - Número de productos por categoría.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
