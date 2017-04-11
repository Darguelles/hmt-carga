package com.hmt.carga.service;

import com.hmt.carga.domain.Evento;
import com.hmt.carga.repository.EventoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Evento.
 */
@Service
@Transactional
public class EventoService {

    private final Logger log = LoggerFactory.getLogger(EventoService.class);
    
    @Inject
    private EventoRepository eventoRepository;

    /**
     * Save a evento.
     *
     * @param evento the entity to save
     * @return the persisted entity
     */
    public Evento save(Evento evento) {
        log.debug("Request to save Evento : {}", evento);
        Evento result = eventoRepository.save(evento);
        return result;
    }

    /**
     *  Get all the eventos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Evento> findAll(Pageable pageable) {
        log.debug("Request to get all Eventos");
        Page<Evento> result = eventoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one evento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Evento findOne(Long id) {
        log.debug("Request to get Evento : {}", id);
        Evento evento = eventoRepository.findOne(id);
        return evento;
    }

    /**
     *  Delete the  evento by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Evento : {}", id);
        eventoRepository.delete(id);
    }
}
