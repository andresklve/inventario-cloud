package com.andres.inventario.servicesinterfaces;

import com.andres.inventario.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    public void insert(Producto producto);
    public List<Producto> list();
    public void update(Producto producto);
    public void delete(Long id);
    public Optional<Producto> listId(Long id);
}
