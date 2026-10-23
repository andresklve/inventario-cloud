package com.andres.inventario.controllers;

import com.andres.inventario.dtos.ProveedorDTO;
import com.andres.inventario.entities.Proveedor;
import com.andres.inventario.servicesinterfaces.IProveedorService;
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
@RequestMapping("/apis/proveedores")
@Tag(name = "Proveedores")
public class ProveedorController {
    private final IProveedorService prS;
    private final ModelMapper modelMapper;

    public ProveedorController(IProveedorService prS, ModelMapper modelMapper) {
        this.prS = prS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Lista los proveedores ordenados por razón social")
    public ResponseEntity<List<ProveedorDTO>> listar() {
        List<ProveedorDTO> lista = prS.list()
                .stream()
                .map(p -> modelMapper.map(p, ProveedorDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un proveedor")
    public ResponseEntity<ProveedorDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(prS.listId(id), ProveedorDTO.class));
    }

    @PostMapping
    @Operation(summary = "Registra un proveedor")
    public ResponseEntity<ProveedorDTO> registrar(@Valid @RequestBody ProveedorDTO dto) {
        Proveedor p = prS.insert(modelMapper.map(dto, Proveedor.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(p.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(p, ProveedorDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un proveedor")
    public ResponseEntity<ProveedorDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProveedorDTO dto) {
        Proveedor p = prS.update(id, modelMapper.map(dto, Proveedor.class));
        return ResponseEntity.ok(modelMapper.map(p, ProveedorDTO.class));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un proveedor sin productos asociados")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        prS.delete(id);
        return ResponseEntity.noContent().build();
    }
}
