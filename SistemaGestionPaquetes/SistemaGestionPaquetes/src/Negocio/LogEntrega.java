package Negocio;

import BaseDatos.HistorialMovimientoBD;
import BaseDatos.PaqueteBD;
import Clases.EstadoPaquete;
import Clases.HistorialMovimiento;
import Clases.Paquete;
import Clases.UsuarioSistema;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LogEntrega {

    PaqueteBD objPaqueteBD = new PaqueteBD();
    HistorialMovimientoBD objHistorialBD = new HistorialMovimientoBD();

    public boolean entregarPaquete(String codigoPaquete, UsuarioSistema repartidor,
                                   String nombreRecibe, String observacion)
            throws ClassNotFoundException, SQLException {

        // 1. Buscar paquete
        Paquete paquete = objPaqueteBD.BuscarPaquetePorCodigo(codigoPaquete);

        // 2. Verificar existencia
        if (paquete == null) {
            return false;
        }

        // 3. Verificar que esté en tránsito
        if (paquete.getEstadoActual().getIdEstado() != 2) {
            return false;
        }

        // 4. Cambiar estado a entregado
        EstadoPaquete nuevoEstado = new EstadoPaquete();
        nuevoEstado.setIdEstado(3);

        paquete.setEstadoActual(nuevoEstado);
        paquete.setUsuarioRepartidor(repartidor);

        int filas = objPaqueteBD.RegistrarEntrega(paquete);

        if (filas > 0) {

            // 5. Registrar historial
            HistorialMovimiento hp = new HistorialMovimiento();
            hp.setPaquete(paquete);
            hp.setUsuario(repartidor);
            hp.setEstado(nuevoEstado);
            hp.setFechaHora(LocalDateTime.now());
            hp.setNombreRecibe(nombreRecibe);
            hp.setObservacion(observacion);

            objHistorialBD.InsertarHistorial(hp);

            return true;
        }

        return false;
    }
}