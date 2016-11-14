package com.hmt.carga.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hmt.carga.domain.Servicio;
import com.hmt.carga.service.ServicioService;
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
 * REST controller for managing Servicio.
 */
@RestController
@RequestMapping("/api")
public class ServicioResource {

    private final Logger log = LoggerFactory.getLogger(ServicioResource.class);
        
    @Inject
    private ServicioService servicioService;

    /**
     * POST  /servicios : Create a new servicio.
     *
     * @param servicio the servicio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new servicio, or with status 400 (Bad Request) if the servicio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servicios")
    @Timed
    public ResponseEntity<Servicio> createServicio(@Valid @RequestBody Servicio servicio) throws URISyntaxException {
        log.debug("REST request to save Servicio : {}", servicio);
        if (servicio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("servicio", "idexists", "A new servicio cannot already have an ID")).body(null);
        }
        Servicio result = servicioService.save(servicio);
        return ResponseEntity.created(new URI("/api/servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("servicio", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servicios : Updates an existing servicio.
     *
     * @param servicio the servicio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated servicio,
     * or with status 400 (Bad Request) if the servicio is not valid,
     * or with status 500 (Internal Server Error) if the servicio couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servicios")
    @Timed
    public ResponseEntity<Servicio> updateServicio(@Valid @RequestBody Servicio servicio) throws URISyntaxException {
        log.debug("REST request to update Servicio : {}", servicio);
        if (servicio.getId() == null) {
            return createServicio(servicio);
        }
        Servicio result = servicioService.save(servicio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("servicio", servicio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servicios : get all the servicios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of servicios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/servicios")
    @Timed
    public ResponseEntity<List<Servicio>> getAllServicios(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Servicios");
        Page<Servicio> page = servicioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/servicios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /servicios/:id : get the "id" servicio.
     *
     * @param id the id of the servicio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the servicio, or with status 404 (Not Found)
     */
    @GetMapping("/servicios/{id}")
    @Timed
    public ResponseEntity<Servicio> getServicio(@PathVariable Long id) {
        log.debug("REST request to get Servicio : {}", id);
        Servicio servicio = servicioService.findOne(id);
        return Optional.ofNullable(servicio)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /servicios/:id : delete the "id" servicio.
     *
     * @param id the id of the servicio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servicios/{id}")
    @Timed
    public ResponseEntity<Void> deleteServicio(@PathVariable Long id) {
        log.debug("REST request to delete Servicio : {}", id);
        servicioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("servicio", id.toString())).build();
    }

}
