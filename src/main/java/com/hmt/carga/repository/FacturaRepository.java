package com.hmt.carga.repository;

import com.hmt.carga.domain.Factura;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Factura entity.
 */
@SuppressWarnings("unused")
public interface FacturaRepository extends JpaRepository<Factura,Long> {

    Factura findOneByCodigo(String codigo);

}
