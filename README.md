# 🛒 Ecommerce Microservices Platform

Una plataforma de comercio electrónico moderna, escalable y distribuida desarrollada bajo una **arquitectura de microservicios** utilizando **Spring Boot**, **Spring Cloud** y **Keycloak** para autenticación y autorización.

Cada microservicio es independiente, cuenta con su propia base de datos y puede desplegarse de forma individual, permitiendo alta disponibilidad, escalabilidad horizontal y tolerancia a fallos.

---

# 📖 Tabla de Contenidos

* [Arquitectura](#-arquitectura)
* [Microservicios](#-microservicios)
* [Tecnologías](#-tecnologías-utilizadas)
* [Seguridad](#-seguridad)
* [Resiliencia](#-resiliencia)
* [Estructura del Proyecto](#-estructura-del-proyecto)
* [Requisitos Previos](#-requisitos-previos)
* [Instalación](#-instalación)
* [Configuración de Keycloak](#-configuración-de-keycloak)
* [Ejecución](#-ejecución-de-los-servicios)
* [API Gateway](#-api-gateway)
* [Base de Datos](#-base-de-datos)
* [Autor](#-autor)

---

# 🏗 Arquitectura

La plataforma sigue el patrón **Database per Service**, donde cada microservicio posee su propia base de datos y lógica de negocio.

```
                    Cliente
                       │
                       ▼
               Spring Cloud Gateway
                       │
      ┌────────────────┼────────────────┐
      │                │                │
      ▼                ▼                ▼
 Product Service   Order Service   Inventory Service
      │                │                │
    MongoDB        PostgreSQL          MySQL

                       │
                       ▼
                    Keycloak
```

---

# 📦 Microservicios

## Gateway Service

Punto de entrada de toda la plataforma.

Responsabilidades:

* Enrutamiento dinámico.
* Validación de JWT.
* Token Relay.
* Balanceo de carga.
* Seguridad centralizada.

---

## Product Service

Gestiona el catálogo de productos.

Funciones:

* CRUD de productos.
* Precios.
* Información del catálogo.

Base de datos:

* MongoDB

---

## Order Service

Gestiona las órdenes de compra.

Funciones:

* Creación de órdenes.
* Estados de la orden.
* Comunicación con Inventory Service.
* Validación de stock.

Base de datos:

* PostgreSQL

---

## Inventory Service

Controla el inventario disponible.

Funciones:

* Existencias.
* Reserva de stock.
* Validación antes de confirmar una compra.

Base de datos:

* MySQL

---

## Keycloak

Servidor de identidad encargado de:

* Autenticación.
* Autorización.
* Gestión de usuarios.
* Roles.
* OAuth2.
* OpenID Connect (OIDC).

---

# 🚀 Tecnologías Utilizadas

## Backend

* Java 21
* Spring Boot 3.x
* Spring Cloud Gateway
* Spring Data JPA
* Hibernate
* Maven

## Seguridad

* Keycloak
* OAuth2
* OpenID Connect (OIDC)
* JWT
* Spring Security

## Persistencia

* PostgreSQL
* MongoDB

## Infraestructura

* Docker
* Docker Compose

## Utilidades

* Lombok
* Resilience4j

---

# 🔒 Seguridad

La autenticación está completamente delegada a **Keycloak**.

Flujo de autenticación:

```
Cliente
   │
   ▼
Keycloak
   │
JWT
   │
   ▼
Gateway
   │
Token Relay
   │
   ▼
Microservicios
```

El Gateway valida el token JWT antes de permitir el acceso a cualquier recurso.

Ejemplo de configuración:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/api/v1/orders/**
          filters:
            - TokenRelay
```

---

# 🛡 Resiliencia

El proyecto implementa **Resilience4j** para evitar fallos en cascada entre servicios.

Patrones utilizados:

* Circuit Breaker
* Retry
* TimeLimiter
* Fallback Methods

Ejemplo:

```yaml
resilience4j:
  circuitbreaker:
    instances:
      inventoryService:
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
        permittedNumberOfCallsInHalfOpenState: 3
```

Con esta configuración, si **Inventory Service** deja de responder, **Order Service** puede continuar utilizando estrategias de recuperación sin afectar a toda la plataforma.

---

# 📁 Estructura del Proyecto

```
ecommerce-microservices/
│
├── api-gateway/
├── config-data/
├── config-server/
├── discovery-server/
├── product-service/
├── order-service/
├── inventory-service/
│
├── docker-compose.yml
├── README.md
```

---

# ⚙ Requisitos Previos

Antes de ejecutar el proyecto es necesario tener instalado:

* Java 21
* Maven 3.8+
* Docker
* Docker Compose
* Git

---

# 🚀 Instalación

## 1. Clonar el repositorio

```bash
git clone https://github.com/AlanAltamiranoH1504/ECommerce-MicroService-SpringBoot4-RabbitMQ.git

cd tu-repositorio
```

---

## 2. Levantar la infraestructura

```bash
docker compose up -d
```

Esto iniciará:

* PostgreSQL
* MongoDB
* Keycloak
* Otros servicios definidos en `docker-compose.yml`

---

# 🔑 Configuración de Keycloak

1. Abrir:

```
http://localhost:8080
```

2. Crear un Realm

```
ecommerce-realm
```

3. Crear un Client para el Gateway.

4. Crear los roles:

```
ROLE_ADMIN
ROLE_USER
```

5. Crear usuarios y asignar sus respectivos roles.

---

# ▶ Ejecución de los Servicios

Desde la raíz del proyecto o desde cada microservicio ejecutar:

```bash
mvn clean install
```

Posteriormente:

```bash
mvn spring-boot:run
```

O ejecutar el archivo JAR:

```bash
java -jar target/*.jar
```

---

# 🌐 API Gateway

Todas las solicitudes pasan primero por el Gateway.

Ejemplo:

```
http://localhost:8080/products

http://localhost:8080/orders

http://localhost:8080/inventory
```

El Gateway se encarga de:

* Validar el JWT.
* Reenviar el token.
* Enrutar las peticiones.
* Aplicar reglas de seguridad.

---

# 🗄 Base de Datos

| Microservicio     | Base de datos |
| ----------------- |---------------|
| Product Service   | MongoDB       |
| Order Service     | PostgreSQL    |
| Inventory Service | MySQL         |

Cada servicio administra su propia persistencia siguiendo el patrón **Database per Service**.

---

# ✨ Características

* Arquitectura basada en microservicios.
* Spring Cloud Gateway.
* Seguridad con Keycloak.
* OAuth2 + OpenID Connect.
* JWT.
* Docker Compose.
* PostgreSQL.
* MongoDB.
* Resilience4j.
* Circuit Breaker.
* Retry.
* TimeLimiter.
* Escalable.
* Alta disponibilidad.
* Fácil despliegue.

---

# 👨‍💻 Autor

Desarrollado como proyecto de arquitectura de microservicios con **Spring Boot**, **Spring Cloud**, **Keycloak** y **Docker**, aplicando buenas prácticas de diseño, seguridad y resiliencia por Alan Altamirano Hernández.
