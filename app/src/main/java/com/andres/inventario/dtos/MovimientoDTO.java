package com.andres.inventario.dtos;

import com.andres.inventario.enums.TipoMovimiento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class MovimientoDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotNull(message = "El producto es obligatorio.")
    private Long productoId;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String productoSku;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String productoNombre;
    @NotNull(message = "El tipo de movimiento es obligatorio.")
    private TipoMovimiento tipo;
    @NotNull(message = "La cantidad es obligatoria.")
    @Min(value = 0, message = "La cantidad no puede ser negativa.")
    @Schema(description = "En ENTRADA y SALIDA es la cantidad a mover; en AJUSTE es el stock contado físicamente.")
    private Integer cantidad;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer stockAnterior;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer stockResultante;
    @Size(max = 255, message = "El motivo no puede superar 255 caracteres.")
    private String motivo;
    @Size(max = 50, message = "La referencia no puede superar 50 caracteres.")
    private String referencia;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getProductoSku() {
        return productoSku;
    }

    public void setProductoSku(String productoSku) {
        this.productoSku = productoSku;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getStockAnterior() {
        return stockAnterior;
    }

    public void setStockAnterior(Integer stockAnterior) {
        this.stockAnterior = stockAnterior;
    }

    public Integer getStockResultante() {
        return stockResultante;
    }

    public void setStockResultante(Integer stockResultante) {
        this.stockResultante = stockResultante;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
