package com.hmt.carga.repository;

import com.hmt.carga.domain.Servicio;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Servicio entity.
 */
@SuppressWarnings("unused")
public interface ServicioRepository extends JpaRepository<Servicio,Long> {

}
