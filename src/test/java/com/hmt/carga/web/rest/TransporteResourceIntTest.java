package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.Transporte;
import com.hmt.carga.domain.TipoUnidad;
import com.hmt.carga.repository.TransporteRepository;
import com.hmt.carga.service.TransporteService;

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
 * Test class for the TransporteResource REST controller.
 *
 * @see TransporteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class TransporteResourceIntTest {

    private static final String DEFAULT_MARCA = "AAAAA";
    private static final String UPDATED_MARCA = "BBBBB";

    private static final String DEFAULT_TRACTO = "AAAAA";
    private static final String UPDATED_TRACTO = "BBBBB";

    private static final String DEFAULT_CARRETA = "AAAAA";
    private static final String UPDATED_CARRETA = "BBBBB";

    private static final String DEFAULT_PLACA_TRACTO = "AAAAA";
    private static final String UPDATED_PLACA_TRACTO = "BBBBB";

    private static final String DEFAULT_PLACA_CARRETA = "AAAAA";
    private static final String UPDATED_PLACA_CARRETA = "BBBBB";

    private static final Double DEFAULT_LARGO_CARRETA = 1D;
    private static final Double UPDATED_LARGO_CARRETA = 2D;

    private static final Double DEFAULT_ANCHO_CARRETA = 1D;
    private static final Double UPDATED_ANCHO_CARRETA = 2D;

    private static final Double DEFAULT_ALTO_CARRETA = 1D;
    private static final Double UPDATED_ALTO_CARRETA = 2D;

    private static final Double DEFAULT_CARGA_UTIL = 1D;
    private static final Double UPDATED_CARGA_UTIL = 2D;

    private static final Boolean DEFAULT_REGISTRO_MATPEL = false;
    private static final Boolean UPDATED_REGISTRO_MATPEL = true;

    private static final Boolean DEFAULT_GPS = false;
    private static final Boolean UPDATED_GPS = true;

    private static final Integer DEFAULT_ANO_FABRICACION = 1;
    private static final Integer UPDATED_ANO_FABRICACION = 2;

    private static final Boolean DEFAULT_UNIDAD_PROPIA = false;
    private static final Boolean UPDATED_UNIDAD_PROPIA = true;

    private static final Double DEFAULT_KILOMETRAJE = 1D;
    private static final Double UPDATED_KILOMETRAJE = 2D;

    private static final LocalDate DEFAULT_FECHA_REVISION_TECNICA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_REVISION_TECNICA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SOAT = "AAAAA";
    private static final String UPDATED_SOAT = "BBBBB";

    private static final LocalDate DEFAULT_FECHA_VENCIMIENTO_SOAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENCIMIENTO_SOAT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MODELO = "AAAAA";
    private static final String UPDATED_MODELO = "BBBBB";

    private static final String DEFAULT_NOMBRE_CONDUCTOR = "AAAAA";
    private static final String UPDATED_NOMBRE_CONDUCTOR = "BBBBB";

    private static final String DEFAULT_LICENCIA_CONDUCTOR = "AAAAA";
    private static final String UPDATED_LICENCIA_CONDUCTOR = "BBBBB";

    @Inject
    private TransporteRepository transporteRepository;

    @Inject
    private TransporteService transporteService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTransporteMockMvc;

    private Transporte transporte;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransporteResource transporteResource = new TransporteResource();
        ReflectionTestUtils.setField(transporteResource, "transporteService", transporteService);
        this.restTransporteMockMvc = MockMvcBuilders.standaloneSetup(transporteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transporte createEntity(EntityManager em) {
        Transporte transporte = new Transporte()
                .marca(DEFAULT_MARCA)
                .tracto(DEFAULT_TRACTO)
                .carreta(DEFAULT_CARRETA)
                .placaTracto(DEFAULT_PLACA_TRACTO)
                .placaCarreta(DEFAULT_PLACA_CARRETA)
                .largoCarreta(DEFAULT_LARGO_CARRETA)
                .anchoCarreta(DEFAULT_ANCHO_CARRETA)
                .altoCarreta(DEFAULT_ALTO_CARRETA)
                .cargaUtil(DEFAULT_CARGA_UTIL)
                .registroMatpel(DEFAULT_REGISTRO_MATPEL)
                .gps(DEFAULT_GPS)
                .anoFabricacion(DEFAULT_ANO_FABRICACION)
                .unidadPropia(DEFAULT_UNIDAD_PROPIA)
                .kilometraje(DEFAULT_KILOMETRAJE)
                .fechaRevisionTecnica(DEFAULT_FECHA_REVISION_TECNICA)
                .soat(DEFAULT_SOAT)
                .fechaVencimientoSoat(DEFAULT_FECHA_VENCIMIENTO_SOAT)
                .modelo(DEFAULT_MODELO)
                .nombreConductor(DEFAULT_NOMBRE_CONDUCTOR)
                .licenciaConductor(DEFAULT_LICENCIA_CONDUCTOR);
        // Add required entity
        TipoUnidad tipoUnidad = TipoUnidadResourceIntTest.createEntity(em);
        em.persist(tipoUnidad);
        em.flush();
        transporte.setTipoUnidad(tipoUnidad);
        return transporte;
    }

    @Before
    public void initTest() {
        transporte = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransporte() throws Exception {
        int databaseSizeBeforeCreate = transporteRepository.findAll().size();

        // Create the Transporte

        restTransporteMockMvc.perform(post("/api/transportes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporte)))
                .andExpect(status().isCreated());

        // Validate the Transporte in the database
        List<Transporte> transportes = transporteRepository.findAll();
        assertThat(transportes).hasSize(databaseSizeBeforeCreate + 1);
        Transporte testTransporte = transportes.get(transportes.size() - 1);
        assertThat(testTransporte.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testTransporte.getTracto()).isEqualTo(DEFAULT_TRACTO);
        assertThat(testTransporte.getCarreta()).isEqualTo(DEFAULT_CARRETA);
        assertThat(testTransporte.getPlacaTracto()).isEqualTo(DEFAULT_PLACA_TRACTO);
        assertThat(testTransporte.getPlacaCarreta()).isEqualTo(DEFAULT_PLACA_CARRETA);
        assertThat(testTransporte.getLargoCarreta()).isEqualTo(DEFAULT_LARGO_CARRETA);
        assertThat(testTransporte.getAnchoCarreta()).isEqualTo(DEFAULT_ANCHO_CARRETA);
        assertThat(testTransporte.getAltoCarreta()).isEqualTo(DEFAULT_ALTO_CARRETA);
        assertThat(testTransporte.getCargaUtil()).isEqualTo(DEFAULT_CARGA_UTIL);
        assertThat(testTransporte.isRegistroMatpel()).isEqualTo(DEFAULT_REGISTRO_MATPEL);
        assertThat(testTransporte.isGps()).isEqualTo(DEFAULT_GPS);
        assertThat(testTransporte.getAnoFabricacion()).isEqualTo(DEFAULT_ANO_FABRICACION);
        assertThat(testTransporte.isUnidadPropia()).isEqualTo(DEFAULT_UNIDAD_PROPIA);
        assertThat(testTransporte.getKilometraje()).isEqualTo(DEFAULT_KILOMETRAJE);
        assertThat(testTransporte.getFechaRevisionTecnica()).isEqualTo(DEFAULT_FECHA_REVISION_TECNICA);
        assertThat(testTransporte.getSoat()).isEqualTo(DEFAULT_SOAT);
        assertThat(testTransporte.getFechaVencimientoSoat()).isEqualTo(DEFAULT_FECHA_VENCIMIENTO_SOAT);
        assertThat(testTransporte.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testTransporte.getNombreConductor()).isEqualTo(DEFAULT_NOMBRE_CONDUCTOR);
        assertThat(testTransporte.getLicenciaConductor()).isEqualTo(DEFAULT_LICENCIA_CONDUCTOR);
    }

    @Test
    @Transactional
    public void checkMarcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = transporteRepository.findAll().size();
        // set the field null
        transporte.setMarca(null);

        // Create the Transporte, which fails.

        restTransporteMockMvc.perform(post("/api/transportes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporte)))
                .andExpect(status().isBadRequest());

        List<Transporte> transportes = transporteRepository.findAll();
        assertThat(transportes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = transporteRepository.findAll().size();
        // set the field null
        transporte.setModelo(null);

        // Create the Transporte, which fails.

        restTransporteMockMvc.perform(post("/api/transportes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporte)))
                .andExpect(status().isBadRequest());

        List<Transporte> transportes = transporteRepository.findAll();
        assertThat(transportes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreConductorIsRequired() throws Exception {
        int databaseSizeBeforeTest = transporteRepository.findAll().size();
        // set the field null
        transporte.setNombreConductor(null);

        // Create the Transporte, which fails.

        restTransporteMockMvc.perform(post("/api/transportes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporte)))
                .andExpect(status().isBadRequest());

        List<Transporte> transportes = transporteRepository.findAll();
        assertThat(transportes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLicenciaConductorIsRequired() throws Exception {
        int databaseSizeBeforeTest = transporteRepository.findAll().size();
        // set the field null
        transporte.setLicenciaConductor(null);

        // Create the Transporte, which fails.

        restTransporteMockMvc.perform(post("/api/transportes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transporte)))
                .andExpect(status().isBadRequest());

        List<Transporte> transportes = transporteRepository.findAll();
        assertThat(transportes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransportes() throws Exception {
        // Initialize the database
        transporteRepository.saveAndFlush(transporte);

        // Get all the transportes
        restTransporteMockMvc.perform(get("/api/transportes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(transporte.getId().intValue())))
                .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA.toString())))
                .andExpect(jsonPath("$.[*].tracto").value(hasItem(DEFAULT_TRACTO.toString())))
                .andExpect(jsonPath("$.[*].carreta").value(hasItem(DEFAULT_CARRETA.toString())))
                .andExpect(jsonPath("$.[*].placaTracto").value(hasItem(DEFAULT_PLACA_TRACTO.toString())))
                .andExpect(jsonPath("$.[*].placaCarreta").value(hasItem(DEFAULT_PLACA_CARRETA.toString())))
                .andExpect(jsonPath("$.[*].largoCarreta").value(hasItem(DEFAULT_LARGO_CARRETA.doubleValue())))
                .andExpect(jsonPath("$.[*].anchoCarreta").value(hasItem(DEFAULT_ANCHO_CARRETA.doubleValue())))
                .andExpect(jsonPath("$.[*].altoCarreta").value(hasItem(DEFAULT_ALTO_CARRETA.doubleValue())))
                .andExpect(jsonPath("$.[*].cargaUtil").value(hasItem(DEFAULT_CARGA_UTIL.doubleValue())))
                .andExpect(jsonPath("$.[*].registroMatpel").value(hasItem(DEFAULT_REGISTRO_MATPEL.booleanValue())))
                .andExpect(jsonPath("$.[*].gps").value(hasItem(DEFAULT_GPS.booleanValue())))
                .andExpect(jsonPath("$.[*].anoFabricacion").value(hasItem(DEFAULT_ANO_FABRICACION)))
                .andExpect(jsonPath("$.[*].unidadPropia").value(hasItem(DEFAULT_UNIDAD_PROPIA.booleanValue())))
                .andExpect(jsonPath("$.[*].kilometraje").value(hasItem(DEFAULT_KILOMETRAJE.doubleValue())))
                .andExpect(jsonPath("$.[*].fechaRevisionTecnica").value(hasItem(DEFAULT_FECHA_REVISION_TECNICA.toString())))
                .andExpect(jsonPath("$.[*].soat").value(hasItem(DEFAULT_SOAT.toString())))
                .andExpect(jsonPath("$.[*].fechaVencimientoSoat").value(hasItem(DEFAULT_FECHA_VENCIMIENTO_SOAT.toString())))
                .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
                .andExpect(jsonPath("$.[*].nombreConductor").value(hasItem(DEFAULT_NOMBRE_CONDUCTOR.toString())))
                .andExpect(jsonPath("$.[*].licenciaConductor").value(hasItem(DEFAULT_LICENCIA_CONDUCTOR.toString())));
    }

    @Test
    @Transactional
    public void getTransporte() throws Exception {
        // Initialize the database
        transporteRepository.saveAndFlush(transporte);

        // Get the transporte
        restTransporteMockMvc.perform(get("/api/transportes/{id}", transporte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transporte.getId().intValue()))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA.toString()))
            .andExpect(jsonPath("$.tracto").value(DEFAULT_TRACTO.toString()))
            .andExpect(jsonPath("$.carreta").value(DEFAULT_CARRETA.toString()))
            .andExpect(jsonPath("$.placaTracto").value(DEFAULT_PLACA_TRACTO.toString()))
            .andExpect(jsonPath("$.placaCarreta").value(DEFAULT_PLACA_CARRETA.toString()))
            .andExpect(jsonPath("$.largoCarreta").value(DEFAULT_LARGO_CARRETA.doubleValue()))
            .andExpect(jsonPath("$.anchoCarreta").value(DEFAULT_ANCHO_CARRETA.doubleValue()))
            .andExpect(jsonPath("$.altoCarreta").value(DEFAULT_ALTO_CARRETA.doubleValue()))
            .andExpect(jsonPath("$.cargaUtil").value(DEFAULT_CARGA_UTIL.doubleValue()))
            .andExpect(jsonPath("$.registroMatpel").value(DEFAULT_REGISTRO_MATPEL.booleanValue()))
            .andExpect(jsonPath("$.gps").value(DEFAULT_GPS.booleanValue()))
            .andExpect(jsonPath("$.anoFabricacion").value(DEFAULT_ANO_FABRICACION))
            .andExpect(jsonPath("$.unidadPropia").value(DEFAULT_UNIDAD_PROPIA.booleanValue()))
            .andExpect(jsonPath("$.kilometraje").value(DEFAULT_KILOMETRAJE.doubleValue()))
            .andExpect(jsonPath("$.fechaRevisionTecnica").value(DEFAULT_FECHA_REVISION_TECNICA.toString()))
            .andExpect(jsonPath("$.soat").value(DEFAULT_SOAT.toString()))
            .andExpect(jsonPath("$.fechaVencimientoSoat").value(DEFAULT_FECHA_VENCIMIENTO_SOAT.toString()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()))
            .andExpect(jsonPath("$.nombreConductor").value(DEFAULT_NOMBRE_CONDUCTOR.toString()))
            .andExpect(jsonPath("$.licenciaConductor").value(DEFAULT_LICENCIA_CONDUCTOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransporte() throws Exception {
        // Get the transporte
        restTransporteMockMvc.perform(get("/api/transportes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransporte() throws Exception {
        // Initialize the database
        transporteService.save(transporte);

        int databaseSizeBeforeUpdate = transporteRepository.findAll().size();

        // Update the transporte
        Transporte updatedTransporte = transporteRepository.findOne(transporte.getId());
        updatedTransporte
                .marca(UPDATED_MARCA)
                .tracto(UPDATED_TRACTO)
                .carreta(UPDATED_CARRETA)
                .placaTracto(UPDATED_PLACA_TRACTO)
                .placaCarreta(UPDATED_PLACA_CARRETA)
                .largoCarreta(UPDATED_LARGO_CARRETA)
                .anchoCarreta(UPDATED_ANCHO_CARRETA)
                .altoCarreta(UPDATED_ALTO_CARRETA)
                .cargaUtil(UPDATED_CARGA_UTIL)
                .registroMatpel(UPDATED_REGISTRO_MATPEL)
                .gps(UPDATED_GPS)
                .anoFabricacion(UPDATED_ANO_FABRICACION)
                .unidadPropia(UPDATED_UNIDAD_PROPIA)
                .kilometraje(UPDATED_KILOMETRAJE)
                .fechaRevisionTecnica(UPDATED_FECHA_REVISION_TECNICA)
                .soat(UPDATED_SOAT)
                .fechaVencimientoSoat(UPDATED_FECHA_VENCIMIENTO_SOAT)
                .modelo(UPDATED_MODELO)
                .nombreConductor(UPDATED_NOMBRE_CONDUCTOR)
                .licenciaConductor(UPDATED_LICENCIA_CONDUCTOR);

        restTransporteMockMvc.perform(put("/api/transportes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTransporte)))
                .andExpect(status().isOk());

        // Validate the Transporte in the database
        List<Transporte> transportes = transporteRepository.findAll();
        assertThat(transportes).hasSize(databaseSizeBeforeUpdate);
        Transporte testTransporte = transportes.get(transportes.size() - 1);
        assertThat(testTransporte.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testTransporte.getTracto()).isEqualTo(UPDATED_TRACTO);
        assertThat(testTransporte.getCarreta()).isEqualTo(UPDATED_CARRETA);
        assertThat(testTransporte.getPlacaTracto()).isEqualTo(UPDATED_PLACA_TRACTO);
        assertThat(testTransporte.getPlacaCarreta()).isEqualTo(UPDATED_PLACA_CARRETA);
        assertThat(testTransporte.getLargoCarreta()).isEqualTo(UPDATED_LARGO_CARRETA);
        assertThat(testTransporte.getAnchoCarreta()).isEqualTo(UPDATED_ANCHO_CARRETA);
        assertThat(testTransporte.getAltoCarreta()).isEqualTo(UPDATED_ALTO_CARRETA);
        assertThat(testTransporte.getCargaUtil()).isEqualTo(UPDATED_CARGA_UTIL);
        assertThat(testTransporte.isRegistroMatpel()).isEqualTo(UPDATED_REGISTRO_MATPEL);
        assertThat(testTransporte.isGps()).isEqualTo(UPDATED_GPS);
        assertThat(testTransporte.getAnoFabricacion()).isEqualTo(UPDATED_ANO_FABRICACION);
        assertThat(testTransporte.isUnidadPropia()).isEqualTo(UPDATED_UNIDAD_PROPIA);
        assertThat(testTransporte.getKilometraje()).isEqualTo(UPDATED_KILOMETRAJE);
        assertThat(testTransporte.getFechaRevisionTecnica()).isEqualTo(UPDATED_FECHA_REVISION_TECNICA);
        assertThat(testTransporte.getSoat()).isEqualTo(UPDATED_SOAT);
        assertThat(testTransporte.getFechaVencimientoSoat()).isEqualTo(UPDATED_FECHA_VENCIMIENTO_SOAT);
        assertThat(testTransporte.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testTransporte.getNombreConductor()).isEqualTo(UPDATED_NOMBRE_CONDUCTOR);
        assertThat(testTransporte.getLicenciaConductor()).isEqualTo(UPDATED_LICENCIA_CONDUCTOR);
    }

    @Test
    @Transactional
    public void deleteTransporte() throws Exception {
        // Initialize the database
        transporteService.save(transporte);

        int databaseSizeBeforeDelete = transporteRepository.findAll().size();

        // Get the transporte
        restTransporteMockMvc.perform(delete("/api/transportes/{id}", transporte.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Transporte> transportes = transporteRepository.findAll();
        assertThat(transportes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
