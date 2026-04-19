package Negocio;

import BaseDatos.HistorialMovimientoBD;
import BaseDatos.PaqueteBD;
import Clases.EstadoPaquete;
import Clases.HistorialMovimiento;
import Clases.Paquete;
import Clases.UsuarioSistema;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

public class LogRecepcion {

    PaqueteBD objPaqueteBD = new PaqueteBD();
    HistorialMovimientoBD objHistorialBD = new HistorialMovimientoBD();

    public boolean registrarPaquete(Paquete objPaquete, UsuarioSistema recepcionista)
            throws ClassNotFoundException, SQLException {

        // 1. Generar código y seguimiento
        objPaquete.setCodigoPaquete(generarCodigoPaquete());
        objPaquete.setNumeroSeguimiento(generarNumeroSeguimiento());
        objPaquete.setFechaRegistro(LocalDateTime.now());

        // 2. Estado inicial = Registrado
        EstadoPaquete estado = new EstadoPaquete();
        estado.setIdEstado(1);
        objPaquete.setEstadoActual(estado);

        // 3. Usuario que registra
        objPaquete.setUsuarioRegistro(recepcionista);

        // 4. Guardar paquete
        int insertado = objPaqueteBD.InsertarPaquete(objPaquete);

        if (insertado > 0) {

            // 5. Buscar paquete para obtener id
            objPaquete = objPaqueteBD.BuscarPaquetePorSeguimiento(objPaquete.getNumeroSeguimiento());

            // 6. Registrar historial
            HistorialMovimiento hp = new HistorialMovimiento();
            hp.setPaquete(objPaquete);
            hp.setUsuario(recepcionista);
            hp.setEstado(estado);
            hp.setFechaHora(LocalDateTime.now());
            hp.setObservacion("Paquete registrado");
            hp.setNombreRecibe(null);

            objHistorialBD.InsertarHistorial(hp);

            return true;
        }

        return false;
    }

    private String generarNumeroSeguimiento() {
        Random r = new Random();
        return "TRK" + (100000 + r.nextInt(900000));
    }

    private String generarCodigoPaquete() {
        Random r = new Random();
        return "PK" + (100000 + r.nextInt(900000));
    }
}
