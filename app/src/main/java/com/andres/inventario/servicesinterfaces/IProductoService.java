package com.andres.inventario.servicesinterfaces;

import com.andres.inventario.dtos.ResumenInventarioDTO;
import com.andres.inventario.entities.Producto;

import java.util.List;

public interface IProductoService {
    public Producto insert(Producto producto, Long categoriaId, Long proveedorId);
    public List<Producto> list(Long categoriaId);
    public Producto update(Long id, Producto datos, Long categoriaId, Long proveedorId);
    public void delete(Long id);
    public Producto reactivar(Long id);
    public Producto listId(Long id);
    public List<Producto> stockBajo();
    public ResumenInventarioDTO resumen();
}
