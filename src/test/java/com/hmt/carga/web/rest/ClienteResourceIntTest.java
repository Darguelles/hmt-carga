package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.Cliente;
import com.hmt.carga.domain.FormaPago;
import com.hmt.carga.domain.CondicionPago;
import com.hmt.carga.repository.ClienteRepository;
import com.hmt.carga.service.ClienteService;

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

import com.hmt.carga.domain.enumeration.TIPO_DESCUENTO;
/**
 * Test class for the ClienteResource REST controller.
 *
 * @see ClienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class ClienteResourceIntTest {

    private static final Long DEFAULT_RUC = 1L;
    private static final Long UPDATED_RUC = 2L;

    private static final String DEFAULT_NOMBRE = "AAAAA";
    private static final String UPDATED_NOMBRE = "BBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAA";
    private static final String UPDATED_DIRECCION = "BBBBB";

    private static final Integer DEFAULT_TELEFONO = 1;
    private static final Integer UPDATED_TELEFONO = 2;

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final String DEFAULT_CONTACTO = "AAAAA";
    private static final String UPDATED_CONTACTO = "BBBBB";

    private static final Double DEFAULT_DESCUENTO = 1D;
    private static final Double UPDATED_DESCUENTO = 2D;

    private static final TIPO_DESCUENTO DEFAULT_TIPO_DESCUENTO = TIPO_DESCUENTO.porcentaje;
    private static final TIPO_DESCUENTO UPDATED_TIPO_DESCUENTO = TIPO_DESCUENTO.monto;

    @Inject
    private ClienteRepository clienteRepository;

    @Inject
    private ClienteService clienteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClienteResource clienteResource = new ClienteResource();
        ReflectionTestUtils.setField(clienteResource, "clienteService", clienteService);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
                .ruc(DEFAULT_RUC)
                .nombre(DEFAULT_NOMBRE)
                .direccion(DEFAULT_DIRECCION)
                .telefono(DEFAULT_TELEFONO)
                .email(DEFAULT_EMAIL)
                .contacto(DEFAULT_CONTACTO)
                .descuento(DEFAULT_DESCUENTO)
                .tipoDescuento(DEFAULT_TIPO_DESCUENTO);
        // Add required entity
        FormaPago formaPago = FormaPagoResourceIntTest.createEntity(em);
        em.persist(formaPago);
        em.flush();
        cliente.setFormaPago(formaPago);
        // Add required entity
        CondicionPago condicionPago = CondicionPagoResourceIntTest.createEntity(em);
        em.persist(condicionPago);
        em.flush();
        cliente.setCondicionPago(condicionPago);
        return cliente;
    }

    @Before
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente

        restClienteMockMvc.perform(post("/api/clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cliente)))
                .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clientes = clienteRepository.findAll();
        assertThat(clientes).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clientes.get(clientes.size() - 1);
        assertThat(testCliente.getRuc()).isEqualTo(DEFAULT_RUC);
        assertThat(testCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCliente.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testCliente.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testCliente.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCliente.getContacto()).isEqualTo(DEFAULT_CONTACTO);
        assertThat(testCliente.getDescuento()).isEqualTo(DEFAULT_DESCUENTO);
        assertThat(testCliente.getTipoDescuento()).isEqualTo(DEFAULT_TIPO_DESCUENTO);
    }

    @Test
    @Transactional
    public void checkRucIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setRuc(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cliente)))
                .andExpect(status().isBadRequest());

        List<Cliente> clientes = clienteRepository.findAll();
        assertThat(clientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNombre(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cliente)))
                .andExpect(status().isBadRequest());

        List<Cliente> clientes = clienteRepository.findAll();
        assertThat(clientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setDireccion(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cliente)))
                .andExpect(status().isBadRequest());

        List<Cliente> clientes = clienteRepository.findAll();
        assertThat(clientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setTelefono(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cliente)))
                .andExpect(status().isBadRequest());

        List<Cliente> clientes = clienteRepository.findAll();
        assertThat(clientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setEmail(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cliente)))
                .andExpect(status().isBadRequest());

        List<Cliente> clientes = clienteRepository.findAll();
        assertThat(clientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setContacto(null);

        // Create the Cliente, which fails.

        restClienteMockMvc.perform(post("/api/clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cliente)))
                .andExpect(status().isBadRequest());

        List<Cliente> clientes = clienteRepository.findAll();
        assertThat(clientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clientes
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
                .andExpect(jsonPath("$.[*].ruc").value(hasItem(DEFAULT_RUC.intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
                .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].contacto").value(hasItem(DEFAULT_CONTACTO.toString())))
                .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO.doubleValue())))
                .andExpect(jsonPath("$.[*].tipoDescuento").value(hasItem(DEFAULT_TIPO_DESCUENTO.toString())));
    }

    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.ruc").value(DEFAULT_RUC.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.contacto").value(DEFAULT_CONTACTO.toString()))
            .andExpect(jsonPath("$.descuento").value(DEFAULT_DESCUENTO.doubleValue()))
            .andExpect(jsonPath("$.tipoDescuento").value(DEFAULT_TIPO_DESCUENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteService.save(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findOne(cliente.getId());
        updatedCliente
                .ruc(UPDATED_RUC)
                .nombre(UPDATED_NOMBRE)
                .direccion(UPDATED_DIRECCION)
                .telefono(UPDATED_TELEFONO)
                .email(UPDATED_EMAIL)
                .contacto(UPDATED_CONTACTO)
                .descuento(UPDATED_DESCUENTO)
                .tipoDescuento(UPDATED_TIPO_DESCUENTO);

        restClienteMockMvc.perform(put("/api/clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCliente)))
                .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clientes = clienteRepository.findAll();
        assertThat(clientes).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clientes.get(clientes.size() - 1);
        assertThat(testCliente.getRuc()).isEqualTo(UPDATED_RUC);
        assertThat(testCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCliente.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testCliente.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testCliente.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCliente.getContacto()).isEqualTo(UPDATED_CONTACTO);
        assertThat(testCliente.getDescuento()).isEqualTo(UPDATED_DESCUENTO);
        assertThat(testCliente.getTipoDescuento()).isEqualTo(UPDATED_TIPO_DESCUENTO);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteService.save(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Get the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cliente> clientes = clienteRepository.findAll();
        assertThat(clientes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
