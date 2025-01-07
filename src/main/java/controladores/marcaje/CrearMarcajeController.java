package controladores.marcaje;

import DAO.AulaDAO;
import DAO.MarcajeDAO;
import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import modelo.Aula;
import modelo.Marcaje;
import modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class CrearMarcajeController {
    private final AulaDAO aulaDAO = new AulaDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final MarcajeDAO marcajeDAO = new MarcajeDAO();
    @FXML
    private ComboBox<Long> cbIdAulas;
    @FXML
    private ComboBox<Long> cbIdProductos;
    @FXML
    private RadioButton rbEntrada;
    @FXML
    private RadioButton rbSalida;

    @FXML
    public void initialize() {
        cbIdAulas.getItems().setAll(obtenerIdAulas());
        cbIdProductos.getItems().setAll(obtenerIdProductos());
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
            App.setRoot("accesibilidad", "Accesibilidad");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirAyuda(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda - Crear Marcaje");
            alert.setHeaderText("Ayuda para el módulo de crear Marcaje.");
            alert.setContentText("Desde este módulo podrás crear marcajes. Los campos a rellenar son:" +
                    "\n- ID del Aula para el marcaje. Debes seleccionar un ID de la lista desplegable junto al texto 'ID del Aula'" +
                    "\n- ID del Producto para el marcaje. Debes seleccionar un ID de la lista desplegable junto al texto 'ID del Producto'" +
                    "\n- Tipo del marcaje, es decir, si es de entrada o de salida." +
                    "\n- Desde el botón de 'Limpiar' puede vacíar todos los campos." +
                    "\n- Desde el botón de 'Volver' puede volver al listado de aulas." +
                    "\n- Por úlitmo, desde el botón de 'Crear' puede crear el marcaje con los datos introducidos.");
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

    public void crear(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            Marcaje marcaje = new Marcaje();
            marcaje.setIdAula(aulaDAO.obtenerPorId(cbIdAulas.getValue()));
            marcaje.setIdProducto(productoDAO.obtenerPorId(cbIdProductos.getValue()));
            marcaje.setTipo(rbEntrada.isSelected());
            if (marcajeDAO.create(marcaje)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Marcaje Creado");
                alert.setHeaderText("Marcaje creado correctamente.");
                alert.setContentText("ID del Aula: " + cbIdAulas.getValue() +
                        "\nID del Producto: " + cbIdProductos.getValue() +
                        "\nTipo: " + (rbEntrada.isSelected() ? "Entrada" : "Salida"));
                alert.showAndWait();
                limpiar();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al crear el marcaje");
                alert.setHeaderText("Ha ocurrido un error al crear el marcaje.");
                alert.showAndWait();
            }
        }
    }

    private void limpiar() {
        cbIdAulas.getSelectionModel().clearSelection();
        cbIdProductos.getSelectionModel().clearSelection();
        rbEntrada.setSelected(false);
        rbSalida.setSelected(false);
    }

    private boolean comprobarCampos() {
        if (cbIdAulas.getSelectionModel().isEmpty() || cbIdProductos.getSelectionModel().isEmpty() || (!rbEntrada.isSelected() && !rbSalida.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error - Crear Marcaje");
            alert.setHeaderText("Error al crear el marcaje.");
            alert.setContentText("Debes rellenar todos los campos para poder crear el marcaje.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
