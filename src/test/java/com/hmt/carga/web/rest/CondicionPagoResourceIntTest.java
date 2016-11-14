package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.CondicionPago;
import com.hmt.carga.repository.CondicionPagoRepository;
import com.hmt.carga.service.CondicionPagoService;

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
 * Test class for the CondicionPagoResource REST controller.
 *
 * @see CondicionPagoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class CondicionPagoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAA";
    private static final String UPDATED_NOMBRE = "BBBBB";

    @Inject
    private CondicionPagoRepository condicionPagoRepository;

    @Inject
    private CondicionPagoService condicionPagoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCondicionPagoMockMvc;

    private CondicionPago condicionPago;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CondicionPagoResource condicionPagoResource = new CondicionPagoResource();
        ReflectionTestUtils.setField(condicionPagoResource, "condicionPagoService", condicionPagoService);
        this.restCondicionPagoMockMvc = MockMvcBuilders.standaloneSetup(condicionPagoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CondicionPago createEntity(EntityManager em) {
        CondicionPago condicionPago = new CondicionPago()
                .nombre(DEFAULT_NOMBRE);
        return condicionPago;
    }

    @Before
    public void initTest() {
        condicionPago = createEntity(em);
    }

    @Test
    @Transactional
    public void createCondicionPago() throws Exception {
        int databaseSizeBeforeCreate = condicionPagoRepository.findAll().size();

        // Create the CondicionPago

        restCondicionPagoMockMvc.perform(post("/api/condicion-pagos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(condicionPago)))
                .andExpect(status().isCreated());

        // Validate the CondicionPago in the database
        List<CondicionPago> condicionPagos = condicionPagoRepository.findAll();
        assertThat(condicionPagos).hasSize(databaseSizeBeforeCreate + 1);
        CondicionPago testCondicionPago = condicionPagos.get(condicionPagos.size() - 1);
        assertThat(testCondicionPago.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = condicionPagoRepository.findAll().size();
        // set the field null
        condicionPago.setNombre(null);

        // Create the CondicionPago, which fails.

        restCondicionPagoMockMvc.perform(post("/api/condicion-pagos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(condicionPago)))
                .andExpect(status().isBadRequest());

        List<CondicionPago> condicionPagos = condicionPagoRepository.findAll();
        assertThat(condicionPagos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCondicionPagos() throws Exception {
        // Initialize the database
        condicionPagoRepository.saveAndFlush(condicionPago);

        // Get all the condicionPagos
        restCondicionPagoMockMvc.perform(get("/api/condicion-pagos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(condicionPago.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getCondicionPago() throws Exception {
        // Initialize the database
        condicionPagoRepository.saveAndFlush(condicionPago);

        // Get the condicionPago
        restCondicionPagoMockMvc.perform(get("/api/condicion-pagos/{id}", condicionPago.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(condicionPago.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCondicionPago() throws Exception {
        // Get the condicionPago
        restCondicionPagoMockMvc.perform(get("/api/condicion-pagos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCondicionPago() throws Exception {
        // Initialize the database
        condicionPagoService.save(condicionPago);

        int databaseSizeBeforeUpdate = condicionPagoRepository.findAll().size();

        // Update the condicionPago
        CondicionPago updatedCondicionPago = condicionPagoRepository.findOne(condicionPago.getId());
        updatedCondicionPago
                .nombre(UPDATED_NOMBRE);

        restCondicionPagoMockMvc.perform(put("/api/condicion-pagos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCondicionPago)))
                .andExpect(status().isOk());

        // Validate the CondicionPago in the database
        List<CondicionPago> condicionPagos = condicionPagoRepository.findAll();
        assertThat(condicionPagos).hasSize(databaseSizeBeforeUpdate);
        CondicionPago testCondicionPago = condicionPagos.get(condicionPagos.size() - 1);
        assertThat(testCondicionPago.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteCondicionPago() throws Exception {
        // Initialize the database
        condicionPagoService.save(condicionPago);

        int databaseSizeBeforeDelete = condicionPagoRepository.findAll().size();

        // Get the condicionPago
        restCondicionPagoMockMvc.perform(delete("/api/condicion-pagos/{id}", condicionPago.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CondicionPago> condicionPagos = condicionPagoRepository.findAll();
        assertThat(condicionPagos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
