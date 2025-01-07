package modelo;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "marcaje")
public class Marcaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMarcaje")
    private Long idMarcaje;

    @ManyToOne (optional = false)
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto", nullable = false)
    private Producto idProducto;

    @ManyToOne (optional = false)
    @JoinColumn(name = "idAula", referencedColumnName = "idAula", nullable = false)
    private Aula idAula;

    @Column(name = "Tipo")
    private boolean tipo; // 0 para la entrada - 1 para la salida

    @Column(name = "TimeStamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha = new Date();

    public Marcaje() {

    }

    public Marcaje(Long idMarcaje, Producto idProducto, Aula idAula, boolean tipo, Date fecha) {
        this.idMarcaje = idMarcaje;
        this.idProducto = idProducto;
        this.idAula = idAula;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public Marcaje(Long idMarcaje, Producto idProducto, Aula idAula, boolean tipo) {
        this.idMarcaje = idMarcaje;
        this.idProducto = idProducto;
        this.idAula = idAula;
        this.tipo = tipo;
        this.fecha = new Date();
    }

    public Marcaje(Producto idProducto, Aula idAula, boolean tipo) {
        this.idProducto = idProducto;
        this.idAula = idAula;
        this.tipo = tipo;
        this.fecha = new Date();
    }

    public Long getIdMarcaje() {
        return idMarcaje;
    }

    public void setIdMarcaje(Long idMarcaje) {
        this.idMarcaje = idMarcaje;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Aula getIdAula() {
        return idAula;
    }

    public void setIdAula(Aula idAula) {
        this.idAula = idAula;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Marcaje --> " + "idMarcaje: " + idMarcaje + " || idProducto: " + idProducto + " || idAula: " + idAula + " || tipo: " + tipo + " || Fecha: " + fecha;
    }
}
