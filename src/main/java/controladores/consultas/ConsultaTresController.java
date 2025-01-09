package controladores.consultas;

import DAO.AulaDAO;
import DAO.MarcajeDAO;
import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Aula;
import modelo.Marcaje;
import modelo.Producto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaTresController {
    private static final AulaDAO aulaDAO = new AulaDAO();
    private static final ProductoDAO productoDAO = new ProductoDAO();
    private static final MarcajeDAO marcajeDAO = new MarcajeDAO();

    private Aula aulaSeleccionada;
    private Producto productoSeleccionado;

    @FXML
    private ComboBox<Long> cbIdAula;
    @FXML
    private ComboBox<Long> cbIdProducto;

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
        cbIdProducto.getItems().setAll(obtenerIdProductos());

        cbIdAula.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (cbIdAula.getValue() != null) {
                aulaSeleccionada = aulaDAO.obtenerPorId(cbIdAula.getValue());
            }
            cargarDatos();
        });

        cbIdProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (cbIdProducto.getValue() != null) {
                productoSeleccionado = productoDAO.obtenerPorId(cbIdProducto.getValue());
            }
            cargarDatos();
        });

        buttonLimpiar.setOnAction(event ->
                limpiar()
        );
    }

    private List<Long> obtenerIdAulas() {
        List<Aula> aulas = aulaDAO.getTodos();
        List<Long> idAulas = new ArrayList<>();
        for (Aula aula : aulas) {
            idAulas.add(aula.getIdAula());
        }
        return idAulas;
    }

    private List<Long> obtenerIdProductos() {
        List<Producto> productos = productoDAO.getTodos();
        List<Long> idProductos = new ArrayList<>();
        for (Producto producto : productos) {
            idProductos.add(producto.getIdProducto());
        }
        return idProductos;
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
            alert.setHeaderText("Ayuda - Consulta 3");
            alert.setContentText("En esta ventana puedes consultar los marcajes de una sola aula y de un solo producto." +
                    "\n\nPara ello, selecciona un aula de la lista desplegable de todas las aulas." +
                    "\nTambién, debes seleccionar un producto de la lista desplegable de todos los productos." +
                    "\nSi lo desea, puede limpiar los campos utilizando el botón de 'Limpiar'." +
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
        cbIdAula.setValue(null);
        tablaMarcajes.getItems().clear();
    }

    private void cargarDatos() {
        if (aulaSeleccionada != null && productoSeleccionado != null) {
            List<Marcaje> marcajes = marcajeDAO.obtenerMarcajesAulaYProducto(aulaSeleccionada, productoSeleccionado);
            tablaMarcajes.getItems().setAll(marcajes);
        }
    }
}
