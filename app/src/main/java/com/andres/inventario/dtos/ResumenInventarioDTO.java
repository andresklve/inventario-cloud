package com.andres.inventario.dtos;

import java.math.BigDecimal;

public class ResumenInventarioDTO {
    private long productosActivos;
    private long unidadesEnStock;
    // Suma de stock_actual * precio_compra de los productos activos.
    private BigDecimal valorInventario;
    private long productosStockBajo;
    private long productosSinStock;

    public ResumenInventarioDTO(long productosActivos, long unidadesEnStock, BigDecimal valorInventario,
                                long productosStockBajo, long productosSinStock) {
        this.productosActivos = productosActivos;
        this.unidadesEnStock = unidadesEnStock;
        this.valorInventario = valorInventario;
        this.productosStockBajo = productosStockBajo;
        this.productosSinStock = productosSinStock;
    }

    public long getProductosActivos() {
        return productosActivos;
    }

    public long getUnidadesEnStock() {
        return unidadesEnStock;
    }

    public BigDecimal getValorInventario() {
        return valorInventario;
    }

    public long getProductosStockBajo() {
        return productosStockBajo;
    }

    public long getProductosSinStock() {
        return productosSinStock;
    }
}
