package controladores.marcaje;

import DAO.MarcajeDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Marcaje;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ListarMarcajesController {
    private final MarcajeDAO marcajeDAO = new MarcajeDAO();

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
            filtrarEntreFechas();
        });

        dateFinal.valueProperty().addListener((observable, oldValue, newValue) -> {
            filtrarEntreFechas();
        });

        buttonLimpiar.setOnAction(event ->
                limpiar(event)
        );
    }

    private void filtrarEntreFechas() {
        if (comprobarFechas() && comprobarInicioYFin()) {
            LocalDate inicio = dateInicio.getValue();
            LocalDate fin = dateFinal.getValue();
            List<Marcaje> marcajes = marcajeDAO.getEntreFechas(inicio, fin);
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
            alert.setContentText("Esta es la ventana de listado de marcajes. Aquí se mostrarán todas los marcajes disponibles en el sistema.\n" +
                    "Existe la opción de filtrar los resultados mediante los campos de búsqueda.\n" +
                    "El primer campo pertenece a la fecha de inicio por la que se quiere comenzar a filtrar.\n" +
                    "El segundo campo pertenece a la fecha de final para la que se quiere terminar de filtar.\n" +
                    "El resultado serán los marcajes que tenga su fecha dentro del intervalo de fechas que hayas marcado.\n" +
                    "Para limpiar los campos, debe pulsar el botón 'Limpiar'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setDirectory(3);
            App.setRoot("marcajes", "Sistema de gestión de Marcajes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiar(ActionEvent actionEvent) {
        dateInicio.setValue(null);
        dateFinal.setValue(null);
        cargarMarcajes();
    }

    private boolean comprobarFechas() {
        if (dateInicio.getValue() == null || dateFinal.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Debes seleccionar una fecha de inicio y una fecha final para poder filtrar los marcajes.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean comprobarInicioYFin() {
        if (dateInicio.getValue().isAfter(dateFinal.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("La fecha de inicio no puede ser posterior a la fecha final.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
