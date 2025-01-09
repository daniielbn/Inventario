package controladores.prodCat;

import DAO.CategoriaDAO;
import DAO.ProductoCategoriaDAO;
import DAO.ProductoDAO;
import com.example.inventario_hib.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import modelo.Categoria;
import modelo.Producto;
import modelo.ProductoCategoria;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrearProdCatController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final ProductoCategoriaDAO productoCategoriaDAO = new ProductoCategoriaDAO();

    @FXML
    private ComboBox<Long> cbIdCategorias;
    @FXML
    private ComboBox<Long> cbIdProductos;

    @FXML
    public void initialize() {
        cbIdCategorias.getItems().setAll(obtenerIdCategorias());
        cbIdProductos.getItems().setAll(obtenerIdProductos(true));

        cbIdCategorias.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cbIdProductos.getItems().setAll(obtenerIdProductos(false));
            }
        });
    }

    private List<Long> obtenerIdCategorias() {
        List<Categoria> categorias = categoriaDAO.getTodos();
        List<Long> idCategorias = new ArrayList<>();
        for (Categoria categoria : categorias) {
            idCategorias.add(categoria.getIdCategoria());
        }
        return idCategorias;
    }

    private List<Long> obtenerIdProductos(boolean primeraVez) {
        List<Long> idProductos = new ArrayList<>();
        if (primeraVez) {
            List<Producto> productos = productoDAO.getTodos();
            for (Producto producto : productos) {
                idProductos.add(producto.getIdProducto());
            }
        } else {
            List<Producto> productos = productoDAO.getProductosNoEnCategoria(cbIdCategorias.getValue());
            for (Producto producto : productos) {
                idProductos.add(producto.getIdProducto());
            }
        }
        return idProductos;
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
            alert.setHeaderText("Ayuda - Añadir Producto a categoría");
            alert.setContentText("Esta es la ventana de añadir productos a una categoría. Aquí se mostrarán todas las categorías y productos disponibles en el sistema.\n" +
                    "Solo debes seleccionar la categoría a la que se quiere añadir un producto, y elegir el producto que deseas añadir.\n" +
                    "Deberás darle al botón de crear para terminar de añadir el producto a la categoría.\n" +
                    "Para limpiar los campos, debe pulsar el botón 'Limpiar'.\n" +
                    "Por último, si desea volver solo debe pulsar en 'Volver'.");
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

    public void crear(ActionEvent actionEvent) {
        if (comprobarCampos()) {
            Producto producto = productoDAO.obtenerPorId(cbIdProductos.getValue());
            Categoria categoria = categoriaDAO.obtenerPorId(cbIdCategorias.getValue());
            ProductoCategoria productoCategoria = new ProductoCategoria(producto, categoria);
            if (productoCategoriaDAO.create(productoCategoria)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Producto añadido a categoría");
                alert.setContentText("El producto se ha añadido a la categoría correctamente." +
                        "Id de categoría: " + cbIdCategorias.getValue() + "\n" +
                        "Id de producto: " + cbIdProductos.getValue());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al añadir producto a categoría");
                alert.setContentText("Ha ocurrido un error al añadir el producto a la categoría.");
                alert.showAndWait();
            }
        }
    }

    private boolean comprobarCampos() {
        if (cbIdCategorias.getValue() == null || cbIdProductos.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al añadir producto a categoría");
            alert.setContentText("Debes seleccionar una categoría y un producto para poder añadirlo a la categoría.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
