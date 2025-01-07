package controladores.marcaje;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModificarMarcajeController {
    private final AulaDAO aulaDAO = new AulaDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final MarcajeDAO marcajeDAO = new MarcajeDAO();

    private Marcaje marcajeSeleccionado;

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
    private ComboBox<Long> cbIdAulas;
    @FXML
    private ComboBox<Long> cbIdProductos;
    @FXML
    private ComboBox<Long> cbIdMarcajes;
    @FXML
    private RadioButton rbEntrada;
    @FXML
    private RadioButton rbSalida;
    @FXML
    private DatePicker dateFecha;



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

        cbIdAulas.getItems().setAll(obtenerIdAulas());
        cbIdProductos.getItems().setAll(obtenerIdProductos());
        cbIdMarcajes.getItems().setAll(obtenerIdMarcajes());

        tablaMarcajes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                marcajeSeleccionado = newValue;
                cbIdMarcajes.setValue(marcajeSeleccionado.getIdMarcaje());
                cbIdAulas.setValue(marcajeSeleccionado.getIdAula().getIdAula());
                cbIdProductos.setValue(marcajeSeleccionado.getIdProducto().getIdProducto());
                dateFecha.setValue(marcajeSeleccionado.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                if (marcajeSeleccionado.isTipo()) {
                    rbEntrada.setSelected(true);
                } else {
                    rbSalida.setSelected(true);
                }
            }
        });

        cbIdMarcajes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Marcaje marcaje = marcajeDAO.obtenerPorId(newValue);
                if (marcaje != null) {
                    marcajeSeleccionado = marcaje;
                    tablaMarcajes.getSelectionModel().select(marcaje);
                    cbIdAulas.setValue(marcajeSeleccionado.getIdAula().getIdAula());
                    cbIdProductos.setValue(marcajeSeleccionado.getIdProducto().getIdProducto());
                    dateFecha.setValue(marcajeSeleccionado.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                    if (marcajeSeleccionado.isTipo()) {
                        rbEntrada.setSelected(true);
                    } else {
                        rbSalida.setSelected(true);
                    }
                }
            }
        });

        buttonLimpiar.setOnAction(event ->
                limpiar()
        );
    }

    private void cargarMarcajes() {
        List<Marcaje> marcajes = marcajeDAO.getTodos();
        tablaMarcajes.getItems().clear();
        tablaMarcajes.getItems().setAll(marcajes);
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

    private List<Long> obtenerIdMarcajes() {
        List<Marcaje> marcajes = marcajeDAO.getTodos();
        List<Long> idMarcajes = new ArrayList<>();
        for (Marcaje marcaje : marcajes) {
            idMarcajes.add(marcaje.getIdMarcaje());
        }
        return idMarcajes;
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
            alert.setHeaderText("Ayuda - Modificar categorías");
            alert.setContentText("Esta es la ventana de modificado de categorías. Aquí se mostrarán todas las categorías disponibles en el sistema para las cuales podrás modificar cualquier dato (menos su ID).\n" +
                    "Puedes seleccionar el registro en la tabla que deseas modificar, o eligiendo si ID en la caja desplegable de 'Id de la Categoría'.\n" +
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
            App.setDirectory(3);
            App.setRoot("marcajes", "Sistema de gestión de Marcajes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificar(ActionEvent actionEvent) {
        if (comprobarCampos() && comprobarCamposModificados()) {
            Marcaje marcaje = new Marcaje();
            marcaje.setIdMarcaje(cbIdMarcajes.getValue());
            marcaje.setIdAula(new Aula(cbIdAulas.getValue()));
            marcaje.setIdProducto(new Producto(cbIdProductos.getValue()));
            marcaje.setFecha(java.sql.Date.valueOf(dateFecha.getValue()));
            marcaje.setTipo(rbEntrada.isSelected());
            if (marcajeDAO.update(marcaje)) {
                cargarMarcajes();
                limpiar();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Marcaje modificado");
                alert.setContentText("ID del Aula: " + marcaje.getIdAula().getIdAula() + "\n" +
                        "ID del Producto: " + marcaje.getIdProducto().getIdProducto() + "\n" +
                        "Fecha: " + marcaje.getFecha() + "\n" +
                        "Tipo: " + (marcaje.isTipo() ? "Entrada" : "Salida"));
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error - Marcaje no modificado");
                alert.setContentText("Ha ocurrido un error al modificar el marcaje. Por favor, inténtelo de nuevo.");
                alert.showAndWait();
            }
        }
    }

    private void limpiar() {
        marcajeSeleccionado = null;
        cbIdAulas.setValue(null);
        cbIdProductos.setValue(null);
        cbIdMarcajes.setValue(null);
        dateFecha.setValue(null);
        rbEntrada.setSelected(false);
        rbSalida.setSelected(false);
    }

    private boolean comprobarCampos() {
        if (cbIdAulas.getValue() == null || cbIdProductos.getValue() == null || dateFecha.getValue() == null || (!rbEntrada.isSelected() && !rbSalida.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error - Campos vacíos");
            alert.setContentText("Debes rellenar todos los campos para poder modificar un marcaje.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean comprobarCamposModificados() {
        if (marcajeSeleccionado.getIdAula().getIdAula() == cbIdAulas.getValue() && marcajeSeleccionado.getIdProducto().getIdProducto() == cbIdProductos.getValue() && marcajeSeleccionado.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().equals(dateFecha.getValue()) && ((marcajeSeleccionado.isTipo() && rbEntrada.isSelected()) || (!marcajeSeleccionado.isTipo() && rbSalida.isSelected()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error - Campos no modificados");
            alert.setContentText("Debes modificar al menos un campo para poder modificar un marcaje.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
