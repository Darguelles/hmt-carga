package com.hmt.carga.repository;

import com.hmt.carga.domain.FormaPago;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FormaPago entity.
 */
@SuppressWarnings("unused")
public interface FormaPagoRepository extends JpaRepository<FormaPago,Long> {

}
