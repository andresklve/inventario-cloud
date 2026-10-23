package com.andres.inventario.servicesimplements;

import com.andres.inventario.entities.MovimientoInventario;
import com.andres.inventario.entities.Producto;
import com.andres.inventario.enums.TipoMovimiento;
import com.andres.inventario.exceptions.BadRequestException;
import com.andres.inventario.exceptions.ConflictException;
import com.andres.inventario.exceptions.ResourceNotFoundException;
import com.andres.inventario.repositories.IMovimientoRepository;
import com.andres.inventario.repositories.IProductoRepository;
import com.andres.inventario.servicesinterfaces.IMovimientoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovimientoServiceImplement implements IMovimientoService {
    private final IMovimientoRepository mR;
    private final IProductoRepository pR;

    public MovimientoServiceImplement(IMovimientoRepository mR, IProductoRepository pR) {
        this.mR = mR;
        this.pR = pR;
    }

    @Override
    @Transactional
    public MovimientoInventario registrar(Long productoId, MovimientoInventario movimiento) {
        Producto p = pR.findByIdParaActualizar(productoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No existe un producto con el id: " + productoId
                        )
                );
        if (!p.getActivo()) {
            throw new ConflictException("El producto " + p.getSku() + " está dado de baja y no admite movimientos.");
        }

        TipoMovimiento tipo = movimiento.getTipo();
        int cantidad = movimiento.getCantidad();
        if (tipo != TipoMovimiento.AJUSTE && cantidad <= 0) {
            throw new BadRequestException("La cantidad de una " + tipo + " debe ser mayor a cero.");
        }

        int stockAnterior = p.getStockActual();
        int stockResultante = switch (tipo) {
            case ENTRADA -> stockAnterior + cantidad;
            case SALIDA -> stockAnterior - cantidad;
            case AJUSTE -> cantidad;
        };
        if (stockResultante < 0) {
            throw new ConflictException(
                    "Stock insuficiente para " + p.getSku() + ": disponible " + stockAnterior
                            + ", solicitado " + cantidad + "."
            );
        }
        if (tipo == TipoMovimiento.AJUSTE && stockResultante == stockAnterior) {
            throw new BadRequestException("El ajuste no cambia el stock: ya es " + stockAnterior + ".");
        }

        p.setStockActual(stockResultante);
        movimiento.setId(null);
        movimiento.setFecha(null);
        movimiento.setProducto(p);
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockResultante(stockResultante);
        pR.save(p);
        return mR.save(movimiento);
    }

    @Override
    public List<MovimientoInventario> list() {
        return mR.findAllByOrderByFechaDescIdDesc();
    }

    @Override
    public List<MovimientoInventario> listPorProducto(Long productoId) {
        if (!pR.existsById(productoId)) {
            throw new ResourceNotFoundException("No existe un producto con el id: " + productoId);
        }
        return mR.findByProductoIdOrderByFechaAscIdAsc(productoId);
    }
}
