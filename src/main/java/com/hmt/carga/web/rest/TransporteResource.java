package com.hmt.carga.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hmt.carga.domain.Transporte;
import com.hmt.carga.service.TransporteService;
import com.hmt.carga.web.rest.util.HeaderUtil;
import com.hmt.carga.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Transporte.
 */
@RestController
@RequestMapping("/api")
public class TransporteResource {

    private final Logger log = LoggerFactory.getLogger(TransporteResource.class);
        
    @Inject
    private TransporteService transporteService;

    /**
     * POST  /transportes : Create a new transporte.
     *
     * @param transporte the transporte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transporte, or with status 400 (Bad Request) if the transporte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transportes")
    @Timed
    public ResponseEntity<Transporte> createTransporte(@Valid @RequestBody Transporte transporte) throws URISyntaxException {
        log.debug("REST request to save Transporte : {}", transporte);
        if (transporte.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("transporte", "idexists", "A new transporte cannot already have an ID")).body(null);
        }
        Transporte result = transporteService.save(transporte);
        return ResponseEntity.created(new URI("/api/transportes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("transporte", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transportes : Updates an existing transporte.
     *
     * @param transporte the transporte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transporte,
     * or with status 400 (Bad Request) if the transporte is not valid,
     * or with status 500 (Internal Server Error) if the transporte couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transportes")
    @Timed
    public ResponseEntity<Transporte> updateTransporte(@Valid @RequestBody Transporte transporte) throws URISyntaxException {
        log.debug("REST request to update Transporte : {}", transporte);
        if (transporte.getId() == null) {
            return createTransporte(transporte);
        }
        Transporte result = transporteService.save(transporte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("transporte", transporte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transportes : get all the transportes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of transportes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/transportes")
    @Timed
    public ResponseEntity<List<Transporte>> getAllTransportes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Transportes");
        Page<Transporte> page = transporteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transportes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /transportes/:id : get the "id" transporte.
     *
     * @param id the id of the transporte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transporte, or with status 404 (Not Found)
     */
    @GetMapping("/transportes/{id}")
    @Timed
    public ResponseEntity<Transporte> getTransporte(@PathVariable Long id) {
        log.debug("REST request to get Transporte : {}", id);
        Transporte transporte = transporteService.findOne(id);
        return Optional.ofNullable(transporte)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /transportes/:id : delete the "id" transporte.
     *
     * @param id the id of the transporte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transportes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransporte(@PathVariable Long id) {
        log.debug("REST request to delete Transporte : {}", id);
        transporteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("transporte", id.toString())).build();
    }

}
