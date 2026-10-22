# Contexto del proyecto: Inventario Cloud (app)

## Resumen
API REST de inventario en Spring Boot que gestiona la tabla `productos` en PostgreSQL. La estructura por capas se tomó de `demoMO_seguridad/demoMO` (UPC, Arqui Web), sin la parte de seguridad JWT.

## Stack
- Java 21, Spring Boot 4.1.1, Maven (`./mvnw`)
- Spring Data JPA + PostgreSQL
- ModelMapper 3.2.6 (Entidad ↔ DTO)
- spring-boot-starter-validation
- springdoc-openapi 3.1.0 (Swagger UI)

## Estructura (`com.andres.inventario`)
| Capa | Archivo |
|---|---|
| entities | `Producto` |
| dtos | `ProductoDTO`, `ErrorResponse` |
| repositories | `IProductoRepository` |
| servicesinterfaces | `IProductoService` |
| servicesimplements | `ProductoServiceImplement` |
| controllers | `ProductoController` (`/apis/productos`) |
| exceptions | `ResourceNotFoundException`, `GlobalExceptionHandler` (404 y 400) |
| configs | `ModelMapperConfig`, `DatosIniciales` (carga 15 productos si la tabla está vacía) |

## Tabla `productos`
| Columna | Tipo | Notas |
|---|---|---|
| id | BIGSERIAL (PK) | Se genera solo |
| nombre | VARCHAR(100) | Obligatorio |
| cantidad | INTEGER | No negativa (check), por defecto 0 |
| precio | NUMERIC(10,2) | Opcional |
| creado_en | TIMESTAMP | Fecha de creación, por defecto "ahora" |

Hibernate la crea/actualiza con `ddl-auto=update`.

## Endpoints
- `GET /apis/productos`: lista
- `GET /apis/productos/{id}`: detalle
- `POST /apis/productos`: crea (201)
- `PUT /apis/productos`: actualiza
- `DELETE /apis/productos/{id}`: elimina (204)

Swagger UI: http://localhost:8080/swagger-ui/index.html

## Configuración (`application.properties`)
- Puerto 8080
- Base de datos `inventario` en `localhost:5432`
- Variables de entorno: `DB_URL`, `DB_USER` (por defecto `postgres`), `DB_PASSWORD` (por defecto `postgres`)

## Cómo ejecutar
```bash
DB_PASSWORD=<tu_clave> ./mvnw spring-boot:run
```
PostgreSQL 18 está instalado en `/Library/PostgreSQL/18` (binarios en `bin/`, por ejemplo `psql`, `pg_isready`).

## Estado
- La app compila y arrancó correctamente contra la base `inventario`.
- Los 15 productos de prueba están cargados y Swagger responde 200.

## No incluido (a diferencia de demoMO)
- Spring Security / JWT, login y roles.
- Se puede agregar más adelante copiando `securities/` y `Users`/`Role`.

## Pendiente / ideas
- Decidir si se agrega seguridad JWT.
- Dockerizar la app (el proyecto vive en `devops/inventario-cloud`).
- Pruebas de los endpoints.
