package Logica;

import BaseDatos.BDClientes;
import Clases.Clientes;
import java.util.List;

public class LogCliente {

    BDClientes objBDClientes = new BDClientes();

    public boolean ValidarCedula(Clientes objCliente) {

        if (objCliente.getCedula().length() == 10) {
            return true;
        } else {
            return false;
        }
    }

    public Clientes ConsultarClienteCedula(Clientes objCliente) {

        if (ValidarCedula(objCliente) == true) {

            List<Clientes> listaClientes = objBDClientes.BuscarClienteCedula(objCliente);

            if (listaClientes.isEmpty()) {
                return null;
            } else {
                return listaClientes.get(0);
            }

        } else {
            return null;
        }
    }

    public boolean RegistrarCliente(Clientes objCliente) throws Exception {

        if (ValidarCedula(objCliente) == true) {

            Clientes clienteEncontrado = ConsultarClienteCedula(objCliente);

            if (clienteEncontrado == null) {
                objBDClientes.InsertarCliente(objCliente);
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }
}