package com.hmt.carga.service;

import com.hmt.carga.domain.GuiaRemision;
import com.hmt.carga.repository.GuiaRemisionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing GuiaRemision.
 */
@Service
@Transactional
public class GuiaRemisionService {

    private final Logger log = LoggerFactory.getLogger(GuiaRemisionService.class);

    @Inject
    private GuiaRemisionRepository guiaRemisionRepository;

    /**
     * Save a guiaRemision.
     *
     * @param guiaRemision the entity to save
     * @return the persisted entity
     */
    public GuiaRemision save(GuiaRemision guiaRemision) {
        log.debug("Request to save GuiaRemision : {}", guiaRemision);
        GuiaRemision result = guiaRemisionRepository.save(guiaRemision);
        return result;
    }

    /**
     *  Get all the guiaRemisions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GuiaRemision> findAll(Pageable pageable) {
        log.debug("Request to get all GuiaRemisions");
        Page<GuiaRemision> result = guiaRemisionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one guiaRemision by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GuiaRemision findOne(Long id) {
        log.debug("Request to get GuiaRemision : {}", id);
        GuiaRemision guiaRemision = guiaRemisionRepository.findOne(id);
        return guiaRemision;
    }

    /**
     *  Delete the  guiaRemision by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GuiaRemision : {}", id);
        guiaRemisionRepository.delete(id);
    }

    public List<GuiaRemision> findAllByFacturada(Boolean facturada){
        return guiaRemisionRepository.findAllByFacturada(facturada);
    }
}
