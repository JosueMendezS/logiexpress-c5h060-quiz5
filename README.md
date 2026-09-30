# LogiExpress - API de Gestion de Envios

API RESTful de LogiExpress construida con Java 21 y Spring Boot. Curso IF0009 - Desarrollo de Software IV, Quiz 4.

## Arquitectura y desacoplamiento de capas

El sistema sigue un modelo cliente-servidor desacoplado. El back-end solo expone servicios REST en JSON y no conoce ni depende de ningun cliente en particular.

- `domain`: entidades JPA (`Cliente`, `Paquete`, `EstadoPaquete`).
- `data`: repositorios Spring Data JPA.
- `business`: servicios y reglas de negocio (`PaqueteService`).
- `controller`: controladores REST y DTOs, que separan la capa HTTP de las entidades.
- `config`: seguridad (Spring Security + JWT) y OpenAPI.
- `exception`: excepciones de negocio y manejador global RFC 7807.

Cada capa solo se comunica con la inmediatamente inferior, por lo que se puede cambiar la base de datos, la seguridad o el formato de entrada sin reescribir las demas. La API REST es la columna vertebral del sistema: una SPA web, una aplicacion movil o un sistema de terceros pueden consumir exactamente los mismos endpoints sin modificar el back-end.

## Requisitos

- JDK 21
- Maven (o el wrapper `./mvnw` incluido)

## Ejecucion

```
cd logiexpress
./mvnw spring-boot:run
```

Pruebas unitarias:

```
./mvnw test
```

La base de datos es H2 en memoria y se inicializa con dos clientes de ejemplo (ids 1 y 2).

Swagger UI: http://localhost:8080/swagger-ui.html

## Usuarios

| Usuario  | Contrasena  | Rol           |
|----------|-------------|---------------|
| admin    | admin123    | ROLE_ADMIN    |
| operador | operador123 | ROLE_OPERADOR |

## Endpoints

| Metodo | Ruta                              | Acceso                |
|--------|-----------------------------------|-----------------------|
| POST   | /api/v1/auth/login                | Publico               |
| GET    | /api/v1/paquetes?estado=REGISTRADO&page=0&size=10 | ADMIN, OPERADOR |
| GET    | /api/v1/paquetes/{id}             | ADMIN, OPERADOR       |
| POST   | /api/v1/paquetes                  | ADMIN                 |
| DELETE | /api/v1/paquetes/{id}             | ADMIN                 |

Ejemplo de login:

```
POST /api/v1/auth/login
{"username": "admin", "password": "admin123"}
```

Ejemplo de registro de paquete (encabezado `Authorization: Bearer <token>`):

```
POST /api/v1/paquetes
{"clienteId": 1, "codigoRastreo": "LX-001", "descripcion": "Caja pequena", "pesoKg": 10.5}
```

Un `pesoKg` mayor a 30.0 responde 400 y un cliente inexistente responde 404, ambos con formato Problem Details (RFC 7807).
