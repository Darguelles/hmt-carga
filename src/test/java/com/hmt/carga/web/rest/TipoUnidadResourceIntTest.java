package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.TipoUnidad;
import com.hmt.carga.repository.TipoUnidadRepository;
import com.hmt.carga.service.TipoUnidadService;

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
 * Test class for the TipoUnidadResource REST controller.
 *
 * @see TipoUnidadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class TipoUnidadResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAA";
    private static final String UPDATED_NOMBRE = "BBBBB";

    private static final String DEFAULT_CONFIGURACION = "AAAAA";
    private static final String UPDATED_CONFIGURACION = "BBBBB";

    @Inject
    private TipoUnidadRepository tipoUnidadRepository;

    @Inject
    private TipoUnidadService tipoUnidadService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTipoUnidadMockMvc;

    private TipoUnidad tipoUnidad;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoUnidadResource tipoUnidadResource = new TipoUnidadResource();
        ReflectionTestUtils.setField(tipoUnidadResource, "tipoUnidadService", tipoUnidadService);
        this.restTipoUnidadMockMvc = MockMvcBuilders.standaloneSetup(tipoUnidadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoUnidad createEntity(EntityManager em) {
        TipoUnidad tipoUnidad = new TipoUnidad()
                .nombre(DEFAULT_NOMBRE)
                .configuracion(DEFAULT_CONFIGURACION);
        return tipoUnidad;
    }

    @Before
    public void initTest() {
        tipoUnidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoUnidad() throws Exception {
        int databaseSizeBeforeCreate = tipoUnidadRepository.findAll().size();

        // Create the TipoUnidad

        restTipoUnidadMockMvc.perform(post("/api/tipo-unidads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipoUnidad)))
                .andExpect(status().isCreated());

        // Validate the TipoUnidad in the database
        List<TipoUnidad> tipoUnidads = tipoUnidadRepository.findAll();
        assertThat(tipoUnidads).hasSize(databaseSizeBeforeCreate + 1);
        TipoUnidad testTipoUnidad = tipoUnidads.get(tipoUnidads.size() - 1);
        assertThat(testTipoUnidad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoUnidad.getConfiguracion()).isEqualTo(DEFAULT_CONFIGURACION);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoUnidadRepository.findAll().size();
        // set the field null
        tipoUnidad.setNombre(null);

        // Create the TipoUnidad, which fails.

        restTipoUnidadMockMvc.perform(post("/api/tipo-unidads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipoUnidad)))
                .andExpect(status().isBadRequest());

        List<TipoUnidad> tipoUnidads = tipoUnidadRepository.findAll();
        assertThat(tipoUnidads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConfiguracionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoUnidadRepository.findAll().size();
        // set the field null
        tipoUnidad.setConfiguracion(null);

        // Create the TipoUnidad, which fails.

        restTipoUnidadMockMvc.perform(post("/api/tipo-unidads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipoUnidad)))
                .andExpect(status().isBadRequest());

        List<TipoUnidad> tipoUnidads = tipoUnidadRepository.findAll();
        assertThat(tipoUnidads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoUnidads() throws Exception {
        // Initialize the database
        tipoUnidadRepository.saveAndFlush(tipoUnidad);

        // Get all the tipoUnidads
        restTipoUnidadMockMvc.perform(get("/api/tipo-unidads?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tipoUnidad.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].configuracion").value(hasItem(DEFAULT_CONFIGURACION.toString())));
    }

    @Test
    @Transactional
    public void getTipoUnidad() throws Exception {
        // Initialize the database
        tipoUnidadRepository.saveAndFlush(tipoUnidad);

        // Get the tipoUnidad
        restTipoUnidadMockMvc.perform(get("/api/tipo-unidads/{id}", tipoUnidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoUnidad.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.configuracion").value(DEFAULT_CONFIGURACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoUnidad() throws Exception {
        // Get the tipoUnidad
        restTipoUnidadMockMvc.perform(get("/api/tipo-unidads/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoUnidad() throws Exception {
        // Initialize the database
        tipoUnidadService.save(tipoUnidad);

        int databaseSizeBeforeUpdate = tipoUnidadRepository.findAll().size();

        // Update the tipoUnidad
        TipoUnidad updatedTipoUnidad = tipoUnidadRepository.findOne(tipoUnidad.getId());
        updatedTipoUnidad
                .nombre(UPDATED_NOMBRE)
                .configuracion(UPDATED_CONFIGURACION);

        restTipoUnidadMockMvc.perform(put("/api/tipo-unidads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTipoUnidad)))
                .andExpect(status().isOk());

        // Validate the TipoUnidad in the database
        List<TipoUnidad> tipoUnidads = tipoUnidadRepository.findAll();
        assertThat(tipoUnidads).hasSize(databaseSizeBeforeUpdate);
        TipoUnidad testTipoUnidad = tipoUnidads.get(tipoUnidads.size() - 1);
        assertThat(testTipoUnidad.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoUnidad.getConfiguracion()).isEqualTo(UPDATED_CONFIGURACION);
    }

    @Test
    @Transactional
    public void deleteTipoUnidad() throws Exception {
        // Initialize the database
        tipoUnidadService.save(tipoUnidad);

        int databaseSizeBeforeDelete = tipoUnidadRepository.findAll().size();

        // Get the tipoUnidad
        restTipoUnidadMockMvc.perform(delete("/api/tipo-unidads/{id}", tipoUnidad.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoUnidad> tipoUnidads = tipoUnidadRepository.findAll();
        assertThat(tipoUnidads).hasSize(databaseSizeBeforeDelete - 1);
    }
}
