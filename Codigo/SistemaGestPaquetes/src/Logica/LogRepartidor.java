package Logica;

import BaseDatos.BDEntregas;
import BaseDatos.BDHistorialPaquetes;
import BaseDatos.BDPaquetes;
import Clases.Entregas;
import Clases.Historialpaquetes;
import Clases.Paquetes;
import Clases.Usuarios;
import java.util.Date;
import java.util.List;

public class LogRepartidor {

    BDPaquetes objBDPaquetes = new BDPaquetes();
    BDHistorialPaquetes objBDHistorial = new BDHistorialPaquetes();
    BDEntregas objBDEntregas = new BDEntregas();
    LogUsuario objLogUsuario = new LogUsuario();

    public List<Paquetes> ListarPaquetesAsignados(Usuarios objRepartidor) {

        if (objLogUsuario.EsRepartidor(objRepartidor) == true) {
            return objBDPaquetes.ListarPaquetesAsignados(objRepartidor);
        } else {
            return null;
        }
    }

    public boolean ValidarPaqueteEnTransito(Paquetes objPaquete) {

        if (objPaquete == null) {
            return false;
        }
        if (objPaquete.getEstadoActualPaquete().equals("EN_TRANSITO")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ValidarPaqueteAsignado(Paquetes objPaquete, Usuarios objRepartidor) {

        if (objPaquete.getIdRepartidor() == null) {
            return false;
        }
        if (objPaquete.getIdRepartidor().getIdUsuario().equals(objRepartidor.getIdUsuario())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ValidarDatosEntrega(String nombreRecibe) {

        if (nombreRecibe == null) {
            return false;
        }
        if (nombreRecibe.length() == 0) {
            return false;
        }
        return true;
    }

    public Paquetes BuscarPaqueteAsignadoPorCodigo(Usuarios objRepartidor, String codigoPaquete) {

        List<Paquetes> lista = ListarPaquetesAsignados(objRepartidor);

        if (lista == null) {
            return null;
        }
        for (Paquetes p : lista) {
            if (p.getCodigoPaquete().equals(codigoPaquete)) {
                return p;
            }
        }
        return null;
    }

    public boolean RegistrarEntrega(Paquetes objPaquete, Usuarios objRepartidor,
            String nombreRecibe, String observaciones) throws Exception {

        if (objLogUsuario.EsRepartidor(objRepartidor) == false) {
            return false;
        }
        if (ValidarPaqueteEnTransito(objPaquete) == false) {
            return false;
        }
        if (ValidarPaqueteAsignado(objPaquete, objRepartidor) == false) {
            return false;
        }
        if (ValidarDatosEntrega(nombreRecibe) == false) {
            return false;
        }

        objPaquete.setEstadoActualPaquete("ENTREGADO");

        objBDPaquetes.ActualizarEstadoPaquete(objPaquete);

        Historialpaquetes objHistorial = new Historialpaquetes();
        objHistorial.setIdPaquete(objPaquete);
        objHistorial.setEstado("ENTREGADO");
        objHistorial.setFecha(new Date());
        objHistorial.setIdUsuario(objRepartidor);

        objHistorial = objBDHistorial.InsertarHistorial(objHistorial);

        Entregas objEntrega = new Entregas();
        objEntrega.setNombreRecibe(nombreRecibe);
        objEntrega.setObservaciones(observaciones);
        objEntrega.setIdHistoriaPaq(objHistorial);

        objBDEntregas.InsertarEntrega(objEntrega);
        return true;
    }
}
