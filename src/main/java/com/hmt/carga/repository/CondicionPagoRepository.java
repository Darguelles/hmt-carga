package com.hmt.carga.repository;

import com.hmt.carga.domain.CondicionPago;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CondicionPago entity.
 */
@SuppressWarnings("unused")
public interface CondicionPagoRepository extends JpaRepository<CondicionPago,Long> {

}
