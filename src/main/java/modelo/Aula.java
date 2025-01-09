package modelo;

import jakarta.persistence.*;

@Entity
@Table (name = "aula")
public class Aula {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "IdAula")
    private Long idAula;

    @Column (name = "Numeracion")
    private String numeracion; // #.#.# (Pabellon, Piso, Aula)

    @Column (name = "Descripcion")
    private String descripcion;

    @Column (name = "IP")
    private String direccionIp; // 192.168.1.1

    public Aula() {

    }

    public Aula(Long idAula, String numeracion, String descripcion, String direccionIp) {
        this.idAula = idAula;
        this.numeracion = numeracion;
        this.descripcion = descripcion;
        this.direccionIp = direccionIp;
    }

    public Aula(String numeracion, String descripcion, String direccionIp) {
        this.numeracion = numeracion;
        this.descripcion = descripcion;
        this.direccionIp = direccionIp;
    }

    public Aula (Long idAula) {
        this.idAula = idAula;
    }

    public Long getIdAula() {
        return idAula;
    }

    public void setIdAula(Long idAula) {
        this.idAula = idAula;
    }

    public String getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(String numeracion) {
        this.numeracion = numeracion;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Aula --> " + "idAula: " + idAula + " || numeracion: " + numeracion + " || descripcion: " + descripcion + " || direccionIp: " + direccionIp;
    }
}
