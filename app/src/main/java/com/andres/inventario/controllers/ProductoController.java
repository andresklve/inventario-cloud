package com.andres.inventario.controllers;

import com.andres.inventario.dtos.ProductoDTO;
import com.andres.inventario.entities.Producto;
import com.andres.inventario.exceptions.ResourceNotFoundException;
import com.andres.inventario.servicesinterfaces.IProductoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/apis/productos")
public class ProductoController {
    private final IProductoService pS;
    private final ModelMapper modelMapper;

    public ProductoController(IProductoService pS, ModelMapper modelMapper) {
        this.pS = pS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listar() {
        List<ProductoDTO> lista = pS.list()
                .stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> buscar(@PathVariable Long id) {
        Producto p = pS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe un producto con el id: " + id
                        )
                );

        return ResponseEntity.ok(modelMapper.map(p, ProductoDTO.class));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> registrar(@Valid @RequestBody ProductoDTO dto) {
        Producto p = modelMapper.map(dto, Producto.class);
        p.setId(null);
        p.setCreadoEn(null);
        pS.insert(p);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(p, ProductoDTO.class));
    }

    @PutMapping
    public ResponseEntity<ProductoDTO> actualizar(@Valid @RequestBody ProductoDTO dto) {
        Producto p = pS.listId(dto.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe un producto con el id: " + dto.getId()
                        )
                );

        p.setNombre(dto.getNombre());
        p.setCantidad(dto.getCantidad());
        p.setPrecio(dto.getPrecio());
        pS.update(p);

        return ResponseEntity.ok(modelMapper.map(p, ProductoDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Producto p = pS.listId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe un producto con el id: " + id
                        )
                );
        pS.delete(p.getId());
        return ResponseEntity.noContent().build();
    }
}
