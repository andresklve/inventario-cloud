package com.andres.inventario.servicesimplements;

import com.andres.inventario.entities.Categoria;
import com.andres.inventario.exceptions.ConflictException;
import com.andres.inventario.exceptions.ResourceNotFoundException;
import com.andres.inventario.repositories.ICategoriaRepository;
import com.andres.inventario.repositories.IProductoRepository;
import com.andres.inventario.servicesinterfaces.ICategoriaService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaServiceImplement implements ICategoriaService {
    private final ICategoriaRepository cR;
    private final IProductoRepository pR;

    public CategoriaServiceImplement(ICategoriaRepository cR, IProductoRepository pR) {
        this.cR = cR;
        this.pR = pR;
    }

    @Override
    @Transactional
    public Categoria insert(Categoria categoria) {
        categoria.setId(null);
        categoria.setNombre(categoria.getNombre().trim());
        if (cR.existsByNombreIgnoreCase(categoria.getNombre())) {
            throw new ConflictException("Ya existe una categoría con el nombre: " + categoria.getNombre());
        }
        return cR.save(categoria);
    }

    @Override
    public List<Categoria> list() {
        return cR.findAll(Sort.by("nombre"));
    }

    @Override
    @Transactional
    public Categoria update(Long id, Categoria datos) {
        Categoria c = listId(id);
        String nombre = datos.getNombre().trim();
        if (cR.existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
            throw new ConflictException("Ya existe una categoría con el nombre: " + nombre);
        }
        c.setNombre(nombre);
        c.setDescripcion(datos.getDescripcion());
        return cR.save(c);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Categoria c = listId(id);
        if (pR.existsByCategoriaId(id)) {
            throw new ConflictException("No se puede eliminar la categoría porque tiene productos asociados.");
        }
        cR.delete(c);
    }

    @Override
    public Categoria listId(Long id) {
        return cR.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe una categoría con el id: " + id
                        )
                );
    }
}
