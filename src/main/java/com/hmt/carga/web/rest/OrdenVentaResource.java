package com.hmt.carga.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hmt.carga.domain.Cotizacion;
import com.hmt.carga.domain.OrdenVenta;
import com.hmt.carga.service.CotizacionService;
import com.hmt.carga.service.MailService;
import com.hmt.carga.service.OrdenVentaService;
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

/**
 * REST controller for managing OrdenVenta.
 */
@RestController
@RequestMapping("/api")
public class OrdenVentaResource {

    private final Logger log = LoggerFactory.getLogger(OrdenVentaResource.class);

    @Inject
    private OrdenVentaService ordenVentaService;

    @Inject
    private MailService mailService;

    @Inject
    private CotizacionService cotizacionService;

    /**
     * POST  /orden-ventas : Create a new ordenVenta.
     *
     * @param ordenVenta the ordenVenta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordenVenta, or with status 400 (Bad Request) if the ordenVenta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orden-ventas")
    @Timed
    public ResponseEntity<OrdenVenta> createOrdenVenta(@Valid @RequestBody OrdenVenta ordenVenta) throws URISyntaxException {
        log.debug("REST request to save OrdenVenta : {}", ordenVenta);
        if (ordenVenta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ordenVenta", "idexists", "A new ordenVenta cannot already have an ID")).body(null);
        }
        OrdenVenta result = ordenVentaService.save(ordenVenta);

        if (result.getCotizacion() != null) {
            Cotizacion current = result.getCotizacion();
            current.setEstado(APROBADA.name());
            cotizacionService.save(current);
        }

        mailService.sendEmail(ordenVenta.getEmailDestino(), "HMT System - Aprobación de orden de venta", generateEmailOrdenVenta(ordenVenta), false, true);
//        mailService.sendEmailWithAttachment(ordenVenta.getEmailDestino(), "HMT System - Aprobación de orden de venta", generateEmailOrdenVenta(ordenVenta), false, true, null);
        return ResponseEntity.created(new URI("/api/orden-ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ordenVenta", result.getId().toString()))
            .body(result);
    }

    private String generateEmailOrdenVenta(OrdenVenta ordenVenta) {
        StringBuilder sb = new StringBuilder("<h3>Tiene una nueva orden de venta: </h3>");
        sb.append("<label>Reciba el presente documento de parte de Grupo HM Peru</label>");
        sb.append("<br></br>");
        sb.append("<b>Codigo de cotización : " + ordenVenta.getCotizacion().getId() + "</b>");
        sb.append("<br></br>");
        sb.append("<b>Servicio :</b>" + ordenVenta.getCotizacion().getServicio().getNombre());
        sb.append("<br></br>");
        sb.append("<b>Cliente :</b>" + ordenVenta.getCotizacion().getCliente().getNombre());
        sb.append("<br></br>");
        sb.append("<b>Fecha :</b>" + ordenVenta.getCotizacion().getFecha());
        sb.append("<br></br>");
        sb.append("<b>Origen :</b>" + ordenVenta.getCotizacion().getOrigen());
        sb.append("<br></br>");
        sb.append("<b>Destino :</b>" + ordenVenta.getCotizacion().getDestino());
        sb.append("<br></br>");
        sb.append("<b>Mercaderia :</b>" + ordenVenta.getCotizacion().getMercaderia());
        sb.append("<br></br>");
        sb.append("<b>Precio :</b>" + ordenVenta.getCotizacion().getPrecio());
        sb.append("<label>El precio no incluye IGV</label>");

        return sb.toString();
    }

    /**
     * PUT  /orden-ventas : Updates an existing ordenVenta.
     *
     * @param ordenVenta the ordenVenta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordenVenta,
     * or with status 400 (Bad Request) if the ordenVenta is not valid,
     * or with status 500 (Internal Server Error) if the ordenVenta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orden-ventas")
    @Timed
    public ResponseEntity<OrdenVenta> updateOrdenVenta(@Valid @RequestBody OrdenVenta ordenVenta) throws URISyntaxException {
        log.debug("REST request to update OrdenVenta : {}", ordenVenta);
        if (ordenVenta.getId() == null) {
            return createOrdenVenta(ordenVenta);
        }
        OrdenVenta result = ordenVentaService.save(ordenVenta);
//        mailService.sendEmailWithAttachment(ordenVenta.getEmailDestino(), "HMT System - Aprobación de orden de venta", generateEmailOrdenVenta(ordenVenta), false, true, null);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ordenVenta", ordenVenta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orden-ventas : get all the ordenVentas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ordenVentas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/orden-ventas")
    @Timed
    public ResponseEntity<List<OrdenVenta>> getAllOrdenVentas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of OrdenVentas");
        Page<OrdenVenta> page = ordenVentaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orden-ventas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /orden-ventas/:id : get the "id" ordenVenta.
     *
     * @param id the id of the ordenVenta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordenVenta, or with status 404 (Not Found)
     */
    @GetMapping("/orden-ventas/{id}")
    @Timed
    public ResponseEntity<OrdenVenta> getOrdenVenta(@PathVariable Long id) {
        log.debug("REST request to get OrdenVenta : {}", id);
        OrdenVenta ordenVenta = ordenVentaService.findOne(id);
        return Optional.ofNullable(ordenVenta)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orden-ventas/:id : delete the "id" ordenVenta.
     *
     * @param id the id of the ordenVenta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orden-ventas/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdenVenta(@PathVariable Long id) {
        log.debug("REST request to delete OrdenVenta : {}", id);
        ordenVentaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ordenVenta", id.toString())).build();
    }

}
