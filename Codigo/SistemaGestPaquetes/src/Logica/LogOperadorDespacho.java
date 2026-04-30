package Logica;

import BaseDatos.BDHistorialPaquetes;
import BaseDatos.BDPaquetes;
import Clases.Historialpaquetes;
import Clases.Paquetes;
import Clases.Usuarios;
import java.util.Date;
import java.util.List;

public class LogOperadorDespacho {

    BDPaquetes objBDPaquetes = new BDPaquetes();
    BDHistorialPaquetes objBDHistorial = new BDHistorialPaquetes();
    LogUsuario objLogUsuario = new LogUsuario();

    public Paquetes ConsultarPaqueteCodigo(Paquetes objPaquete) {

        List<Paquetes> listaPaquetes = objBDPaquetes.BuscarPaqueteCodigo(objPaquete);

        if (listaPaquetes.isEmpty()) {
            return null;
        } else {
            return listaPaquetes.get(0);
        }
    }

    public boolean ValidarPaqueteRegistrado(Paquetes objPaquete) {

        if (objPaquete == null) {
            return false;
        }

        if (objPaquete.getEstadoActualPaquete().equals("REGISTRADO")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean DespacharPaquete(Paquetes objPaquete, Usuarios objOperador, Usuarios objRepartidor) 
            throws Exception {

        if (objLogUsuario.EsOperadorDespacho(objOperador) == false) {
            return false;
        }

        if (objLogUsuario.EsRepartidor(objRepartidor) == false) {
            return false;
        }

        if (ValidarPaqueteRegistrado(objPaquete) == false) {
            return false;
        }

        objPaquete.setEstadoActualPaquete("EN_TRANSITO");
        objPaquete.setIdRepartidor(objRepartidor);

        objBDPaquetes.ActualizarEstadoYRepartidor(objPaquete);

        Historialpaquetes objHistorial = new Historialpaquetes();
        objHistorial.setIdPaquete(objPaquete);
        objHistorial.setEstado("EN_TRANSITO");
        objHistorial.setFecha(new Date());
        objHistorial.setIdUsuario(objOperador);

        objBDHistorial.InsertarHistorial(objHistorial);

        return true;
    }
}