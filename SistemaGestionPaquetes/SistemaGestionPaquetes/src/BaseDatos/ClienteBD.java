package BaseDatos;

import Clases.Cliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteBD {

    Conexiones BLcon = new Conexiones();

    public int InsertarCliente(Cliente objCliente)
            throws ClassNotFoundException, SQLException {

        String Sentencia = "INSERT INTO cliente "
                + "(cedula, nombre, apellido, direccion, telefono, correo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);

        ps.setString(1, objCliente.getCedula());
        ps.setString(2, objCliente.getNombre());
        ps.setString(3, objCliente.getApellido());
        ps.setString(4, objCliente.getDireccion());
        ps.setString(5, objCliente.getTelefono());
        ps.setString(6, objCliente.getCorreo());

        return ps.executeUpdate();
    }

    public ResultSet BuscarClientexCedula(Cliente objCliente)
            throws SQLException, ClassNotFoundException {

        String Sentencia = "SELECT * FROM cliente WHERE cedula = ?";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);
        ps.setString(1, objCliente.getCedula());

        return ps.executeQuery();
    }

    public int ActualizarCliente(Cliente objCliente)
            throws SQLException, ClassNotFoundException {

        String Sentencia = "UPDATE cliente SET nombre=?, apellido=?, direccion=?,"
                + " telefono=?, correo=? WHERE cedula=?";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);

        ps.setString(1, objCliente.getNombre());
        ps.setString(2, objCliente.getApellido());
        ps.setString(3, objCliente.getDireccion());
        ps.setString(4, objCliente.getTelefono());
        ps.setString(5, objCliente.getCorreo());
        ps.setString(6, objCliente.getCedula());

        return ps.executeUpdate();
    }

    public ResultSet ListarClientes()
            throws SQLException, ClassNotFoundException {

        String Sentencia = "SELECT * FROM cliente";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);

        return ps.executeQuery();
    }

}
