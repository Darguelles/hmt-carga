package com.hmt.carga.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hmt.carga.domain.TipoUnidad;
import com.hmt.carga.service.TipoUnidadService;
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
 * REST controller for managing TipoUnidad.
 */
@RestController
@RequestMapping("/api")
public class TipoUnidadResource {

    private final Logger log = LoggerFactory.getLogger(TipoUnidadResource.class);
        
    @Inject
    private TipoUnidadService tipoUnidadService;

    /**
     * POST  /tipo-unidads : Create a new tipoUnidad.
     *
     * @param tipoUnidad the tipoUnidad to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoUnidad, or with status 400 (Bad Request) if the tipoUnidad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-unidads")
    @Timed
    public ResponseEntity<TipoUnidad> createTipoUnidad(@Valid @RequestBody TipoUnidad tipoUnidad) throws URISyntaxException {
        log.debug("REST request to save TipoUnidad : {}", tipoUnidad);
        if (tipoUnidad.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tipoUnidad", "idexists", "A new tipoUnidad cannot already have an ID")).body(null);
        }
        TipoUnidad result = tipoUnidadService.save(tipoUnidad);
        return ResponseEntity.created(new URI("/api/tipo-unidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipoUnidad", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-unidads : Updates an existing tipoUnidad.
     *
     * @param tipoUnidad the tipoUnidad to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoUnidad,
     * or with status 400 (Bad Request) if the tipoUnidad is not valid,
     * or with status 500 (Internal Server Error) if the tipoUnidad couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-unidads")
    @Timed
    public ResponseEntity<TipoUnidad> updateTipoUnidad(@Valid @RequestBody TipoUnidad tipoUnidad) throws URISyntaxException {
        log.debug("REST request to update TipoUnidad : {}", tipoUnidad);
        if (tipoUnidad.getId() == null) {
            return createTipoUnidad(tipoUnidad);
        }
        TipoUnidad result = tipoUnidadService.save(tipoUnidad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipoUnidad", tipoUnidad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-unidads : get all the tipoUnidads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoUnidads in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tipo-unidads")
    @Timed
    public ResponseEntity<List<TipoUnidad>> getAllTipoUnidads(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TipoUnidads");
        Page<TipoUnidad> page = tipoUnidadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-unidads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-unidads/:id : get the "id" tipoUnidad.
     *
     * @param id the id of the tipoUnidad to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoUnidad, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-unidads/{id}")
    @Timed
    public ResponseEntity<TipoUnidad> getTipoUnidad(@PathVariable Long id) {
        log.debug("REST request to get TipoUnidad : {}", id);
        TipoUnidad tipoUnidad = tipoUnidadService.findOne(id);
        return Optional.ofNullable(tipoUnidad)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipo-unidads/:id : delete the "id" tipoUnidad.
     *
     * @param id the id of the tipoUnidad to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-unidads/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoUnidad(@PathVariable Long id) {
        log.debug("REST request to delete TipoUnidad : {}", id);
        tipoUnidadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipoUnidad", id.toString())).build();
    }

}
