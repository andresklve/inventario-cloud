package com.andres.inventario.entities;

import com.andres.inventario.enums.TipoMovimiento;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_inventario")
public class MovimientoInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 10, nullable = false)
    private TipoMovimiento tipo;
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    @Column(name = "stock_anterior", nullable = false)
    private Integer stockAnterior;
    @Column(name = "stock_resultante", nullable = false)
    private Integer stockResultante;
    @Column(name = "motivo", length = 255)
    private String motivo;
    // Documento que respalda el movimiento: factura, guía de remisión, etc.
    @Column(name = "referencia", length = 50)
    private String referencia;
    @Column(name = "fecha", nullable = false, updatable = false)
    private LocalDateTime fecha;

    public MovimientoInventario() {
    }

    public MovimientoInventario(TipoMovimiento tipo, Integer cantidad, String motivo, String referencia) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.referencia = referencia;
    }

    @PrePersist
    void prePersist() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
