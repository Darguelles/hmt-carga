package com.hmt.carga.repository;

import com.hmt.carga.domain.GuiaRemision;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GuiaRemision entity.
 */
@SuppressWarnings("unused")
public interface GuiaRemisionRepository extends JpaRepository<GuiaRemision,Long> {

    List<GuiaRemision> findAllByFacturada(Integer facturada);

}
