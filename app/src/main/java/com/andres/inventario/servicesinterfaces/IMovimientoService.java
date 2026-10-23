package com.andres.inventario.servicesinterfaces;

import com.andres.inventario.entities.MovimientoInventario;

import java.util.List;

public interface IMovimientoService {
    public MovimientoInventario registrar(Long productoId, MovimientoInventario movimiento);
    public List<MovimientoInventario> list();
    public List<MovimientoInventario> listPorProducto(Long productoId);
}
