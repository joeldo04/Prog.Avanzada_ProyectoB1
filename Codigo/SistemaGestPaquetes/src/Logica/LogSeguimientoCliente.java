package Logica;

import BaseDatos.BDEntregas;
import BaseDatos.BDHistorialPaquetes;
import BaseDatos.BDPaquetes;
import Clases.Entregas;
import Clases.Historialpaquetes;
import Clases.Paquetes;
import java.util.List;

public class LogSeguimientoCliente {

    BDPaquetes objBDPaquetes = new BDPaquetes();
    BDHistorialPaquetes objBDHistorial = new BDHistorialPaquetes();
    BDEntregas objBDEntregas = new BDEntregas();

    public Paquetes ConsultarPaqueteSeguimiento(Paquetes objPaquete) {

        if (objPaquete.getNumSeguimiento().length() == 10) {

            List<Paquetes> listaPaquetes = objBDPaquetes.BuscarPaqueteSeguimiento(objPaquete);

            if (listaPaquetes.isEmpty()) {
                return null;
            } else {
                return listaPaquetes.get(0);
            }

        } else {
            return null;
        }
    }

    public List<Historialpaquetes> ConsultarHistorialPaquete(Paquetes objPaquete) {

        if (objPaquete != null) {
            return objBDHistorial.ListarHistorialPaquete(objPaquete);
        } else {
            return null;
        }
    }

    public Entregas ConsultarEntregaPaquete(Paquetes objPaquete) {

        if (objPaquete == null) {
            return null;
        }

        if (objPaquete.getEstadoActualPaquete().equals("ENTREGADO")) {

            List<Historialpaquetes> listaHistorial = objBDHistorial.ListarHistorialPaquete(objPaquete);

            for (Historialpaquetes historial : listaHistorial) {

                if (historial.getEstado().equals("ENTREGADO")) {

                    List<Entregas> listaEntregas = objBDEntregas.BuscarEntregaPorHistorial(historial);

                    if (listaEntregas.isEmpty()) {
                        return null;
                    } else {
                        return listaEntregas.get(0);
                    }
                }
            }

            return null;

        } else {
            return null;
        }
    }
}