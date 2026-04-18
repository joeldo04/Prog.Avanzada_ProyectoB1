package Clases;

public class UsuarioSistema {
    private int idUsuario;
    private String nombreApellido;
    private String email;
    private String rolUsuario;

    public UsuarioSistema() {
    }

    public UsuarioSistema(int idUsuario, String nombreApellido, String email, 
            String rolUsuario) {
        this.idUsuario = idUsuario;
        this.nombreApellido = nombreApellido;
        this.email = email;
        this.rolUsuario = rolUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public String getEmail() {
        return email;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    @Override
    public String toString() {
        return "UsuarioSistema{" + "idUsuario=" + idUsuario +
                ", nombreApellido=" + nombreApellido + ", email=" + email +
                ", rolUsuario=" + rolUsuario + '}';
    }
    
}
