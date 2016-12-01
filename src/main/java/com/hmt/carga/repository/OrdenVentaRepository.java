package com.hmt.carga.repository;

import com.hmt.carga.domain.OrdenVenta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrdenVenta entity.
 */
@SuppressWarnings("unused")
public interface OrdenVentaRepository extends JpaRepository<OrdenVenta,Long> {

}
