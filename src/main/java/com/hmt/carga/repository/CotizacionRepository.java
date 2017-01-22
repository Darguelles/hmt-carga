package com.hmt.carga.repository;

import com.hmt.carga.domain.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Cotizacion entity.
 */
@SuppressWarnings("unused")
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {

    List<Cotizacion> findAllByClienteRuc(String ruc);

    List<Cotizacion> findAllByEstado(String estado);

    List<Cotizacion> findAllByClienteRucAndEstado(String ruc, String estado);

}
