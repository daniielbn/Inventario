package controladores.aula;

import DAO.AulaDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Aula;

public class CrearAulaController {
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
    private Spinner<Integer> spinnerNumeracion1;
    @FXML
    private Spinner<Integer> spinnerNumeracion2;
    @FXML
    private Spinner<Integer> spinnerNumeracion3;

    @FXML // Importamos el botón de limpiar
    private Button buttonLimpiar;

    @FXML
    public void initialize() {
        spinnerIp1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinnerIp2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinnerIp3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));
        spinnerIp4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0));

        spinnerNumeracion1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spinnerNumeracion2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
        spinnerNumeracion3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 25, 0));

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
            alert.setTitle("Ayuda - Crear Aula");
            alert.setHeaderText("Ayuda para el módulo de crear Aulas.");
            alert.setContentText("Desde este módulo podrás crear aulas. Los campos a rellenar son:" +
                    "\n- Descripción del Aula: Ej.: 'Aula DAM'." +
                    "\n- Dirección IP del Aula: Ej.: 192.168.16.1 (cada campo puede variar desde el 0 hasta el 255)" +
                    "\n- Numeración del Aula: Ej.: 1.1.1" +
                    "\n- Desde el botón de 'Limpiar' puede vacíar todos los campos." +
                    "\n- Desde el botón de 'Volver' puede volver al listado de aulas." +
                    "\n- Por úlitmo, desde el botón de 'Crear' puede crear el aula con los datos introducidos.");
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

    public void crear(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            AulaDAO aulaDAO = new AulaDAO();
            String descripcion = txtDescripcion.getText();
            String ip = spinnerIp1.getValue() + "." + spinnerIp2.getValue() + "." + spinnerIp3.getValue() + "." + spinnerIp4.getValue();
            String numeracion = spinnerNumeracion1.getValue() + "." + spinnerNumeracion2.getValue() + "." + spinnerNumeracion3.getValue();
            Aula aula = new Aula(numeracion, descripcion, ip);
            if (aulaDAO.create(aula)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Aula creada");
                alert.setHeaderText("Aula creada correctamente.");
                alert.setContentText("Descripción: " + txtDescripcion.getText() +
                        "\nDirección IP: " + spinnerIp1.getValue() + "." + spinnerIp2.getValue() + "." + spinnerIp3.getValue() + "." + spinnerIp4.getValue() +
                        "\nNumeración: " + spinnerNumeracion1.getValue() + "." + spinnerNumeracion2.getValue() + "." + spinnerNumeracion3.getValue());
                alert.showAndWait();
                limpiar(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al crear el Aula");
                alert.setHeaderText("Error al crear el Aula.");
                alert.setContentText("No se ha podido crear el aula. Por favor, inténtelo de nuevo.");
                alert.showAndWait();
            }
        }
    }

    private boolean comprobarCampos() {
        if (comprobarDescripcion() && comprobarIp() && comprobarNumeracion()) {
            return true;
        }
        return false;
    }

    private boolean comprobarDescripcion() {
        if (!txtDescripcion.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean comprobarIp() {
        if (spinnerIp1.getValue() != null && spinnerIp2.getValue() != null && spinnerIp3.getValue() != null && spinnerIp4.getValue() != null) {
            String ip = spinnerIp1.getValue() + "." + spinnerIp2.getValue() + "." + spinnerIp3.getValue() + "." + spinnerIp4.getValue();
            if (ip.matches("^(?:\\d{1,3}\\.){3}\\d{1,3}$")) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean comprobarNumeracion() {
        if (spinnerNumeracion1.getValue() != null && spinnerNumeracion2.getValue() != null && spinnerNumeracion3.getValue() != null) {
            String numeracion = spinnerNumeracion1.getValue() + "." + spinnerNumeracion2.getValue() + "." + spinnerNumeracion3.getValue();
            if (numeracion.matches("^\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$")) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void limpiar(ActionEvent actionEvent) {
        txtDescripcion.clear();

        spinnerIp1.getValueFactory().setValue(0);
        spinnerIp2.getValueFactory().setValue(0);
        spinnerIp3.getValueFactory().setValue(0);
        spinnerIp4.getValueFactory().setValue(0);

        spinnerNumeracion1.getValueFactory().setValue(0);
        spinnerNumeracion2.getValueFactory().setValue(0);
        spinnerNumeracion3.getValueFactory().setValue(0);
    }
}
