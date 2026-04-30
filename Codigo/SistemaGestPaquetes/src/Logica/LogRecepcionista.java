package Logica;

import BaseDatos.BDHistorialPaquetes;
import BaseDatos.BDPaquetes;
import Clases.Historialpaquetes;
import Clases.Paquetes;
import Clases.Usuarios;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class LogRecepcionista {

    BDPaquetes objBDPaquetes = new BDPaquetes();
    BDHistorialPaquetes objBDHistorial = new BDHistorialPaquetes();
    LogUsuario objLogUsuario = new LogUsuario();

    public boolean ValidarDatosPaquete(Paquetes objPaquete) {

        if (objPaquete.getIdRemitente() == null) {
            return false;
        }

        if (objPaquete.getIdDestinatario() == null) {
            return false;
        }

        if (objPaquete.getPeso() <= 0) {
            return false;
        }

        if (objPaquete.getTipoEnvio() == null) {
            return false;
        }

        if (objPaquete.getTipoEnvio().length() == 0) {
            return false;
        }

        if (objPaquete.getDireccionEntrega() == null) {
            return false;
        }

        if (objPaquete.getDireccionEntrega().length() == 0) {
            return false;
        }

        return true;
    }

    public String GenerarCodigoPaquete() {

        Random aleatorio = new Random();
        String codigo = "";
        boolean repetido = true;

        while (repetido == true) {

            int numero = 10000 + aleatorio.nextInt(90000);
            codigo = "PKT" + numero;

            Paquetes objPaquete = new Paquetes();
            objPaquete.setCodigoPaquete(codigo);

            List<Paquetes> lista = objBDPaquetes.BuscarPaqueteCodigo(objPaquete);

            if (lista.isEmpty()) {
                repetido = false;
            }
        }

        return codigo;
    }

    public String GenerarNumeroSeguimiento() {

        Random aleatorio = new Random();
        String numeroSeguimiento = "";
        boolean repetido = true;

        while (repetido == true) {

            int parteUno = 10000 + aleatorio.nextInt(90000);
            int parteDos = 10000 + aleatorio.nextInt(90000);

            numeroSeguimiento = "" + parteUno + parteDos;

            Paquetes objPaquete = new Paquetes();
            objPaquete.setNumSeguimiento(numeroSeguimiento);

            List<Paquetes> lista = objBDPaquetes.BuscarPaqueteSeguimiento(objPaquete);

            if (lista.isEmpty()) {
                repetido = false;
            }
        }

        return numeroSeguimiento;
    }

    public boolean RegistrarPaquete(Paquetes objPaquete, Usuarios objRecepcionista) throws Exception {

        if (objLogUsuario.EsRecepcionista(objRecepcionista) == false) {
            return false;
        }

        if (ValidarDatosPaquete(objPaquete) == false) {
            return false;
        }

        objPaquete.setCodigoPaquete(GenerarCodigoPaquete());
        objPaquete.setNumSeguimiento(GenerarNumeroSeguimiento());
        objPaquete.setEstadoActualPaquete("REGISTRADO");
        objPaquete.setFechaRegistro(new Date());
        objPaquete.setIdRepartidor(null);

        objBDPaquetes.InsertarPaquete(objPaquete);

        Historialpaquetes objHistorial = new Historialpaquetes();
        objHistorial.setIdPaquete(objPaquete);
        objHistorial.setEstado("REGISTRADO");
        objHistorial.setFecha(new Date());
        objHistorial.setIdUsuario(objRecepcionista);

        objBDHistorial.InsertarHistorial(objHistorial);

        return true;
    }
}