package controladores.consultas;

import DAO.AulaDAO;
import DAO.MarcajeDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Aula;
import modelo.Marcaje;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaDosController {
    private static final AulaDAO aulaDAO = new AulaDAO();
    private static final MarcajeDAO marcajeDAO = new MarcajeDAO();
    private Aula aulaSeleccionada;

    @FXML
    private ComboBox<Long> cbIdAula;
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

        cbIdAula.getItems().setAll(obtenerIdAulas());

        cbIdAula.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (cbIdAula.getValue() != null) {
                aulaSeleccionada = aulaDAO.obtenerPorId(cbIdAula.getValue());
                filtrarEntreFechas();
            }
        });

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
    }

    private List<Long> obtenerIdAulas() {
        List<Aula> productos = aulaDAO.getTodos();
        List<Long> idAulas = new ArrayList<>();
        for (Aula aula : productos) {
            idAulas.add(aula.getIdAula());
        }
        return idAulas;
    }

    private void filtrarEntreFechas() {
        if (comprobarFechas() && comprobarInicioYFin()) {
            LocalDate inicio = dateInicio.getValue();
            LocalDate fin = dateFinal.getValue();
            List<Marcaje> marcajes = marcajeDAO.obtenerMarcajesDeAulaEntreFechas(aulaSeleccionada, inicio, fin);
            tablaMarcajes.getItems().setAll(marcajes);
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
            alert.setHeaderText("Ayuda - Consulta 2");
            alert.setContentText("En esta ventana puedes consultar los marcajes de una sola aula en un rango de dos fechas." +
                    "\n\nPara ello, selecciona un aula de la lista desplegable y elige dos fechas." +
                    "\nPuedes pulsar el botón de 'Limpiar' si deseas vacías los campos." +
                    "\nPor último, si lo deseas, puedes pulsar el botón de 'Volver' para volver a la página inicial.");
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
        cbIdAula.setValue(null);
        dateInicio.setValue(null);
        dateFinal.setValue(null);
        tablaMarcajes.getItems().clear();
    }

    private boolean comprobarLista() {
        if (cbIdAula.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la selección de aula");
            alert.setContentText("Por favor, selecciona un aula de la lista desplegable.");
            alert.showAndWait();
            return false;
        }
        return true;
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
