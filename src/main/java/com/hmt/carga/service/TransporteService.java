package com.hmt.carga.service;

import com.hmt.carga.domain.Transporte;
import com.hmt.carga.repository.TransporteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Transporte.
 */
@Service
@Transactional
public class TransporteService {

    private final Logger log = LoggerFactory.getLogger(TransporteService.class);
    
    @Inject
    private TransporteRepository transporteRepository;

    /**
     * Save a transporte.
     *
     * @param transporte the entity to save
     * @return the persisted entity
     */
    public Transporte save(Transporte transporte) {
        log.debug("Request to save Transporte : {}", transporte);
        Transporte result = transporteRepository.save(transporte);
        return result;
    }

    /**
     *  Get all the transportes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Transporte> findAll(Pageable pageable) {
        log.debug("Request to get all Transportes");
        Page<Transporte> result = transporteRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one transporte by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Transporte findOne(Long id) {
        log.debug("Request to get Transporte : {}", id);
        Transporte transporte = transporteRepository.findOne(id);
        return transporte;
    }

    /**
     *  Delete the  transporte by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Transporte : {}", id);
        transporteRepository.delete(id);
    }
}
