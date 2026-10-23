package com.andres.inventario.repositories;

import com.andres.inventario.entities.Producto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsBySkuIgnoreCase(String sku);
    boolean existsBySkuIgnoreCaseAndIdNot(String sku, Long id);
    boolean existsByCategoriaId(Long categoriaId);
    boolean existsByProveedorId(Long proveedorId);
    List<Producto> findByCategoriaIdOrderByNombre(Long categoriaId);

    // Bloquea la fila hasta terminar la transacción para que dos salidas simultáneas
    // no lean el mismo stock y lo dejen negativo.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Producto p where p.id = :id")
    Optional<Producto> findByIdParaActualizar(@Param("id") Long id);

    @Query("select p from Producto p where p.activo = true and p.stockActual < p.stockMinimo order by p.stockActual, p.nombre")
    List<Producto> findStockBajo();

    long countByActivoTrue();

    @Query("select count(p) from Producto p where p.activo = true and p.stockActual < p.stockMinimo")
    long countStockBajo();

    long countByActivoTrueAndStockActual(Integer stockActual);

    @Query("select coalesce(sum(p.stockActual), 0) from Producto p where p.activo = true")
    long sumUnidadesEnStock();

    @Query("select coalesce(sum(p.stockActual * p.precioCompra), 0) from Producto p where p.activo = true")
    BigDecimal sumValorInventario();
}
