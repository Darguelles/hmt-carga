package com.hmt.carga.service;

import com.hmt.carga.domain.Servicio;
import com.hmt.carga.repository.ServicioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Servicio.
 */
@Service
@Transactional
public class ServicioService {

    private final Logger log = LoggerFactory.getLogger(ServicioService.class);
    
    @Inject
    private ServicioRepository servicioRepository;

    /**
     * Save a servicio.
     *
     * @param servicio the entity to save
     * @return the persisted entity
     */
    public Servicio save(Servicio servicio) {
        log.debug("Request to save Servicio : {}", servicio);
        Servicio result = servicioRepository.save(servicio);
        return result;
    }

    /**
     *  Get all the servicios.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Servicio> findAll(Pageable pageable) {
        log.debug("Request to get all Servicios");
        Page<Servicio> result = servicioRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one servicio by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Servicio findOne(Long id) {
        log.debug("Request to get Servicio : {}", id);
        Servicio servicio = servicioRepository.findOne(id);
        return servicio;
    }

    /**
     *  Delete the  servicio by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Servicio : {}", id);
        servicioRepository.delete(id);
    }
}
