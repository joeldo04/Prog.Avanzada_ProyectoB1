package Negocio;

import BaseDatos.HistorialMovimientoBD;
import BaseDatos.PaqueteBD;
import Clases.Paquete;
import java.sql.ResultSet;

public class LogConsultaCliente {
    PaqueteBD datosPaquete = new PaqueteBD();
    HistorialMovimientoBD datosHistorial = new HistorialMovimientoBD();

    public Paquete consultarEstado(String numeroSeguimiento) throws Exception {
        return datosPaquete.BuscarPorSeguimiento(numeroSeguimiento);
    }

    public ResultSet consultarHistorial(String numeroSeguimiento) throws Exception {

        Paquete paquete = datosPaquete.BuscarPorSeguimiento(numeroSeguimiento);

        if (paquete != null) {
            return datosHistorial.ConsultarHistorialPorPaquete(paquete.getIdPaquete());
        }

        return null;
    }
}