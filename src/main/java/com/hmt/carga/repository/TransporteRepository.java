package com.hmt.carga.repository;

import com.hmt.carga.domain.Transporte;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Transporte entity.
 */
@SuppressWarnings("unused")
public interface TransporteRepository extends JpaRepository<Transporte,Long> {

}
