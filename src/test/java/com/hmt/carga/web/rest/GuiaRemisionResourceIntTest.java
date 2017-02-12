package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.GuiaRemision;
import com.hmt.carga.domain.Proveedor;
import com.hmt.carga.domain.Transporte;
import com.hmt.carga.repository.GuiaRemisionRepository;
import com.hmt.carga.service.GuiaRemisionService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GuiaRemisionResource REST controller.
 *
 * @see GuiaRemisionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class GuiaRemisionResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAA";
    private static final String UPDATED_CODIGO = "BBBBB";

    private static final LocalDate DEFAULT_FECHA_EMISION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_EMISION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_TRASLADO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_TRASLADO = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_CANTIDAD = 1D;
    private static final Double UPDATED_CANTIDAD = 2D;

    private static final Double DEFAULT_PESO = 1D;
    private static final Double UPDATED_PESO = 2D;

    private static final String DEFAULT_UNIDAD_MEDIDA = "AAAAA";
    private static final String UPDATED_UNIDAD_MEDIDA = "BBBBB";

    private static final Double DEFAULT_COSTO_MINIMO = 1D;
    private static final Double UPDATED_COSTO_MINIMO = 2D;

    private static final ZonedDateTime DEFAULT_FECHA_INGRESO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_FECHA_INGRESO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_FECHA_INGRESO_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_FECHA_INGRESO);

    private static final ZonedDateTime DEFAULT_FECHA_SALIDA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_FECHA_SALIDA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_FECHA_SALIDA_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_FECHA_SALIDA);

    private static final String DEFAULT_OBSERVACIONES = "AAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBB";

    private static final Boolean DEFAULT_ORIGEN_DATOS = false;
    private static final Boolean UPDATED_ORIGEN_DATOS = true;

    private static final Boolean DEFAULT_FACTURADA = false;
    private static final Boolean UPDATED_FACTURADA = true;

    @Inject
    private GuiaRemisionRepository guiaRemisionRepository;

    @Inject
    private GuiaRemisionService guiaRemisionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGuiaRemisionMockMvc;

    private GuiaRemision guiaRemision;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GuiaRemisionResource guiaRemisionResource = new GuiaRemisionResource();
        ReflectionTestUtils.setField(guiaRemisionResource, "guiaRemisionService", guiaRemisionService);
        this.restGuiaRemisionMockMvc = MockMvcBuilders.standaloneSetup(guiaRemisionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuiaRemision createEntity(EntityManager em) {
        GuiaRemision guiaRemision = new GuiaRemision()
                .codigo(DEFAULT_CODIGO)
                .fechaEmision(DEFAULT_FECHA_EMISION)
                .fechaTraslado(DEFAULT_FECHA_TRASLADO)
                .cantidad(DEFAULT_CANTIDAD)
                .peso(DEFAULT_PESO)
                .unidadMedida(DEFAULT_UNIDAD_MEDIDA)
                .costoMinimo(DEFAULT_COSTO_MINIMO)
                .fechaIngreso(DEFAULT_FECHA_INGRESO)
                .fechaSalida(DEFAULT_FECHA_SALIDA)
                .observaciones(DEFAULT_OBSERVACIONES)
                .descripcion(DEFAULT_DESCRIPCION)
                .origenDatos(DEFAULT_ORIGEN_DATOS)
                .facturada(DEFAULT_FACTURADA);
        // Add required entity
        Proveedor proveedor = ProveedorResourceIntTest.createEntity(em);
        em.persist(proveedor);
        em.flush();
        guiaRemision.setProveedor(proveedor);
        // Add required entity
        Transporte transporte = TransporteResourceIntTest.createEntity(em);
        em.persist(transporte);
        em.flush();
        guiaRemision.setTransporte(transporte);
        return guiaRemision;
    }

    @Before
    public void initTest() {
        guiaRemision = createEntity(em);
    }

    @Test
    @Transactional
    public void createGuiaRemision() throws Exception {
        int databaseSizeBeforeCreate = guiaRemisionRepository.findAll().size();

        // Create the GuiaRemision

        restGuiaRemisionMockMvc.perform(post("/api/guia-remisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(guiaRemision)))
                .andExpect(status().isCreated());

        // Validate the GuiaRemision in the database
        List<GuiaRemision> guiaRemisions = guiaRemisionRepository.findAll();
        assertThat(guiaRemisions).hasSize(databaseSizeBeforeCreate + 1);
        GuiaRemision testGuiaRemision = guiaRemisions.get(guiaRemisions.size() - 1);
        assertThat(testGuiaRemision.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testGuiaRemision.getFechaEmision()).isEqualTo(DEFAULT_FECHA_EMISION);
        assertThat(testGuiaRemision.getFechaTraslado()).isEqualTo(DEFAULT_FECHA_TRASLADO);
        assertThat(testGuiaRemision.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testGuiaRemision.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testGuiaRemision.getUnidadMedida()).isEqualTo(DEFAULT_UNIDAD_MEDIDA);
        assertThat(testGuiaRemision.getCostoMinimo()).isEqualTo(DEFAULT_COSTO_MINIMO);
        assertThat(testGuiaRemision.getFechaIngreso()).isEqualTo(DEFAULT_FECHA_INGRESO);
        assertThat(testGuiaRemision.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
        assertThat(testGuiaRemision.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testGuiaRemision.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testGuiaRemision.isOrigenDatos()).isEqualTo(DEFAULT_ORIGEN_DATOS);
        assertThat(testGuiaRemision.isFacturada()).isEqualTo(DEFAULT_FACTURADA);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = guiaRemisionRepository.findAll().size();
        // set the field null
        guiaRemision.setCodigo(null);

        // Create the GuiaRemision, which fails.

        restGuiaRemisionMockMvc.perform(post("/api/guia-remisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(guiaRemision)))
                .andExpect(status().isBadRequest());

        List<GuiaRemision> guiaRemisions = guiaRemisionRepository.findAll();
        assertThat(guiaRemisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaEmisionIsRequired() throws Exception {
        int databaseSizeBeforeTest = guiaRemisionRepository.findAll().size();
        // set the field null
        guiaRemision.setFechaEmision(null);

        // Create the GuiaRemision, which fails.

        restGuiaRemisionMockMvc.perform(post("/api/guia-remisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(guiaRemision)))
                .andExpect(status().isBadRequest());

        List<GuiaRemision> guiaRemisions = guiaRemisionRepository.findAll();
        assertThat(guiaRemisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIngresoIsRequired() throws Exception {
        int databaseSizeBeforeTest = guiaRemisionRepository.findAll().size();
        // set the field null
        guiaRemision.setFechaIngreso(null);

        // Create the GuiaRemision, which fails.

        restGuiaRemisionMockMvc.perform(post("/api/guia-remisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(guiaRemision)))
                .andExpect(status().isBadRequest());

        List<GuiaRemision> guiaRemisions = guiaRemisionRepository.findAll();
        assertThat(guiaRemisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaSalidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = guiaRemisionRepository.findAll().size();
        // set the field null
        guiaRemision.setFechaSalida(null);

        // Create the GuiaRemision, which fails.

        restGuiaRemisionMockMvc.perform(post("/api/guia-remisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(guiaRemision)))
                .andExpect(status().isBadRequest());

        List<GuiaRemision> guiaRemisions = guiaRemisionRepository.findAll();
        assertThat(guiaRemisions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGuiaRemisions() throws Exception {
        // Initialize the database
        guiaRemisionRepository.saveAndFlush(guiaRemision);

        // Get all the guiaRemisions
        restGuiaRemisionMockMvc.perform(get("/api/guia-remisions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(guiaRemision.getId().intValue())))
                .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
                .andExpect(jsonPath("$.[*].fechaEmision").value(hasItem(DEFAULT_FECHA_EMISION.toString())))
                .andExpect(jsonPath("$.[*].fechaTraslado").value(hasItem(DEFAULT_FECHA_TRASLADO.toString())))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.doubleValue())))
                .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
                .andExpect(jsonPath("$.[*].unidadMedida").value(hasItem(DEFAULT_UNIDAD_MEDIDA.toString())))
                .andExpect(jsonPath("$.[*].costoMinimo").value(hasItem(DEFAULT_COSTO_MINIMO.doubleValue())))
                .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO_STR)))
                .andExpect(jsonPath("$.[*].fechaSalida").value(hasItem(DEFAULT_FECHA_SALIDA_STR)))
                .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())))
                .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
                .andExpect(jsonPath("$.[*].origenDatos").value(hasItem(DEFAULT_ORIGEN_DATOS.booleanValue())))
                .andExpect(jsonPath("$.[*].facturada").value(hasItem(DEFAULT_FACTURADA.booleanValue())));
    }

    @Test
    @Transactional
    public void getGuiaRemision() throws Exception {
        // Initialize the database
        guiaRemisionRepository.saveAndFlush(guiaRemision);

        // Get the guiaRemision
        restGuiaRemisionMockMvc.perform(get("/api/guia-remisions/{id}", guiaRemision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(guiaRemision.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.fechaEmision").value(DEFAULT_FECHA_EMISION.toString()))
            .andExpect(jsonPath("$.fechaTraslado").value(DEFAULT_FECHA_TRASLADO.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.doubleValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.unidadMedida").value(DEFAULT_UNIDAD_MEDIDA.toString()))
            .andExpect(jsonPath("$.costoMinimo").value(DEFAULT_COSTO_MINIMO.doubleValue()))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO_STR))
            .andExpect(jsonPath("$.fechaSalida").value(DEFAULT_FECHA_SALIDA_STR))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.origenDatos").value(DEFAULT_ORIGEN_DATOS.booleanValue()))
            .andExpect(jsonPath("$.facturada").value(DEFAULT_FACTURADA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGuiaRemision() throws Exception {
        // Get the guiaRemision
        restGuiaRemisionMockMvc.perform(get("/api/guia-remisions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGuiaRemision() throws Exception {
        // Initialize the database
        guiaRemisionService.save(guiaRemision);

        int databaseSizeBeforeUpdate = guiaRemisionRepository.findAll().size();

        // Update the guiaRemision
        GuiaRemision updatedGuiaRemision = guiaRemisionRepository.findOne(guiaRemision.getId());
        updatedGuiaRemision
                .codigo(UPDATED_CODIGO)
                .fechaEmision(UPDATED_FECHA_EMISION)
                .fechaTraslado(UPDATED_FECHA_TRASLADO)
                .cantidad(UPDATED_CANTIDAD)
                .peso(UPDATED_PESO)
                .unidadMedida(UPDATED_UNIDAD_MEDIDA)
                .costoMinimo(UPDATED_COSTO_MINIMO)
                .fechaIngreso(UPDATED_FECHA_INGRESO)
                .fechaSalida(UPDATED_FECHA_SALIDA)
                .observaciones(UPDATED_OBSERVACIONES)
                .descripcion(UPDATED_DESCRIPCION)
                .origenDatos(UPDATED_ORIGEN_DATOS)
                .facturada(UPDATED_FACTURADA);

        restGuiaRemisionMockMvc.perform(put("/api/guia-remisions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGuiaRemision)))
                .andExpect(status().isOk());

        // Validate the GuiaRemision in the database
        List<GuiaRemision> guiaRemisions = guiaRemisionRepository.findAll();
        assertThat(guiaRemisions).hasSize(databaseSizeBeforeUpdate);
        GuiaRemision testGuiaRemision = guiaRemisions.get(guiaRemisions.size() - 1);
        assertThat(testGuiaRemision.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testGuiaRemision.getFechaEmision()).isEqualTo(UPDATED_FECHA_EMISION);
        assertThat(testGuiaRemision.getFechaTraslado()).isEqualTo(UPDATED_FECHA_TRASLADO);
        assertThat(testGuiaRemision.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testGuiaRemision.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testGuiaRemision.getUnidadMedida()).isEqualTo(UPDATED_UNIDAD_MEDIDA);
        assertThat(testGuiaRemision.getCostoMinimo()).isEqualTo(UPDATED_COSTO_MINIMO);
        assertThat(testGuiaRemision.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testGuiaRemision.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testGuiaRemision.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testGuiaRemision.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testGuiaRemision.isOrigenDatos()).isEqualTo(UPDATED_ORIGEN_DATOS);
        assertThat(testGuiaRemision.isFacturada()).isEqualTo(UPDATED_FACTURADA);
    }

    @Test
    @Transactional
    public void deleteGuiaRemision() throws Exception {
        // Initialize the database
        guiaRemisionService.save(guiaRemision);

        int databaseSizeBeforeDelete = guiaRemisionRepository.findAll().size();

        // Get the guiaRemision
        restGuiaRemisionMockMvc.perform(delete("/api/guia-remisions/{id}", guiaRemision.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GuiaRemision> guiaRemisions = guiaRemisionRepository.findAll();
        assertThat(guiaRemisions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
