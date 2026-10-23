package com.andres.inventario.servicesimplements;

import com.andres.inventario.entities.MovimientoInventario;
import com.andres.inventario.entities.Producto;
import com.andres.inventario.enums.TipoMovimiento;
import com.andres.inventario.exceptions.BadRequestException;
import com.andres.inventario.exceptions.ConflictException;
import com.andres.inventario.repositories.IMovimientoRepository;
import com.andres.inventario.repositories.IProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Pruebas unitarias sin base de datos: corren rápido y sirven en el pipeline.
class MovimientoServiceImplementTest {
    private IMovimientoRepository mR;
    private IProductoRepository pR;
    private MovimientoServiceImplement mS;
    private Producto producto;

    @BeforeEach
    void setUp() {
        mR = mock(IMovimientoRepository.class);
        pR = mock(IProductoRepository.class);
        mS = new MovimientoServiceImplement(mR, pR);

        producto = new Producto();
        producto.setId(1L);
        producto.setSku("TEST-001");
        producto.setStockActual(10);
        producto.setActivo(true);

        when(pR.findByIdParaActualizar(1L)).thenReturn(Optional.of(producto));
        when(mR.save(any(MovimientoInventario.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void entradaSumaAlStock() {
        MovimientoInventario m = mS.registrar(1L, new MovimientoInventario(TipoMovimiento.ENTRADA, 5, null, null));

        assertEquals(15, producto.getStockActual());
        assertEquals(10, m.getStockAnterior());
        assertEquals(15, m.getStockResultante());
    }

    @Test
    void salidaRestaDelStock() {
        mS.registrar(1L, new MovimientoInventario(TipoMovimiento.SALIDA, 4, null, null));

        assertEquals(6, producto.getStockActual());
    }

    @Test
    void salidaMayorAlStockSeRechaza() {
        assertThrows(ConflictException.class, () ->
                mS.registrar(1L, new MovimientoInventario(TipoMovimiento.SALIDA, 11, null, null)));

        assertEquals(10, producto.getStockActual());
        verify(mR, never()).save(any());
    }

    @Test
    void ajusteFijaElConteoFisico() {
        MovimientoInventario m = mS.registrar(1L, new MovimientoInventario(TipoMovimiento.AJUSTE, 7, "Conteo", null));

        assertEquals(7, producto.getStockActual());
        assertEquals(7, m.getStockResultante());
    }

    @Test
    void cantidadCeroEnEntradaSeRechaza() {
        assertThrows(BadRequestException.class, () ->
                mS.registrar(1L, new MovimientoInventario(TipoMovimiento.ENTRADA, 0, null, null)));
    }

    @Test
    void productoInactivoNoAdmiteMovimientos() {
        producto.setActivo(false);

        assertThrows(ConflictException.class, () ->
                mS.registrar(1L, new MovimientoInventario(TipoMovimiento.ENTRADA, 3, null, null)));
    }
}
