package com.hmt.carga.service;

import com.hmt.carga.domain.Factura;
import com.hmt.carga.repository.FacturaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Factura.
 */
@Service
@Transactional
public class FacturaService {

    private final Logger log = LoggerFactory.getLogger(FacturaService.class);
    
    @Inject
    private FacturaRepository facturaRepository;

    /**
     * Save a factura.
     *
     * @param factura the entity to save
     * @return the persisted entity
     */
    public Factura save(Factura factura) {
        log.debug("Request to save Factura : {}", factura);
        Factura result = facturaRepository.save(factura);
        return result;
    }

    /**
     *  Get all the facturas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Factura> findAll(Pageable pageable) {
        log.debug("Request to get all Facturas");
        Page<Factura> result = facturaRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one factura by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Factura findOne(Long id) {
        log.debug("Request to get Factura : {}", id);
        Factura factura = facturaRepository.findOne(id);
        return factura;
    }

    /**
     *  Delete the  factura by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Factura : {}", id);
        facturaRepository.delete(id);
    }
}
