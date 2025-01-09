package controladores.categoria;

import DAO.CategoriaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Categoria;
import java.io.IOException;


public class ModificarCategoriaController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private Categoria categoriaSeleccionada;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private RadioButton rbActivada;
    @FXML
    private RadioButton rbDesactivada;

    @FXML // Importamos el botón de limpiar
    private Button buttonLimpiar;

    @FXML
    public void initialize() {
        categoriaSeleccionada = (Categoria) App.getUserData();

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
            alert.setHeaderText("Ayuda - Modificar categorías");
            alert.setContentText("Esta es la ventana de modificado de categorías. Aquí se mostrarán todas las categorías disponibles en el sistema para las cuales podrás modificar cualquier dato (menos su ID).\n" +
                    "Puedes seleccionar el registro en la tabla que deseas modificar, o eligiendo si ID en la caja desplegable de 'Id de la Categoría'.\n" +
                    "Una vez selecciones el registro que deseas modificar, se autocompletarán los campos con los datos del mismo. Solo tienes que modificar lo que desees y pulsar 'Modificar'.\n" +
                    "Para limpiar los campos, debe pulsar el botón 'Limpiar'." +
                    "Para volver al menú principal, pulse 'Volver'.");
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

    public void modificar(ActionEvent actionEvent) {
        if (comprobarCampos() && comprobarCamposModificados()) {
            categoriaSeleccionada.setNombre(txtNombre.getText());
            categoriaSeleccionada.setDescripcion(txtDescripcion.getText());
            categoriaSeleccionada.setEstado(rbActivada.isSelected());
            if (categoriaDAO.update(categoriaSeleccionada)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Categoría modificada");
                alert.setContentText("Nombre: " + categoriaSeleccionada.getNombre() +
                        "\nDescripción: " + categoriaSeleccionada.getDescripcion() +
                        "\nEstado: " + (categoriaSeleccionada.isEstado() ? "Activado" : "Desactivado"));
                alert.showAndWait();
                limpiar();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al modificar la categoría");
                alert.setContentText("Ha ocurrido un error al modificar la categoría. Por favor, inténtelo de nuevo.");
                alert.showAndWait();
            }
        }
    }

    private void limpiar() {
        txtNombre.setText(categoriaSeleccionada.getNombre());
        txtDescripcion.setText(categoriaSeleccionada.getDescripcion());
        rbActivada.setSelected(categoriaSeleccionada.isEstado());
        rbDesactivada.setSelected(!categoriaSeleccionada.isEstado());
    }

    private boolean comprobarCampos() {
        if (txtNombre.getText().isEmpty() && txtDescripcion.getText().isEmpty() && (!rbActivada.isSelected() && !rbDesactivada.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al modificar la categoría");
            alert.setContentText("Debes rellenar, al menos, un campo.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean comprobarCamposModificados() {
        if (txtNombre.getText().equals(categoriaSeleccionada.getNombre()) && txtDescripcion.getText().equals(categoriaSeleccionada.getDescripcion()) && ((rbActivada.isSelected() && categoriaSeleccionada.isEstado()) || (rbDesactivada.isSelected() && !categoriaSeleccionada.isEstado()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al modificar la categoría");
            alert.setContentText("No has modificado ningún campo.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
