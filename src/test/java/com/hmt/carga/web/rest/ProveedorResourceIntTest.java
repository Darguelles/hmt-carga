package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.Proveedor;
import com.hmt.carga.repository.ProveedorRepository;
import com.hmt.carga.service.ProveedorService;

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
 * Test class for the ProveedorResource REST controller.
 *
 * @see ProveedorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class ProveedorResourceIntTest {

    private static final Long DEFAULT_RUC = 1L;
    private static final Long UPDATED_RUC = 2L;

    private static final String DEFAULT_NOMBRE = "AAAAA";
    private static final String UPDATED_NOMBRE = "BBBBB";

    private static final Integer DEFAULT_TELEFONO = 1;
    private static final Integer UPDATED_TELEFONO = 2;

    private static final String DEFAULT_DIRECCION = "AAAAA";
    private static final String UPDATED_DIRECCION = "BBBBB";

    private static final String DEFAULT_CONTACTO = "AAAAA";
    private static final String UPDATED_CONTACTO = "BBBBB";

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final String DEFAULT_BANCO = "AAAAA";
    private static final String UPDATED_BANCO = "BBBBB";

    private static final String DEFAULT_NUMERO_CUENTA = "AAAAA";
    private static final String UPDATED_NUMERO_CUENTA = "BBBBB";

    private static final String DEFAULT_NUMERO_CUENTA_INTERBANCARIO = "AAAAA";
    private static final String UPDATED_NUMERO_CUENTA_INTERBANCARIO = "BBBBB";

    private static final String DEFAULT_NUMERO_CUENTA_DETRACCION = "AAAAA";
    private static final String UPDATED_NUMERO_CUENTA_DETRACCION = "BBBBB";

    private static final String DEFAULT_MATPEL = "AAAAA";
    private static final String UPDATED_MATPEL = "BBBBB";

    @Inject
    private ProveedorRepository proveedorRepository;

    @Inject
    private ProveedorService proveedorService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProveedorMockMvc;

    private Proveedor proveedor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProveedorResource proveedorResource = new ProveedorResource();
        ReflectionTestUtils.setField(proveedorResource, "proveedorService", proveedorService);
        this.restProveedorMockMvc = MockMvcBuilders.standaloneSetup(proveedorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proveedor createEntity(EntityManager em) {
        Proveedor proveedor = new Proveedor()
                .ruc(DEFAULT_RUC)
                .nombre(DEFAULT_NOMBRE)
                .telefono(DEFAULT_TELEFONO)
                .direccion(DEFAULT_DIRECCION)
                .contacto(DEFAULT_CONTACTO)
                .email(DEFAULT_EMAIL)
                .banco(DEFAULT_BANCO)
                .numeroCuenta(DEFAULT_NUMERO_CUENTA)
                .numeroCuentaInterbancario(DEFAULT_NUMERO_CUENTA_INTERBANCARIO)
                .numeroCuentaDetraccion(DEFAULT_NUMERO_CUENTA_DETRACCION)
                .matpel(DEFAULT_MATPEL);
        return proveedor;
    }

    @Before
    public void initTest() {
        proveedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createProveedor() throws Exception {
        int databaseSizeBeforeCreate = proveedorRepository.findAll().size();

        // Create the Proveedor

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isCreated());

        // Validate the Proveedor in the database
        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeCreate + 1);
        Proveedor testProveedor = proveedors.get(proveedors.size() - 1);
        assertThat(testProveedor.getRuc()).isEqualTo(DEFAULT_RUC);
        assertThat(testProveedor.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProveedor.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testProveedor.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testProveedor.getContacto()).isEqualTo(DEFAULT_CONTACTO);
        assertThat(testProveedor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProveedor.getBanco()).isEqualTo(DEFAULT_BANCO);
        assertThat(testProveedor.getNumeroCuenta()).isEqualTo(DEFAULT_NUMERO_CUENTA);
        assertThat(testProveedor.getNumeroCuentaInterbancario()).isEqualTo(DEFAULT_NUMERO_CUENTA_INTERBANCARIO);
        assertThat(testProveedor.getNumeroCuentaDetraccion()).isEqualTo(DEFAULT_NUMERO_CUENTA_DETRACCION);
        assertThat(testProveedor.getMatpel()).isEqualTo(DEFAULT_MATPEL);
    }

    @Test
    @Transactional
    public void checkRucIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setRuc(null);

        // Create the Proveedor, which fails.

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isBadRequest());

        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setNombre(null);

        // Create the Proveedor, which fails.

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isBadRequest());

        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setTelefono(null);

        // Create the Proveedor, which fails.

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isBadRequest());

        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setDireccion(null);

        // Create the Proveedor, which fails.

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isBadRequest());

        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorRepository.findAll().size();
        // set the field null
        proveedor.setContacto(null);

        // Create the Proveedor, which fails.

        restProveedorMockMvc.perform(post("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proveedor)))
                .andExpect(status().isBadRequest());

        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProveedors() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get all the proveedors
        restProveedorMockMvc.perform(get("/api/proveedors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(proveedor.getId().intValue())))
                .andExpect(jsonPath("$.[*].ruc").value(hasItem(DEFAULT_RUC.intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
                .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
                .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].banco").value(hasItem(DEFAULT_BANCO.toString())))
                .andExpect(jsonPath("$.[*].numeroCuenta").value(hasItem(DEFAULT_NUMERO_CUENTA.toString())))
                .andExpect(jsonPath("$.[*].numeroCuentaInterbancario").value(hasItem(DEFAULT_NUMERO_CUENTA_INTERBANCARIO.toString())))
                .andExpect(jsonPath("$.[*].numeroCuentaDetraccion").value(hasItem(DEFAULT_NUMERO_CUENTA_DETRACCION.toString())))
                .andExpect(jsonPath("$.[*].matpel").value(hasItem(DEFAULT_MATPEL.toString())));
    }

    @Test
    @Transactional
    public void getProveedor() throws Exception {
        // Initialize the database
        proveedorRepository.saveAndFlush(proveedor);

        // Get the proveedor
        restProveedorMockMvc.perform(get("/api/proveedors/{id}", proveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proveedor.getId().intValue()))
            .andExpect(jsonPath("$.ruc").value(DEFAULT_RUC.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.contacto").value(DEFAULT_CONTACTO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.banco").value(DEFAULT_BANCO.toString()))
            .andExpect(jsonPath("$.numeroCuenta").value(DEFAULT_NUMERO_CUENTA.toString()))
            .andExpect(jsonPath("$.numeroCuentaInterbancario").value(DEFAULT_NUMERO_CUENTA_INTERBANCARIO.toString()))
            .andExpect(jsonPath("$.numeroCuentaDetraccion").value(DEFAULT_NUMERO_CUENTA_DETRACCION.toString()))
            .andExpect(jsonPath("$.matpel").value(DEFAULT_MATPEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProveedor() throws Exception {
        // Get the proveedor
        restProveedorMockMvc.perform(get("/api/proveedors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProveedor() throws Exception {
        // Initialize the database
        proveedorService.save(proveedor);

        int databaseSizeBeforeUpdate = proveedorRepository.findAll().size();

        // Update the proveedor
        Proveedor updatedProveedor = proveedorRepository.findOne(proveedor.getId());
        updatedProveedor
                .ruc(UPDATED_RUC)
                .nombre(UPDATED_NOMBRE)
                .telefono(UPDATED_TELEFONO)
                .direccion(UPDATED_DIRECCION)
                .contacto(UPDATED_CONTACTO)
                .email(UPDATED_EMAIL)
                .banco(UPDATED_BANCO)
                .numeroCuenta(UPDATED_NUMERO_CUENTA)
                .numeroCuentaInterbancario(UPDATED_NUMERO_CUENTA_INTERBANCARIO)
                .numeroCuentaDetraccion(UPDATED_NUMERO_CUENTA_DETRACCION)
                .matpel(UPDATED_MATPEL);

        restProveedorMockMvc.perform(put("/api/proveedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProveedor)))
                .andExpect(status().isOk());

        // Validate the Proveedor in the database
        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeUpdate);
        Proveedor testProveedor = proveedors.get(proveedors.size() - 1);
        assertThat(testProveedor.getRuc()).isEqualTo(UPDATED_RUC);
        assertThat(testProveedor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProveedor.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testProveedor.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testProveedor.getContacto()).isEqualTo(UPDATED_CONTACTO);
        assertThat(testProveedor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProveedor.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testProveedor.getNumeroCuenta()).isEqualTo(UPDATED_NUMERO_CUENTA);
        assertThat(testProveedor.getNumeroCuentaInterbancario()).isEqualTo(UPDATED_NUMERO_CUENTA_INTERBANCARIO);
        assertThat(testProveedor.getNumeroCuentaDetraccion()).isEqualTo(UPDATED_NUMERO_CUENTA_DETRACCION);
        assertThat(testProveedor.getMatpel()).isEqualTo(UPDATED_MATPEL);
    }

    @Test
    @Transactional
    public void deleteProveedor() throws Exception {
        // Initialize the database
        proveedorService.save(proveedor);

        int databaseSizeBeforeDelete = proveedorRepository.findAll().size();

        // Get the proveedor
        restProveedorMockMvc.perform(delete("/api/proveedors/{id}", proveedor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Proveedor> proveedors = proveedorRepository.findAll();
        assertThat(proveedors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
