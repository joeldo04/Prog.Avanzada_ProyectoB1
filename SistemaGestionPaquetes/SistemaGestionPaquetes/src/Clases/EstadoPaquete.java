package Clases;

public class EstadoPaquete {
    private int idEstado;
    private String codigoEstado;
    private String nombreEstado;
    private int ordenEstado;

    public EstadoPaquete() {
    }

    public EstadoPaquete(int idEstado, String codigoEstado, 
            String nombreEstado, int ordenEstado) {
        this.idEstado = idEstado;
        this.codigoEstado = codigoEstado;
        this.nombreEstado = nombreEstado;
        this.ordenEstado = ordenEstado;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public String getCodigoEstado() {
        return codigoEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public int getOrdenEstado() {
        return ordenEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public void setOrdenEstado(int ordenEstado) {
        this.ordenEstado = ordenEstado;
    }

    @Override
    public String toString() {
        return "EstadoPaquete{" + "nombreEstado=" + nombreEstado + '}';
    }
    
}
