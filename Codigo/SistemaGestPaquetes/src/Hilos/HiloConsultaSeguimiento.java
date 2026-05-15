package Hilos;

import Clases.Entregas;
import Clases.Historialpaquetes;
import Clases.Paquetes;
import Logica.LogSeguimientoCliente;
import java.util.List;

public class HiloConsultaSeguimiento extends Thread {

    private String numeroSeguimiento;
    private Paquetes paqueteEncontrado;
    private List<Historialpaquetes> historial;
    private Entregas entrega;

    public HiloConsultaSeguimiento(String numeroSeguimiento) {
        this.numeroSeguimiento = numeroSeguimiento;
    }

    @Override
    public void run() {
        LogSeguimientoCliente objLogSeguimiento = new LogSeguimientoCliente();

        Paquetes objPaquete = new Paquetes();
        objPaquete.setNumSeguimiento(numeroSeguimiento);

        paqueteEncontrado = objLogSeguimiento.ConsultarPaqueteSeguimiento(objPaquete);

        if (paqueteEncontrado != null) {
            historial = objLogSeguimiento.ConsultarHistorialPaquete(paqueteEncontrado);
            entrega = objLogSeguimiento.ConsultarEntregaPaquete(paqueteEncontrado);
        }
    }

    public Paquetes getPaqueteEncontrado() {
        return paqueteEncontrado;
    }

    public List<Historialpaquetes> getHistorial() {
        return historial;
    }

    public Entregas getEntrega() {
        return entrega;
    }
}