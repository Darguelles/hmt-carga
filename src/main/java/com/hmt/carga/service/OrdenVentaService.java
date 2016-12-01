package com.hmt.carga.service;

import com.hmt.carga.domain.OrdenVenta;
import com.hmt.carga.repository.OrdenVentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing OrdenVenta.
 */
@Service
@Transactional
public class OrdenVentaService {

    private final Logger log = LoggerFactory.getLogger(OrdenVentaService.class);
    
    @Inject
    private OrdenVentaRepository ordenVentaRepository;

    /**
     * Save a ordenVenta.
     *
     * @param ordenVenta the entity to save
     * @return the persisted entity
     */
    public OrdenVenta save(OrdenVenta ordenVenta) {
        log.debug("Request to save OrdenVenta : {}", ordenVenta);
        OrdenVenta result = ordenVentaRepository.save(ordenVenta);
        return result;
    }

    /**
     *  Get all the ordenVentas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<OrdenVenta> findAll(Pageable pageable) {
        log.debug("Request to get all OrdenVentas");
        Page<OrdenVenta> result = ordenVentaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one ordenVenta by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OrdenVenta findOne(Long id) {
        log.debug("Request to get OrdenVenta : {}", id);
        OrdenVenta ordenVenta = ordenVentaRepository.findOne(id);
        return ordenVenta;
    }

    /**
     *  Delete the  ordenVenta by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrdenVenta : {}", id);
        ordenVentaRepository.delete(id);
    }
}
