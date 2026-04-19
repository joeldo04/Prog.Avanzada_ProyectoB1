package Presentacion;

import BaseDatos.ClienteBD;
import Clases.Cliente;
import java.sql.ResultSet;

public class Presentar {
    public static void main(String[] args) {
        try {
            Cliente objCliente = new Cliente();
            ClienteBD bdCliente = new ClienteBD();

            objCliente.setCedula("1150545448");
            objCliente.setNombre("Joel");
            objCliente.setApellido("Dominguez");
            objCliente.setDireccion("Loja");
            objCliente.setTelefono("0988662820");
            objCliente.setCorreo("joeladominguez@gmail.com");

            int resultado = bdCliente.InsertarCliente(objCliente);

            if (resultado > 0) {
                System.out.println("Cliente guardado correctamente");
            }

            ResultSet rs = bdCliente.BuscarClientexCedula(objCliente);

            if (rs.next()) {
                System.out.println("Cliente encontrado:");
                System.out.println("ID: " + rs.getInt("idCliente"));
                System.out.println("Cedula: " + rs.getString("cedula"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Apellido: " + rs.getString("apellido"));
                System.out.println("Direccion: " + rs.getString("direccion"));
                System.out.println("Telefono: " + rs.getString("telefono"));
                System.out.println("Correo: " + rs.getString("correo"));
            } else {
                System.out.println("Cliente no encontrado");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}