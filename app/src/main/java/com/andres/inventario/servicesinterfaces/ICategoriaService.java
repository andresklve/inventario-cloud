package com.andres.inventario.servicesinterfaces;

import com.andres.inventario.entities.Categoria;

import java.util.List;

public interface ICategoriaService {
    public Categoria insert(Categoria categoria);
    public List<Categoria> list();
    public Categoria update(Long id, Categoria datos);
    public void delete(Long id);
    public Categoria listId(Long id);
}
