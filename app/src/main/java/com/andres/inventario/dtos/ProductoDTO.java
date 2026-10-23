package com.andres.inventario.dtos;

import com.andres.inventario.enums.UnidadMedida;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductoDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "El SKU es obligatorio.")
    @Pattern(regexp = "[A-Za-z0-9-]{3,30}", message = "El SKU debe tener de 3 a 30 caracteres: letras, números o guiones.")
    private String sku;
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres.")
    private String nombre;
    @Size(max = 255, message = "La descripción no puede superar 255 caracteres.")
    private String descripcion;
    @NotNull(message = "La categoría es obligatoria.")
    private Long categoriaId;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String categoriaNombre;
    private Long proveedorId;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String proveedorRazonSocial;
    @NotNull(message = "La unidad de medida es obligatoria.")
    private UnidadMedida unidadMedida = UnidadMedida.UNIDAD;
    @DecimalMin(value = "0.00", message = "El precio de compra no puede ser negativo.")
    @Digits(integer = 8, fraction = 2, message = "El precio de compra admite hasta 8 enteros y 2 decimales.")
    private BigDecimal precioCompra;
    @DecimalMin(value = "0.00", message = "El precio de venta no puede ser negativo.")
    @Digits(integer = 8, fraction = 2, message = "El precio de venta admite hasta 8 enteros y 2 decimales.")
    private BigDecimal precioVenta;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Se modifica solo registrando movimientos.")
    private Integer stockActual;
    @NotNull(message = "El stock mínimo es obligatorio.")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo.")
    private Integer stockMinimo = 5;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean activo;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime creadoEn;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime actualizadoEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorRazonSocial() {
        return proveedorRazonSocial;
    }

    public void setProveedorRazonSocial(String proveedorRazonSocial) {
        this.proveedorRazonSocial = proveedorRazonSocial;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}
