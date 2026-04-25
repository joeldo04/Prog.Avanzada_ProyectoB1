package Clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "historialpaquetes")
@NamedQueries({
    @NamedQuery(name = "Historialpaquetes.findAll", query = "SELECT h FROM Historialpaquetes h"),
    @NamedQuery(name = "Historialpaquetes.findByIdHistorial", query = "SELECT h FROM Historialpaquetes h WHERE h.idHistorial = :idHistorial"),
    @NamedQuery(name = "Historialpaquetes.findByEstado", query = "SELECT h FROM Historialpaquetes h WHERE h.estado = :estado"),
    @NamedQuery(name = "Historialpaquetes.findByFecha", query = "SELECT h FROM Historialpaquetes h WHERE h.fecha = :fecha")})
public class Historialpaquetes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idHistorial")
    private Integer idHistorial;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "idPaquete", referencedColumnName = "idPaquete")
    @ManyToOne(optional = false)
    private Paquetes idPaquete;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuario;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idHistoriaPaq")
    private Entregas entregas;

    public Historialpaquetes() {
    }

    public Historialpaquetes(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public Historialpaquetes(Integer idHistorial, String estado, Date fecha) {
        this.idHistorial = idHistorial;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Integer getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Integer idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Paquetes getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Paquetes idPaquete) {
        this.idPaquete = idPaquete;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Entregas getEntregas() {
        return entregas;
    }

    public void setEntregas(Entregas entregas) {
        this.entregas = entregas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistorial != null ? idHistorial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historialpaquetes)) {
            return false;
        }
        Historialpaquetes other = (Historialpaquetes) object;
        if ((this.idHistorial == null && other.idHistorial != null) || (this.idHistorial != null && !this.idHistorial.equals(other.idHistorial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Historialpaquetes[ idHistorial=" + idHistorial + " ]";
    }
    
}
