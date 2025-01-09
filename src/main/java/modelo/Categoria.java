package modelo;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private boolean estado;

    @ManyToMany(mappedBy = "categorias", fetch = FetchType.EAGER)
    private List<Producto> productos;

    public Categoria() {

    }

    public Categoria(String nombre, String descripcion, boolean estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Categoria --> " + "idCategoria: " + idCategoria + " || nombre: " + nombre + " || descripcion: " + descripcion + " || estado: " + estado;
    }
}
