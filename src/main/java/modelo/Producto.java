package modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private Long idProducto;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "EAN13")
    private Long EAN13;

    @Column(name = "keyRFID")
    private String keyRFID;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "productos_categorias",
            joinColumns = @JoinColumn(name = "idProducto", referencedColumnName = "idProducto", nullable = true),
            inverseJoinColumns = @JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria", nullable = true))
    private List<Categoria> categorias;


    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Producto() {

    }

    public Producto(Long idProducto, String descripcion, Long EAN13, String keyRFID) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.EAN13 = EAN13;
        this.keyRFID = keyRFID;
    }

    public Producto(String descripcion, Long EAN13, String keyRFID) {
        this.descripcion = descripcion;
        this.EAN13 = EAN13;
        this.keyRFID = keyRFID;
    }

    public Producto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEAN13() {
        return EAN13;
    }

    public void setEAN13(Long EAN13) {
        this.EAN13 = EAN13;
    }

    public String getKeyRFID() {
        return keyRFID;
    }

    public void setKeyRFID(String keyRFID) {
        this.keyRFID = keyRFID;
    }

    @Override
    public String toString() {
        return "Producto --> " + "idProducto: " + idProducto + " || Descripcion: " + descripcion + " || EAN13: " + EAN13 + " || keyRFID: " + keyRFID;
    }
}
