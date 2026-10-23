package com.andres.inventario.servicesinterfaces;

import com.andres.inventario.entities.Proveedor;

import java.util.List;

public interface IProveedorService {
    public Proveedor insert(Proveedor proveedor);
    public List<Proveedor> list();
    public Proveedor update(Long id, Proveedor datos);
    public void delete(Long id);
    public Proveedor listId(Long id);
}
