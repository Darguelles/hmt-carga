package com.hmt.carga.repository;

import com.hmt.carga.domain.Evento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Evento entity.
 */
@SuppressWarnings("unused")
public interface EventoRepository extends JpaRepository<Evento,Long> {

}
