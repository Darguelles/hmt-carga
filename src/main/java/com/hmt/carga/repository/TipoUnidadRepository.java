package com.hmt.carga.repository;

import com.hmt.carga.domain.TipoUnidad;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TipoUnidad entity.
 */
@SuppressWarnings("unused")
public interface TipoUnidadRepository extends JpaRepository<TipoUnidad,Long> {

}
