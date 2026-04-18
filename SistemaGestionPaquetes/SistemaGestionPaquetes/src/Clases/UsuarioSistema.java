package Clases;

public class UsuarioSistema {
    private int idUsuario;
    private String cedula;
    private String nombreApellido;
    private String email;
    private String rolUsuario;

    public UsuarioSistema() {
    }

    public UsuarioSistema(int idUsuario, String nombreApellido, String email, 
            String cedula, String rolUsuario) {
        this.idUsuario = idUsuario;
        this.cedula = cedula;
        this.nombreApellido = nombreApellido;
        this.email = email;
        this.rolUsuario = rolUsuario;
    }
}