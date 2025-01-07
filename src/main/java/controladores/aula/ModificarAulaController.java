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

public class ModificarAulaController {
    private final AulaDAO aulaDAO = new AulaDAO();
    private Aula aulaSeleccionada;
    @FXML
    private ComboBox<Long> cbIdAula;

    @FXML
    private Spinner<Integer> spinnerNum1;
    @FXML
    private Spinner<Integer> spinnerNum2;
    @FXML
    private Spinner<Integer> spinnerNum3;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private Spinner<Integer> spinnerIp1;
    @FXML
    private Spinner<Integer> spinnerIp2;
    @FXML
    private Spinner<Integer> spinnerIp3;
    @FXML
    private Spinner<Integer> spinnerIp4;

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

        spinnerIp1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinnerIp2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinnerIp3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinnerIp4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));

        spinnerNum1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spinnerNum2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spinnerNum3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0));

        tablaAulas.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Aula>) (observable, oldValue, newValue) -> {
            if (newValue != null) {
                aulaSeleccionada = newValue;
                cbIdAula.setValue(aulaSeleccionada.getIdAula());
                txtDescripcion.setText(aulaSeleccionada.getDescripcion());
                String[] ip = aulaSeleccionada.getDireccionIp().split("\\.");
                establecerSpinnerIp(ip);
                String[] numeracion = aulaSeleccionada.getNumeracion().split("\\.");
                establecerSpinnerNumeracion(numeracion);
            }
        });

        cbIdAula.getItems().setAll(obtenerIdAulas());

        cbIdAula.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                aulaSeleccionada = aulaDAO.obtenerPorId(newValue);
                tablaAulas.getSelectionModel().select(aulaSeleccionada);
                txtDescripcion.setText(aulaSeleccionada.getDescripcion());
                String[] ip = aulaSeleccionada.getDireccionIp().split("\\.");
                establecerSpinnerIp(ip);
                String[] numeracion = aulaSeleccionada.getNumeracion().split("\\.");
                establecerSpinnerNumeracion(numeracion);
            }
        });

        buttonLimpiar.setOnAction(event -> {
            limpiar(event);
        });
    }

    private void cargarAulas() {
        List<Aula> aulas = aulaDAO.getTodos();
        tablaAulas.getItems().clear();
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
            alert.setHeaderText("Ayuda - Modificar aulas");
            alert.setContentText("Esta es la ventana de modificado de aulas. Aquí se mostrarán todas las aulas disponibles en el sistema para las cuales podrás modificar cualquier dato (menos su ID).\n" +
                    "Puedes seleccionar el registro en la tabla que deseas modificar, o eligiendo si ID en la caja desplegable de 'Id de Aula'.\n" +
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
            App.setDirectory(1);
            App.setRoot("aulas", "Sistema de gestión de Aulas.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiar(ActionEvent actionEvent) {
        aulaSeleccionada = null;
        cbIdAula.setValue(null);
        txtDescripcion.clear();
        spinnerIp1.getValueFactory().setValue(0);
        spinnerIp2.getValueFactory().setValue(0);
        spinnerIp3.getValueFactory().setValue(0);
        spinnerIp4.getValueFactory().setValue(0);
        spinnerNum1.getValueFactory().setValue(0);
        spinnerNum2.getValueFactory().setValue(0);
        spinnerNum3.getValueFactory().setValue(0);
        tablaAulas.getSelectionModel().clearSelection();
    }

    public void modificar(ActionEvent actionEvent) {
        if (comprobarCampos() && comprobarCamposModificados()) {
            aulaSeleccionada.setDescripcion(txtDescripcion.getText());
            aulaSeleccionada.setDireccionIp(getDireccionIp());
            aulaSeleccionada.setNumeracion(getNumeracion());
            if (aulaDAO.update(aulaSeleccionada)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Aula modfiicada");
                alert.setHeaderText("Aula modificada correctamente.");
                alert.setContentText("Descripción: " + txtDescripcion.getText() +
                        "\nDirección IP: " + getDireccionIp() +
                        "\nNumeración: " + spinnerNum1.getValue() + "." + spinnerNum2.getValue() + "." + spinnerNum3.getValue());
                alert.showAndWait();
                cargarAulas();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modificar Aula");
                alert.setHeaderText("Error al modificar el aula.");
                alert.showAndWait();
            }
        }
    }

    private void establecerSpinnerIp(String[] campos) {
        spinnerIp1.getValueFactory().setValue(Integer.parseInt(campos[0]));
        spinnerIp2.getValueFactory().setValue(Integer.parseInt(campos[1]));
        spinnerIp3.getValueFactory().setValue(Integer.parseInt(campos[2]));
        spinnerIp4.getValueFactory().setValue(Integer.parseInt(campos[3]));
    }
    private void establecerSpinnerNumeracion(String[] campos) {
        spinnerNum1.getValueFactory().setValue(Integer.parseInt(campos[0]));
        spinnerNum2.getValueFactory().setValue(Integer.parseInt(campos[1]));
        spinnerNum3.getValueFactory().setValue(Integer.parseInt(campos[2]));
    }

    private List<Long> obtenerIdAulas() {
        List<Long> ids = new ArrayList<>();
        for (Aula aula : tablaAulas.getItems()) {
            ids.add(aula.getIdAula());
        }
        return ids;
    }

    private boolean comprobarCampos() {
        if (!txtDescripcion.getText().isEmpty() && comprobarSpinnerIp() && comprobarSpinnerNumeracion()) {
            return true;
        }
        return false;
    }

    private boolean comprobarSpinnerIp() {
        if (spinnerIp1.getValue() != null && spinnerIp2.getValue() != null && spinnerIp3.getValue() != null && spinnerIp4.getValue() != null) {
            String ip = getDireccionIp();
            if (ip.matches("^(?:\\d{1,3}\\.){3}\\d{1,3}$")) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean comprobarSpinnerNumeracion() {
        if (spinnerNum1.getValue() != null && spinnerNum2.getValue() != null && spinnerNum3.getValue() != null) {
            String numeracion = getNumeracion();
            if (numeracion.matches("^\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$")) {
                return true;
            }
            return false;
        }
        return false;
    }

    private String getNumeracion() {
        return spinnerNum1.getValue() + "." + spinnerNum2.getValue() + "." + spinnerNum3.getValue();
    }

    private String getDireccionIp() {
        return spinnerIp1.getValue() + "." + spinnerIp2.getValue() + "." + spinnerIp3.getValue() + "." + spinnerIp4.getValue();
    }

    private boolean comprobarCamposModificados() {
        if (aulaSeleccionada.getDescripcion().equals(txtDescripcion.getText()) &&
                aulaSeleccionada.getDireccionIp().equals(getDireccionIp()) &&
                aulaSeleccionada.getNumeracion().equals(getNumeracion())) {
            return false;
        }
        return true;
    }
}
