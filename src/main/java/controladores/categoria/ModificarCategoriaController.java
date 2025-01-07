package controladores.categoria;

import DAO.CategoriaDAO;
import com.example.inventario_hib.App;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Aula;
import modelo.Categoria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModificarCategoriaController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private Categoria categoriaSeleccionada;

    @FXML
    private ComboBox<Long> cbIdCategorias;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private RadioButton rbActivada;
    @FXML
    private RadioButton rbDesactivada;

    @FXML
    private TableView<Categoria> tablaCategorias;
    @FXML
    private TableColumn<Categoria, Long> colIdCategoria;
    @FXML
    private TableColumn<Categoria, String> colNombre;
    @FXML
    private TableColumn<Categoria, String> colDescripcion;
    @FXML
    private TableColumn<Categoria, Boolean> colEstado;

    @FXML // Importamos el botón de limpiar
    private Button buttonLimpiar;

    @FXML
    public void initialize() {
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        cargarCategorias();

        cbIdCategorias.getItems().setAll(obtenerIdCategorias());

        tablaCategorias.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Categoria>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                categoriaSeleccionada = newValue;
                cbIdCategorias.setValue(categoriaSeleccionada.getIdCategoria());
                txtNombre.setText(categoriaSeleccionada.getNombre());
                txtDescripcion.setText(categoriaSeleccionada.getDescripcion());
                if (categoriaSeleccionada.isEstado()) {
                    rbActivada.setSelected(true);
                } else {
                    rbDesactivada.setSelected(true);
                }
            }
        });

        cbIdCategorias.valueProperty().addListener((ChangeListener<Long>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                Categoria categoria = categoriaDAO.obtenerPorId(newValue);
                if (categoria != null) {
                    categoriaSeleccionada = categoria;
                    txtNombre.setText(categoriaSeleccionada.getNombre());
                    txtDescripcion.setText(categoriaSeleccionada.getDescripcion());
                    if (categoriaSeleccionada.isEstado()) {
                        rbActivada.setSelected(true);
                    } else {
                        rbDesactivada.setSelected(true);
                    }
                }
            }
        });

        buttonLimpiar.setOnAction(event ->
                limpiar()
        );
    }

    private void cargarCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        tablaCategorias.getItems().clear();
        tablaCategorias.getItems().setAll(categorias);
    }

    private List<Long> obtenerIdCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        List<Long> idCategorias = new ArrayList<>();
        for (Categoria categoria : categorias) {
            idCategorias.add(categoria.getIdCategoria());
        }
        return idCategorias;
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
                cargarCategorias();
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
        categoriaSeleccionada = null;
        cbIdCategorias.setValue(null);
        tablaCategorias.getSelectionModel().clearSelection();
        txtNombre.clear();
        txtDescripcion.clear();
        rbActivada.setSelected(false);
        rbDesactivada.setSelected(false);
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
