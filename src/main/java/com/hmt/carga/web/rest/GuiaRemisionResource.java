package com.hmt.carga.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hmt.carga.domain.Cotizacion;
import com.hmt.carga.domain.GuiaRemision;
import com.hmt.carga.service.CotizacionService;
import com.hmt.carga.service.GuiaRemisionService;
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

import static com.hmt.carga.util.Cotizacion.EJECUTADA;

/**
 * REST controller for managing GuiaRemision.
 */
@RestController
@RequestMapping("/api")
public class GuiaRemisionResource {

    private final Logger log = LoggerFactory.getLogger(GuiaRemisionResource.class);

    @Inject
    private GuiaRemisionService guiaRemisionService;
    @Inject
    private CotizacionService cotizacionService;

    /**
     * POST  /guia-remisions : Create a new guiaRemision.
     *
     * @param guiaRemision the guiaRemision to create
     * @return the ResponseEntity with status 201 (Created) and with body the new guiaRemision, or with status 400 (Bad Request) if the guiaRemision has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/guia-remisions")
    @Timed
    public ResponseEntity<GuiaRemision> createGuiaRemision(@Valid @RequestBody GuiaRemision guiaRemision) throws URISyntaxException {
        log.debug("REST request to save GuiaRemision : {}", guiaRemision);
        if (guiaRemision.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("guiaRemision", "idexists", "A new guiaRemision cannot already have an ID")).body(null);
        }
        GuiaRemision result = guiaRemisionService.save(guiaRemision);

        //Update status oc Cotizacion
        if(result.getCotizacion()!=null){
            Cotizacion current = result.getCotizacion();
            current.setEstado(EJECUTADA.name());
            cotizacionService.save(current);
        }

        return ResponseEntity.created(new URI("/api/guia-remisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("guiaRemision", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /guia-remisions : Updates an existing guiaRemision.
     *
     * @param guiaRemision the guiaRemision to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated guiaRemision,
     * or with status 400 (Bad Request) if the guiaRemision is not valid,
     * or with status 500 (Internal Server Error) if the guiaRemision couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/guia-remisions")
    @Timed
    public ResponseEntity<GuiaRemision> updateGuiaRemision(@Valid @RequestBody GuiaRemision guiaRemision) throws URISyntaxException {
        log.debug("REST request to update GuiaRemision : {}", guiaRemision);
        if (guiaRemision.getId() == null) {
            return createGuiaRemision(guiaRemision);
        }
        GuiaRemision result = guiaRemisionService.save(guiaRemision);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("guiaRemision", guiaRemision.getId().toString()))
            .body(result);
    }

    /**
     * GET  /guia-remisions : get all the guiaRemisions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of guiaRemisions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/guia-remisions")
    @Timed
    public ResponseEntity<List<GuiaRemision>> getAllGuiaRemisions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of GuiaRemisions");
        Page<GuiaRemision> page = guiaRemisionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/guia-remisions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /guia-remisions/:id : get the "id" guiaRemision.
     *
     * @param id the id of the guiaRemision to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the guiaRemision, or with status 404 (Not Found)
     */
    @GetMapping("/guia-remisions/{id}")
    @Timed
    public ResponseEntity<GuiaRemision> getGuiaRemision(@PathVariable Long id) {
        log.debug("REST request to get GuiaRemision : {}", id);
        GuiaRemision guiaRemision = guiaRemisionService.findOne(id);
        return Optional.ofNullable(guiaRemision)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /guia-remisions/:id : delete the "id" guiaRemision.
     *
     * @param id the id of the guiaRemision to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/guia-remisions/{id}")
    @Timed
    public ResponseEntity<Void> deleteGuiaRemision(@PathVariable Long id) {
        log.debug("REST request to delete GuiaRemision : {}", id);
        guiaRemisionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("guiaRemision", id.toString())).build();
    }

}
