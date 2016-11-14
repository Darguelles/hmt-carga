package com.hmt.carga.service;

import com.hmt.carga.domain.TipoUnidad;
import com.hmt.carga.repository.TipoUnidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing TipoUnidad.
 */
@Service
@Transactional
public class TipoUnidadService {

    private final Logger log = LoggerFactory.getLogger(TipoUnidadService.class);
    
    @Inject
    private TipoUnidadRepository tipoUnidadRepository;

    /**
     * Save a tipoUnidad.
     *
     * @param tipoUnidad the entity to save
     * @return the persisted entity
     */
    public TipoUnidad save(TipoUnidad tipoUnidad) {
        log.debug("Request to save TipoUnidad : {}", tipoUnidad);
        TipoUnidad result = tipoUnidadRepository.save(tipoUnidad);
        return result;
    }

    /**
     *  Get all the tipoUnidads.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TipoUnidad> findAll(Pageable pageable) {
        log.debug("Request to get all TipoUnidads");
        Page<TipoUnidad> result = tipoUnidadRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tipoUnidad by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TipoUnidad findOne(Long id) {
        log.debug("Request to get TipoUnidad : {}", id);
        TipoUnidad tipoUnidad = tipoUnidadRepository.findOne(id);
        return tipoUnidad;
    }

    /**
     *  Delete the  tipoUnidad by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoUnidad : {}", id);
        tipoUnidadRepository.delete(id);
    }
}
