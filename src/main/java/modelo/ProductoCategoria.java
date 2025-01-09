package modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "productos_categorias")
public class ProductoCategoria {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto_categoria")
    private Long idProductoCategoria;

    @ManyToOne
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria")
    private Categoria categoria;

    public ProductoCategoria() {
    }

    public ProductoCategoria(Producto producto, Categoria categoria) {
        this.producto = producto;
        this.categoria = categoria;
    }

    public Long getIdProductoCategoria() {
        return idProductoCategoria;
    }

    public void setIdProductoCategoria(Long idProductoCategoria) {
        this.idProductoCategoria = idProductoCategoria;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ProductoCategoria --> " + "idProductoCategoria: " + idProductoCategoria + " || producto: " + producto + " || categoria: " + categoria;
    }
}
