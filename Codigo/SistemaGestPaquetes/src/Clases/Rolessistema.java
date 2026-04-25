package Clases;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rolessistema")
@NamedQueries({
    @NamedQuery(name = "Rolessistema.findAll", query = "SELECT r FROM Rolessistema r"),
    @NamedQuery(name = "Rolessistema.findByIdRol", query = "SELECT r FROM Rolessistema r WHERE r.idRol = :idRol"),
    @NamedQuery(name = "Rolessistema.findByNombreRol", query = "SELECT r FROM Rolessistema r WHERE r.nombreRol = :nombreRol")})
public class Rolessistema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRol")
    private Integer idRol;
    @Basic(optional = false)
    @Column(name = "nombreRol")
    private String nombreRol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRol")
    private Collection<Usuarios> usuariosCollection;

    public Rolessistema() {
    }

    public Rolessistema(Integer idRol) {
        this.idRol = idRol;
    }

    public Rolessistema(Integer idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rolessistema)) {
            return false;
        }
        Rolessistema other = (Rolessistema) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Rolessistema[ idRol=" + idRol + " ]";
    }
    
}
