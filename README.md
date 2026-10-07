# 📦 Sistema de Gestión de Entrega de Paquetes

### Tecnologías utilizadas

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
![NetBeans](https://img.shields.io/badge/NetBeans-1B6AC6?style=flat&logo=apachenetbeanside&logoColor=white)
![Swing](https://img.shields.io/badge/Java_Swing-5382A1?style=flat&logo=openjdk&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-EclipseLink-orange?style=flat)
![JDBC](https://img.shields.io/badge/JDBC-Connector/J-blue?style=flat&logo=mysql&logoColor=white)

## Descripción Proyecto: Programación Avanzada

El sistema permite gestionar el proceso completo de envío de paquetes dentro de una empresa de logística, desde su registro hasta la entrega final.

Cuenta con una interfaz gráfica desarrollada en **Java Swing**, una base de datos **MySQL** y diferentes roles de usuario, cada uno con sus funciones dentro del sistema.

Además, permite consultar el estado de los paquetes y revisar su historial de movimientos mediante un número de seguimiento.

---

## Objetivo del sistema

Desarrollar una aplicación que permita:

* Registrar paquetes y clientes.
* Controlar el estado del paquete en cada etapa.
* Registrar la salida y entrega de paquetes.
* Llevar un historial de movimientos.
* Permitir al cliente consultar el estado de su paquete.

---

## Actores del sistema (roles)

### 1. Recepcionista (Registro del paquete)

Encargado de ingresar los paquetes al sistema.

**Funciones:**

* Registrar los datos del remitente y destinatario.
* Registrar información del paquete:
  * Código único.
  * Peso.
  * Tipo de envío.
  * Dirección de entrega.
* Generar automáticamente el código y número de seguimiento.
* Establecer el estado inicial: **Registrado**.

---

### 2. Operador de despacho (Salida de la empresa)

Encargado de registrar la salida de los paquetes.

**Funciones:**

* Buscar paquetes por código.
* Consultar la información del paquete.
* Asignar un repartidor.
* Registrar la salida del paquete.
* Actualizar el estado: **En tránsito**.
* Guardar el movimiento en el historial.

---

### 3. Repartidor (Entrega al cliente)

Encargado de realizar la entrega final.

**Funciones:**

* Consultar los paquetes asignados.
* Buscar paquetes por código.
* Registrar la entrega al cliente.
* Actualizar el estado: **Entregado**.
* Registrar:
  * Fecha y hora.
  * Nombre de quien recibe.
  * Observaciones (opcional).

---

### 4. Cliente (Seguimiento)

Usuario que consulta el estado de su paquete.

**Funciones:**

* Ingresar el número de seguimiento.
* Visualizar la información del paquete.
* Consultar el estado actual.
* Ver el historial de movimientos.
* Consultar los datos de entrega.

---

## Flujo del sistema

**Registrado → En tránsito → Entregado**

1. La recepcionista registra el paquete.
2. El operador registra su salida y asigna un repartidor.
3. El repartidor realiza la entrega.
4. El cliente puede consultar el estado y los movimientos de su paquete.

---

## Funcionalidades principales

### Gestión de paquetes

* Registrar paquetes.
* Consultar paquetes.
* Actualizar estados.
* Listar paquetes.
* Asignar repartidores.

### Seguimiento

* Consulta por número de seguimiento.
* Visualización del estado actual.
* Historial de movimientos.
* Consulta de información de entrega.

### Control de usuarios

* Ingreso de usuarios mediante cédula.
* Validación de roles.
* Acceso a las funciones correspondientes a cada usuario.

### Control de estados

* Registrado.
* En tránsito.
* Entregado.

---

## Reglas de negocio

* Cada paquete tiene un código único y un número de seguimiento.
* El paquete inicia con el estado **Registrado**.
* Solo el operador puede despachar los paquetes.
* No se puede entregar un paquete si no está en tránsito.
* El repartidor solo puede entregar los paquetes que tiene asignados.
* El estado sigue un orden lógico:

**Registrado → En tránsito → Entregado**

* El cliente solo puede consultar, no modificar información.
* Cada cambio de estado queda registrado en el historial.

---

## Funcionalidades adicionales

Durante el desarrollo también se implementaron:

* Interfaz gráfica mediante formularios de Java Swing.
* Validaciones para el registro de clientes y paquetes.
* Generación automática de códigos de seguimiento.
* Manejo de usuarios según su rol.
* Consultas a la base de datos mediante JPA.
* Uso de hilos para la consulta del seguimiento.
* Registro del historial de los paquetes.

---

## Arquitectura utilizada

Se trabajó con una **arquitectura por capas**, organizando el proyecto de la siguiente manera:

* **Presentación:** interfaces gráficas desarrolladas con Java Swing.
* **Lógica:** validaciones y reglas del sistema.
* **BaseDatos:** consultas y operaciones con la base de datos.
* **Clases:** entidades que representan las tablas.
* **Hilos:** consulta del seguimiento de paquetes.

---

## Tecnologías utilizadas

* **Lenguaje:** Java.
* **Base de datos:** MySQL.
* **Persistencia:** JPA con EclipseLink.
* **Conector:** MySQL Connector/J (JDBC).
* **Interfaz gráfica:** Java Swing (JFrame).
* **Entorno de desarrollo:** Apache NetBeans.
* **Consultas:** JPQL.
* **Concurrencia:** Hilos de Java.

---