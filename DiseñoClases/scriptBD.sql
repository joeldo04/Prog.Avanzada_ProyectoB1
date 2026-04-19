DROP DATABASE IF EXISTS gestionPaquetes;
CREATE DATABASE gestionPaquetes
CHARACTER SET utf8mb4
COLLATE utf8mb4_spanish_ci;

USE gestionPaquetes;

-- =====================================================
-- TABLA: rol
-- Catálogo de roles internos del sistema
-- =====================================================
CREATE TABLE rol (
    idRol INT AUTO_INCREMENT PRIMARY KEY,
    nombreRol VARCHAR(30) NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO rol (nombreRol) VALUES
('ADMINISTRADOR'),
('RECEPCIONISTA'),
('OPERADOR'),
('REPARTIDOR');

-- =====================================================
-- TABLA: usuarioSistema
-- Usuarios internos del sistema
-- =====================================================
CREATE TABLE usuarioSistema (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(15) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    idRol INT NOT NULL,

    CONSTRAINT fk_usuarioSistema_rol
        FOREIGN KEY (idRol) REFERENCES rol(idRol)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE INDEX idx_usuarioSistema_cedula ON usuarioSistema (cedula);
CREATE INDEX idx_usuarioSistema_rol ON usuarioSistema (idRol);

-- =====================================================
-- TABLA: cliente
-- Remitente o destinatario
-- =====================================================
CREATE TABLE cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(15) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(100) NULL
) ENGINE=InnoDB;

CREATE INDEX idx_cliente_cedula ON cliente (cedula);

-- =====================================================
-- TABLA: estadoPaquete
-- Catálogo de estados del paquete
-- =====================================================
CREATE TABLE estadoPaquete (
    idEstado INT AUTO_INCREMENT PRIMARY KEY,
    codigoEstado VARCHAR(20) NOT NULL UNIQUE,
    nombreEstado VARCHAR(30) NOT NULL UNIQUE,
    ordenEstado TINYINT NOT NULL UNIQUE
) ENGINE=InnoDB;

INSERT INTO estadoPaquete (codigoEstado, nombreEstado, ordenEstado) VALUES
('REGISTRADO', 'Registrado', 1),
('EN_TRANSITO', 'En tránsito', 2),
('ENTREGADO', 'Entregado', 3);

-- =====================================================
-- TABLA: paquete
-- Información principal del paquete
-- =====================================================
CREATE TABLE paquete (
    idPaquete INT AUTO_INCREMENT PRIMARY KEY,
    codigoPaquete VARCHAR(30) NOT NULL UNIQUE,
    numeroSeguimiento VARCHAR(30) NOT NULL UNIQUE,
    peso DECIMAL(10,2) NOT NULL,
    tipoEnvio VARCHAR(50) NOT NULL,
    direccionEntrega VARCHAR(150) NOT NULL,
    fechaRegistro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    idEstadoActual INT NOT NULL,
    idRemitente INT NOT NULL,
    idDestinatario INT NOT NULL,
    idUsuarioRegistro INT NOT NULL,
    idUsuarioRepartidor INT NULL,

    CONSTRAINT ck_paquete_peso
        CHECK (peso > 0),

    CONSTRAINT fk_paquete_estado
        FOREIGN KEY (idEstadoActual) REFERENCES estadoPaquete(idEstado)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_paquete_remitente
        FOREIGN KEY (idRemitente) REFERENCES cliente(idCliente)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_paquete_destinatario
        FOREIGN KEY (idDestinatario) REFERENCES cliente(idCliente)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_paquete_usuarioRegistro
        FOREIGN KEY (idUsuarioRegistro) REFERENCES usuarioSistema(idUsuario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_paquete_usuarioRepartidor
        FOREIGN KEY (idUsuarioRepartidor) REFERENCES usuarioSistema(idUsuario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE INDEX idx_paquete_codigoPaquete ON paquete (codigoPaquete);
CREATE INDEX idx_paquete_numeroSeguimiento ON paquete (numeroSeguimiento);
CREATE INDEX idx_paquete_estadoActual ON paquete (idEstadoActual);
CREATE INDEX idx_paquete_remitente ON paquete (idRemitente);
CREATE INDEX idx_paquete_destinatario ON paquete (idDestinatario);
CREATE INDEX idx_paquete_usuarioRegistro ON paquete (idUsuarioRegistro);
CREATE INDEX idx_paquete_usuarioRepartidor ON paquete (idUsuarioRepartidor);

-- =====================================================
-- TABLA: historialMovimiento
-- Guarda cada cambio de estado del paquete
-- =====================================================
CREATE TABLE historialMovimiento (
    idHistorial INT AUTO_INCREMENT PRIMARY KEY,
    idPaquete INT NOT NULL,
    idUsuario INT NOT NULL,
    idEstado INT NOT NULL,
    fechaHora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observacion VARCHAR(200) NULL,
    nombreRecibe VARCHAR(100) NULL,

    CONSTRAINT fk_historial_paquete
        FOREIGN KEY (idPaquete) REFERENCES paquete(idPaquete)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_historial_usuario
        FOREIGN KEY (idUsuario) REFERENCES usuarioSistema(idUsuario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_historial_estado
        FOREIGN KEY (idEstado) REFERENCES estadoPaquete(idEstado)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE INDEX idx_historial_paquete_fecha ON historialMovimiento (idPaquete, fechaHora);
CREATE INDEX idx_historial_usuario ON historialMovimiento (idUsuario);
CREATE INDEX idx_historial_estado ON historialMovimiento (idEstado);