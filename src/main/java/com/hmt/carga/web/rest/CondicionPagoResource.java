package com.hmt.carga.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hmt.carga.domain.CondicionPago;
import com.hmt.carga.service.CondicionPagoService;
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
 * REST controller for managing CondicionPago.
 */
@RestController
@RequestMapping("/api")
public class CondicionPagoResource {

    private final Logger log = LoggerFactory.getLogger(CondicionPagoResource.class);
        
    @Inject
    private CondicionPagoService condicionPagoService;

    /**
     * POST  /condicion-pagos : Create a new condicionPago.
     *
     * @param condicionPago the condicionPago to create
     * @return the ResponseEntity with status 201 (Created) and with body the new condicionPago, or with status 400 (Bad Request) if the condicionPago has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/condicion-pagos")
    @Timed
    public ResponseEntity<CondicionPago> createCondicionPago(@Valid @RequestBody CondicionPago condicionPago) throws URISyntaxException {
        log.debug("REST request to save CondicionPago : {}", condicionPago);
        if (condicionPago.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("condicionPago", "idexists", "A new condicionPago cannot already have an ID")).body(null);
        }
        CondicionPago result = condicionPagoService.save(condicionPago);
        return ResponseEntity.created(new URI("/api/condicion-pagos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("condicionPago", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /condicion-pagos : Updates an existing condicionPago.
     *
     * @param condicionPago the condicionPago to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated condicionPago,
     * or with status 400 (Bad Request) if the condicionPago is not valid,
     * or with status 500 (Internal Server Error) if the condicionPago couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/condicion-pagos")
    @Timed
    public ResponseEntity<CondicionPago> updateCondicionPago(@Valid @RequestBody CondicionPago condicionPago) throws URISyntaxException {
        log.debug("REST request to update CondicionPago : {}", condicionPago);
        if (condicionPago.getId() == null) {
            return createCondicionPago(condicionPago);
        }
        CondicionPago result = condicionPagoService.save(condicionPago);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("condicionPago", condicionPago.getId().toString()))
            .body(result);
    }

    /**
     * GET  /condicion-pagos : get all the condicionPagos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of condicionPagos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/condicion-pagos")
    @Timed
    public ResponseEntity<List<CondicionPago>> getAllCondicionPagos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CondicionPagos");
        Page<CondicionPago> page = condicionPagoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/condicion-pagos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /condicion-pagos/:id : get the "id" condicionPago.
     *
     * @param id the id of the condicionPago to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the condicionPago, or with status 404 (Not Found)
     */
    @GetMapping("/condicion-pagos/{id}")
    @Timed
    public ResponseEntity<CondicionPago> getCondicionPago(@PathVariable Long id) {
        log.debug("REST request to get CondicionPago : {}", id);
        CondicionPago condicionPago = condicionPagoService.findOne(id);
        return Optional.ofNullable(condicionPago)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /condicion-pagos/:id : delete the "id" condicionPago.
     *
     * @param id the id of the condicionPago to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/condicion-pagos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCondicionPago(@PathVariable Long id) {
        log.debug("REST request to delete CondicionPago : {}", id);
        condicionPagoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("condicionPago", id.toString())).build();
    }

}
