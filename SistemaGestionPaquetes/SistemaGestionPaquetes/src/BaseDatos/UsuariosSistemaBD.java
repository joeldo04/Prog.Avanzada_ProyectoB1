package BaseDatos;

import Clases.UsuarioSistema;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosSistemaBD {

    Conexiones BLcon = new Conexiones();

    public int InsertarUsuario(UsuarioSistema objUsuario)
            throws ClassNotFoundException, SQLException {

        String Sentencia = "INSERT INTO usuarioSistema "
                + "(cedula, nombre, apellido, correo, telefono, idRol) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);

        ps.setString(1, objUsuario.getCedula());
        ps.setString(2, objUsuario.getNombre());
        ps.setString(3, objUsuario.getApellido());
        ps.setString(4, objUsuario.getCorreo());
        ps.setString(5, objUsuario.getTelefono());
        ps.setInt(6, objUsuario.getRolUsuario().getIdRol());

        return ps.executeUpdate();
    }

    public ResultSet BuscarUsuarioxCedula(UsuarioSistema objUsuario)
            throws SQLException, ClassNotFoundException {

        String Sentencia = "SELECT * FROM usuarioSistema WHERE cedula = ?";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);
        ps.setString(1, objUsuario.getCedula());

        return ps.executeQuery();
    }

    public int ActualizarUsuario(UsuarioSistema objUsuario)
            throws SQLException, ClassNotFoundException {

        String Sentencia = "UPDATE usuarioSistema SET nombre=?, apellido=?, "
                + "correo=?, telefono=?, idRol=? WHERE cedula=?";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);

        ps.setString(1, objUsuario.getNombre());
        ps.setString(2, objUsuario.getApellido());
        ps.setString(3, objUsuario.getCorreo());
        ps.setString(4, objUsuario.getTelefono());
        ps.setInt(5, objUsuario.getRolUsuario().getIdRol());
        ps.setString(6, objUsuario.getCedula());

        return ps.executeUpdate();
    }

    public ResultSet ListarUsuarios()
            throws SQLException, ClassNotFoundException {

        String Sentencia = "SELECT * FROM usuarioSistema";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);

        return ps.executeQuery();
    }
}