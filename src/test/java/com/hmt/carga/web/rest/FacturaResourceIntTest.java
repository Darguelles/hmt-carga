package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.Factura;
import com.hmt.carga.domain.Cliente;
import com.hmt.carga.domain.Servicio;
import com.hmt.carga.repository.FacturaRepository;
import com.hmt.carga.service.FacturaService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FacturaResource REST controller.
 *
 * @see FacturaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class FacturaResourceIntTest {

    private static final Double DEFAULT_PRECIO_UNITARIO = 1D;
    private static final Double UPDATED_PRECIO_UNITARIO = 2D;

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Double DEFAULT_PRECIO_BASE = 1D;
    private static final Double UPDATED_PRECIO_BASE = 2D;

    private static final Double DEFAULT_IGV = 1D;
    private static final Double UPDATED_IGV = 2D;

    private static final Double DEFAULT_PRECIO_TOTAL = 1D;
    private static final Double UPDATED_PRECIO_TOTAL = 2D;

    private static final String DEFAULT_CODIGO = "AAAAA";
    private static final String UPDATED_CODIGO = "BBBBB";

    @Inject
    private FacturaRepository facturaRepository;

    @Inject
    private FacturaService facturaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFacturaMockMvc;

    private Factura factura;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FacturaResource facturaResource = new FacturaResource();
        ReflectionTestUtils.setField(facturaResource, "facturaService", facturaService);
        this.restFacturaMockMvc = MockMvcBuilders.standaloneSetup(facturaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factura createEntity(EntityManager em) {
        Factura factura = new Factura()
                .precioUnitario(DEFAULT_PRECIO_UNITARIO)
                .cantidad(DEFAULT_CANTIDAD)
                .precioBase(DEFAULT_PRECIO_BASE)
                .igv(DEFAULT_IGV)
                .precioTotal(DEFAULT_PRECIO_TOTAL)
                .codigo(DEFAULT_CODIGO);
        // Add required entity
        Cliente cliente = ClienteResourceIntTest.createEntity(em);
        em.persist(cliente);
        em.flush();
        factura.setCliente(cliente);
        // Add required entity
        Servicio servicio = ServicioResourceIntTest.createEntity(em);
        em.persist(servicio);
        em.flush();
        factura.setServicio(servicio);
        return factura;
    }

    @Before
    public void initTest() {
        factura = createEntity(em);
    }

    @Test
    @Transactional
    public void createFactura() throws Exception {
        int databaseSizeBeforeCreate = facturaRepository.findAll().size();

        // Create the Factura

        restFacturaMockMvc.perform(post("/api/facturas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(factura)))
                .andExpect(status().isCreated());

        // Validate the Factura in the database
        List<Factura> facturas = facturaRepository.findAll();
        assertThat(facturas).hasSize(databaseSizeBeforeCreate + 1);
        Factura testFactura = facturas.get(facturas.size() - 1);
        assertThat(testFactura.getPrecioUnitario()).isEqualTo(DEFAULT_PRECIO_UNITARIO);
        assertThat(testFactura.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testFactura.getPrecioBase()).isEqualTo(DEFAULT_PRECIO_BASE);
        assertThat(testFactura.getIgv()).isEqualTo(DEFAULT_IGV);
        assertThat(testFactura.getPrecioTotal()).isEqualTo(DEFAULT_PRECIO_TOTAL);
        assertThat(testFactura.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void checkPrecioUnitarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setPrecioUnitario(null);

        // Create the Factura, which fails.

        restFacturaMockMvc.perform(post("/api/facturas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(factura)))
                .andExpect(status().isBadRequest());

        List<Factura> facturas = facturaRepository.findAll();
        assertThat(facturas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setCantidad(null);

        // Create the Factura, which fails.

        restFacturaMockMvc.perform(post("/api/facturas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(factura)))
                .andExpect(status().isBadRequest());

        List<Factura> facturas = facturaRepository.findAll();
        assertThat(facturas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioBaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setPrecioBase(null);

        // Create the Factura, which fails.

        restFacturaMockMvc.perform(post("/api/facturas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(factura)))
                .andExpect(status().isBadRequest());

        List<Factura> facturas = facturaRepository.findAll();
        assertThat(facturas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIgvIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setIgv(null);

        // Create the Factura, which fails.

        restFacturaMockMvc.perform(post("/api/facturas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(factura)))
                .andExpect(status().isBadRequest());

        List<Factura> facturas = facturaRepository.findAll();
        assertThat(facturas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setPrecioTotal(null);

        // Create the Factura, which fails.

        restFacturaMockMvc.perform(post("/api/facturas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(factura)))
                .andExpect(status().isBadRequest());

        List<Factura> facturas = facturaRepository.findAll();
        assertThat(facturas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturaRepository.findAll().size();
        // set the field null
        factura.setCodigo(null);

        // Create the Factura, which fails.

        restFacturaMockMvc.perform(post("/api/facturas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(factura)))
                .andExpect(status().isBadRequest());

        List<Factura> facturas = facturaRepository.findAll();
        assertThat(facturas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacturas() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get all the facturas
        restFacturaMockMvc.perform(get("/api/facturas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(factura.getId().intValue())))
                .andExpect(jsonPath("$.[*].precioUnitario").value(hasItem(DEFAULT_PRECIO_UNITARIO.doubleValue())))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
                .andExpect(jsonPath("$.[*].precioBase").value(hasItem(DEFAULT_PRECIO_BASE.doubleValue())))
                .andExpect(jsonPath("$.[*].igv").value(hasItem(DEFAULT_IGV.doubleValue())))
                .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(DEFAULT_PRECIO_TOTAL.doubleValue())))
                .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())));
    }

    @Test
    @Transactional
    public void getFactura() throws Exception {
        // Initialize the database
        facturaRepository.saveAndFlush(factura);

        // Get the factura
        restFacturaMockMvc.perform(get("/api/facturas/{id}", factura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(factura.getId().intValue()))
            .andExpect(jsonPath("$.precioUnitario").value(DEFAULT_PRECIO_UNITARIO.doubleValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.precioBase").value(DEFAULT_PRECIO_BASE.doubleValue()))
            .andExpect(jsonPath("$.igv").value(DEFAULT_IGV.doubleValue()))
            .andExpect(jsonPath("$.precioTotal").value(DEFAULT_PRECIO_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFactura() throws Exception {
        // Get the factura
        restFacturaMockMvc.perform(get("/api/facturas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFactura() throws Exception {
        // Initialize the database
        facturaService.save(factura);

        int databaseSizeBeforeUpdate = facturaRepository.findAll().size();

        // Update the factura
        Factura updatedFactura = facturaRepository.findOne(factura.getId());
        updatedFactura
                .precioUnitario(UPDATED_PRECIO_UNITARIO)
                .cantidad(UPDATED_CANTIDAD)
                .precioBase(UPDATED_PRECIO_BASE)
                .igv(UPDATED_IGV)
                .precioTotal(UPDATED_PRECIO_TOTAL)
                .codigo(UPDATED_CODIGO);

        restFacturaMockMvc.perform(put("/api/facturas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFactura)))
                .andExpect(status().isOk());

        // Validate the Factura in the database
        List<Factura> facturas = facturaRepository.findAll();
        assertThat(facturas).hasSize(databaseSizeBeforeUpdate);
        Factura testFactura = facturas.get(facturas.size() - 1);
        assertThat(testFactura.getPrecioUnitario()).isEqualTo(UPDATED_PRECIO_UNITARIO);
        assertThat(testFactura.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testFactura.getPrecioBase()).isEqualTo(UPDATED_PRECIO_BASE);
        assertThat(testFactura.getIgv()).isEqualTo(UPDATED_IGV);
        assertThat(testFactura.getPrecioTotal()).isEqualTo(UPDATED_PRECIO_TOTAL);
        assertThat(testFactura.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void deleteFactura() throws Exception {
        // Initialize the database
        facturaService.save(factura);

        int databaseSizeBeforeDelete = facturaRepository.findAll().size();

        // Get the factura
        restFacturaMockMvc.perform(delete("/api/facturas/{id}", factura.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Factura> facturas = facturaRepository.findAll();
        assertThat(facturas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
