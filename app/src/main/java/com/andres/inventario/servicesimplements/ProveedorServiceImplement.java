package com.andres.inventario.servicesimplements;

import com.andres.inventario.entities.Proveedor;
import com.andres.inventario.exceptions.ConflictException;
import com.andres.inventario.exceptions.ResourceNotFoundException;
import com.andres.inventario.repositories.IProductoRepository;
import com.andres.inventario.repositories.IProveedorRepository;
import com.andres.inventario.servicesinterfaces.IProveedorService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProveedorServiceImplement implements IProveedorService {
    private final IProveedorRepository prR;
    private final IProductoRepository pR;

    public ProveedorServiceImplement(IProveedorRepository prR, IProductoRepository pR) {
        this.prR = prR;
        this.pR = pR;
    }

    @Override
    @Transactional
    public Proveedor insert(Proveedor proveedor) {
        proveedor.setId(null);
        if (prR.existsByRuc(proveedor.getRuc())) {
            throw new ConflictException("Ya existe un proveedor con el RUC: " + proveedor.getRuc());
        }
        return prR.save(proveedor);
    }

    @Override
    public List<Proveedor> list() {
        return prR.findAll(Sort.by("razonSocial"));
    }

    @Override
    @Transactional
    public Proveedor update(Long id, Proveedor datos) {
        Proveedor p = listId(id);
        if (prR.existsByRucAndIdNot(datos.getRuc(), id)) {
            throw new ConflictException("Ya existe un proveedor con el RUC: " + datos.getRuc());
        }
        p.setRuc(datos.getRuc());
        p.setRazonSocial(datos.getRazonSocial());
        p.setContacto(datos.getContacto());
        p.setTelefono(datos.getTelefono());
        p.setEmail(datos.getEmail());
        return prR.save(p);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Proveedor p = listId(id);
        if (pR.existsByProveedorId(id)) {
            throw new ConflictException("No se puede eliminar el proveedor porque tiene productos asociados.");
        }
        prR.delete(p);
    }

    @Override
    public Proveedor listId(Long id) {
        return prR.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe un proveedor con el id: " + id
                        )
                );
    }
}
