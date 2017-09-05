package com.hmt.carga.util;

import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by D-Developer on 03/04/2017.
 */
@Component
public class PDFExporter {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private static final Logger log = LoggerFactory.getLogger(PDFExporter.class);

    public java.sql.Connection getConnection(){
        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
        java.sql.Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            log.error("ERROR : "+ex);
        } catch (ClassNotFoundException ex) {
            log.error("ERROR : "+ex);
        }
        return conn;
    }

}
