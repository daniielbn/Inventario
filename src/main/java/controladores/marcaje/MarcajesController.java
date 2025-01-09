package controladores.marcaje;

import DAO.MarcajeDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Marcaje;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MarcajesController {
    private final MarcajeDAO marcajeDAO = new MarcajeDAO();
    private Marcaje marcajeSeleccionado;

    @FXML
    private DatePicker dateInicio;
    @FXML
    private DatePicker dateFinal;


    @FXML
    private TableView<Marcaje> tablaMarcajes;
    @FXML
    private TableColumn<Marcaje, Long> colIdMarcaje;
    @FXML
    private TableColumn<Marcaje, Long> colIdAula;
    @FXML
    private TableColumn<Marcaje, Long> colIdProducto;
    @FXML
    private TableColumn<Marcaje, Date> colFecha;
    @FXML
    private TableColumn<Marcaje, Boolean> colTipo;

    @FXML
    private Button buttonLimpiar;


    @FXML
    public void initialize() {
        colIdMarcaje.setCellValueFactory(new PropertyValueFactory<>("idMarcaje"));
        colIdAula.setCellValueFactory(new PropertyValueFactory<>("idAula"));
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cargarMarcajes();

        dateInicio.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (dateInicio.getValue() != null && dateFinal.getValue() != null) {
                if (comprobarInicioYFin()) {
                    filtrarEntreFechas();
                }
            }
        });

        dateFinal.valueProperty().addListener((observable, oldValue, newValue) -> {
            filtrarEntreFechas();
        });

        buttonLimpiar.setOnAction(event ->
                limpiar()
        );

        tablaMarcajes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            marcajeSeleccionado = newValue;
        });
    }

    private void filtrarEntreFechas() {
        if (comprobarFechas() && comprobarInicioYFin()) {
            LocalDate inicio = dateInicio.getValue();
            LocalDate fin = dateFinal.getValue();
            List<Marcaje> marcajes = marcajeDAO.obtenerEntreFechas(inicio, fin);
            tablaMarcajes.getItems().setAll(marcajes);
        }
    }

    private void cargarMarcajes() {
        List<Marcaje> marcajes = marcajeDAO.getTodos();
        tablaMarcajes.getItems().setAll(marcajes);
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
            alert.setTitle("Ayuda");
            alert.setHeaderText("Ayuda - Marcajes");
            alert.setContentText("Desde este módulo podrás gestionar los marcajes. Podrás:" +
                    "\n- Crear marcajes." +
                    "\n- Listar marcajes." +
                    "\n- Modificar marcajes." +
                    "\n- Eliminar marcajes." +
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

    public void crearMarcaje(MouseEvent mouseEvent) {
        try {
            App.setDirectory(3);
            App.setRoot("crearMarcaje", "Sistema de gestión de marcajes - Crear Marcaje.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarMarcaje(MouseEvent mouseEvent) {
        try {
            if (marcajeSeleccionado != null) {
                App.setUserData(marcajeSeleccionado);
                App.setDirectory(3);
                App.setRoot("modificarMarcaje", "Sistema de gestión de marcajes - Modificar Marcaje.");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Debes seleccionar un marcaje para poder modificarlo.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarMarcaje(MouseEvent mouseEvent) {
        try {
            if (marcajeSeleccionado != null) {
                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setTitle("Eliminar marcaje");
                alertConfirmation.setHeaderText("¿Estás seguro de que deseas eliminar el marcaje?");
                alertConfirmation.setContentText("Se eliminará el marcaje seleccionado.");
                alertConfirmation.showAndWait();
                if (alertConfirmation.getResult() != ButtonType.OK) {
                    return;
                } else {
                    if (marcajeDAO.delete(marcajeSeleccionado)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Éxito");
                        alert.setHeaderText("Marcaje eliminado.");
                        alert.setContentText("El marcaje seleccionado ha sido eliminado correctamente.");
                        alert.showAndWait();
                        cargarMarcajes();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error al eliminar el maracje.");
                        alert.setContentText("Ha ocurrido un error al intentar eliminar el marcaje seleccionado.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Debes seleccionar un marcaje para poder modificarlo.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiar() {
        dateInicio.setValue(null);
        dateFinal.setValue(null);
        cargarMarcajes();
    }

    private boolean comprobarFechas() {
        if (dateInicio.getValue() == null || dateFinal.getValue() == null) {
            return false;
        }
        return true;
    }

    private boolean comprobarInicioYFin() {
        if (!comprobarFechas()) {
            return false;
        }
        if (dateInicio.getValue().isAfter(dateFinal.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el rango de fechas");
            alert.setContentText("La fecha de inicio no puede ser posterior a la fecha final. Por favor, corrige el rango de fechas.");
            alert.showAndWait();
            dateInicio.setValue(null);
            dateFinal.setValue(null);
            return false;
        }
        return true;
    }
}
