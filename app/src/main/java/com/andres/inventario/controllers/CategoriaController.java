package com.andres.inventario.controllers;

import com.andres.inventario.dtos.CategoriaDTO;
import com.andres.inventario.entities.Categoria;
import com.andres.inventario.servicesinterfaces.ICategoriaService;
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
@RequestMapping("/apis/categorias")
@Tag(name = "Categorías")
public class CategoriaController {
    private final ICategoriaService cS;
    private final ModelMapper modelMapper;

    public CategoriaController(ICategoriaService cS, ModelMapper modelMapper) {
        this.cS = cS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Lista las categorías ordenadas por nombre")
    public ResponseEntity<List<CategoriaDTO>> listar() {
        List<CategoriaDTO> lista = cS.list()
                .stream()
                .map(c -> modelMapper.map(c, CategoriaDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una categoría")
    public ResponseEntity<CategoriaDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(cS.listId(id), CategoriaDTO.class));
    }

    @PostMapping
    @Operation(summary = "Crea una categoría")
    public ResponseEntity<CategoriaDTO> registrar(@Valid @RequestBody CategoriaDTO dto) {
        Categoria c = cS.insert(modelMapper.map(dto, Categoria.class));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(c.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(c, CategoriaDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una categoría")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {
        Categoria c = cS.update(id, modelMapper.map(dto, Categoria.class));
        return ResponseEntity.ok(modelMapper.map(c, CategoriaDTO.class));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una categoría sin productos asociados")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cS.delete(id);
        return ResponseEntity.noContent().build();
    }
}
