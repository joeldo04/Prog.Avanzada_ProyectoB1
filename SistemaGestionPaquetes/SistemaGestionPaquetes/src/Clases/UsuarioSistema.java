package Clases;

public class UsuarioSistema {
    private int idUsuario;
    private String cedula;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private Rol rolUsuario;

    public UsuarioSistema() {
    }

    public UsuarioSistema(int idUsuario, String cedula, String nombre, String apellido, 
            String correo, String telefono, Rol rolUsuario) {
        this.idUsuario = idUsuario;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.rolUsuario = rolUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Rol getRolUsuario() {
        return rolUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setRolUsuario(Rol rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    @Override
    public String toString() {
        return "UsuarioSistema{" + "idUsuario=" + idUsuario + ", cedula=" + cedula + 
                ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo + 
                ", telefono=" + telefono + ", rolUsuario=" + rolUsuario + '}';
    }
}