package com.hmt.carga.service.util;

import com.hmt.carga.domain.Factura;
import com.hmt.carga.web.rest.FacturaResource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by D-Developer on 20/02/2017.
 */
public class DocumentExporter {

    private static final Logger log = LoggerFactory.getLogger(DocumentExporter.class);



    public static void printFactura(Factura factura) throws IOException {
        File reportFile = new File("C:/Works/HMTransportes/hmt-carga/src/main/resources/reports/factura.jasper");
        Map parameters = new HashMap();
        parameters.put("nombreCliente", factura.getCliente().getNombre());
        parameters.put("direccionCliente", factura.getCliente().getDireccion());
        parameters.put("rucCliente", factura.getCliente().getRuc().toString());
        parameters.put("condicionPago", factura.getCliente().getCondicionPago().getNombre());
        parameters.put("idFactura", factura.getId().intValue());
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://192.168.99.100:3306/hmtcarga";
            String usr = "root";
            String psw = "";
            conn = DriverManager.getConnection(url, usr, psw);

        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {

        }

        try {
//            JasperReport report = JasperCompileManager.compileReport(reportFile.toString());
            String filename = "factura"+factura.getId()+".pdf";
            JasperPrint print = JasperFillManager.fillReport(reportFile.getPath(), parameters, conn);
            JasperExportManager.exportReportToPdfFile(print, "C:/Works/"+filename);




//            File pdf = File.createTempFile("factura"+factura.getId(), ".pdf");
//            JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdf));
//            JasperViewer.viewReport(print);
        } catch (JRException ex) {
            log.info("WIIIIIIIII "+ex);
//            Logger.getLogger(TestDashboardView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
