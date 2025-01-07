package controladores.aula;

import DAO.AulaDAO;
import com.example.inventario_hib.App;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Aula;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EliminarAulasController {
    private final AulaDAO aulaDAO = new AulaDAO();
    private Aula aulaSeleccionada;
    @FXML
    private ComboBox<Long> cbIdAula;
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

        tablaAulas.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Aula>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                aulaSeleccionada = newValue;
                cbIdAula.setValue(aulaSeleccionada.getIdAula());
            }
        });

        cbIdAula.getItems().setAll(obtenerIdAulas());

        cbIdAula.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                aulaSeleccionada = aulaDAO.obtenerPorId(newValue);
                tablaAulas.getSelectionModel().select(aulaSeleccionada);
            }
        });

        buttonLimpiar.setOnAction(event ->
                limpiar(event)
        );
    }

    private void cargarAulas() {
        List<Aula> aulas = aulaDAO.getTodos();
        tablaAulas.getItems().setAll(aulas);
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
            alert.setHeaderText("Ayuda - Eliminar aula");
            alert.setContentText("Esta es la ventana de eliminar aulas. Aquí se mostrarán todas las aulas disponibles en el sistema.\n" +
                    "Solo debes seleccionar, ya puede ser en la tabla o en la lista desplegable, el aula que se quiere eliminar.\n" +
                    "Deberás darle al botón de eliminar, y volver a confirmar una segunda vez para evitar eliminados accidentales.\n" +
                    "Para limpiar los campos, debe pulsar el botón 'Limpiar'.\n" +
                    "Por último, si desea volver solo debe pulsar en 'Volver'.");
            alert.showAndWait();
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

    public void eliminar(ActionEvent actionEvent) {
        if (aulaSeleccionada != null) {
            Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmacion.setTitle("Confirmación");
            alertConfirmacion.setHeaderText("¿Estás seguro de que deseas eliminar el aula?");
            alertConfirmacion.setContentText("Se eliminará el aula seleccionada.");
            if (alertConfirmacion.getResult() != ButtonType.OK) {
                return;
            } else {
                if (aulaDAO.delete(aulaSeleccionada)) {
                    cargarAulas();
                    cbIdAula.getItems().setAll(obtenerIdAulas());
                    aulaSeleccionada = null;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito");
                    alert.setHeaderText("Aula eliminada.");
                    alert.setContentText("El aula seleccionada ha sido eliminada correctamente.");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error al eliminar el aula.");
                    alert.setContentText("No se ha podido eliminar el aula seleccionada.");
                    alert.showAndWait();
                }
            }
        }
    }

    public void limpiar(ActionEvent actionEvent) {
        aulaSeleccionada = null;
        cbIdAula.setValue(null);
        tablaAulas.getSelectionModel().clearSelection();
    }

    private List<Long> obtenerIdAulas() {
        List<Long> ids = new ArrayList<>();
        for (Aula aula : tablaAulas.getItems()) {
            ids.add(aula.getIdAula());
        }
        return ids;
    }
}
