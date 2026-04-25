package Clases;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "entregas")
@NamedQueries({
    @NamedQuery(name = "Entregas.findAll", query = "SELECT e FROM Entregas e"),
    @NamedQuery(name = "Entregas.findByIdEntrega", query = "SELECT e FROM Entregas e WHERE e.idEntrega = :idEntrega"),
    @NamedQuery(name = "Entregas.findByNombreRecibe", query = "SELECT e FROM Entregas e WHERE e.nombreRecibe = :nombreRecibe"),
    @NamedQuery(name = "Entregas.findByObservaciones", query = "SELECT e FROM Entregas e WHERE e.observaciones = :observaciones")})
public class Entregas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEntrega")
    private Integer idEntrega;
    @Basic(optional = false)
    @Column(name = "nombreRecibe")
    private String nombreRecibe;
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "idHistoriaPaq", referencedColumnName = "idHistorial")
    @OneToOne(optional = false)
    private Historialpaquetes idHistoriaPaq;

    public Entregas() {
    }

    public Entregas(Integer idEntrega) {
        this.idEntrega = idEntrega;
    }

    public Entregas(Integer idEntrega, String nombreRecibe) {
        this.idEntrega = idEntrega;
        this.nombreRecibe = nombreRecibe;
    }

    public Integer getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Integer idEntrega) {
        this.idEntrega = idEntrega;
    }

    public String getNombreRecibe() {
        return nombreRecibe;
    }

    public void setNombreRecibe(String nombreRecibe) {
        this.nombreRecibe = nombreRecibe;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Historialpaquetes getIdHistoriaPaq() {
        return idHistoriaPaq;
    }

    public void setIdHistoriaPaq(Historialpaquetes idHistoriaPaq) {
        this.idHistoriaPaq = idHistoriaPaq;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntrega != null ? idEntrega.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entregas)) {
            return false;
        }
        Entregas other = (Entregas) object;
        if ((this.idEntrega == null && other.idEntrega != null) || (this.idEntrega != null && !this.idEntrega.equals(other.idEntrega))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Entregas[ idEntrega=" + idEntrega + " ]";
    }
    
}
