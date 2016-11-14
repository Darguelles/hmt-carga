package com.hmt.carga.repository;

import com.hmt.carga.domain.Proveedor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Proveedor entity.
 */
@SuppressWarnings("unused")
public interface ProveedorRepository extends JpaRepository<Proveedor,Long> {

}
