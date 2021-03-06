package com.hmt.carga.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hmt.carga.domain.Factura;
import com.hmt.carga.domain.GuiaRemision;
import com.hmt.carga.service.FacturaService;
import com.hmt.carga.service.GuiaRemisionService;
import com.hmt.carga.util.NumberConverter;
import com.hmt.carga.util.PDFExporter;
import com.hmt.carga.web.rest.util.HeaderUtil;
import com.hmt.carga.web.rest.util.PaginationUtil;
import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.TextStyle;
import java.util.*;

/**
 * REST controller for managing Factura.
 */
@RestController
@RequestMapping("/api")
public class FacturaResource {

    private final Logger log = LoggerFactory.getLogger(FacturaResource.class);

    @Inject
    private FacturaService facturaService;

    @Inject
    private GuiaRemisionService guiaRemisionService;

    /**
     * POST  /facturas : Create a new factura.
     *
     * @param factura the factura to create
     * @return the ResponseEntity with status 201 (Created) and with body the new factura, or with status 400 (Bad Request) if the factura has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facturas")
    @Timed
    public ResponseEntity<Factura> createFactura(@Valid @RequestBody Factura factura) throws URISyntaxException, IOException {
        log.debug("REST request to save Factura : {}", factura);

        List<GuiaRemision> currentGuides = factura.getListaGuias();

        if (factura.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("factura", "idexists", "A new factura cannot already have an ID")).body(null);
        }

        Factura result = facturaService.save(factura);
        result.setListaGuias(null);
        System.out.println("RESULT : "+result);

        for (GuiaRemision guiaRemision : currentGuides){
            System.out.println("CURETN GUIDE : "+ guiaRemision);
            guiaRemision.setFactura(result);
            guiaRemision.setFacturada(1);
            guiaRemisionService.save(guiaRemision);
        }
        return ResponseEntity.created(new URI("/api/facturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("factura", result.getId().toString()))
            .body(result);
    }



    @Autowired
    private PDFExporter pdfExporter;

    @RequestMapping(value = "/factura/pdf/{codigoFactura}", method = RequestMethod.GET)
    @Timed
    public @ResponseBody ResponseEntity<byte[]> exportFacturaAsPDF(HttpServletResponse response, @PathVariable String codigoFactura) throws JRException, SQLException {

        response.setHeader("Content-Disposition", "inline; filename=file.pdf");
        response.setContentType("application/pdf");

        InputStream jasperStream = this.getClass().getResourceAsStream("/reports/factura.jrxml");

        Factura factura = facturaService.findOneByCodigo(codigoFactura);

        Map<String, Object> parameters = new HashMap();
        parameters.put("nombreCliente", factura.getCliente().getNombre()!=null ? factura.getCliente().getNombre(): "");
        parameters.put("direccionCliente", factura.getCliente().getDireccion()!=null ? factura.getCliente().getDireccion() : "" );
        parameters.put("rucCliente", factura.getCliente().getRuc()!=null ? factura.getCliente().getRuc().toString(): "");
        parameters.put("condicionPago", factura.getCliente().getCondicionPago().getNombre()!=null ? factura.getCliente().getCondicionPago().getNombre() : "");
        parameters.put("idFactura", codigoFactura);
        parameters.put("totalLetras", NumberConverter.convertir(factura.getPrecioTotal().toString(), true, " NUEVOS SOLES"));
        parameters.put("subTotal", factura.getPrecioBase());
        parameters.put("igv", factura.getIgv());
        parameters.put("total", factura.getPrecioTotal());
        parameters.put("fechaActualDia", String.valueOf(factura.getFecha().getDayOfMonth()));
        parameters.put("fechaActualMes", String.valueOf(factura.getFecha().getMonth().getDisplayName(TextStyle.FULL, new Locale("es_PE"))));
        parameters.put("fechaActualAnio", String.valueOf(factura.getFecha().getYear()-2000));


        JasperReport report = JasperCompileManager.compileReport(jasperStream);
        JasperPrint print = JasperFillManager.fillReport(report, parameters, pdfExporter.getConnection());
        byte[] file = JasperExportManager.exportReportToPdf(print);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));

        String filename = "Factura-"+codigoFactura+".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> pdfEntity = new ResponseEntity<byte[]>(file, headers, HttpStatus.OK);

        System.out.println("EXPORT PROCESS COMPLETE");
        System.out.println(file);
        System.out.println(pdfEntity);
        return pdfEntity;
    }



    /**
     * PUT  /facturas : Updates an existing factura.
     *
     * @param factura the factura to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated factura,
     * or with status 400 (Bad Request) if the factura is not valid,
     * or with status 500 (Internal Server Error) if the factura couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facturas")
    @Timed
    public ResponseEntity<Factura> updateFactura(@Valid @RequestBody Factura factura) throws URISyntaxException, IOException {
        log.debug("REST request to update Factura : {}", factura);
//        if (factura.getId() == null) {
//            return createFactura(factura);
//        }
        Factura result = facturaService.save(factura);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("factura", factura.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facturas : get all the facturas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facturas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/facturas")
    @Timed
    public ResponseEntity<List<Factura>> getAllFacturas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Facturas");

        Page<Factura> page = facturaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/facturas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facturas/:id : get the "id" factura.
     *
     * @param id the id of the factura to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the factura, or with status 404 (Not Found)
     */
    @GetMapping("/facturas/{id}")
    @Timed
    public ResponseEntity<Factura> getFactura(@PathVariable Long id) {
        log.debug("REST request to get Factura : {}", id);
        Factura factura = facturaService.findOne(id);
        return Optional.ofNullable(factura)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /facturas/:id : delete the "id" factura.
     *
     * @param id the id of the factura to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facturas/{id}")
    @Timed
    public ResponseEntity<Void> deleteFactura(@PathVariable Long id) {
        log.debug("REST request to delete Factura : {}", id);
        facturaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("factura", id.toString())).build();
    }


}
