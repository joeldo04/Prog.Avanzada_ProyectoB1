# Sistema de Gestión de Entrega de Paquetes

## Descripción general

El sistema permitirá gestionar el proceso completo de envío de paquetes dentro de una empresa de logística. Incluye el registro del paquete, control de entregas en distintas etapas y seguimiento por parte del cliente en tiempo real. El sistema estará basado en roles de usuario, cada uno con responsabilidades específicas dentro del flujo logístico.

---

## Objetivo del sistema

Desarrollar una aplicación que permita:

* Registrar paquetes enviados a los clientes
* Controlar el estado del paquete en cada etapa
* Garantizar trazabilidad del envío
* Permitir al cliente consultar el estado de su paquete

---

## Actores del sistema (roles)

### 1. Recepcionista (Registro del paquete)

Encargado de ingresar el paquete al sistema.

**Funciones:**

* Registrar datos del cliente (remitente y destinatario)
* Registrar información del paquete:

  * Código único
  * Peso
  * Tipo de envío
  * Dirección de entrega
* Generar número de seguimiento
* Estado inicial: **Registrado**

---

### 2. Operador de despacho (Salida de la empresa)

Encargado de registrar la salida del paquete.

**Funciones:**

* Buscar paquete por código
* Registrar salida del paquete
* Actualizar estado: **En tránsito**
* Registrar fecha y hora de despacho

---

### 3. Repartidor (Entrega al cliente)

Encargado de la entrega final.

**Funciones:**

* Consultar paquetes asignados
* Registrar entrega al cliente
* Actualizar estado: **Entregado**
* Registrar:

  * Fecha y hora
  * Nombre de quien recibe
  * Observaciones (opcional)

---

### 4. Cliente (seguimiento)

Usuario externo que consulta el estado del paquete.

**Funciones:**

* Ingresar código de seguimiento
* Visualizar estado actual del paquete
* Ver historial de movimientos

---

## Flujo del sistema

**Registro → En tránsito → Entregado**

1. Se registra el paquete
2. Se despacha desde la empresa
3. Se entrega al cliente
4. El cliente puede consultar en cualquier momento

---

## Funcionalidades principales

### Gestión de paquetes

* Crear paquete
* Consultar paquete
* Actualizar estado
* Listar paquetes

### Seguimiento

* Consulta por código
* Visualización de estado actual
* Historial de estados

### Control de estados

* Registrado
* En tránsito
* Entregado

---

## Reglas de negocio

* Cada paquete tiene un código único
* No se puede entregar un paquete si no está en tránsito
* El estado sigue un orden lógico:

**Registrado → En tránsito → Entregado**

* El cliente solo puede consultar, no modificar información
* Cada cambio de estado debe registrarse en el historial

---

## Cualquier adicional que el grupo defina o quiera cambiar

---

## Arquitectura sugerida

Se recomienda usar:

### Arquitectura por capas

* Presentación
* Negocio
* Datos

### Clases

---

## Tecnologías sugeridas

* **Lenguaje:** Java
* **Base de datos:** MySQL
* **Conexión:** JDBC
* **Interfaz:** Consola o Swing *(puntaje adicional)*
