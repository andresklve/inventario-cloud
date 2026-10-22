package com.andres.inventario.servicesimplements;

import com.andres.inventario.entities.Producto;
import com.andres.inventario.repositories.IProductoRepository;
import com.andres.inventario.servicesinterfaces.IProductoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImplement implements IProductoService {
    private final IProductoRepository pR;

    public ProductoServiceImplement(IProductoRepository pR) {
        this.pR = pR;
    }

    @Override
    public void insert(Producto producto) {
        pR.save(producto);
    }

    @Override
    public List<Producto> list() {
        return pR.findAll();
    }

    @Override
    public void update(Producto producto) {
        pR.save(producto);
    }

    @Override
    public void delete(Long id) {
        pR.deleteById(id);
    }

    @Override
    public Optional<Producto> listId(Long id) {
        return pR.findById(id);
    }
}
