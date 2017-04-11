package com.hmt.carga.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hmt.carga.domain.Cotizacion;
import com.hmt.carga.service.CotizacionService;
import com.hmt.carga.service.MailService;
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

import static com.hmt.carga.util.Cotizacion.APROBADA;
import static com.hmt.carga.util.Cotizacion.GENERADA;

/**
 * REST controller for managing Cotizacion.
 */
@RestController
@RequestMapping("/api")
public class CotizacionResource {

    private final Logger log = LoggerFactory.getLogger(CotizacionResource.class);

    @Inject
    private CotizacionService cotizacionService;

    @Inject
    private MailService mailService;

    /**
     * POST  /cotizacions : Create a new cotizacion.
     *
     * @param cotizacion the cotizacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cotizacion, or with status 400 (Bad Request) if the cotizacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cotizacions")
    @Timed
    public ResponseEntity<Cotizacion> createCotizacion(@Valid @RequestBody Cotizacion cotizacion) throws URISyntaxException {
        log.debug("REST request to save Cotizacion : {}", cotizacion);
        if (cotizacion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cotizacion", "idexists", "A new cotizacion cannot already have an ID")).body(null);
        }
        Cotizacion result = cotizacionService.save(cotizacion);

        mailService.sendEmailWithAttachment(cotizacion.getEmail(), "HMT System - Aprobación de orden de venta", "Se adjunta la cotización requerida.", false, true, "factura", result);


        return ResponseEntity.created(new URI("/api/cotizacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cotizacion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cotizacions : Updates an existing cotizacion.
     *
     * @param cotizacion the cotizacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cotizacion,
     * or with status 400 (Bad Request) if the cotizacion is not valid,
     * or with status 500 (Internal Server Error) if the cotizacion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cotizacions")
    @Timed
    public ResponseEntity<Cotizacion> updateCotizacion(@Valid @RequestBody Cotizacion cotizacion) throws URISyntaxException {
        log.debug("REST request to update Cotizacion : {}", cotizacion);
        if (cotizacion.getId() == null) {
            return createCotizacion(cotizacion);
        }
        Cotizacion result = cotizacionService.save(cotizacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cotizacion", cotizacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cotizacions : get all the cotizacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cotizacions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/cotizacions")
    @Timed
    public ResponseEntity<List<Cotizacion>> getAllCotizacions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cotizacions");
        Page<Cotizacion> page = cotizacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cotizacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/cotizacionsByEstadoGenerated")
    @Timed
    public ResponseEntity<List<Cotizacion>> getAllCotizacionsByEstadoGenerada()
        throws URISyntaxException {
        log.debug("REST request to get a page of Cotizacions");
        List<Cotizacion> page = cotizacionService.findAllByEstado(GENERADA.name());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/cotizacionsByEstadoApproved")
    @Timed
    public ResponseEntity<List<Cotizacion>> getAllCotizacionsByEstadoAprobada()
        throws URISyntaxException {
        log.debug("REST request to get a page of Cotizacions");
        List<Cotizacion> page = cotizacionService.findAllByEstado(APROBADA.name());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     * GET  /cotizacions/:id : get the "id" cotizacion.
     *
     * @param id the id of the cotizacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cotizacion, or with status 404 (Not Found)
     */
    @GetMapping("/cotizacions/{id}")
    @Timed
    public ResponseEntity<Cotizacion> getCotizacion(@PathVariable Long id) {
        log.debug("REST request to get Cotizacion : {}", id);
        Cotizacion cotizacion = cotizacionService.findOne(id);
        return Optional.ofNullable(cotizacion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cotizacions/:id : delete the "id" cotizacion.
     *
     * @param id the id of the cotizacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cotizacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCotizacion(@PathVariable Long id) {
        log.debug("REST request to delete Cotizacion : {}", id);
        cotizacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cotizacion", id.toString())).build();
    }

//    @GetMapping("/cotizacions/{ruc}")
//    public ResponseEntity<List<Cotizacion>> getCotizacionesPendientesByRuc(@PathVariable String ruc){
//        List<Cotizacion> list = cotizacionService.findAllByEstadoAndRuc(ruc);
//        return ResponseEntity.ok(list);
//    }

}
