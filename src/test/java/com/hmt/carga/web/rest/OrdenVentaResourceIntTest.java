package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.OrdenVenta;
import com.hmt.carga.domain.Cotizacion;
import com.hmt.carga.repository.OrdenVentaRepository;
import com.hmt.carga.service.OrdenVentaService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrdenVentaResource REST controller.
 *
 * @see OrdenVentaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class OrdenVentaResourceIntTest {

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_FECHA_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_FECHA);

    private static final String DEFAULT_EMAIL_DESTINO = "AAAAA";
    private static final String UPDATED_EMAIL_DESTINO = "BBBBB";

    @Inject
    private OrdenVentaRepository ordenVentaRepository;

    @Inject
    private OrdenVentaService ordenVentaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOrdenVentaMockMvc;

    private OrdenVenta ordenVenta;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrdenVentaResource ordenVentaResource = new OrdenVentaResource();
        ReflectionTestUtils.setField(ordenVentaResource, "ordenVentaService", ordenVentaService);
        this.restOrdenVentaMockMvc = MockMvcBuilders.standaloneSetup(ordenVentaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdenVenta createEntity(EntityManager em) {
        OrdenVenta ordenVenta = new OrdenVenta()
                .fecha(DEFAULT_FECHA)
                .emailDestino(DEFAULT_EMAIL_DESTINO);
        // Add required entity
        Cotizacion cotizacion = CotizacionResourceIntTest.createEntity(em);
        em.persist(cotizacion);
        em.flush();
        ordenVenta.setCotizacion(cotizacion);
        return ordenVenta;
    }

    @Before
    public void initTest() {
        ordenVenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdenVenta() throws Exception {
        int databaseSizeBeforeCreate = ordenVentaRepository.findAll().size();

        // Create the OrdenVenta

        restOrdenVentaMockMvc.perform(post("/api/orden-ventas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ordenVenta)))
                .andExpect(status().isCreated());

        // Validate the OrdenVenta in the database
        List<OrdenVenta> ordenVentas = ordenVentaRepository.findAll();
        assertThat(ordenVentas).hasSize(databaseSizeBeforeCreate + 1);
        OrdenVenta testOrdenVenta = ordenVentas.get(ordenVentas.size() - 1);
        assertThat(testOrdenVenta.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testOrdenVenta.getEmailDestino()).isEqualTo(DEFAULT_EMAIL_DESTINO);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordenVentaRepository.findAll().size();
        // set the field null
        ordenVenta.setFecha(null);

        // Create the OrdenVenta, which fails.

        restOrdenVentaMockMvc.perform(post("/api/orden-ventas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ordenVenta)))
                .andExpect(status().isBadRequest());

        List<OrdenVenta> ordenVentas = ordenVentaRepository.findAll();
        assertThat(ordenVentas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrdenVentas() throws Exception {
        // Initialize the database
        ordenVentaRepository.saveAndFlush(ordenVenta);

        // Get all the ordenVentas
        restOrdenVentaMockMvc.perform(get("/api/orden-ventas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ordenVenta.getId().intValue())))
                .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA_STR)))
                .andExpect(jsonPath("$.[*].emailDestino").value(hasItem(DEFAULT_EMAIL_DESTINO.toString())));
    }

    @Test
    @Transactional
    public void getOrdenVenta() throws Exception {
        // Initialize the database
        ordenVentaRepository.saveAndFlush(ordenVenta);

        // Get the ordenVenta
        restOrdenVentaMockMvc.perform(get("/api/orden-ventas/{id}", ordenVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordenVenta.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA_STR))
            .andExpect(jsonPath("$.emailDestino").value(DEFAULT_EMAIL_DESTINO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdenVenta() throws Exception {
        // Get the ordenVenta
        restOrdenVentaMockMvc.perform(get("/api/orden-ventas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdenVenta() throws Exception {
        // Initialize the database
        ordenVentaService.save(ordenVenta);

        int databaseSizeBeforeUpdate = ordenVentaRepository.findAll().size();

        // Update the ordenVenta
        OrdenVenta updatedOrdenVenta = ordenVentaRepository.findOne(ordenVenta.getId());
        updatedOrdenVenta
                .fecha(UPDATED_FECHA)
                .emailDestino(UPDATED_EMAIL_DESTINO);

        restOrdenVentaMockMvc.perform(put("/api/orden-ventas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedOrdenVenta)))
                .andExpect(status().isOk());

        // Validate the OrdenVenta in the database
        List<OrdenVenta> ordenVentas = ordenVentaRepository.findAll();
        assertThat(ordenVentas).hasSize(databaseSizeBeforeUpdate);
        OrdenVenta testOrdenVenta = ordenVentas.get(ordenVentas.size() - 1);
        assertThat(testOrdenVenta.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testOrdenVenta.getEmailDestino()).isEqualTo(UPDATED_EMAIL_DESTINO);
    }

    @Test
    @Transactional
    public void deleteOrdenVenta() throws Exception {
        // Initialize the database
        ordenVentaService.save(ordenVenta);

        int databaseSizeBeforeDelete = ordenVentaRepository.findAll().size();

        // Get the ordenVenta
        restOrdenVentaMockMvc.perform(delete("/api/orden-ventas/{id}", ordenVenta.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrdenVenta> ordenVentas = ordenVentaRepository.findAll();
        assertThat(ordenVentas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
