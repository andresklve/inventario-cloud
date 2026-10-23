package com.andres.inventario.controllers;

import com.andres.inventario.dtos.MovimientoDTO;
import com.andres.inventario.entities.MovimientoInventario;
import com.andres.inventario.servicesinterfaces.IMovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Los movimientos no se editan ni se eliminan: un error se corrige con un AJUSTE.
@RestController
@RequestMapping("/apis/movimientos")
@Tag(name = "Movimientos de inventario")
public class MovimientoController {
    private final IMovimientoService mS;
    private final ModelMapper modelMapper;

    public MovimientoController(IMovimientoService mS, ModelMapper modelMapper) {
        this.mS = mS;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Lista todos los movimientos, del más reciente al más antiguo")
    public ResponseEntity<List<MovimientoDTO>> listar() {
        List<MovimientoDTO> lista = mS.list()
                .stream()
                .map(m -> modelMapper.map(m, MovimientoDTO.class))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Registra una ENTRADA, SALIDA o AJUSTE y actualiza el stock del producto")
    public ResponseEntity<MovimientoDTO> registrar(@Valid @RequestBody MovimientoDTO dto) {
        MovimientoInventario m = mS.registrar(
                dto.getProductoId(),
                new MovimientoInventario(dto.getTipo(), dto.getCantidad(), dto.getMotivo(), dto.getReferencia())
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(m, MovimientoDTO.class));
    }
}
