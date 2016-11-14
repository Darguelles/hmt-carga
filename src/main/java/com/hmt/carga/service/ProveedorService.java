package com.hmt.carga.service;

import com.hmt.carga.domain.Proveedor;
import com.hmt.carga.repository.ProveedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Proveedor.
 */
@Service
@Transactional
public class ProveedorService {

    private final Logger log = LoggerFactory.getLogger(ProveedorService.class);
    
    @Inject
    private ProveedorRepository proveedorRepository;

    /**
     * Save a proveedor.
     *
     * @param proveedor the entity to save
     * @return the persisted entity
     */
    public Proveedor save(Proveedor proveedor) {
        log.debug("Request to save Proveedor : {}", proveedor);
        Proveedor result = proveedorRepository.save(proveedor);
        return result;
    }

    /**
     *  Get all the proveedors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Proveedor> findAll(Pageable pageable) {
        log.debug("Request to get all Proveedors");
        Page<Proveedor> result = proveedorRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one proveedor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Proveedor findOne(Long id) {
        log.debug("Request to get Proveedor : {}", id);
        Proveedor proveedor = proveedorRepository.findOne(id);
        return proveedor;
    }

    /**
     *  Delete the  proveedor by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Proveedor : {}", id);
        proveedorRepository.delete(id);
    }
}
