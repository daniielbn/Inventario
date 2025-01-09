package controladores.aula;

import DAO.AulaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Aula;

import java.util.List;

public class AulasController {
    private final AulaDAO aulaDAO = new AulaDAO();
    private Aula aulaSeleccionada;

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

    @FXML // Importamos el botón de limpiar
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

        tablaAulas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            aulaSeleccionada = newValue;
        });

        buttonLimpiar.setOnAction(event ->
                limpiar()
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

    public void crearAula(MouseEvent mouseEvent) {
        try {
            App.setDirectory(1);
            App.setRoot("crearAula", "Sistema de gestión de Aulas - Crear aulas.");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void modificarAula(MouseEvent mouseEvent) {
        try {
            if (aulaSeleccionada != null) {
                App.setUserData(aulaSeleccionada);
                App.setDirectory(1);
                App.setRoot("modificarAula", "Sistema de gestión de Aulas - Modificar aulas.");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Debes seleccionar un aula para poder modificarla.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarAula(MouseEvent mouseEvent) {
        try {
            if (aulaSeleccionada != null) {
                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setTitle("Eliminar aula");
                alertConfirmation.setHeaderText("¿Estás seguro de que deseas eliminar el aula?");
                alertConfirmation.setContentText("Se eliminará el aula seleccionada.");
                alertConfirmation.showAndWait();
                if (alertConfirmation.getResult() != ButtonType.OK) {
                    return;
                } else {
                    if (aulaDAO.delete(aulaSeleccionada)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito");
                        alert.setHeaderText("Aula eliminada.");
                        alert.setContentText("El aula seleccionada ha sido eliminada correctamente.");
                        alert.showAndWait();
                        cargarAulas();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error al eliminar el aula.");
                        alert.setContentText("Ha ocurrido un error al intentar eliminar el aula seleccionada.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Debes seleccionar un aula para poder modificarla.");
                alert.showAndWait();
            }
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

    private void limpiar() {
        txtNumeracion.clear();
        txtDescripcion.clear();
        cargarAulas();
    }
}
