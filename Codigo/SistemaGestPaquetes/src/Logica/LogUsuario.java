package Logica;

import BaseDatos.BDUsuarios;
import Clases.Usuarios;
import java.util.List;

public class LogUsuario {

    BDUsuarios objBDUsuarios = new BDUsuarios();

    public boolean ValidarCedula(Usuarios objUsuario) {

        if (objUsuario.getCedula().length() == 10) {
            return true;
        } else {
            return false;
        }
    }

    public Usuarios ConsultarUsuarioCedula(Usuarios objUsuario) {

        if (ValidarCedula(objUsuario) == true) {

            List<Usuarios> listaUsuarios = objBDUsuarios.BuscarUsuarioCedula(objUsuario);

            if (listaUsuarios.isEmpty()) {
                return null;
            } else {
                return listaUsuarios.get(0);
            }

        } else {
            return null;
        }
    }

    public boolean EsRecepcionista(Usuarios objUsuario) {

        if (objUsuario.getIdRol().getNombreRol().equals("RECEPCIONISTA")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EsOperadorDespacho(Usuarios objUsuario) {

        if (objUsuario.getIdRol().getNombreRol().equals("OPERADOR_DESPACHO")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EsRepartidor(Usuarios objUsuario) {

        if (objUsuario.getIdRol().getNombreRol().equals("REPARTIDOR")) {
            return true;
        } else {
            return false;
        }
    }

    public List<Usuarios> ListarRepartidores() {
        return objBDUsuarios.ListarRepartidores();
    }
}