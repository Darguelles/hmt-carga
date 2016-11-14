package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.FormaPago;
import com.hmt.carga.repository.FormaPagoRepository;
import com.hmt.carga.service.FormaPagoService;

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
 * Test class for the FormaPagoResource REST controller.
 *
 * @see FormaPagoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class FormaPagoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAA";
    private static final String UPDATED_NOMBRE = "BBBBB";

    @Inject
    private FormaPagoRepository formaPagoRepository;

    @Inject
    private FormaPagoService formaPagoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFormaPagoMockMvc;

    private FormaPago formaPago;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FormaPagoResource formaPagoResource = new FormaPagoResource();
        ReflectionTestUtils.setField(formaPagoResource, "formaPagoService", formaPagoService);
        this.restFormaPagoMockMvc = MockMvcBuilders.standaloneSetup(formaPagoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaPago createEntity(EntityManager em) {
        FormaPago formaPago = new FormaPago()
                .nombre(DEFAULT_NOMBRE);
        return formaPago;
    }

    @Before
    public void initTest() {
        formaPago = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormaPago() throws Exception {
        int databaseSizeBeforeCreate = formaPagoRepository.findAll().size();

        // Create the FormaPago

        restFormaPagoMockMvc.perform(post("/api/forma-pagos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formaPago)))
                .andExpect(status().isCreated());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagos = formaPagoRepository.findAll();
        assertThat(formaPagos).hasSize(databaseSizeBeforeCreate + 1);
        FormaPago testFormaPago = formaPagos.get(formaPagos.size() - 1);
        assertThat(testFormaPago.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = formaPagoRepository.findAll().size();
        // set the field null
        formaPago.setNombre(null);

        // Create the FormaPago, which fails.

        restFormaPagoMockMvc.perform(post("/api/forma-pagos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(formaPago)))
                .andExpect(status().isBadRequest());

        List<FormaPago> formaPagos = formaPagoRepository.findAll();
        assertThat(formaPagos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormaPagos() throws Exception {
        // Initialize the database
        formaPagoRepository.saveAndFlush(formaPago);

        // Get all the formaPagos
        restFormaPagoMockMvc.perform(get("/api/forma-pagos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(formaPago.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getFormaPago() throws Exception {
        // Initialize the database
        formaPagoRepository.saveAndFlush(formaPago);

        // Get the formaPago
        restFormaPagoMockMvc.perform(get("/api/forma-pagos/{id}", formaPago.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formaPago.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormaPago() throws Exception {
        // Get the formaPago
        restFormaPagoMockMvc.perform(get("/api/forma-pagos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormaPago() throws Exception {
        // Initialize the database
        formaPagoService.save(formaPago);

        int databaseSizeBeforeUpdate = formaPagoRepository.findAll().size();

        // Update the formaPago
        FormaPago updatedFormaPago = formaPagoRepository.findOne(formaPago.getId());
        updatedFormaPago
                .nombre(UPDATED_NOMBRE);

        restFormaPagoMockMvc.perform(put("/api/forma-pagos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFormaPago)))
                .andExpect(status().isOk());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagos = formaPagoRepository.findAll();
        assertThat(formaPagos).hasSize(databaseSizeBeforeUpdate);
        FormaPago testFormaPago = formaPagos.get(formaPagos.size() - 1);
        assertThat(testFormaPago.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteFormaPago() throws Exception {
        // Initialize the database
        formaPagoService.save(formaPago);

        int databaseSizeBeforeDelete = formaPagoRepository.findAll().size();

        // Get the formaPago
        restFormaPagoMockMvc.perform(delete("/api/forma-pagos/{id}", formaPago.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FormaPago> formaPagos = formaPagoRepository.findAll();
        assertThat(formaPagos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
