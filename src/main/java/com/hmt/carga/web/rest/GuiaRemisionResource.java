package com.hmt.carga.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hmt.carga.domain.Cotizacion;
import com.hmt.carga.domain.Factura;
import com.hmt.carga.domain.GuiaRemision;
import com.hmt.carga.service.CotizacionService;
import com.hmt.carga.service.GuiaRemisionService;
import com.hmt.carga.util.PDFExporter;
import com.hmt.carga.web.rest.util.HeaderUtil;
import com.hmt.carga.web.rest.util.PaginationUtil;
//import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private PDFExporter pdfExporter;

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
        guiaRemision.setFacturada(0);
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

    @RequestMapping(value = "/guia-remisions/pdf/{codigoGuia}", method = RequestMethod.GET)
    @Timed
    public @ResponseBody ResponseEntity<byte[]> exportFacturaAsPDF(HttpServletResponse response, @PathVariable String codigoGuia) throws JRException, SQLException {

        response.setHeader("Content-Disposition", "inline; filename=file.pdf");
        response.setContentType("application/pdf");

        InputStream jasperStream = this.getClass().getResourceAsStream("/reports/guia_remision.jrxml");

        GuiaRemision guia = guiaRemisionService.findOneByCodigo(codigoGuia);

        Map<String, Object> parameters = new HashMap();
        parameters.put("direccionPartida", guia.getCotizacion().getOrigen()!=null? guia.getCotizacion().getOrigen().toString() :"");
        parameters.put("direccionLlegada", guia.getCotizacion().getDestino()!=null? guia.getCotizacion().getDestino().toString() :"");
        parameters.put("remitente", "HM Transportes");
        parameters.put("destinatario", guia.getCotizacion().getNombreReceptor()!=null? guia.getCotizacion().getNombreReceptor().toString() :"");
        parameters.put("fechaSalida", guia.getFechaSalida()!=null? guia.getFechaSalida().format(DateTimeFormatter.ISO_LOCAL_DATE).toString() :"");
        parameters.put("fechaingreso", guia.getFechaIngreso()!=null?  guia.getFechaIngreso().format(DateTimeFormatter.ISO_LOCAL_DATE).toString() :"");
        parameters.put("marcaVehiculo", guia.getTransporte().getMarca()!=null? guia.getTransporte().getMarca().toString() :"");
        parameters.put("tracto", guia.getTransporte().getTracto()!=null? guia.getTransporte().getTracto().toString() :"");
        parameters.put("plataforma", guia.getTransporte().getPlacaCarreta()!=null? guia.getTransporte().getPlacaCarreta().toString() :"");
        parameters.put("configuracionVehicular", guia.getTransporte().getTipoUnidad().getConfiguracion()!=null? guia.getTransporte().getTipoUnidad().getConfiguracion().toString() :"");
        parameters.put("conductor", guia.getTransporte().getNombreConductor()!=null? guia.getTransporte().getNombreConductor().toString():"");
        parameters.put("camaBaja", guia.getTransporte().getAnchoCarreta()!=null? guia.getTransporte().getAnchoCarreta().toString() :"");
        parameters.put("certificacionInscripcion", guia.getTransporte().getSoat()!=null ? guia.getTransporte().getSoat().toString() :"");
        parameters.put("licenciaConducir", guia.getTransporte().getLicenciaConductor()!=null? guia.getTransporte().getLicenciaConductor().toString() :"");
        parameters.put("datosEmpresa", "UNDEFINED");
        parameters.put("observaciones", guia.getObservaciones()!=null? guia.getObservaciones() :"");
        parameters.put("fechaEmision", guia.getFechaEmision()!=null? guia.getFechaEmision().format(DateTimeFormatter.ISO_LOCAL_DATE).toString() :"");
        parameters.put("fechaTraslado", guia.getFechaTraslado()!=null? guia.getFechaTraslado().format(DateTimeFormatter.ISO_LOCAL_DATE).toString() :"");
        parameters.put("numeroGuia", guia.getCodigo()!=null? guia.getCodigo().toString().toString() :"");



        JasperReport report = JasperCompileManager.compileReport(jasperStream);
        JasperPrint print = JasperFillManager.fillReport(report, parameters, pdfExporter.getConnection());
        byte[] file = JasperExportManager.exportReportToPdf(print);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));

        String filename = "GuiaRemision-"+codigoGuia+".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> pdfEntity = new ResponseEntity<byte[]>(file, headers, HttpStatus.OK);

        System.out.println("EXPORT PROCESS COMPLETE");
        System.out.println(file);
        System.out.println(pdfEntity);
        return pdfEntity;
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
        throws URISyntaxException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        log.debug("REST request to get a page of GuiaRemisions");
        Page<GuiaRemision> page = guiaRemisionService.findAll(pageable);
        List<GuiaRemision> guias = guiaRemisionService.findAllByFacturada(1);
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

    @GetMapping("/pendingGuides")
    @Timed
    public ResponseEntity<List<GuiaRemision>> getGuiasRemisionNoFacturadas()
        throws URISyntaxException {
        log.debug("REST request to get a page of Cotizacions");
        System.out.println("=============================FINDING ");
        List<GuiaRemision> guiasNoFacturadas = guiaRemisionService.findAllByFacturada(0);
        System.out.println("=============================FOUNDED : "+guiasNoFacturadas.size());
        return new ResponseEntity<>(guiasNoFacturadas, HttpStatus.OK);
    }




}
