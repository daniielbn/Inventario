package controladores.aula;

import DAO.AulaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Aula;

import java.io.IOException;
import java.util.List;

public class ListarAulasController {
    private final AulaDAO aulaDAO = new AulaDAO();
    @FXML
    private TextField txtNumeracion;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TableView<Aula> tablaAulas;
    @FXML
    private TableColumn<Aula, Long> colIdAula;
    @FXML
    private TableColumn<Aula, String> colNumeracion;
    @FXML
    private TableColumn<Aula, String> colDescripcion;
    @FXML
    private TableColumn<Aula, String> colIp;
    @FXML
    private Button buttonLimpiar;

    @FXML
    public void initialize() {
        colIdAula.setCellValueFactory(new PropertyValueFactory<>("idAula"));
        colNumeracion.setCellValueFactory(new PropertyValueFactory<>("numeracion"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colIp.setCellValueFactory(new PropertyValueFactory<>("direccionIp"));
        cargarAulas();

        txtNumeracion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarNumeracion();
        });

        txtDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarDescripcion();
        });

        buttonLimpiar.setOnAction(event ->
                limpiar(event)
        );
    }

    private void cargarAulas() {
        List<Aula> aulas = aulaDAO.getTodos();
        tablaAulas.getItems().setAll(aulas);
    }

    private void filtrarNumeracion() {
        if (!txtNumeracion.getText().isEmpty()) {
            List<Aula> aulas = aulaDAO.getPorNumeracion(txtNumeracion.getText());
            tablaAulas.getItems().clear();
            tablaAulas.getItems().setAll(aulas);
        } else {
            cargarAulas();
        }
    }

    private void filtrarDescripcion() {
        if (!txtDescripcion.getText().isEmpty()) {
            List<Aula> aulas = aulaDAO.getPorDescripcion(txtDescripcion.getText());
            tablaAulas.getItems().clear();
            tablaAulas.getItems().setAll(aulas);
        } else {
            cargarAulas();
        }
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
            alert.setHeaderText("Ayuda");
            alert.setContentText("Esta es la ventana de listado de aulas. Aquí se mostrarán todas las aulas disponibles en el sistema.\n" +
                        "Existe la opción de filtrar los resultados mediante los campos de búsqueda.\n" +
                        "El primer campo pertenece a la numeración del Aula, es decir, '2.2.2'.\n" +
                        "El segundo campo pertenece a la descripción del Aula\n" +
                        "Para limpiar los campos, debe pulsar el botón 'Limpiar'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setDirectory(1);
            App.setRoot("aulas", "Sistema de gestión de Aulas.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiar(ActionEvent actionEvent) {
        txtNumeracion.clear();
        txtDescripcion.clear();
        cargarAulas();
    }
}
