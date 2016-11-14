package com.hmt.carga.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hmt.carga.domain.Proveedor;
import com.hmt.carga.service.ProveedorService;
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
 * REST controller for managing Proveedor.
 */
@RestController
@RequestMapping("/api")
public class ProveedorResource {

    private final Logger log = LoggerFactory.getLogger(ProveedorResource.class);
        
    @Inject
    private ProveedorService proveedorService;

    /**
     * POST  /proveedors : Create a new proveedor.
     *
     * @param proveedor the proveedor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new proveedor, or with status 400 (Bad Request) if the proveedor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proveedors")
    @Timed
    public ResponseEntity<Proveedor> createProveedor(@Valid @RequestBody Proveedor proveedor) throws URISyntaxException {
        log.debug("REST request to save Proveedor : {}", proveedor);
        if (proveedor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("proveedor", "idexists", "A new proveedor cannot already have an ID")).body(null);
        }
        Proveedor result = proveedorService.save(proveedor);
        return ResponseEntity.created(new URI("/api/proveedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("proveedor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proveedors : Updates an existing proveedor.
     *
     * @param proveedor the proveedor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated proveedor,
     * or with status 400 (Bad Request) if the proveedor is not valid,
     * or with status 500 (Internal Server Error) if the proveedor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proveedors")
    @Timed
    public ResponseEntity<Proveedor> updateProveedor(@Valid @RequestBody Proveedor proveedor) throws URISyntaxException {
        log.debug("REST request to update Proveedor : {}", proveedor);
        if (proveedor.getId() == null) {
            return createProveedor(proveedor);
        }
        Proveedor result = proveedorService.save(proveedor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("proveedor", proveedor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proveedors : get all the proveedors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of proveedors in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/proveedors")
    @Timed
    public ResponseEntity<List<Proveedor>> getAllProveedors(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Proveedors");
        Page<Proveedor> page = proveedorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proveedors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /proveedors/:id : get the "id" proveedor.
     *
     * @param id the id of the proveedor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the proveedor, or with status 404 (Not Found)
     */
    @GetMapping("/proveedors/{id}")
    @Timed
    public ResponseEntity<Proveedor> getProveedor(@PathVariable Long id) {
        log.debug("REST request to get Proveedor : {}", id);
        Proveedor proveedor = proveedorService.findOne(id);
        return Optional.ofNullable(proveedor)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /proveedors/:id : delete the "id" proveedor.
     *
     * @param id the id of the proveedor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proveedors/{id}")
    @Timed
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        log.debug("REST request to delete Proveedor : {}", id);
        proveedorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("proveedor", id.toString())).build();
    }

}
