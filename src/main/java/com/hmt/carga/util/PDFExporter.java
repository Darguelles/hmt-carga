package com.hmt.carga.util;

import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by D-Developer on 03/04/2017.
 */
public class PDFExporter {

    private static final Logger log = LoggerFactory.getLogger(PDFExporter.class);

    public static java.sql.Connection getConnection(){
        java.sql.Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://192.168.99.100:3306/hmtcarga";
            String usr = "root";
            String psw = "";
            conn = DriverManager.getConnection(url, usr, psw);

        } catch (SQLException ex) {
            log.error("ERROR : "+ex);
        } catch (ClassNotFoundException ex) {
            log.error("ERROR : "+ex);
        }
        return conn;
    }

}
