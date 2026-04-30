package Presentacion;

import BaseDatos.ClientesJpaController;
import BaseDatos.EntregasJpaController;
import BaseDatos.HistorialpaquetesJpaController;
import BaseDatos.PaquetesJpaController;
import BaseDatos.RolessistemaJpaController;
import BaseDatos.UsuariosJpaController;
import Clases.Clientes;
import Clases.Entregas;
import Clases.Historialpaquetes;
import Clases.Paquetes;
import Clases.Rolessistema;
import Clases.Usuarios;
import java.util.Date;

public class Presentar {

    public static void main(String[] args)throws Exception {

        RolessistemaJpaController objRolJpa = new RolessistemaJpaController();
        UsuariosJpaController objUsuarioJpa = new UsuariosJpaController();
        ClientesJpaController objClienteJpa = new ClientesJpaController();
        PaquetesJpaController objPaqueteJpa = new PaquetesJpaController();
        HistorialpaquetesJpaController objHistorialJpa = new HistorialpaquetesJpaController();
        EntregasJpaController objEntregaJpa = new EntregasJpaController();

        // Roles ya existentes en la base de datos
        Rolessistema rolRecepcionista = objRolJpa.findRolessistema(1);
        Rolessistema rolOperador = objRolJpa.findRolessistema(2);
        Rolessistema rolRepartidor = objRolJpa.findRolessistema(3);

        // Usuarios 
        Usuarios usuarioRecepcionista = new Usuarios();
        usuarioRecepcionista.setCedula("1105555555");
        usuarioRecepcionista.setNombre("Carlos");
        usuarioRecepcionista.setApellido("Ochoa");
        usuarioRecepcionista.setTelefono("0995555555");
        usuarioRecepcionista.setCorreo("carlos.ochoa@gm.com");
        usuarioRecepcionista.setIdRol(rolRecepcionista);
        objUsuarioJpa.create(usuarioRecepcionista);

        Usuarios usuarioOperador = new Usuarios();
        usuarioOperador.setCedula("1106666666");
        usuarioOperador.setNombre("Vicente");
        usuarioOperador.setApellido("Torres");
        usuarioOperador.setTelefono("0996666666");
        usuarioOperador.setCorreo("vicente.torres@gm.com");
        usuarioOperador.setIdRol(rolOperador);
        objUsuarioJpa.create(usuarioOperador);

        Usuarios usuarioRepartidor = new Usuarios();
        usuarioRepartidor.setCedula("1107777777");
        usuarioRepartidor.setNombre("Maria");
        usuarioRepartidor.setApellido("Perez");
        usuarioRepartidor.setTelefono("0997777777");
        usuarioRepartidor.setCorreo("maria.perez@gm.com");
        usuarioRepartidor.setIdRol(rolRepartidor);
        objUsuarioJpa.create(usuarioRepartidor);

        // Clientes 
        Clientes remitente = new Clientes();
        remitente.setCedula("1155555555");
        remitente.setNombre("David");
        remitente.setApellido("Lopez");
        remitente.setTelefono("0985555555");
        remitente.setCorreo("david.lopez@gmail.com");
        remitente.setDireccion("Loja, El Valle");
        objClienteJpa.create(remitente);

        Clientes destinatario = new Clientes();
        destinatario.setCedula("1166666666");
        destinatario.setNombre("Sofia");
        destinatario.setApellido("Castillo");
        destinatario.setTelefono("0986666666");
        destinatario.setCorreo("sofia.castillo@gmail.com");
        destinatario.setDireccion("Loja, Centro");
        objClienteJpa.create(destinatario);

        // Paquete registrado
        Paquetes paquete = new Paquetes();
        paquete.setCodigoPaquete("PKT777");
        paquete.setNumSeguimiento("SEG777");
        paquete.setIdRemitente(remitente);
        paquete.setIdDestinatario(destinatario);
        paquete.setPeso(3.7);
        paquete.setTipoEnvio("FRAGIL");
        paquete.setDireccionEntrega("Loja, Centro");
        paquete.setEstadoActualPaquete("REGISTRADO");
        paquete.setFechaRegistro(new Date());
        paquete.setIdRepartidor(null);
        objPaqueteJpa.create(paquete);

        Historialpaquetes historial1 = new Historialpaquetes();
        historial1.setIdPaquete(paquete);
        historial1.setEstado("REGISTRADO");
        historial1.setFecha(new Date());
        historial1.setIdUsuario(usuarioRecepcionista);
        objHistorialJpa.create(historial1);

        // Paquete en tránsito
        paquete.setEstadoActualPaquete("EN_TRANSITO");
        paquete.setIdRepartidor(usuarioRepartidor);
        objPaqueteJpa.edit(paquete);

        Historialpaquetes historial2 = new Historialpaquetes();
        historial2.setIdPaquete(paquete);
        historial2.setEstado("EN_TRANSITO");
        historial2.setFecha(new Date());
        historial2.setIdUsuario(usuarioOperador);
        objHistorialJpa.create(historial2);

        // Paquete entregado
        paquete.setEstadoActualPaquete("ENTREGADO");
        objPaqueteJpa.edit(paquete);

        Historialpaquetes historial3 = new Historialpaquetes();
        historial3.setIdPaquete(paquete);
        historial3.setEstado("ENTREGADO");
        historial3.setFecha(new Date());
        historial3.setIdUsuario(usuarioRepartidor);
        objHistorialJpa.create(historial3);

        Entregas entrega = new Entregas();
        entrega.setNombreRecibe("Maria Castillo - Mamá de Sofia Castillo");
        entrega.setObservaciones("Entrega realizada correctamente.");
        entrega.setIdHistoriaPaq(historial3);
        objEntregaJpa.create(entrega);

        System.out.println("Prueba realizada correctamente.");
    }
}