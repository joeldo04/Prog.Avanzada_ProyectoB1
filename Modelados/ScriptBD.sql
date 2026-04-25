DROP DATABASE IF EXISTS BDSistemaPaquetes;
CREATE DATABASE BDSistemaPaquetes
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE BDSistemaPaquetes;

CREATE TABLE RolesSistema (
    idRol INT AUTO_INCREMENT PRIMARY KEY,
    nombreRol VARCHAR(60) NOT NULL UNIQUE
) ENGINE = InnoDB;

CREATE TABLE Usuarios (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(15) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    idRol INT NOT NULL,

    CONSTRAINT fkUsuariosRolesSistema
        FOREIGN KEY (idRol)
        REFERENCES RolesSistema(idRol)
) ENGINE = InnoDB;

CREATE TABLE Clientes (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(15) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    direccion VARCHAR(250) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE Paquetes (
    idPaquete INT AUTO_INCREMENT PRIMARY KEY,
    codigoPaquete VARCHAR(10) NOT NULL UNIQUE,
    numSeguimiento VARCHAR(10) NOT NULL UNIQUE,

    idRemitente INT NOT NULL,
    idDestinatario INT NOT NULL,

    peso DOUBLE NOT NULL,
    tipoEnvio VARCHAR(50) NOT NULL,
    direccionEntrega VARCHAR(250) NOT NULL,
    estadoActualPaquete VARCHAR(50) NOT NULL DEFAULT 'REGISTRADO',
    fechaRegistro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    idRepartidor INT NULL,

    CONSTRAINT fkPaquetesRemitente
        FOREIGN KEY (idRemitente)
        REFERENCES Clientes(idCliente),

    CONSTRAINT fkPaquetesDestinatario
        FOREIGN KEY (idDestinatario)
        REFERENCES Clientes(idCliente),

    CONSTRAINT fkPaquetesRepartidor
        FOREIGN KEY (idRepartidor)
        REFERENCES Usuarios(idUsuario)
) ENGINE = InnoDB;

CREATE TABLE HistorialPaquetes (
    idHistorial INT AUTO_INCREMENT PRIMARY KEY,
    idPaquete INT NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idUsuario INT NOT NULL,

    CONSTRAINT fkHistorialPaquetesPaquetes
        FOREIGN KEY (idPaquete)
        REFERENCES Paquetes(idPaquete),

    CONSTRAINT fkHistorialPaquetesUsuarios
        FOREIGN KEY (idUsuario)
        REFERENCES Usuarios(idUsuario)
) ENGINE = InnoDB;

CREATE TABLE Entregas (
    idEntrega INT AUTO_INCREMENT PRIMARY KEY,
    nombreRecibe VARCHAR(80) NOT NULL,
    observaciones VARCHAR(250),
    idHistoriaPaq INT NOT NULL UNIQUE,

    CONSTRAINT fkEntregasHistorialPaquetes
        FOREIGN KEY (idHistoriaPaq)
        REFERENCES HistorialPaquetes(idHistorial)
) ENGINE = InnoDB;