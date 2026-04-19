package Clases;

import java.time.LocalDateTime;

public class HistorialMovimiento {
    private int idHistorial;
    private Paquete paquete;
    private UsuarioSistema usuario;
    private EstadoPaquete estado;
    private LocalDateTime fechaHora;
    private String observacion;    
    private String nombreRecibe;

    public HistorialMovimiento() {
    }
    
    public HistorialMovimiento(int idHistorial, Paquete paquete, UsuarioSistema usuario, EstadoPaquete estado, String observacion, String nombreRecibe) {
        this.idHistorial = idHistorial;
        this.paquete = paquete;
        this.usuario = usuario;
        this.estado = estado;
        this.observacion = observacion;
        this.nombreRecibe = nombreRecibe;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public void setUsuario(UsuarioSistema usuario) {
        this.usuario = usuario;
    }

    public void setEstado(EstadoPaquete estado) {
        this.estado = estado;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setNombreRecibe(String nombreRecibe) {
        this.nombreRecibe = nombreRecibe;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public UsuarioSistema getUsuario() {
        return usuario;
    }

    public EstadoPaquete getEstado() {
        return estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getObservacion() {
        return observacion;
    }

    public String getNombreRecibe() {
        return nombreRecibe;
    }

    @Override
    public String toString() {
        return "HistorialMovimiento{" + "idHistorial=" + idHistorial + 
                ", paquete=" + paquete + ", usuario=" + usuario + ", estado=" + 
                estado + ", fechaHora=" + fechaHora + ", observacion=" + observacion + 
                ", nombreRecibe=" + nombreRecibe + '}';
    }
      
}