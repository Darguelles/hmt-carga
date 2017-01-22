package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.Cotizacion;
import com.hmt.carga.domain.Cliente;
import com.hmt.carga.domain.Servicio;
import com.hmt.carga.domain.TipoUnidad;
import com.hmt.carga.repository.CotizacionRepository;
import com.hmt.carga.service.CotizacionService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CotizacionResource REST controller.
 *
 * @see CotizacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class CotizacionResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ORIGEN = "AAAAA";
    private static final String UPDATED_ORIGEN = "BBBBB";

    private static final String DEFAULT_DESTINO = "AAAAA";
    private static final String UPDATED_DESTINO = "BBBBB";

    private static final String DEFAULT_MERCADERIA = "AAAAA";
    private static final String UPDATED_MERCADERIA = "BBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    private static final Integer DEFAULT_MONEDA = 1;
    private static final Integer UPDATED_MONEDA = 2;

    private static final String DEFAULT_ESTADO = "AAAAA";
    private static final String UPDATED_ESTADO = "BBBBB";

    @Inject
    private CotizacionRepository cotizacionRepository;

    @Inject
    private CotizacionService cotizacionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCotizacionMockMvc;

    private Cotizacion cotizacion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CotizacionResource cotizacionResource = new CotizacionResource();
        ReflectionTestUtils.setField(cotizacionResource, "cotizacionService", cotizacionService);
        this.restCotizacionMockMvc = MockMvcBuilders.standaloneSetup(cotizacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cotizacion createEntity(EntityManager em) {
        Cotizacion cotizacion = new Cotizacion()
                .fecha(DEFAULT_FECHA)
                .origen(DEFAULT_ORIGEN)
                .destino(DEFAULT_DESTINO)
                .mercaderia(DEFAULT_MERCADERIA)
                .precio(DEFAULT_PRECIO)
                .moneda(DEFAULT_MONEDA)
                .estado(DEFAULT_ESTADO);
        // Add required entity
        Cliente cliente = ClienteResourceIntTest.createEntity(em);
        em.persist(cliente);
        em.flush();
        cotizacion.setCliente(cliente);
        // Add required entity
        Servicio servicio = ServicioResourceIntTest.createEntity(em);
        em.persist(servicio);
        em.flush();
        cotizacion.setServicio(servicio);
        // Add required entity
        TipoUnidad tipoUnidad = TipoUnidadResourceIntTest.createEntity(em);
        em.persist(tipoUnidad);
        em.flush();
        cotizacion.setTipoUnidad(tipoUnidad);
        return cotizacion;
    }

    @Before
    public void initTest() {
        cotizacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCotizacion() throws Exception {
        int databaseSizeBeforeCreate = cotizacionRepository.findAll().size();

        // Create the Cotizacion

        restCotizacionMockMvc.perform(post("/api/cotizacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
                .andExpect(status().isCreated());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacions = cotizacionRepository.findAll();
        assertThat(cotizacions).hasSize(databaseSizeBeforeCreate + 1);
        Cotizacion testCotizacion = cotizacions.get(cotizacions.size() - 1);
        assertThat(testCotizacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCotizacion.getOrigen()).isEqualTo(DEFAULT_ORIGEN);
        assertThat(testCotizacion.getDestino()).isEqualTo(DEFAULT_DESTINO);
        assertThat(testCotizacion.getMercaderia()).isEqualTo(DEFAULT_MERCADERIA);
        assertThat(testCotizacion.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testCotizacion.getMoneda()).isEqualTo(DEFAULT_MONEDA);
        assertThat(testCotizacion.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setFecha(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
                .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacions = cotizacionRepository.findAll();
        assertThat(cotizacions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrigenIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setOrigen(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
                .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacions = cotizacionRepository.findAll();
        assertThat(cotizacions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDestinoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setDestino(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
                .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacions = cotizacionRepository.findAll();
        assertThat(cotizacions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMercaderiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setMercaderia(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
                .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacions = cotizacionRepository.findAll();
        assertThat(cotizacions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setPrecio(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
                .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacions = cotizacionRepository.findAll();
        assertThat(cotizacions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonedaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setMoneda(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
                .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacions = cotizacionRepository.findAll();
        assertThat(cotizacions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cotizacionRepository.findAll().size();
        // set the field null
        cotizacion.setEstado(null);

        // Create the Cotizacion, which fails.

        restCotizacionMockMvc.perform(post("/api/cotizacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cotizacion)))
                .andExpect(status().isBadRequest());

        List<Cotizacion> cotizacions = cotizacionRepository.findAll();
        assertThat(cotizacions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCotizacions() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        // Get all the cotizacions
        restCotizacionMockMvc.perform(get("/api/cotizacions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cotizacion.getId().intValue())))
                .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
                .andExpect(jsonPath("$.[*].origen").value(hasItem(DEFAULT_ORIGEN.toString())))
                .andExpect(jsonPath("$.[*].destino").value(hasItem(DEFAULT_DESTINO.toString())))
                .andExpect(jsonPath("$.[*].mercaderia").value(hasItem(DEFAULT_MERCADERIA.toString())))
                .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
                .andExpect(jsonPath("$.[*].moneda").value(hasItem(DEFAULT_MONEDA)))
                .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void getCotizacion() throws Exception {
        // Initialize the database
        cotizacionRepository.saveAndFlush(cotizacion);

        // Get the cotizacion
        restCotizacionMockMvc.perform(get("/api/cotizacions/{id}", cotizacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cotizacion.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.origen").value(DEFAULT_ORIGEN.toString()))
            .andExpect(jsonPath("$.destino").value(DEFAULT_DESTINO.toString()))
            .andExpect(jsonPath("$.mercaderia").value(DEFAULT_MERCADERIA.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.moneda").value(DEFAULT_MONEDA))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCotizacion() throws Exception {
        // Get the cotizacion
        restCotizacionMockMvc.perform(get("/api/cotizacions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCotizacion() throws Exception {
        // Initialize the database
        cotizacionService.save(cotizacion);

        int databaseSizeBeforeUpdate = cotizacionRepository.findAll().size();

        // Update the cotizacion
        Cotizacion updatedCotizacion = cotizacionRepository.findOne(cotizacion.getId());
        updatedCotizacion
                .fecha(UPDATED_FECHA)
                .origen(UPDATED_ORIGEN)
                .destino(UPDATED_DESTINO)
                .mercaderia(UPDATED_MERCADERIA)
                .precio(UPDATED_PRECIO)
                .moneda(UPDATED_MONEDA)
                .estado(UPDATED_ESTADO);

        restCotizacionMockMvc.perform(put("/api/cotizacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCotizacion)))
                .andExpect(status().isOk());

        // Validate the Cotizacion in the database
        List<Cotizacion> cotizacions = cotizacionRepository.findAll();
        assertThat(cotizacions).hasSize(databaseSizeBeforeUpdate);
        Cotizacion testCotizacion = cotizacions.get(cotizacions.size() - 1);
        assertThat(testCotizacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCotizacion.getOrigen()).isEqualTo(UPDATED_ORIGEN);
        assertThat(testCotizacion.getDestino()).isEqualTo(UPDATED_DESTINO);
        assertThat(testCotizacion.getMercaderia()).isEqualTo(UPDATED_MERCADERIA);
        assertThat(testCotizacion.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testCotizacion.getMoneda()).isEqualTo(UPDATED_MONEDA);
        assertThat(testCotizacion.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void deleteCotizacion() throws Exception {
        // Initialize the database
        cotizacionService.save(cotizacion);

        int databaseSizeBeforeDelete = cotizacionRepository.findAll().size();

        // Get the cotizacion
        restCotizacionMockMvc.perform(delete("/api/cotizacions/{id}", cotizacion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cotizacion> cotizacions = cotizacionRepository.findAll();
        assertThat(cotizacions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
