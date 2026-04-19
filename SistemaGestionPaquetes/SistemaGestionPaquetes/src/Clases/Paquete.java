package Clases;
import java.time.LocalDateTime;

public class Paquete {
    private int idPaquete;
    private String codigoPaquete;
    private String numeroSeguimiento;
    private double peso;
    private String tipoEnvio;
    private String direccionEntrega;
    private LocalDateTime fechaRegistro;

    private EstadoPaquete estadoActual;
    private Cliente remitente;
    private Cliente destinatario;
    private UsuarioSistema usuarioRegistro;
    private UsuarioSistema usuarioRepartidor;

    public Paquete() {
    }

    public Paquete(int idPaquete, String codigoPaquete, String numeroSeguimiento, 
            double peso, String tipoEnvio, String direccionEntrega, 
            LocalDateTime fechaRegistro, EstadoPaquete estadoActual, 
            Cliente remitente, Cliente destinatario, UsuarioSistema usuarioRegistro, 
            UsuarioSistema usuarioRepartidor) {
        this.idPaquete = idPaquete;
        this.codigoPaquete = codigoPaquete;
        this.numeroSeguimiento = numeroSeguimiento;
        this.peso = peso;
        this.tipoEnvio = tipoEnvio;
        this.direccionEntrega = direccionEntrega;
        this.fechaRegistro = fechaRegistro;
        this.estadoActual = estadoActual;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.usuarioRegistro = usuarioRegistro;
        this.usuarioRepartidor = usuarioRepartidor;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public String getCodigoPaquete() {
        return codigoPaquete;
    }

    public String getNumeroSeguimiento() {
        return numeroSeguimiento;
    }

    public double getPeso() {
        return peso;
    }

    public String getTipoEnvio() {
        return tipoEnvio;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public EstadoPaquete getEstadoActual() {
        return estadoActual;
    }

    public Cliente getRemitente() {
        return remitente;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

    public UsuarioSistema getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public UsuarioSistema getUsuarioRepartidor() {
        return usuarioRepartidor;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public void setCodigoPaquete(String codigoPaquete) {
        this.codigoPaquete = codigoPaquete;
    }

    public void setNumeroSeguimiento(String numeroSeguimiento) {
        this.numeroSeguimiento = numeroSeguimiento;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setEstadoActual(EstadoPaquete estadoActual) {
        this.estadoActual = estadoActual;
    }

    public void setRemitente(Cliente remitente) {
        this.remitente = remitente;
    }

    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }

    public void setUsuarioRegistro(UsuarioSistema usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public void setUsuarioRepartidor(UsuarioSistema usuarioRepartidor) {
        this.usuarioRepartidor = usuarioRepartidor;
    }

    @Override
    public String toString() {
        return "Paquete{" + "idPaquete=" + idPaquete + ", codigoPaquete=" + 
                codigoPaquete + ", numeroSeguimiento=" + numeroSeguimiento + 
                ", peso=" + peso + ", tipoEnvio=" + tipoEnvio + ", direccionEntrega=" + 
                direccionEntrega + ", fechaRegistro=" + fechaRegistro + 
                ", estadoActual=" + estadoActual + ", remitente=" + 
                remitente + ", destinatario=" + destinatario + ", usuarioRegistro=" + usuarioRegistro + 
                ", usuarioRepartidor=" + usuarioRepartidor + '}';
    }
    
    
}
