package com.hmt.carga.service;

import com.hmt.carga.domain.FormaPago;
import com.hmt.carga.repository.FormaPagoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing FormaPago.
 */
@Service
@Transactional
public class FormaPagoService {

    private final Logger log = LoggerFactory.getLogger(FormaPagoService.class);
    
    @Inject
    private FormaPagoRepository formaPagoRepository;

    /**
     * Save a formaPago.
     *
     * @param formaPago the entity to save
     * @return the persisted entity
     */
    public FormaPago save(FormaPago formaPago) {
        log.debug("Request to save FormaPago : {}", formaPago);
        FormaPago result = formaPagoRepository.save(formaPago);
        return result;
    }

    /**
     *  Get all the formaPagos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<FormaPago> findAll(Pageable pageable) {
        log.debug("Request to get all FormaPagos");
        Page<FormaPago> result = formaPagoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one formaPago by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public FormaPago findOne(Long id) {
        log.debug("Request to get FormaPago : {}", id);
        FormaPago formaPago = formaPagoRepository.findOne(id);
        return formaPago;
    }

    /**
     *  Delete the  formaPago by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FormaPago : {}", id);
        formaPagoRepository.delete(id);
    }
}
