package BaseDatos;

import Clases.EstadoPaquete;
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

        String Sentencia = "UPDATE paquete SET idEstadoActual = ? WHERE idPaquete = ?";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);

        ps.setInt(1, objPaquete.getEstadoActual().getIdEstado());
        ps.setInt(2, objPaquete.getIdPaquete());

        return ps.executeUpdate();
    }

    public ResultSet ListarPaquetes()
            throws SQLException, ClassNotFoundException {

        String Sentencia = "SELECT * FROM paquete";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);

        return ps.executeQuery();
    }

    public Paquete BuscarPaquetePorSeguimiento(String seguimiento)
            throws SQLException, ClassNotFoundException {

        String Sentencia = "SELECT * FROM paquete WHERE numeroSeguimiento = ?";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);
        ps.setString(1, seguimiento);

        ResultSet rs = ps.executeQuery();

        Paquete p = null;

        if (rs.next()) {
            p = new Paquete();
            p.setIdPaquete(rs.getInt("idPaquete"));
            p.setNumeroSeguimiento(rs.getString("numeroSeguimiento"));
        }

        return p;
    }

    public Paquete BuscarPaquetePorCodigo(String codigo)
            throws SQLException, ClassNotFoundException {

        String Sentencia = "SELECT * FROM paquete WHERE codigoPaquete = ?";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(Sentencia);
        ps.setString(1, codigo);

        ResultSet rs = ps.executeQuery();

        Paquete p = null;

        if (rs.next()) {
            p = new Paquete();
            p.setIdPaquete(rs.getInt("idPaquete"));

            EstadoPaquete estado = new EstadoPaquete();
            estado.setIdEstado(rs.getInt("idEstadoActual"));

            p.setEstadoActual(estado);
        }

        return p;
    }

    public int RegistrarEntrega(Paquete objPaquete)
            throws SQLException, ClassNotFoundException {

        String sentencia = "UPDATE paquete SET idEstadoActual = ?, idUsuarioRepartidor = ? WHERE idPaquete = ?";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(sentencia);

        ps.setInt(1, objPaquete.getEstadoActual().getIdEstado());
        ps.setInt(2, objPaquete.getUsuarioRepartidor().getIdUsuario());
        ps.setInt(3, objPaquete.getIdPaquete());

        return ps.executeUpdate();
    }

    public Paquete BuscarPorSeguimiento(String seguimiento)
            throws SQLException, ClassNotFoundException {

        String sentencia = "SELECT idPaquete, codigoPaquete, numeroSeguimiento, idEstadoActual "
                + "FROM paquete WHERE numeroSeguimiento = ?";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(sentencia);
        ps.setString(1, seguimiento);

        ResultSet rs = ps.executeQuery();

        Paquete objPaquete = null;

        if (rs.next()) {
            objPaquete = new Paquete();

            objPaquete.setIdPaquete(rs.getInt("idPaquete"));
            objPaquete.setCodigoPaquete(rs.getString("codigoPaquete"));
            objPaquete.setNumeroSeguimiento(rs.getString("numeroSeguimiento"));

            EstadoPaquete estado = new EstadoPaquete();
            estado.setIdEstado(rs.getInt("idEstadoActual"));

            objPaquete.setEstadoActual(estado);
        }

        rs.close();
        return objPaquete;
    }

}
