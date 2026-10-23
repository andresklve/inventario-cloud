package com.andres.inventario.repositories;

import com.andres.inventario.entities.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMovimientoRepository extends JpaRepository<MovimientoInventario, Long> {
    List<MovimientoInventario> findAllByOrderByFechaDescIdDesc();
    List<MovimientoInventario> findByProductoIdOrderByFechaAscIdAsc(Long productoId);
}
