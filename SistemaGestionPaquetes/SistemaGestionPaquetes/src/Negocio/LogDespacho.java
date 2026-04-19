package Negocio;

import BaseDatos.HistorialMovimientoBD;
import BaseDatos.PaqueteBD;
import Clases.EstadoPaquete;
import Clases.HistorialMovimiento;
import Clases.Paquete;
import Clases.UsuarioSistema;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LogDespacho {

    PaqueteBD objPaqueteBD = new PaqueteBD();
    HistorialMovimientoBD objHistorialBD = new HistorialMovimientoBD();

    public boolean despacharPaquete(String codigoPaquete, UsuarioSistema operador)
            throws ClassNotFoundException, SQLException {

        // 1. Buscar paquete
        Paquete paquete = objPaqueteBD.BuscarPaquetePorCodigo(codigoPaquete);

        if(paquete == null){
            return false;
        }

        // 2. Validar que esté en estado Registrado
        if(paquete.getEstadoActual().getIdEstado() != 1){
            return false;
        }

        // 3. Cambiar estado a En tránsito
        EstadoPaquete nuevoEstado = new EstadoPaquete();
        nuevoEstado.setIdEstado(2);

        paquete.setEstadoActual(nuevoEstado);

        int actualizado = objPaqueteBD.ActualizarEstadoPaquete(paquete);

        if(actualizado > 0){

            // 4. Guardar historial
            HistorialMovimiento hm = new HistorialMovimiento();
            hm.setPaquete(paquete);
            hm.setUsuario(operador);
            hm.setEstado(nuevoEstado);
            hm.setFechaHora(LocalDateTime.now());
            hm.setObservacion("Paquete despachado");
            hm.setNombreRecibe(null);

            objHistorialBD.InsertarHistorial(hm);

            return true;
        }

        return false;
    }
}