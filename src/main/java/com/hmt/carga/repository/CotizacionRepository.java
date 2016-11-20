package com.hmt.carga.repository;

import com.hmt.carga.domain.Cotizacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cotizacion entity.
 */
@SuppressWarnings("unused")
public interface CotizacionRepository extends JpaRepository<Cotizacion,Long> {

}
