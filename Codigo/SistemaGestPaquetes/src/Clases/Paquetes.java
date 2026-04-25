package Clases;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "paquetes")
@NamedQueries({
    @NamedQuery(name = "Paquetes.findAll", query = "SELECT p FROM Paquetes p"),
    @NamedQuery(name = "Paquetes.findByIdPaquete", query = "SELECT p FROM Paquetes p WHERE p.idPaquete = :idPaquete"),
    @NamedQuery(name = "Paquetes.findByCodigoPaquete", query = "SELECT p FROM Paquetes p WHERE p.codigoPaquete = :codigoPaquete"),
    @NamedQuery(name = "Paquetes.findByNumSeguimiento", query = "SELECT p FROM Paquetes p WHERE p.numSeguimiento = :numSeguimiento"),
    @NamedQuery(name = "Paquetes.findByPeso", query = "SELECT p FROM Paquetes p WHERE p.peso = :peso"),
    @NamedQuery(name = "Paquetes.findByTipoEnvio", query = "SELECT p FROM Paquetes p WHERE p.tipoEnvio = :tipoEnvio"),
    @NamedQuery(name = "Paquetes.findByDireccionEntrega", query = "SELECT p FROM Paquetes p WHERE p.direccionEntrega = :direccionEntrega"),
    @NamedQuery(name = "Paquetes.findByEstadoActualPaquete", query = "SELECT p FROM Paquetes p WHERE p.estadoActualPaquete = :estadoActualPaquete"),
    @NamedQuery(name = "Paquetes.findByFechaRegistro", query = "SELECT p FROM Paquetes p WHERE p.fechaRegistro = :fechaRegistro")})
public class Paquetes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPaquete")
    private Integer idPaquete;
    @Basic(optional = false)
    @Column(name = "codigoPaquete")
    private String codigoPaquete;
    @Basic(optional = false)
    @Column(name = "numSeguimiento")
    private String numSeguimiento;
    @Basic(optional = false)
    @Column(name = "peso")
    private double peso;
    @Basic(optional = false)
    @Column(name = "tipoEnvio")
    private String tipoEnvio;
    @Basic(optional = false)
    @Column(name = "direccionEntrega")
    private String direccionEntrega;
    @Basic(optional = false)
    @Column(name = "estadoActualPaquete")
    private String estadoActualPaquete;
    @Basic(optional = false)
    @Column(name = "fechaRegistro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaquete")
    private Collection<Historialpaquetes> historialpaquetesCollection;
    @JoinColumn(name = "idDestinatario", referencedColumnName = "idCliente")
    @ManyToOne(optional = false)
    private Clientes idDestinatario;
    @JoinColumn(name = "idRemitente", referencedColumnName = "idCliente")
    @ManyToOne(optional = false)
    private Clientes idRemitente;
    @JoinColumn(name = "idRepartidor", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuarios idRepartidor;

    public Paquetes() {
    }

    public Paquetes(Integer idPaquete) {
        this.idPaquete = idPaquete;
    }

    public Paquetes(Integer idPaquete, String codigoPaquete, String numSeguimiento, double peso, String tipoEnvio, String direccionEntrega, String estadoActualPaquete, Date fechaRegistro) {
        this.idPaquete = idPaquete;
        this.codigoPaquete = codigoPaquete;
        this.numSeguimiento = numSeguimiento;
        this.peso = peso;
        this.tipoEnvio = tipoEnvio;
        this.direccionEntrega = direccionEntrega;
        this.estadoActualPaquete = estadoActualPaquete;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Integer idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getCodigoPaquete() {
        return codigoPaquete;
    }

    public void setCodigoPaquete(String codigoPaquete) {
        this.codigoPaquete = codigoPaquete;
    }

    public String getNumSeguimiento() {
        return numSeguimiento;
    }

    public void setNumSeguimiento(String numSeguimiento) {
        this.numSeguimiento = numSeguimiento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getEstadoActualPaquete() {
        return estadoActualPaquete;
    }

    public void setEstadoActualPaquete(String estadoActualPaquete) {
        this.estadoActualPaquete = estadoActualPaquete;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Collection<Historialpaquetes> getHistorialpaquetesCollection() {
        return historialpaquetesCollection;
    }

    public void setHistorialpaquetesCollection(Collection<Historialpaquetes> historialpaquetesCollection) {
        this.historialpaquetesCollection = historialpaquetesCollection;
    }

    public Clientes getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Clientes idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public Clientes getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(Clientes idRemitente) {
        this.idRemitente = idRemitente;
    }

    public Usuarios getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(Usuarios idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaquete != null ? idPaquete.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paquetes)) {
            return false;
        }
        Paquetes other = (Paquetes) object;
        if ((this.idPaquete == null && other.idPaquete != null) || (this.idPaquete != null && !this.idPaquete.equals(other.idPaquete))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Clases.Paquetes[ idPaquete=" + idPaquete + " ]";
    }
    
}
