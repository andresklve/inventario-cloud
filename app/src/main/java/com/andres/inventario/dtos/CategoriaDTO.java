package com.andres.inventario.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 60, message = "El nombre no puede superar 60 caracteres.")
    private String nombre;
    @Size(max = 255, message = "La descripción no puede superar 255 caracteres.")
    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
