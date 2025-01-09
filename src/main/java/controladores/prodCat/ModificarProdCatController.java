package controladores.prodCat;

import DAO.CategoriaDAO;
import DAO.ProductoCategoriaDAO;
import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Categoria;
import modelo.Producto;
import modelo.ProductoCategoria;

import java.util.ArrayList;
import java.util.List;

public class ModificarProdCatController {
    private final static CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final static ProductoDAO productoDAO = new ProductoDAO();
    private final static ProductoCategoriaDAO productoCategoriaDAO = new ProductoCategoriaDAO();
    private Categoria categoriaSeleccionada;
    private Producto productoSeleccionado;
    private ProductoCategoria productoCategoriaSeleccionado;

    @FXML
    private TableView<Categoria> tablaCategorias;
    @FXML
    private TableColumn<Categoria, Long> colIdCategoria;
    @FXML
    private TableColumn<Categoria, String> colNombre;
    @FXML
    private TableColumn<Categoria, String> colDescripcion;
    @FXML
    private TableColumn<Categoria, Boolean> colEstado;

    @FXML
    private ComboBox<Long> cbIdCategorias;
    @FXML
    private ComboBox<Long> cbIdProductos;

    @FXML
    public void initialize() {
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        cargarCategorias();

        tablaCategorias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categoriaSeleccionada = newValue;
            cbIdCategorias.setValue(categoriaSeleccionada.getIdCategoria());
        });

        cbIdCategorias.getItems().setAll(obtenerIdCategorias());
        cbIdProductos.getItems().setAll(obtenerIdProductos());

        cbIdCategorias.setOnAction(event -> {
            categoriaSeleccionada = categoriaDAO.obtenerPorId(cbIdCategorias.getValue());
        });

        cbIdProductos.setOnAction(event -> {
            productoSeleccionado = productoDAO.obtenerPorId(cbIdProductos.getValue());
        });
    }

    private void cargarCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        tablaCategorias.getItems().clear();
        tablaCategorias.getItems().setAll(categorias);
    }

    private List<Long> obtenerIdCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        List<Long> idCategorias = new ArrayList<>();
        for (Categoria categoria : categorias) {
            idCategorias.add(categoria.getIdCategoria());
        }
        return idCategorias;
    }

    private List<Long> obtenerIdProductos() {
        List<Producto> productos = productoDAO.getTodos();
        List<Long> idProductos = new ArrayList<>();
        for (Producto producto : productos) {
            idProductos.add(producto.getIdProducto());
        }
        return idProductos;
    }

    public void limpiar(ActionEvent actionEvent) {
        cbIdCategorias.setValue(null);
        cbIdProductos.setValue(null);
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
            alert.setHeaderText("Ayuda - Modificar relación entre Producto-Categoría");
            alert.setContentText("Desde este módulo podrás modificar las relaciones entre una categoría y los producto." +
                    "\n- Primero, debes seleccionar el id de la Categoría que quieras modificar. Puedes hacerlo seleccionando el elemento en la tabla, o eligiendolo manualmente en la lista " +
                    "desplegable llamada 'ID de la categoría'." +
                    "\n- Posteriormente, debes seleccionar el producto para esa categoría." +
                    "\n- En caso de que el producto se encuentre dentro de la categoria, se modificará, eliminando al mismo de la categoría." +
                    "\n- Por el contrario, si modificas un producto que no esté en dicha categoría, este se añadirá." +
                    "\n- En caso de querer limpiar los campos, pulse el botón de 'Limpiar'" +
                    "\n- Si desea volver a la pagina anterior, debe pulsar el botón de 'Volver'" +
                    "\n\nAdemás, podrás acceder a la accesibilidad de la aplicación y a la ayuda.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void volver(ActionEvent actionEvent) {
        try {
            App.setDirectory(5);
            App.setRoot("prodCat", "Sistema de gestión de la relación entre Producto-Categoría.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificar(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            productoCategoriaSeleccionado = productoCategoriaDAO.getPorIdProductoYIdCategoria(productoSeleccionado.getIdProducto(), categoriaSeleccionada.getIdCategoria());
            if (productoCategoriaSeleccionado != null) {
                if (productoCategoriaDAO.delete(productoCategoriaSeleccionado)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Relación eliminada");
                    alert.setContentText("La relación entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + " ha sido eliminada correctamente.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Ha ocurrido un error al intentar eliminar la relación entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + ".");
                    alert.showAndWait();}
            } else {
                productoCategoriaSeleccionado = new ProductoCategoria();
                productoCategoriaSeleccionado.setProducto(productoSeleccionado);
                productoCategoriaSeleccionado.setCategoria(categoriaSeleccionada);
                if (productoCategoriaDAO.create(productoCategoriaSeleccionado)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Relación creada");
                    alert.setContentText("La relación entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + " ha sido creada correctamente.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Ha ocurrido un error al intentar crear la relación entre el producto con id: " + productoSeleccionado.getIdProducto() + " y la categoría con id: " + categoriaSeleccionada.getIdCategoria() + ".");
                    alert.showAndWait();
                }
            }
        }
    }

    private boolean comprobarCampos() {
        if (cbIdCategorias.getValue() != null && cbIdProductos.getValue() != null) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Debes seleccionar una categoría y un producto para poder modificar la relación.");
            alert.showAndWait();
            return false;
        }
    }
}
