# Contexto del proyecto: Inventario Cloud (app)

## Resumen
API REST de inventario en Spring Boot con PostgreSQL. Maneja categorías, proveedores, productos y un kardex de movimientos (entradas, salidas y ajustes). La estructura por capas se tomó de `demoMO_seguridad/demoMO` (UPC, Arqui Web), sin la parte de seguridad JWT.

Es la app base del proyecto `devops-cloud-pipeline-project`: Docker + Compose + Nginx, backups con Bash/Cron a S3, CI/CD con GitHub Actions + Trivy y despliegue en AWS EC2.

## Stack
- Java 21, Spring Boot 4.1.1, Maven (`./mvnw`)
- Spring Data JPA + PostgreSQL
- ModelMapper 3.2.6 (Entidad ↔ DTO, estrategia STRICT con mapeos explícitos para relaciones)
- spring-boot-starter-validation
- spring-boot-starter-actuator (`/health`)
- springdoc-openapi 3.1.0 (Swagger UI)

## Estructura (`com.andres.inventario`)
| Capa | Archivos |
|---|---|
| entities | `Categoria`, `Proveedor`, `Producto`, `MovimientoInventario` |
| enums | `TipoMovimiento` (ENTRADA, SALIDA, AJUSTE), `UnidadMedida` |
| dtos | `CategoriaDTO`, `ProveedorDTO`, `ProductoDTO`, `MovimientoDTO`, `ResumenInventarioDTO`, `ErrorResponse` |
| repositories | `ICategoriaRepository`, `IProveedorRepository`, `IProductoRepository`, `IMovimientoRepository` |
| servicesinterfaces / servicesimplements | Un servicio por entidad; las reglas de negocio viven aquí |
| controllers | `CategoriaController`, `ProveedorController`, `ProductoController`, `MovimientoController` |
| exceptions | `ResourceNotFoundException` (404), `BadRequestException` (400), `ConflictException` (409), `GlobalExceptionHandler` |
| configs | `ModelMapperConfig`, `DatosIniciales` (6 categorías, 3 proveedores ficticios, 16 productos con su kardex si la base está vacía) |

## Modelo de datos
- `categorias`: id, nombre (único), descripcion
- `proveedores`: id, ruc (único, 11 dígitos), razon_social, contacto, telefono, email
- `productos`: id, sku (único), nombre, descripcion, categoria_id (FK, obligatorio), proveedor_id (FK, opcional), unidad_medida, precio_compra, precio_venta, stock_actual (≥ 0), stock_minimo (por defecto 5), activo, creado_en, actualizado_en
- `movimientos_inventario`: id, producto_id (FK), tipo, cantidad, stock_anterior, stock_resultante, motivo, referencia (factura, guía...), fecha

Hibernate crea o actualiza las tablas con `ddl-auto=update`.

## Reglas de negocio
- El stock **no se edita** desde el CRUD de productos: solo cambia registrando movimientos. Un producto nuevo nace con stock 0.
- ENTRADA suma, SALIDA resta, AJUSTE fija el stock al conteo físico. Cada movimiento guarda el stock anterior y el resultante.
- Una SALIDA mayor al stock disponible se rechaza con 409. El producto se bloquea (`PESSIMISTIC_WRITE`) mientras se registra el movimiento para evitar carreras.
- Los movimientos no se editan ni se eliminan; un error se corrige con un AJUSTE.
- DELETE de producto es una baja lógica (`activo = false`) y conserva el historial. Un producto inactivo no admite movimientos y se reactiva con `PATCH /{id}/reactivar`.
- No se puede eliminar una categoría o un proveedor con productos asociados (409).
- Stock bajo: producto activo con `stock_actual < stock_minimo`.

## Endpoints
| Método | Ruta | Descripción |
|---|---|---|
| GET/POST | `/apis/categorias` | Listar / crear |
| GET/PUT/DELETE | `/apis/categorias/{id}` | Detalle / actualizar / eliminar |
| GET/POST | `/apis/proveedores` | Listar / crear |
| GET/PUT/DELETE | `/apis/proveedores/{id}` | Detalle / actualizar / eliminar |
| GET | `/apis/productos?categoriaId=` | Listar, con filtro opcional |
| POST | `/apis/productos` | Crear (201, stock 0) |
| GET/PUT/DELETE | `/apis/productos/{id}` | Detalle / actualizar / baja lógica |
| PATCH | `/apis/productos/{id}/reactivar` | Reactivar |
| GET | `/apis/productos/stock-bajo` | Productos bajo su stock mínimo |
| GET | `/apis/productos/resumen` | Indicadores: activos, unidades, valor del inventario, stock bajo, sin stock |
| GET | `/apis/productos/{id}/movimientos` | Kardex del producto |
| GET/POST | `/apis/movimientos` | Listar / registrar movimiento |
| GET | `/health` | Estado de la app y de la conexión a PostgreSQL |

Swagger UI: http://localhost:8080/swagger-ui/index.html

## Configuración (`application.properties`)
- Puerto 8080
- Base de datos `inventario` en `localhost:5432`
- Variables de entorno: `DB_URL`, `DB_USER` (por defecto `postgres`), `DB_PASSWORD` (por defecto `postgres`)
- Actuator expone solo `health`, en la raíz (`/health`)

## Cómo ejecutar
```bash
DB_USER=<tu_usuario> DB_PASSWORD=<tu_clave> ./mvnw spring-boot:run
```

## Pruebas
`MovimientoServiceImplementTest`: pruebas unitarias con Mockito de las reglas de stock. No necesitan base de datos, así que sirven para el pipeline:
```bash
./mvnw test -Dtest=MovimientoServiceImplementTest
```
`InventarioApplicationTests` (contextLoads) sí necesita PostgreSQL.

## No incluido (a diferencia de demoMO)
- Spring Security / JWT, login y roles.
- Se puede agregar más adelante copiando `securities/` y `Users`/`Role`.

## Pendiente / ideas
- Dockerizar la app: Dockerfile, docker-compose con PostgreSQL y Nginx.
- Pasar de `ddl-auto=update` a migraciones con Flyway.
- Decidir si se agrega seguridad JWT.
- Paginación en los listados.
