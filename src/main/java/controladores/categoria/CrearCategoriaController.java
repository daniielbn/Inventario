package controladores.categoria;

import DAO.CategoriaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import modelo.Categoria;

public class CrearCategoriaController {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private RadioButton rbActivada;
    @FXML
    private RadioButton rbDesactivada;

    @FXML
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
            alert.setTitle("Ayuda - Crear Categoría");
            alert.setHeaderText("Ayuda para el módulo de crear Categorías.");
            alert.setContentText("Desde este módulo podrás crear categorías. Los campos a rellenar son:" +
                    "\n- Nombre de la Categoría: Ej.: 'Informática'." +
                    "\n- Descripción de la categoría: Ej.: 'Dispositivos electrónicos'." +
                    "\n- Estado de la categoría, es decir, si se encuentra activada o desactivada." +
                    "\n- Desde el botón de 'Limpiar' puede vacíar todos los campos." +
                    "\n- Desde el botón de 'Volver' puede volver al listado de aulas." +
                    "\n- Por úlitmo, desde el botón de 'Crear' puede crear la categoría con los datos introducidos.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setDirectory(2);
            App.setRoot("categorias", "Sistema de gestión de Categorías.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crear(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            boolean estado = rbActivada.isSelected();
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            Categoria categoria = new Categoria(txtNombre.getText(), txtDescripcion.getText(), estado);
            if (categoriaDAO.create(categoria)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Categoría creada");
                alert.setHeaderText("Categoría creada correctamente.");
                alert.setContentText("Nombre: " + txtNombre.getText() +
                        "\nDescripción: " + txtDescripcion.getText() +
                        "\nEstado: " + (estado ? "Activado" : "Desactivado"));
                alert.showAndWait();
                limpiar(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al crear la categoría");
                alert.setHeaderText("Ha ocurrido un error al crear la categoría.");
                alert.showAndWait();
            }
        }
    }

    private boolean comprobarCampos() {
        if (txtNombre.getText().isEmpty() && txtDescripcion.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    private void limpiar(ActionEvent event) {
        txtNombre.clear();
        txtDescripcion.clear();
        rbActivada.setSelected(true);
        rbDesactivada.setSelected(false);
    }
}
