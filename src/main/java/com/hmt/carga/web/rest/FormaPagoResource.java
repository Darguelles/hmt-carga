package com.hmt.carga.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hmt.carga.domain.FormaPago;
import com.hmt.carga.service.FormaPagoService;
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
 * REST controller for managing FormaPago.
 */
@RestController
@RequestMapping("/api")
public class FormaPagoResource {

    private final Logger log = LoggerFactory.getLogger(FormaPagoResource.class);
        
    @Inject
    private FormaPagoService formaPagoService;

    /**
     * POST  /forma-pagos : Create a new formaPago.
     *
     * @param formaPago the formaPago to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formaPago, or with status 400 (Bad Request) if the formaPago has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forma-pagos")
    @Timed
    public ResponseEntity<FormaPago> createFormaPago(@Valid @RequestBody FormaPago formaPago) throws URISyntaxException {
        log.debug("REST request to save FormaPago : {}", formaPago);
        if (formaPago.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("formaPago", "idexists", "A new formaPago cannot already have an ID")).body(null);
        }
        FormaPago result = formaPagoService.save(formaPago);
        return ResponseEntity.created(new URI("/api/forma-pagos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("formaPago", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forma-pagos : Updates an existing formaPago.
     *
     * @param formaPago the formaPago to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formaPago,
     * or with status 400 (Bad Request) if the formaPago is not valid,
     * or with status 500 (Internal Server Error) if the formaPago couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forma-pagos")
    @Timed
    public ResponseEntity<FormaPago> updateFormaPago(@Valid @RequestBody FormaPago formaPago) throws URISyntaxException {
        log.debug("REST request to update FormaPago : {}", formaPago);
        if (formaPago.getId() == null) {
            return createFormaPago(formaPago);
        }
        FormaPago result = formaPagoService.save(formaPago);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("formaPago", formaPago.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forma-pagos : get all the formaPagos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of formaPagos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/forma-pagos")
    @Timed
    public ResponseEntity<List<FormaPago>> getAllFormaPagos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FormaPagos");
        Page<FormaPago> page = formaPagoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/forma-pagos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /forma-pagos/:id : get the "id" formaPago.
     *
     * @param id the id of the formaPago to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formaPago, or with status 404 (Not Found)
     */
    @GetMapping("/forma-pagos/{id}")
    @Timed
    public ResponseEntity<FormaPago> getFormaPago(@PathVariable Long id) {
        log.debug("REST request to get FormaPago : {}", id);
        FormaPago formaPago = formaPagoService.findOne(id);
        return Optional.ofNullable(formaPago)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /forma-pagos/:id : delete the "id" formaPago.
     *
     * @param id the id of the formaPago to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forma-pagos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormaPago(@PathVariable Long id) {
        log.debug("REST request to delete FormaPago : {}", id);
        formaPagoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("formaPago", id.toString())).build();
    }

}
