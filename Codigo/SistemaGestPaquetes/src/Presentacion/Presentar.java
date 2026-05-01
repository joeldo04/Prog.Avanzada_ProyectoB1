package Presentacion;

import Clases.Clientes;
import Clases.Entregas;
import Clases.Historialpaquetes;
import Clases.Paquetes;
import Clases.Usuarios;
import Logica.LogCliente;
import Logica.LogOperadorDespacho;
import Logica.LogRecepcionista;
import Logica.LogRepartidor;
import Logica.LogSeguimientoCliente;
import Logica.LogUsuario;
import java.util.List;
import java.util.Scanner;

public class Presentar {

    static Scanner sc = new Scanner(System.in);

    static LogUsuario objLogUsuario = new LogUsuario();
    static LogCliente objLogCliente = new LogCliente();
    static LogRecepcionista objLogRecepcionista = new LogRecepcionista();
    static LogOperadorDespacho objLogOperador = new LogOperadorDespacho();
    static LogRepartidor objLogRepartidor = new LogRepartidor();
    static LogSeguimientoCliente objLogSeguimiento = new LogSeguimientoCliente();

    public static void main(String[] args) throws Exception {

        int opcion = 0;

        while (opcion != 3) {

            System.out.println("|    SISTEMA DE GESTION DE PAQUETES   |");
            System.out.println("1. Usuario del sistema");
            System.out.println("2. Cliente seguimiento");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                ingresoUsuarioSistema();
            } else if (opcion == 2) {
                consultaCliente();
            } else if (opcion == 3) {
                System.out.println("Sistema finalizado.");
            } else {
                System.out.println("Opcion incorrecta.");
            }
        }
    }

    public static void ingresoUsuarioSistema() throws Exception {

        Usuarios objUsuario = new Usuarios();

        System.out.print("Ingrese su cedula: ");
        objUsuario.setCedula(sc.nextLine());

        Usuarios usuarioEncontrado = objLogUsuario.ConsultarUsuarioCedula(objUsuario);

        if (usuarioEncontrado == null) {
            System.out.println("Usuario no encontrado o cedula incorrecta.");
        } else {
            System.out.println("Bienvenido: " + usuarioEncontrado.getNombre() + " " + usuarioEncontrado.getApellido());
            System.out.println("Rol: " + usuarioEncontrado.getIdRol().getNombreRol());

            if (objLogUsuario.EsRecepcionista(usuarioEncontrado)) {
                menuRecepcionista(usuarioEncontrado);
            } else if (objLogUsuario.EsOperadorDespacho(usuarioEncontrado)) {
                menuOperador(usuarioEncontrado);
            } else if (objLogUsuario.EsRepartidor(usuarioEncontrado)) {
                menuRepartidor(usuarioEncontrado);
            }
        }
    }

    public static void menuRecepcionista(Usuarios recepcionista) throws Exception {

        int opcion = 0;

        while (opcion != 3) {

            System.out.println("\n===== MENU RECEPCIONISTA =====");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Registrar paquete");
            System.out.println("3. Volver");
            System.out.print("Seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                Clientes cliente = pedirDatosCliente();
                boolean registrado = objLogCliente.RegistrarCliente(cliente);

                if (registrado) {
                    System.out.println("Cliente registrado correctamente.");
                } else {
                    System.out.println("No se pudo registrar. Cedula incorrecta o cliente ya existe.");
                }

            } else if (opcion == 2) {
                registrarPaquete(recepcionista);
            }
        }
    }

    public static void registrarPaquete(Usuarios recepcionista) throws Exception {

        System.out.println("\n--- Datos del remitente ---");
        Clientes remitente = buscarORegistrarCliente();

        System.out.println("\n--- Datos del destinatario ---");
        Clientes destinatario = buscarORegistrarCliente();

        Paquetes paquete = new Paquetes();

        paquete.setIdRemitente(remitente);
        paquete.setIdDestinatario(destinatario);

        System.out.print("Peso del paquete: ");
        paquete.setPeso(Double.parseDouble(sc.nextLine()));

        System.out.print("Tipo de envio: ");
        paquete.setTipoEnvio(sc.nextLine());

        System.out.print("Direccion de entrega: ");
        paquete.setDireccionEntrega(sc.nextLine());

        boolean registrado = objLogRecepcionista.RegistrarPaquete(paquete, recepcionista);

        if (registrado) {
            System.out.println("Paquete registrado correctamente.");
            System.out.println("Codigo paquete: " + paquete.getCodigoPaquete());
            System.out.println("Numero seguimiento: " + paquete.getNumSeguimiento());
        } else {
            System.out.println("No se pudo registrar el paquete.");
        }
    }

    public static Clientes buscarORegistrarCliente() throws Exception {

        Clientes clienteBuscar = new Clientes();

        System.out.print("Cedula del cliente: ");
        clienteBuscar.setCedula(sc.nextLine());

        Clientes clienteEncontrado = objLogCliente.ConsultarClienteCedula(clienteBuscar);

        if (clienteEncontrado != null) {
            System.out.println("Cliente encontrado: " + clienteEncontrado.getNombre() + " " + clienteEncontrado.getApellido());
            return clienteEncontrado;
        } else {
            System.out.println("Cliente no existe. Ingrese los datos para registrarlo.");

            Clientes nuevoCliente = new Clientes();
            nuevoCliente.setCedula(clienteBuscar.getCedula());

            System.out.print("Nombre: ");
            nuevoCliente.setNombre(sc.nextLine());

            System.out.print("Apellido: ");
            nuevoCliente.setApellido(sc.nextLine());

            System.out.print("Telefono: ");
            nuevoCliente.setTelefono(sc.nextLine());

            System.out.print("Correo: ");
            nuevoCliente.setCorreo(sc.nextLine());

            System.out.print("Direccion: ");
            nuevoCliente.setDireccion(sc.nextLine());

            boolean registrado = objLogCliente.RegistrarCliente(nuevoCliente);

            if (registrado) {
                return objLogCliente.ConsultarClienteCedula(nuevoCliente);
            } else {
                return null;
            }
        }
    }

    public static Clientes pedirDatosCliente() {

        Clientes cliente = new Clientes();

        System.out.print("Cedula: ");
        cliente.setCedula(sc.nextLine());

        System.out.print("Nombre: ");
        cliente.setNombre(sc.nextLine());

        System.out.print("Apellido: ");
        cliente.setApellido(sc.nextLine());

        System.out.print("Telefono: ");
        cliente.setTelefono(sc.nextLine());

        System.out.print("Correo: ");
        cliente.setCorreo(sc.nextLine());

        System.out.print("Direccion: ");
        cliente.setDireccion(sc.nextLine());

        return cliente;
    }

    public static void menuOperador(Usuarios operador) throws Exception {

        int opcion = 0;

        while (opcion != 2) {

            System.out.println("\n===== MENU OPERADOR DE DESPACHO =====");
            System.out.println("1. Despachar paquete");
            System.out.println("2. Volver");
            System.out.print("Seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                despacharPaquete(operador);
            }
        }
    }

    public static void despacharPaquete(Usuarios operador) throws Exception {

        Paquetes paqueteBuscar = new Paquetes();

        System.out.print("Ingrese codigo del paquete: ");
        paqueteBuscar.setCodigoPaquete(sc.nextLine());

        Paquetes paquete = objLogOperador.ConsultarPaqueteCodigo(paqueteBuscar);

        if (paquete == null) {
            System.out.println("Paquete no encontrado.");
            return;
        }

        System.out.println("Paquete encontrado.");
        System.out.println("Estado actual: " + paquete.getEstadoActualPaquete());

        Usuarios repartidorBuscar = new Usuarios();

        System.out.print("Ingrese cedula del repartidor: ");
        repartidorBuscar.setCedula(sc.nextLine());

        Usuarios repartidor = objLogUsuario.ConsultarUsuarioCedula(repartidorBuscar);

        if (repartidor == null) {
            System.out.println("Repartidor no encontrado.");
            return;
        }

        boolean despachado = objLogOperador.DespacharPaquete(paquete, operador, repartidor);

        if (despachado) {
            System.out.println("Paquete despachado correctamente.");
        } else {
            System.out.println("No se pudo despachar. Verifique estado o rol del repartidor.");
        }
    }

    public static void menuRepartidor(Usuarios repartidor) throws Exception {

        int opcion = 0;

        while (opcion != 3) {

            System.out.println("\n===== MENU REPARTIDOR =====");
            System.out.println("1. Ver paquetes asignados");
            System.out.println("2. Registrar entrega");
            System.out.println("3. Volver");
            System.out.print("Seleccione una opcion: ");
            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                listarPaquetesAsignados(repartidor);
            } else if (opcion == 2) {
                registrarEntrega(repartidor);
            }
        }
    }

    public static void listarPaquetesAsignados(Usuarios repartidor) {

        List<Paquetes> lista = objLogRepartidor.ListarPaquetesAsignados(repartidor);

        if (lista == null || lista.isEmpty()) {
            System.out.println("No tiene paquetes asignados.");
        } else {
            System.out.println("\n--- Paquetes asignados ---");

            for (Paquetes p : lista) {
                System.out.println("Codigo: " + p.getCodigoPaquete()
                        + " | Seguimiento: " + p.getNumSeguimiento()
                        + " | Estado: " + p.getEstadoActualPaquete()
                        + " | Direccion: " + p.getDireccionEntrega());
            }
        }
    }

    public static void registrarEntrega(Usuarios repartidor) throws Exception {

        List<Paquetes> lista = objLogRepartidor.ListarPaquetesAsignados(repartidor);

        if (lista == null || lista.isEmpty()) {
            System.out.println("No tiene paquetes para entregar.");
            return;
        }

        System.out.print("Ingrese codigo del paquete a entregar: ");
        String codigo = sc.nextLine();

        Paquetes paqueteSeleccionado = null;

        for (Paquetes p : lista) {
            if (p.getCodigoPaquete().equals(codigo)) {
                paqueteSeleccionado = p;
            }
        }

        if (paqueteSeleccionado == null) {
            System.out.println("Ese paquete no esta asignado a usted.");
            return;
        }

        System.out.print("Nombre de quien recibe: ");
        String nombreRecibe = sc.nextLine();

        System.out.print("Observaciones: ");
        String observaciones = sc.nextLine();

        boolean entregado = objLogRepartidor.RegistrarEntrega(
                paqueteSeleccionado,
                repartidor,
                nombreRecibe,
                observaciones
        );

        if (entregado) {
            System.out.println("Entrega registrada correctamente.");
        } else {
            System.out.println("No se pudo registrar la entrega.");
        }
    }

    public static void consultaCliente() {

        Paquetes paqueteBuscar = new Paquetes();

        System.out.print("Ingrese numero de seguimiento: ");
        paqueteBuscar.setNumSeguimiento(sc.nextLine());

        Paquetes paquete = objLogSeguimiento.ConsultarPaqueteSeguimiento(paqueteBuscar);

        if (paquete == null) {
            System.out.println("No existe paquete con ese numero de seguimiento.");
            return;
        }

        System.out.println("\n===== INFORMACION DEL PAQUETE =====");
        System.out.println("Codigo: " + paquete.getCodigoPaquete());
        System.out.println("Numero seguimiento: " + paquete.getNumSeguimiento());
        System.out.println("Estado actual: " + paquete.getEstadoActualPaquete());
        System.out.println("Direccion entrega: " + paquete.getDireccionEntrega());

        List<Historialpaquetes> historial = objLogSeguimiento.ConsultarHistorialPaquete(paquete);

        System.out.println("\n--- Historial ---");

        for (Historialpaquetes h : historial) {
            System.out.println(h.getEstado() + " | " + h.getFecha()
                    + " | Usuario: " + h.getIdUsuario().getNombre()
                    + " " + h.getIdUsuario().getApellido());
        }

        Entregas entrega = objLogSeguimiento.ConsultarEntregaPaquete(paquete);

        if (entrega != null) {
            System.out.println("\n--- Entrega final ---");
            System.out.println("Recibido por: " + entrega.getNombreRecibe());
            System.out.println("Observaciones: " + entrega.getObservaciones());
        }
    }
}
