package BaseDatos;

import Clases.Paquete;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaqueteBD {

    Conexiones BLcon = new Conexiones();

    public int InsertarPaquete(Paquete objPaquete)
            throws ClassNotFoundException, SQLException {

        String Sentencia = "INSERT INTO paquete "
                + "(codigoPaquete, numeroSeguimiento, peso, tipoEnvio, direccionEntrega,"
                + " fechaRegistro, idEstadoActual, idRemitente, idDestinatario,"
                + " idUsuarioRegistro, idUsuarioRepartidor) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);

        ps.setString(1, objPaquete.getCodigoPaquete());
        ps.setString(2, objPaquete.getNumeroSeguimiento());
        ps.setDouble(3, objPaquete.getPeso());
        ps.setString(4, objPaquete.getTipoEnvio());
        ps.setString(5, objPaquete.getDireccionEntrega());
        ps.setTimestamp(6, java.sql.Timestamp.valueOf(objPaquete.getFechaRegistro()));
        ps.setInt(7, objPaquete.getEstadoActual().getIdEstado());
        ps.setInt(8, objPaquete.getRemitente().getIdCliente());
        ps.setInt(9, objPaquete.getDestinatario().getIdCliente());
        ps.setInt(10, objPaquete.getUsuarioRegistro().getIdUsuario());

        if (objPaquete.getUsuarioRepartidor() != null) {
            ps.setInt(11, objPaquete.getUsuarioRepartidor().getIdUsuario());
        } else {
            ps.setNull(11, java.sql.Types.INTEGER);
        }

        return ps.executeUpdate();
    }

    public ResultSet BuscarPaquetexCodigo(Paquete objPaquete)
            throws SQLException, ClassNotFoundException {

        String Sentencia = "SELECT * FROM paquete WHERE codigoPaquete = ?";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);
        ps.setString(1, objPaquete.getCodigoPaquete());

        return ps.executeQuery();
    }

    public int ActualizarEstadoPaquete(Paquete objPaquete)
            throws SQLException, ClassNotFoundException {

        String Sentencia = "UPDATE paquete SET idEstadoActual=?, idUsuarioRepartidor=? "
                + "WHERE codigoPaquete=?";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);

        ps.setInt(1, objPaquete.getEstadoActual().getIdEstado());

        if (objPaquete.getUsuarioRepartidor() != null) {
            ps.setInt(2, objPaquete.getUsuarioRepartidor().getIdUsuario());
        } else {
            ps.setNull(2, java.sql.Types.INTEGER);
        }

        ps.setString(3, objPaquete.getCodigoPaquete());

        return ps.executeUpdate();
    }

    public ResultSet ListarPaquetes()
            throws SQLException, ClassNotFoundException {

        String Sentencia = "SELECT * FROM paquete";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);

        return ps.executeQuery();
    }
}
