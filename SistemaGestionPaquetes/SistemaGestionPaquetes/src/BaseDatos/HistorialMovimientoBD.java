package BaseDatos;

import Clases.HistorialMovimiento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistorialMovimientoBD {

    Conexiones BLcon = new Conexiones();

    public int InsertarHistorial(HistorialMovimiento objHistorial)
            throws ClassNotFoundException, SQLException {

        String sentencia = "INSERT INTO historialMovimiento "
                + "(idPaquete, idUsuario, idEstado, fechaHora, observacion, nombreRecibe) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(sentencia);

        ps.setInt(1, objHistorial.getPaquete().getIdPaquete());
        ps.setInt(2, objHistorial.getUsuario().getIdUsuario());
        ps.setInt(3, objHistorial.getEstado().getIdEstado());
        ps.setTimestamp(4, java.sql.Timestamp.valueOf(objHistorial.getFechaHora()));
        ps.setString(5, objHistorial.getObservacion());
        ps.setString(6, objHistorial.getNombreRecibe());

        return ps.executeUpdate();
    }

    public ResultSet ConsultarHistorialPorPaquete(int idPaquete)
            throws SQLException, ClassNotFoundException {

        String sentencia = "SELECT h.idHistorial, h.fechaHora, h.observacion, h.nombreRecibe, "
                + "e.nombreEstado, u.nombre, u.apellido "
                + "FROM historialMovimiento h "
                + "JOIN estadoPaquete e ON h.idEstado = e.idEstado "
                + "JOIN usuarioSistema u ON h.idUsuario = u.idUsuario "
                + "WHERE h.idPaquete = ? "
                + "ORDER BY h.fechaHora";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(sentencia);
        ps.setInt(1, idPaquete);

        return ps.executeQuery();
    }

    public ResultSet ListarHistorialGeneral()
            throws SQLException, ClassNotFoundException {

        String sentencia = "SELECT h.idHistorial, h.idPaquete, h.fechaHora, "
                + "e.nombreEstado, u.nombre, u.apellido "
                + "FROM historialMovimiento h "
                + "JOIN estadoPaquete e ON h.idEstado = e.idEstado "
                + "JOIN usuarioSistema u ON h.idUsuario = u.idUsuario "
                + "ORDER BY h.fechaHora DESC";

        PreparedStatement ps = BLcon.getConnection().prepareStatement(sentencia);

        return ps.executeQuery();
    }
}