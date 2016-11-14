package com.hmt.carga.service;

import com.hmt.carga.domain.CondicionPago;
import com.hmt.carga.repository.CondicionPagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing CondicionPago.
 */
@Service
@Transactional
public class CondicionPagoService {

    private final Logger log = LoggerFactory.getLogger(CondicionPagoService.class);
    
    @Inject
    private CondicionPagoRepository condicionPagoRepository;

    /**
     * Save a condicionPago.
     *
     * @param condicionPago the entity to save
     * @return the persisted entity
     */
    public CondicionPago save(CondicionPago condicionPago) {
        log.debug("Request to save CondicionPago : {}", condicionPago);
        CondicionPago result = condicionPagoRepository.save(condicionPago);
        return result;
    }

    /**
     *  Get all the condicionPagos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<CondicionPago> findAll(Pageable pageable) {
        log.debug("Request to get all CondicionPagos");
        Page<CondicionPago> result = condicionPagoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one condicionPago by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CondicionPago findOne(Long id) {
        log.debug("Request to get CondicionPago : {}", id);
        CondicionPago condicionPago = condicionPagoRepository.findOne(id);
        return condicionPago;
    }

    /**
     *  Delete the  condicionPago by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CondicionPago : {}", id);
        condicionPagoRepository.delete(id);
    }
}
