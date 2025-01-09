package controladores.consultas;

import DAO.MarcajeDAO;
import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Marcaje;
import modelo.Producto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaUnoController {
    private static final ProductoDAO productoDAO = new ProductoDAO();
    private static final MarcajeDAO marcajeDAO = new MarcajeDAO();
    private Producto productoSeleccionado;

    @FXML
    private ComboBox<Long> cbIdProducto;
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

        cbIdProducto.getItems().setAll(obtenerIdProductos());

        cbIdProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (cbIdProducto.getValue() != null) {
                productoSeleccionado = productoDAO.obtenerPorId(cbIdProducto.getValue());
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

    private List<Long> obtenerIdProductos() {
        List<Producto> productos = productoDAO.getTodos();
        List<Long> idProductos = new ArrayList<>();
        for (Producto producto : productos) {
            idProductos.add(producto.getIdProducto());
        }
        return idProductos;
    }

    private void filtrarEntreFechas() {
        if (comprobarFechas() && comprobarInicioYFin()) {
            LocalDate inicio = dateInicio.getValue();
            LocalDate fin = dateFinal.getValue();
            List<Marcaje> marcajes = marcajeDAO.obtenerMarcajesDeProductoEntreFechas(productoSeleccionado, inicio, fin);
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
            alert.setHeaderText("Ayuda - Consulta 1");
            alert.setContentText("En esta ventana puedes consultar los marcajes de un solo producto en un rango de dos fechas." +
                    "\n\nPara ello, selecciona un producto de la lista desplegable y elige dos fechas." +
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
        cbIdProducto.setValue(null);
        dateInicio.setValue(null);
        dateFinal.setValue(null);
        tablaMarcajes.getItems().clear();
    }

    private boolean comprobarLista() {
        if (cbIdProducto.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la selección de producto");
            alert.setContentText("Por favor, selecciona un producto de la lista desplegable.");
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
