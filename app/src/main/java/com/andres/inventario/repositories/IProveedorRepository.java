package com.andres.inventario.repositories;

import com.andres.inventario.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor, Long> {
    boolean existsByRuc(String ruc);
    boolean existsByRucAndIdNot(String ruc, Long id);
}
