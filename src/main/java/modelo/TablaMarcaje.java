package modelo;

import java.util.Date;

public class TablaMarcaje {
    private Long idMarcaje;
    private Long idProducto;
    private Long idAula;
    private boolean tipo;
    private Date fecha;

    public TablaMarcaje() {

    }

    public TablaMarcaje(Long idMarcaje, Long idProducto, Long idAula, boolean tipo, Date fecha) {
        this.idMarcaje = idMarcaje;
        this.idProducto = idProducto;
        this.idAula = idAula;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    public Long getIdMarcaje() {
        return idMarcaje;
    }

    public void setIdMarcaje(Long idMarcaje) {
        this.idMarcaje = idMarcaje;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getIdAula() {
        return idAula;
    }

    public void setIdAula(Long idAula) {
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
        return "TablaMarcaje{" + "idMarcaje=" + idMarcaje + ", idProducto=" + idProducto + ", idAula=" + idAula + ", tipo=" + tipo + ", fecha=" + fecha + '}';
    }
}
