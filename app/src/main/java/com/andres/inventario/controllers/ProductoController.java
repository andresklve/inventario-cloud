package com.andres.inventario.controllers;

import com.andres.inventario.dtos.MovimientoDTO;
import com.andres.inventario.dtos.ProductoDTO;
import com.andres.inventario.dtos.ResumenInventarioDTO;
import com.andres.inventario.entities.Producto;
import com.andres.inventario.servicesinterfaces.IMovimientoService;
import com.andres.inventario.servicesinterfaces.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/apis/productos")
@Tag(name = "Productos")
public class ProductoController {
    private final IProductoService pS;
    private final IMovimientoService mS;
    private final ModelMapper modelMapper;

    public ProductoController(IProductoService pS, IMovimientoService mS, ModelMapper modelMapper) {
        this.pS = pS;
        this.mS = mS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Lista los productos, opcionalmente filtrados por categoría")
    public ResponseEntity<List<ProductoDTO>> listar(@RequestParam(required = false) Long categoriaId) {
        return ResponseEntity.ok(aDTOs(pS.list(categoriaId)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un producto")
    public ResponseEntity<ProductoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(pS.listId(id), ProductoDTO.class));
    }

    @GetMapping("/stock-bajo")
    @Operation(summary = "Productos activos con stock por debajo de su stock mínimo")
    public ResponseEntity<List<ProductoDTO>> stockBajo() {
        return ResponseEntity.ok(aDTOs(pS.stockBajo()));
    }

    @GetMapping("/resumen")
    @Operation(summary = "Indicadores generales del inventario")
    public ResponseEntity<ResumenInventarioDTO> resumen() {
        return ResponseEntity.ok(pS.resumen());
    }

    @GetMapping("/{id}/movimientos")
    @Operation(summary = "Kardex del producto: sus movimientos en orden cronológico")
    public ResponseEntity<List<MovimientoDTO>> kardex(@PathVariable Long id) {
        List<MovimientoDTO> lista = mS.listPorProducto(id)
                .stream()
                .map(m -> modelMapper.map(m, MovimientoDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Crea un producto con stock 0; el stock se carga con un movimiento de ENTRADA")
    public ResponseEntity<ProductoDTO> registrar(@Valid @RequestBody ProductoDTO dto) {
        Producto p = pS.insert(modelMapper.map(dto, Producto.class), dto.getCategoriaId(), dto.getProveedorId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(p, ProductoDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza los datos del producto (no su stock)")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        Producto p = pS.update(id, modelMapper.map(dto, Producto.class), dto.getCategoriaId(), dto.getProveedorId());
        return ResponseEntity.ok(modelMapper.map(p, ProductoDTO.class));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Da de baja el producto (baja lógica, conserva su historial)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pS.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reactivar")
    @Operation(summary = "Vuelve a activar un producto dado de baja")
    public ResponseEntity<ProductoDTO> reactivar(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(pS.reactivar(id), ProductoDTO.class));
    }

    private List<ProductoDTO> aDTOs(List<Producto> productos) {
        return productos.stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .toList();
    }
}
