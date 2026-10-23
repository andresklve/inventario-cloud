package com.andres.inventario.servicesimplements;

import com.andres.inventario.dtos.ResumenInventarioDTO;
import com.andres.inventario.entities.Producto;
import com.andres.inventario.exceptions.ConflictException;
import com.andres.inventario.exceptions.ResourceNotFoundException;
import com.andres.inventario.repositories.IProductoRepository;
import com.andres.inventario.servicesinterfaces.ICategoriaService;
import com.andres.inventario.servicesinterfaces.IProductoService;
import com.andres.inventario.servicesinterfaces.IProveedorService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImplement implements IProductoService {
    private final IProductoRepository pR;
    private final ICategoriaService cS;
    private final IProveedorService prS;

    public ProductoServiceImplement(IProductoRepository pR, ICategoriaService cS, IProveedorService prS) {
        this.pR = pR;
        this.cS = cS;
        this.prS = prS;
    }

    @Override
    @Transactional
    public Producto insert(Producto producto, Long categoriaId, Long proveedorId) {
        String sku = normalizarSku(producto.getSku());
        if (pR.existsBySkuIgnoreCase(sku)) {
            throw new ConflictException("Ya existe un producto con el SKU: " + sku);
        }
        producto.setId(null);
        producto.setSku(sku);
        producto.setCategoria(cS.listId(categoriaId));
        producto.setProveedor(proveedorId == null ? null : prS.listId(proveedorId));
        // Todo producto nace sin stock; las unidades entran registrando un movimiento de ENTRADA.
        producto.setStockActual(0);
        producto.setActivo(true);
        producto.setCreadoEn(null);
        producto.setActualizadoEn(null);
        return pR.save(producto);
    }

    @Override
    public List<Producto> list(Long categoriaId) {
        if (categoriaId != null) {
            return pR.findByCategoriaIdOrderByNombre(categoriaId);
        }
        return pR.findAll(Sort.by("nombre"));
    }

    @Override
    @Transactional
    public Producto update(Long id, Producto datos, Long categoriaId, Long proveedorId) {
        Producto p = listId(id);
        String sku = normalizarSku(datos.getSku());
        if (pR.existsBySkuIgnoreCaseAndIdNot(sku, id)) {
            throw new ConflictException("Ya existe un producto con el SKU: " + sku);
        }
        p.setSku(sku);
        p.setNombre(datos.getNombre());
        p.setDescripcion(datos.getDescripcion());
        p.setCategoria(cS.listId(categoriaId));
        p.setProveedor(proveedorId == null ? null : prS.listId(proveedorId));
        p.setUnidadMedida(datos.getUnidadMedida());
        p.setPrecioCompra(datos.getPrecioCompra());
        p.setPrecioVenta(datos.getPrecioVenta());
        p.setStockMinimo(datos.getStockMinimo());
        return pR.save(p);
    }

    // Baja lógica: el producto tiene historial de movimientos que no debe perderse.
    @Override
    @Transactional
    public void delete(Long id) {
        Producto p = listId(id);
        p.setActivo(false);
        pR.save(p);
    }

    @Override
    @Transactional
    public Producto reactivar(Long id) {
        Producto p = listId(id);
        p.setActivo(true);
        return pR.save(p);
    }

    @Override
    public Producto listId(Long id) {
        return pR.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe un producto con el id: " + id
                        )
                );
    }

    @Override
    public List<Producto> stockBajo() {
        return pR.findStockBajo();
    }

    @Override
    public ResumenInventarioDTO resumen() {
        return new ResumenInventarioDTO(
                pR.countByActivoTrue(),
                pR.sumUnidadesEnStock(),
                pR.sumValorInventario(),
                pR.countStockBajo(),
                pR.countByActivoTrueAndStockActual(0)
        );
    }

    private String normalizarSku(String sku) {
        return sku.trim().toUpperCase();
    }
}
