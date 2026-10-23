package com.andres.inventario.entities;

import com.andres.inventario.enums.UnidadMedida;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sku", length = 30, nullable = false, unique = true)
    private String sku;
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;
    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", length = 20, nullable = false)
    private UnidadMedida unidadMedida = UnidadMedida.UNIDAD;
    @Column(name = "precio_compra", precision = 10, scale = 2)
    private BigDecimal precioCompra;
    @Column(name = "precio_venta", precision = 10, scale = 2)
    private BigDecimal precioVenta;
    // Solo cambia a través de MovimientoInventario, nunca desde el CRUD de productos.
    @Column(name = "stock_actual", nullable = false, columnDefinition = "integer default 0 check (stock_actual >= 0)")
    private Integer stockActual = 0;
    @Column(name = "stock_minimo", nullable = false, columnDefinition = "integer default 5 check (stock_minimo >= 0)")
    private Integer stockMinimo = 5;
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    public Producto() {
    }

    @PrePersist
    void prePersist() {
        if (creadoEn == null) {
            creadoEn = LocalDateTime.now();
        }
        if (stockActual == null) {
            stockActual = 0;
        }
        if (stockMinimo == null) {
            stockMinimo = 5;
        }
        if (activo == null) {
            activo = true;
        }
    }

    @PreUpdate
    void preUpdate() {
        actualizadoEn = LocalDateTime.now();
    }

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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
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
