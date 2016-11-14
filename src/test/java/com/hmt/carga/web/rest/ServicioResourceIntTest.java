package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.Servicio;
import com.hmt.carga.repository.ServicioRepository;
import com.hmt.carga.service.ServicioService;

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
 * Test class for the ServicioResource REST controller.
 *
 * @see ServicioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class ServicioResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAA";
    private static final String UPDATED_NOMBRE = "BBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBB";

    @Inject
    private ServicioRepository servicioRepository;

    @Inject
    private ServicioService servicioService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restServicioMockMvc;

    private Servicio servicio;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServicioResource servicioResource = new ServicioResource();
        ReflectionTestUtils.setField(servicioResource, "servicioService", servicioService);
        this.restServicioMockMvc = MockMvcBuilders.standaloneSetup(servicioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicio createEntity(EntityManager em) {
        Servicio servicio = new Servicio()
                .nombre(DEFAULT_NOMBRE)
                .descripcion(DEFAULT_DESCRIPCION);
        return servicio;
    }

    @Before
    public void initTest() {
        servicio = createEntity(em);
    }

    @Test
    @Transactional
    public void createServicio() throws Exception {
        int databaseSizeBeforeCreate = servicioRepository.findAll().size();

        // Create the Servicio

        restServicioMockMvc.perform(post("/api/servicios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(servicio)))
                .andExpect(status().isCreated());

        // Validate the Servicio in the database
        List<Servicio> servicios = servicioRepository.findAll();
        assertThat(servicios).hasSize(databaseSizeBeforeCreate + 1);
        Servicio testServicio = servicios.get(servicios.size() - 1);
        assertThat(testServicio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testServicio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicioRepository.findAll().size();
        // set the field null
        servicio.setNombre(null);

        // Create the Servicio, which fails.

        restServicioMockMvc.perform(post("/api/servicios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(servicio)))
                .andExpect(status().isBadRequest());

        List<Servicio> servicios = servicioRepository.findAll();
        assertThat(servicios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicioRepository.findAll().size();
        // set the field null
        servicio.setDescripcion(null);

        // Create the Servicio, which fails.

        restServicioMockMvc.perform(post("/api/servicios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(servicio)))
                .andExpect(status().isBadRequest());

        List<Servicio> servicios = servicioRepository.findAll();
        assertThat(servicios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServicios() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        // Get all the servicios
        restServicioMockMvc.perform(get("/api/servicios?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(servicio.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getServicio() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        // Get the servicio
        restServicioMockMvc.perform(get("/api/servicios/{id}", servicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(servicio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServicio() throws Exception {
        // Get the servicio
        restServicioMockMvc.perform(get("/api/servicios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServicio() throws Exception {
        // Initialize the database
        servicioService.save(servicio);

        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();

        // Update the servicio
        Servicio updatedServicio = servicioRepository.findOne(servicio.getId());
        updatedServicio
                .nombre(UPDATED_NOMBRE)
                .descripcion(UPDATED_DESCRIPCION);

        restServicioMockMvc.perform(put("/api/servicios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedServicio)))
                .andExpect(status().isOk());

        // Validate the Servicio in the database
        List<Servicio> servicios = servicioRepository.findAll();
        assertThat(servicios).hasSize(databaseSizeBeforeUpdate);
        Servicio testServicio = servicios.get(servicios.size() - 1);
        assertThat(testServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testServicio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void deleteServicio() throws Exception {
        // Initialize the database
        servicioService.save(servicio);

        int databaseSizeBeforeDelete = servicioRepository.findAll().size();

        // Get the servicio
        restServicioMockMvc.perform(delete("/api/servicios/{id}", servicio.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Servicio> servicios = servicioRepository.findAll();
        assertThat(servicios).hasSize(databaseSizeBeforeDelete - 1);
    }
}
