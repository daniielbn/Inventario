package controladores.categoria;

import DAO.CategoriaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Categoria;
import java.util.List;

public class ListarCategoriasController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;

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

        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarNombre();
        });

        txtDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarDescripcion();
        });

        buttonLimpiar.setOnAction(event -> {
            limpiar(event);
        });
    }

    private void cargarCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        tablaCategorias.getItems().setAll(categorias);
    }

    private void filtrarNombre() {
        if (!txtNombre.getText().isEmpty()) {
            List<Categoria> categorias = categoriaDAO.getPorNombre(txtNombre.getText());
            tablaCategorias.getItems().clear();
            tablaCategorias.getItems().setAll(categorias);
        } else {
            cargarCategorias();
        }
    }

    private void filtrarDescripcion() {
        if (!txtDescripcion.getText().isEmpty()) {
            List<Categoria> categorias = categoriaDAO.getPorDescripcion(txtDescripcion.getText());
            tablaCategorias.getItems().clear();
            tablaCategorias.getItems().setAll(categorias);
        } else {
            cargarCategorias();
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
            alert.setContentText("Esta es la ventana de listado de categorias. Aquí se mostrarán todas las categorías disponibles en el sistema.\n" +
                    "Existe la opción de filtrar los resultados mediante los campos de búsqueda.\n" +
                    "El primer campo pertenece al nombre de la Categoría, es decir, 'Electrónico'.\n" +
                    "El segundo campo pertenece a la descripción de la Categoría\n" +
                    "Para limpiar los campos, debe pulsar el botón 'Limpiar'");
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

    public void limpiar(ActionEvent actionEvent) {
        txtNombre.clear();
        txtDescripcion.clear();
        cargarCategorias();
    }
}
