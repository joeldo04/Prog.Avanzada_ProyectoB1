package Presentacion;

import Clases.Clientes;
import Clases.Paquetes;
import Clases.Usuarios;
import Logica.LogCliente;
import Logica.LogRecepcionista;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class MenuRecepcionista extends javax.swing.JFrame {

    Usuarios usuarioActual;

    LogCliente objLogCliente = new LogCliente();
    LogRecepcionista objLogRecepcionista = new LogRecepcionista();

    /**
     * Creates new form MenuRecepcionista
     */
    public MenuRecepcionista() {
        initComponents();
        setSize(950, 580);
        setLocationRelativeTo(null);
        setResizable(false);
        prepararVentana();
        iniciarReloj();
    }

    public MenuRecepcionista(Usuarios usuario) {
        initComponents();
        setSize(950, 580);
        setLocationRelativeTo(null);
        setResizable(false);

        usuarioActual = usuario;

        prepararVentana();
        iniciarReloj();
    }

    public void prepararVentana() {
        txtCodigoUnico.setEditable(false);
        txtNumSeguimiento.setEditable(false);
        txtEstadoPaquete.setEditable(false);

        txtEstadoPaquete.setText("REGISTRADO");
    }

    public void iniciarReloj() {
        actualizarReloj();

        javax.swing.Timer timer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                actualizarReloj();
            }
        });

        timer.start();
    }

    public void actualizarReloj() {
        Date fechaActual = new Date();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        lblReloj.setText(formato.format(fechaActual));
    }

    public void buscarRemitente() {
        Clientes objCliente = new Clientes();
        objCliente.setCedula(txtCedRemitente.getText());

        Clientes clienteEncontrado = objLogCliente.ConsultarClienteCedula(objCliente);

        if (clienteEncontrado == null) {
            JOptionPane.showMessageDialog(this,
                    "Remitente no encontrado. Complete los datos para registrarlo.");
        } else {
            txtNombreRemitente.setText(clienteEncontrado.getNombre());
            txtApellidoRemitente.setText(clienteEncontrado.getApellido());
            txtTelRemitente.setText(clienteEncontrado.getTelefono());
            txtCorreoRemitente.setText(clienteEncontrado.getCorreo());
            txtDireccionRemitente.setText(clienteEncontrado.getDireccion());

            JOptionPane.showMessageDialog(this, "Remitente encontrado.");
        }
    }

    public void buscarDestinatario() {
        Clientes objCliente = new Clientes();
        objCliente.setCedula(txtCedDestinatario1.getText());

        Clientes clienteEncontrado = objLogCliente.ConsultarClienteCedula(objCliente);

        if (clienteEncontrado == null) {
            JOptionPane.showMessageDialog(this,
                    "Destinatario no encontrado. Complete los datos para registrarlo.");
        } else {
            txtNombreDestinatario1.setText(clienteEncontrado.getNombre());
            txtApellidoDestinatario1.setText(clienteEncontrado.getApellido());
            txtTelDestinatario1.setText(clienteEncontrado.getTelefono());
            txtCorreoDestinatario1.setText(clienteEncontrado.getCorreo());
            txtDireccionDestinatario1.setText(clienteEncontrado.getDireccion());

            JOptionPane.showMessageDialog(this, "Destinatario encontrado.");
        }
    }

    public Clientes obtenerRemitente() throws Exception {
        Clientes objCliente = new Clientes();

        objCliente.setCedula(txtCedRemitente.getText());
        objCliente.setNombre(txtNombreRemitente.getText());
        objCliente.setApellido(txtApellidoRemitente.getText());
        objCliente.setTelefono(txtTelRemitente.getText());
        objCliente.setCorreo(txtCorreoRemitente.getText());
        objCliente.setDireccion(txtDireccionRemitente.getText());

        return obtenerCliente(objCliente);
    }

    public Clientes obtenerDestinatario() throws Exception {
        Clientes objCliente = new Clientes();

        objCliente.setCedula(txtCedDestinatario1.getText());
        objCliente.setNombre(txtNombreDestinatario1.getText());
        objCliente.setApellido(txtApellidoDestinatario1.getText());
        objCliente.setTelefono(txtTelDestinatario1.getText());
        objCliente.setCorreo(txtCorreoDestinatario1.getText());
        objCliente.setDireccion(txtDireccionDestinatario1.getText());

        return obtenerCliente(objCliente);
    }

    public Clientes obtenerCliente(Clientes objCliente) throws Exception {
        Clientes clienteEncontrado = objLogCliente.ConsultarClienteCedula(objCliente);

        if (clienteEncontrado == null) {
            boolean registrado = objLogCliente.RegistrarCliente(objCliente);

            if (registrado == true) {
                return objLogCliente.ConsultarClienteCedula(objCliente);
            } else {
                return null;
            }
        } else {
            return clienteEncontrado;
        }
    }

    public void registrarPaquete() {
        try {
            if (usuarioActual == null) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar desde la pantalla principal con una recepcionista.");
                return;
            }

            Clientes remitente = obtenerRemitente();
            Clientes destinatario = obtenerDestinatario();

            if (remitente == null || destinatario == null) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo registrar remitente o destinatario.");
                return;
            }

            Paquetes objPaquete = new Paquetes();

            objPaquete.setIdRemitente(remitente);
            objPaquete.setIdDestinatario(destinatario);
            objPaquete.setPeso(Double.parseDouble(txtPesoKg.getText()));
            objPaquete.setTipoEnvio(txtTipoEnvio.getText());
            objPaquete.setDireccionEntrega(txtDireccionEntrega.getText());

            boolean registrado = objLogRecepcionista.RegistrarPaquete(objPaquete, usuarioActual);

            if (registrado == true) {
                txtCodigoUnico.setText(objPaquete.getCodigoPaquete());
                txtNumSeguimiento.setText(objPaquete.getNumSeguimiento());
                txtEstadoPaquete.setText(objPaquete.getEstadoActualPaquete());

                JOptionPane.showMessageDialog(this,
                        "Paquete registrado correctamente.\n"
                        + "Código: " + objPaquete.getCodigoPaquete() + "\n"
                        + "Seguimiento: " + objPaquete.getNumSeguimiento());
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo registrar el paquete.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El peso debe ser un número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar el paquete.");
        }
    }

    public void limpiarCampos() {
        txtCedRemitente.setText("");
        txtNombreRemitente.setText("");
        txtApellidoRemitente.setText("");
        txtTelRemitente.setText("");
        txtCorreoRemitente.setText("");
        txtDireccionRemitente.setText("");

        txtCedDestinatario1.setText("");
        txtNombreDestinatario1.setText("");
        txtApellidoDestinatario1.setText("");
        txtTelDestinatario1.setText("");
        txtCorreoDestinatario1.setText("");
        txtDireccionDestinatario1.setText("");

        txtCodigoUnico.setText("");
        txtNumSeguimiento.setText("");
        txtPesoKg.setText("");
        txtTipoEnvio.setText("");
        txtDireccionEntrega.setText("");
        txtEstadoPaquete.setText("REGISTRADO");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblReloj = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtCedRemitente = new javax.swing.JTextField();
        txtNombreRemitente = new javax.swing.JTextField();
        txtTelRemitente = new javax.swing.JTextField();
        txtApellidoRemitente = new javax.swing.JTextField();
        txtCorreoRemitente = new javax.swing.JTextField();
        txtDireccionRemitente = new javax.swing.JTextField();
        BuscarRemitente = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtCodigoUnico = new javax.swing.JTextField();
        txtNumSeguimiento = new javax.swing.JTextField();
        txtTipoEnvio = new javax.swing.JTextField();
        txtPesoKg = new javax.swing.JTextField();
        txtDireccionEntrega = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEstadoPaquete = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtCedDestinatario1 = new javax.swing.JTextField();
        txtNombreDestinatario1 = new javax.swing.JTextField();
        txtTelDestinatario1 = new javax.swing.JTextField();
        txtApellidoDestinatario1 = new javax.swing.JTextField();
        txtCorreoDestinatario1 = new javax.swing.JTextField();
        txtDireccionDestinatario1 = new javax.swing.JTextField();
        BuscarDestinatario1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnRegistrarPaquete = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 45, 100));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sistema de Gestión de Paquetes");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Menu Recepcionista - Registro del Paquete");

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\jcvei\\OneDrive\\Escritorio\\4 CICLO COMPUTACION\\Prog.Avanzada_ProyectoB1\\recursos\\logo.png")); // NOI18N
        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 195, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(0, 45, 100));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));

        lblReloj.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblReloj.setForeground(new java.awt.Color(255, 255, 255));
        lblReloj.setText("00/00/0000 - 00:00:00");

        btnVolver.setBackground(new java.awt.Color(0, 45, 100));
        btnVolver.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("< Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel10))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnVolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblReloj))))
                .addGap(17, 17, 17))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel13.setBackground(new java.awt.Color(0, 45, 100));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(10, 59, 119));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("1. Datos del remitente");

        jLabel14.setBackground(new java.awt.Color(102, 102, 102));
        jLabel14.setForeground(new java.awt.Color(128, 128, 128));
        jLabel14.setText("Cedula:");

        jLabel15.setIcon(new javax.swing.ImageIcon("C:\\Users\\jcvei\\OneDrive\\Escritorio\\4 CICLO COMPUTACION\\Prog.Avanzada_ProyectoB1\\recursos\\user.png")); // NOI18N
        jLabel15.setText("jLabel8");

        jLabel16.setBackground(new java.awt.Color(102, 102, 102));
        jLabel16.setForeground(new java.awt.Color(128, 128, 128));
        jLabel16.setText("Nombre:");

        jLabel17.setBackground(new java.awt.Color(102, 102, 102));
        jLabel17.setForeground(new java.awt.Color(128, 128, 128));
        jLabel17.setText("Apellido:");

        jLabel18.setBackground(new java.awt.Color(102, 102, 102));
        jLabel18.setForeground(new java.awt.Color(128, 128, 128));
        jLabel18.setText("Telefono:");

        jLabel19.setBackground(new java.awt.Color(102, 102, 102));
        jLabel19.setForeground(new java.awt.Color(128, 128, 128));
        jLabel19.setText("Dirección:");

        jLabel20.setBackground(new java.awt.Color(102, 102, 102));
        jLabel20.setForeground(new java.awt.Color(128, 128, 128));
        jLabel20.setText("Correo");

        txtCedRemitente.setBackground(new java.awt.Color(255, 255, 255));
        txtCedRemitente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtNombreRemitente.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreRemitente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtTelRemitente.setBackground(new java.awt.Color(255, 255, 255));
        txtTelRemitente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtApellidoRemitente.setBackground(new java.awt.Color(255, 255, 255));
        txtApellidoRemitente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtCorreoRemitente.setBackground(new java.awt.Color(255, 255, 255));
        txtCorreoRemitente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtDireccionRemitente.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccionRemitente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtDireccionRemitente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionRemitenteActionPerformed(evt);
            }
        });

        BuscarRemitente.setBackground(new java.awt.Color(0, 45, 100));
        BuscarRemitente.setForeground(new java.awt.Color(255, 255, 255));
        BuscarRemitente.setText("Buscar Remitente");
        BuscarRemitente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarRemitenteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTelRemitente)
                    .addComponent(txtApellidoRemitente)
                    .addComponent(txtNombreRemitente)
                    .addComponent(txtCorreoRemitente)
                    .addComponent(txtDireccionRemitente)
                    .addComponent(BuscarRemitente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 35, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCedRemitente)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtCedRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarRemitente)))
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtNombreRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtApellidoRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtTelRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtCorreoRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel19)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDireccionRemitente, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel21.setBackground(new java.awt.Color(0, 45, 100));
        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(10, 59, 119));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("3. Información del Paquete");

        jLabel22.setBackground(new java.awt.Color(102, 102, 102));
        jLabel22.setForeground(new java.awt.Color(128, 128, 128));
        jLabel22.setText("Código único:");

        jLabel24.setBackground(new java.awt.Color(102, 102, 102));
        jLabel24.setForeground(new java.awt.Color(128, 128, 128));
        jLabel24.setText("Num. Seguimiento:");

        jLabel25.setBackground(new java.awt.Color(102, 102, 102));
        jLabel25.setForeground(new java.awt.Color(128, 128, 128));
        jLabel25.setText("Peso (kg):");

        jLabel26.setBackground(new java.awt.Color(102, 102, 102));
        jLabel26.setForeground(new java.awt.Color(128, 128, 128));
        jLabel26.setText("Tipo Envío");

        jLabel27.setBackground(new java.awt.Color(102, 102, 102));
        jLabel27.setForeground(new java.awt.Color(128, 128, 128));
        jLabel27.setText("Estado Paquete:");

        jLabel28.setBackground(new java.awt.Color(102, 102, 102));
        jLabel28.setForeground(new java.awt.Color(128, 128, 128));
        jLabel28.setText("Dirección Entrega:");

        txtCodigoUnico.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoUnico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtCodigoUnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoUnicoActionPerformed(evt);
            }
        });

        txtNumSeguimiento.setBackground(new java.awt.Color(255, 255, 255));
        txtNumSeguimiento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtTipoEnvio.setBackground(new java.awt.Color(255, 255, 255));
        txtTipoEnvio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtTipoEnvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoEnvioActionPerformed(evt);
            }
        });

        txtPesoKg.setBackground(new java.awt.Color(255, 255, 255));
        txtPesoKg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtDireccionEntrega.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccionEntrega.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\jcvei\\OneDrive\\Escritorio\\4 CICLO COMPUTACION\\Prog.Avanzada_ProyectoB1\\recursos\\paq.png")); // NOI18N
        jLabel4.setText("jLabel4");

        txtEstadoPaquete.setBackground(new java.awt.Color(255, 255, 255));
        txtEstadoPaquete.setForeground(new java.awt.Color(0, 107, 184));
        txtEstadoPaquete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtEstadoPaquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstadoPaqueteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel21)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtCodigoUnico, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPesoKg, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNumSeguimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTipoEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDireccionEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEstadoPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel21)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtCodigoUnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtNumSeguimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtPesoKg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtTipoEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDireccionEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtEstadoPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel23.setBackground(new java.awt.Color(0, 45, 100));
        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(10, 59, 119));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("2. Datos del destinatario");

        jLabel29.setBackground(new java.awt.Color(102, 102, 102));
        jLabel29.setForeground(new java.awt.Color(128, 128, 128));
        jLabel29.setText("Cedula:");

        jLabel30.setBackground(new java.awt.Color(102, 102, 102));
        jLabel30.setForeground(new java.awt.Color(128, 128, 128));
        jLabel30.setText("Nombre:");

        jLabel31.setBackground(new java.awt.Color(102, 102, 102));
        jLabel31.setForeground(new java.awt.Color(128, 128, 128));
        jLabel31.setText("Apellido:");

        jLabel32.setBackground(new java.awt.Color(102, 102, 102));
        jLabel32.setForeground(new java.awt.Color(128, 128, 128));
        jLabel32.setText("Telefono:");

        jLabel33.setBackground(new java.awt.Color(102, 102, 102));
        jLabel33.setForeground(new java.awt.Color(128, 128, 128));
        jLabel33.setText("Dirección:");

        jLabel34.setBackground(new java.awt.Color(102, 102, 102));
        jLabel34.setForeground(new java.awt.Color(128, 128, 128));
        jLabel34.setText("Correo");

        txtCedDestinatario1.setBackground(new java.awt.Color(255, 255, 255));
        txtCedDestinatario1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtNombreDestinatario1.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreDestinatario1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtTelDestinatario1.setBackground(new java.awt.Color(255, 255, 255));
        txtTelDestinatario1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtApellidoDestinatario1.setBackground(new java.awt.Color(255, 255, 255));
        txtApellidoDestinatario1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtCorreoDestinatario1.setBackground(new java.awt.Color(255, 255, 255));
        txtCorreoDestinatario1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txtDireccionDestinatario1.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccionDestinatario1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtDireccionDestinatario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionDestinatario1ActionPerformed(evt);
            }
        });

        BuscarDestinatario1.setBackground(new java.awt.Color(0, 45, 100));
        BuscarDestinatario1.setForeground(new java.awt.Color(255, 255, 255));
        BuscarDestinatario1.setText("Buscar Destinatario");
        BuscarDestinatario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarDestinatario1ActionPerformed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\jcvei\\OneDrive\\Escritorio\\4 CICLO COMPUTACION\\Prog.Avanzada_ProyectoB1\\recursos\\cliente.png")); // NOI18N
        jLabel11.setText("jLabel9");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTelDestinatario1)
                    .addComponent(txtApellidoDestinatario1)
                    .addComponent(txtNombreDestinatario1)
                    .addComponent(txtCorreoDestinatario1)
                    .addComponent(txtDireccionDestinatario1)
                    .addComponent(BuscarDestinatario1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(0, 35, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCedDestinatario1)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtCedDestinatario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuscarDestinatario1))
                    .addComponent(jLabel11))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtNombreDestinatario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtApellidoDestinatario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtTelDestinatario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtCorreoDestinatario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel33)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDireccionDestinatario1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );

        btnRegistrarPaquete.setBackground(new java.awt.Color(235, 207, 62));
        btnRegistrarPaquete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRegistrarPaquete.setText("Registrar Paquete");
        btnRegistrarPaquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarPaqueteActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(0, 45, 100));
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrarPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 285, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 285, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrarPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BuscarRemitenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarRemitenteActionPerformed
          buscarRemitente();
    }//GEN-LAST:event_BuscarRemitenteActionPerformed

    private void txtDireccionRemitenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionRemitenteActionPerformed
        buscarDestinatario();
    }//GEN-LAST:event_txtDireccionRemitenteActionPerformed

    private void txtDireccionDestinatario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionDestinatario1ActionPerformed
        registrarPaquete();
    }//GEN-LAST:event_txtDireccionDestinatario1ActionPerformed

    private void BuscarDestinatario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarDestinatario1ActionPerformed
        buscarDestinatario();
    }//GEN-LAST:event_BuscarDestinatario1ActionPerformed

    private void txtCodigoUnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoUnicoActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_txtCodigoUnicoActionPerformed

    private void txtEstadoPaqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoPaqueteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstadoPaqueteActionPerformed

    private void btnRegistrarPaqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarPaqueteActionPerformed
        registrarPaquete();
        limpiarCampos();
    }//GEN-LAST:event_btnRegistrarPaqueteActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
         limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        Inicio ventana = new Inicio();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void txtTipoEnvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoEnvioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoEnvioActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BuscarDestinatario1;
    private javax.swing.JButton BuscarRemitente;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrarPaquete;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblReloj;
    private javax.swing.JTextField txtApellidoDestinatario1;
    private javax.swing.JTextField txtApellidoRemitente;
    private javax.swing.JTextField txtCedDestinatario1;
    private javax.swing.JTextField txtCedRemitente;
    private javax.swing.JTextField txtCodigoUnico;
    private javax.swing.JTextField txtCorreoDestinatario1;
    private javax.swing.JTextField txtCorreoRemitente;
    private javax.swing.JTextField txtDireccionDestinatario1;
    private javax.swing.JTextField txtDireccionEntrega;
    private javax.swing.JTextField txtDireccionRemitente;
    private javax.swing.JTextField txtEstadoPaquete;
    private javax.swing.JTextField txtNombreDestinatario1;
    private javax.swing.JTextField txtNombreRemitente;
    private javax.swing.JTextField txtNumSeguimiento;
    private javax.swing.JTextField txtPesoKg;
    private javax.swing.JTextField txtTelDestinatario1;
    private javax.swing.JTextField txtTelRemitente;
    private javax.swing.JTextField txtTipoEnvio;
    // End of variables declaration//GEN-END:variables

}
