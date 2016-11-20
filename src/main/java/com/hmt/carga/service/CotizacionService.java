package com.hmt.carga.service;

import com.hmt.carga.domain.Cotizacion;
import com.hmt.carga.repository.CotizacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Cotizacion.
 */
@Service
@Transactional
public class CotizacionService {

    private final Logger log = LoggerFactory.getLogger(CotizacionService.class);
    
    @Inject
    private CotizacionRepository cotizacionRepository;

    /**
     * Save a cotizacion.
     *
     * @param cotizacion the entity to save
     * @return the persisted entity
     */
    public Cotizacion save(Cotizacion cotizacion) {
        log.debug("Request to save Cotizacion : {}", cotizacion);
        Cotizacion result = cotizacionRepository.save(cotizacion);
        return result;
    }

    /**
     *  Get all the cotizacions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Cotizacion> findAll(Pageable pageable) {
        log.debug("Request to get all Cotizacions");
        Page<Cotizacion> result = cotizacionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one cotizacion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Cotizacion findOne(Long id) {
        log.debug("Request to get Cotizacion : {}", id);
        Cotizacion cotizacion = cotizacionRepository.findOne(id);
        return cotizacion;
    }

    /**
     *  Delete the  cotizacion by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cotizacion : {}", id);
        cotizacionRepository.delete(id);
    }
}
