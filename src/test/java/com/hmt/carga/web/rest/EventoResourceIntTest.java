package com.hmt.carga.web.rest;

import com.hmt.carga.HmtcargaApp;

import com.hmt.carga.domain.Evento;
import com.hmt.carga.repository.EventoRepository;
import com.hmt.carga.service.EventoService;

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
 * Test class for the EventoResource REST controller.
 *
 * @see EventoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmtcargaApp.class)
public class EventoResourceIntTest {

    private static final String DEFAULT_NUMERO_ORDEN_VENTA = "AAAAA";
    private static final String UPDATED_NUMERO_ORDEN_VENTA = "BBBBB";

    private static final String DEFAULT_CLIENTE_GUIA = "AAAAA";
    private static final String UPDATED_CLIENTE_GUIA = "BBBBB";

    private static final String DEFAULT_CLIENTE_FACTURA = "AAAAA";
    private static final String UPDATED_CLIENTE_FACTURA = "BBBBB";

    private static final String DEFAULT_CLIENTE_RUC = "AAAAA";
    private static final String UPDATED_CLIENTE_RUC = "BBBBB";

    private static final String DEFAULT_CLIENTE_RAZON_SOCIAL = "AAAAA";
    private static final String UPDATED_CLIENTE_RAZON_SOCIAL = "BBBBB";

    private static final String DEFAULT_CLIENTE_PESO = "AAAAA";
    private static final String UPDATED_CLIENTE_PESO = "BBBBB";

    private static final Double DEFAULT_CLIENTE_MONTO = 1D;
    private static final Double UPDATED_CLIENTE_MONTO = 2D;

    private static final Double DEFAULT_CLIENTE_TOTAL = 1D;
    private static final Double UPDATED_CLIENTE_TOTAL = 2D;

    private static final Double DEFAULT_CLIENTE_ADELANTO = 1D;
    private static final Double UPDATED_CLIENTE_ADELANTO = 2D;

    private static final LocalDate DEFAULT_FECHA_EMISION_FACTURA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_EMISION_FACTURA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_VENCIMIENTO_FACTURA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENCIMIENTO_FACTURA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLAZO = "AAAAA";
    private static final String UPDATED_PLAZO = "BBBBB";

    private static final LocalDate DEFAULT_FECHA_ENTREGA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENTREGA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LETRA = "AAAAA";
    private static final String UPDATED_LETRA = "BBBBB";

    private static final String DEFAULT_ESTADO_LETRA = "AAAAA";
    private static final String UPDATED_ESTADO_LETRA = "BBBBB";

    private static final LocalDate DEFAULT_FECHA_EMISION_LETRA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_EMISION_LETRA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLAZO_PAGO = "AAAAA";
    private static final String UPDATED_PLAZO_PAGO = "BBBBB";

    private static final LocalDate DEFAULT_VENCIMIENTO_PAGO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VENCIMIENTO_PAGO = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_SALDO_COBRAR = 1D;
    private static final Double UPDATED_SALDO_COBRAR = 2D;

    private static final Double DEFAULT_MONTO_DETRACCION = 1D;
    private static final Double UPDATED_MONTO_DETRACCION = 2D;

    private static final String DEFAULT_CLIENTE_ESTADO = "AAAAA";
    private static final String UPDATED_CLIENTE_ESTADO = "BBBBB";

    private static final String DEFAULT_PROVEEDOR_GUIA = "AAAAA";
    private static final String UPDATED_PROVEEDOR_GUIA = "BBBBB";

    private static final String DEFAULT_PROVEEDOR_FACTURA = "AAAAA";
    private static final String UPDATED_PROVEEDOR_FACTURA = "BBBBB";

    private static final String DEFAULT_PROVEEDOR_RUC = "AAAAA";
    private static final String UPDATED_PROVEEDOR_RUC = "BBBBB";

    private static final String DEFAULT_PROVEEDOR_RAZON_SOCIAL = "AAAAA";
    private static final String UPDATED_PROVEEDOR_RAZON_SOCIAL = "BBBBB";

    private static final String DEFAULT_MATERIAL_TRANSPORTE = "AAAAA";
    private static final String UPDATED_MATERIAL_TRANSPORTE = "BBBBB";

    private static final LocalDate DEFAULT_FECHA_SALIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SALIDA = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTO_SERVICIO = 1D;
    private static final Double UPDATED_MONTO_SERVICIO = 2D;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final Double DEFAULT_ADELANTO_1 = 1D;
    private static final Double UPDATED_ADELANTO_1 = 2D;

    private static final LocalDate DEFAULT_FECHA_ADELANTO_1 = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ADELANTO_1 = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_ADELANTO_2 = 1D;
    private static final Double UPDATED_ADELANTO_2 = 2D;

    private static final LocalDate DEFAULT_FECHA_ADELANTO_2 = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ADELANTO_2 = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_ADELANTO_3 = 1D;
    private static final Double UPDATED_ADELANTO_3 = 2D;

    private static final LocalDate DEFAULT_FECHA_ADELANTO_3 = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ADELANTO_3 = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_ADELANTO_4 = 1D;
    private static final Double UPDATED_ADELANTO_4 = 2D;

    private static final LocalDate DEFAULT_FECHA_ADELANTO_4 = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ADELANTO_4 = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_PAGO_SALDO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PAGO_SALDO = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PROVEEDOR_MONTO_DETRACCION = 1D;
    private static final Double UPDATED_PROVEEDOR_MONTO_DETRACCION = 2D;

    private static final Double DEFAULT_PROVEEDOR_COMISION_DETRACCION = 1D;
    private static final Double UPDATED_PROVEEDOR_COMISION_DETRACCION = 2D;

    private static final Double DEFAULT_PROVEEDOR_TOTAL_PAGAR = 1D;
    private static final Double UPDATED_PROVEEDOR_TOTAL_PAGAR = 2D;

    private static final String DEFAULT_PROVEEDOR_ESTADO = "AAAAA";
    private static final String UPDATED_PROVEEDOR_ESTADO = "BBBBB";

    private static final String DEFAULT_COMPRA_FACTURA = "AAAAA";
    private static final String UPDATED_COMPRA_FACTURA = "BBBBB";

    private static final String DEFAULT_COMPRA_PROVEEDOR = "AAAAA";
    private static final String UPDATED_COMPRA_PROVEEDOR = "BBBBB";

    private static final String DEFAULT_COMPRA_PRODUCTO = "AAAAA";
    private static final String UPDATED_COMPRA_PRODUCTO = "BBBBB";

    private static final Double DEFAULT_COMPRA_TOTAL = 1D;
    private static final Double UPDATED_COMPRA_TOTAL = 2D;

    private static final Double DEFAULT_COMPRA_TOTAL_IGV = 1D;
    private static final Double UPDATED_COMPRA_TOTAL_IGV = 2D;

    private static final String DEFAULT_COMPRA_ESTADO = "AAAAA";
    private static final String UPDATED_COMPRA_ESTADO = "BBBBB";

    @Inject
    private EventoRepository eventoRepository;

    @Inject
    private EventoService eventoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEventoMockMvc;

    private Evento evento;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventoResource eventoResource = new EventoResource();
        ReflectionTestUtils.setField(eventoResource, "eventoService", eventoService);
        this.restEventoMockMvc = MockMvcBuilders.standaloneSetup(eventoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evento createEntity(EntityManager em) {
        Evento evento = new Evento()
                .numeroOrdenVenta(DEFAULT_NUMERO_ORDEN_VENTA)
                .clienteGuia(DEFAULT_CLIENTE_GUIA)
                .clienteFactura(DEFAULT_CLIENTE_FACTURA)
                .clienteRuc(DEFAULT_CLIENTE_RUC)
                .clienteRazonSocial(DEFAULT_CLIENTE_RAZON_SOCIAL)
                .clientePeso(DEFAULT_CLIENTE_PESO)
                .clienteMonto(DEFAULT_CLIENTE_MONTO)
                .clienteTotal(DEFAULT_CLIENTE_TOTAL)
                .clienteAdelanto(DEFAULT_CLIENTE_ADELANTO)
                .fechaEmisionFactura(DEFAULT_FECHA_EMISION_FACTURA)
                .fechaVencimientoFactura(DEFAULT_FECHA_VENCIMIENTO_FACTURA)
                .plazo(DEFAULT_PLAZO)
                .fechaEntrega(DEFAULT_FECHA_ENTREGA)
                .letra(DEFAULT_LETRA)
                .estadoLetra(DEFAULT_ESTADO_LETRA)
                .fechaEmisionLetra(DEFAULT_FECHA_EMISION_LETRA)
                .plazoPago(DEFAULT_PLAZO_PAGO)
                .vencimientoPago(DEFAULT_VENCIMIENTO_PAGO)
                .saldoCobrar(DEFAULT_SALDO_COBRAR)
                .montoDetraccion(DEFAULT_MONTO_DETRACCION)
                .clienteEstado(DEFAULT_CLIENTE_ESTADO)
                .proveedorGuia(DEFAULT_PROVEEDOR_GUIA)
                .proveedorFactura(DEFAULT_PROVEEDOR_FACTURA)
                .proveedorRuc(DEFAULT_PROVEEDOR_RUC)
                .proveedorRazonSocial(DEFAULT_PROVEEDOR_RAZON_SOCIAL)
                .materialTransporte(DEFAULT_MATERIAL_TRANSPORTE)
                .fechaSalida(DEFAULT_FECHA_SALIDA)
                .montoServicio(DEFAULT_MONTO_SERVICIO)
                .total(DEFAULT_TOTAL)
                .adelanto1(DEFAULT_ADELANTO_1)
                .fechaAdelanto1(DEFAULT_FECHA_ADELANTO_1)
                .adelanto2(DEFAULT_ADELANTO_2)
                .fechaAdelanto2(DEFAULT_FECHA_ADELANTO_2)
                .adelanto3(DEFAULT_ADELANTO_3)
                .fechaAdelanto3(DEFAULT_FECHA_ADELANTO_3)
                .adelanto4(DEFAULT_ADELANTO_4)
                .fechaAdelanto4(DEFAULT_FECHA_ADELANTO_4)
                .fechaPagoSaldo(DEFAULT_FECHA_PAGO_SALDO)
                .proveedorMontoDetraccion(DEFAULT_PROVEEDOR_MONTO_DETRACCION)
                .proveedorComisionDetraccion(DEFAULT_PROVEEDOR_COMISION_DETRACCION)
                .proveedorTotalPagar(DEFAULT_PROVEEDOR_TOTAL_PAGAR)
                .proveedorEstado(DEFAULT_PROVEEDOR_ESTADO)
                .compraFactura(DEFAULT_COMPRA_FACTURA)
                .compraProveedor(DEFAULT_COMPRA_PROVEEDOR)
                .compraProducto(DEFAULT_COMPRA_PRODUCTO)
                .compraTotal(DEFAULT_COMPRA_TOTAL)
                .compraTotalIgv(DEFAULT_COMPRA_TOTAL_IGV)
                .compraEstado(DEFAULT_COMPRA_ESTADO);
        return evento;
    }

    @Before
    public void initTest() {
        evento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvento() throws Exception {
        int databaseSizeBeforeCreate = eventoRepository.findAll().size();

        // Create the Evento

        restEventoMockMvc.perform(post("/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evento)))
                .andExpect(status().isCreated());

        // Validate the Evento in the database
        List<Evento> eventos = eventoRepository.findAll();
        assertThat(eventos).hasSize(databaseSizeBeforeCreate + 1);
        Evento testEvento = eventos.get(eventos.size() - 1);
        assertThat(testEvento.getNumeroOrdenVenta()).isEqualTo(DEFAULT_NUMERO_ORDEN_VENTA);
        assertThat(testEvento.getClienteGuia()).isEqualTo(DEFAULT_CLIENTE_GUIA);
        assertThat(testEvento.getClienteFactura()).isEqualTo(DEFAULT_CLIENTE_FACTURA);
        assertThat(testEvento.getClienteRuc()).isEqualTo(DEFAULT_CLIENTE_RUC);
        assertThat(testEvento.getClienteRazonSocial()).isEqualTo(DEFAULT_CLIENTE_RAZON_SOCIAL);
        assertThat(testEvento.getClientePeso()).isEqualTo(DEFAULT_CLIENTE_PESO);
        assertThat(testEvento.getClienteMonto()).isEqualTo(DEFAULT_CLIENTE_MONTO);
        assertThat(testEvento.getClienteTotal()).isEqualTo(DEFAULT_CLIENTE_TOTAL);
        assertThat(testEvento.getClienteAdelanto()).isEqualTo(DEFAULT_CLIENTE_ADELANTO);
        assertThat(testEvento.getFechaEmisionFactura()).isEqualTo(DEFAULT_FECHA_EMISION_FACTURA);
        assertThat(testEvento.getFechaVencimientoFactura()).isEqualTo(DEFAULT_FECHA_VENCIMIENTO_FACTURA);
        assertThat(testEvento.getPlazo()).isEqualTo(DEFAULT_PLAZO);
        assertThat(testEvento.getFechaEntrega()).isEqualTo(DEFAULT_FECHA_ENTREGA);
        assertThat(testEvento.getLetra()).isEqualTo(DEFAULT_LETRA);
        assertThat(testEvento.getEstadoLetra()).isEqualTo(DEFAULT_ESTADO_LETRA);
        assertThat(testEvento.getFechaEmisionLetra()).isEqualTo(DEFAULT_FECHA_EMISION_LETRA);
        assertThat(testEvento.getPlazoPago()).isEqualTo(DEFAULT_PLAZO_PAGO);
        assertThat(testEvento.getVencimientoPago()).isEqualTo(DEFAULT_VENCIMIENTO_PAGO);
        assertThat(testEvento.getSaldoCobrar()).isEqualTo(DEFAULT_SALDO_COBRAR);
        assertThat(testEvento.getMontoDetraccion()).isEqualTo(DEFAULT_MONTO_DETRACCION);
        assertThat(testEvento.getClienteEstado()).isEqualTo(DEFAULT_CLIENTE_ESTADO);
        assertThat(testEvento.getProveedorGuia()).isEqualTo(DEFAULT_PROVEEDOR_GUIA);
        assertThat(testEvento.getProveedorFactura()).isEqualTo(DEFAULT_PROVEEDOR_FACTURA);
        assertThat(testEvento.getProveedorRuc()).isEqualTo(DEFAULT_PROVEEDOR_RUC);
        assertThat(testEvento.getProveedorRazonSocial()).isEqualTo(DEFAULT_PROVEEDOR_RAZON_SOCIAL);
        assertThat(testEvento.getMaterialTransporte()).isEqualTo(DEFAULT_MATERIAL_TRANSPORTE);
        assertThat(testEvento.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
        assertThat(testEvento.getMontoServicio()).isEqualTo(DEFAULT_MONTO_SERVICIO);
        assertThat(testEvento.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testEvento.getAdelanto1()).isEqualTo(DEFAULT_ADELANTO_1);
        assertThat(testEvento.getFechaAdelanto1()).isEqualTo(DEFAULT_FECHA_ADELANTO_1);
        assertThat(testEvento.getAdelanto2()).isEqualTo(DEFAULT_ADELANTO_2);
        assertThat(testEvento.getFechaAdelanto2()).isEqualTo(DEFAULT_FECHA_ADELANTO_2);
        assertThat(testEvento.getAdelanto3()).isEqualTo(DEFAULT_ADELANTO_3);
        assertThat(testEvento.getFechaAdelanto3()).isEqualTo(DEFAULT_FECHA_ADELANTO_3);
        assertThat(testEvento.getAdelanto4()).isEqualTo(DEFAULT_ADELANTO_4);
        assertThat(testEvento.getFechaAdelanto4()).isEqualTo(DEFAULT_FECHA_ADELANTO_4);
        assertThat(testEvento.getFechaPagoSaldo()).isEqualTo(DEFAULT_FECHA_PAGO_SALDO);
        assertThat(testEvento.getProveedorMontoDetraccion()).isEqualTo(DEFAULT_PROVEEDOR_MONTO_DETRACCION);
        assertThat(testEvento.getProveedorComisionDetraccion()).isEqualTo(DEFAULT_PROVEEDOR_COMISION_DETRACCION);
        assertThat(testEvento.getProveedorTotalPagar()).isEqualTo(DEFAULT_PROVEEDOR_TOTAL_PAGAR);
        assertThat(testEvento.getProveedorEstado()).isEqualTo(DEFAULT_PROVEEDOR_ESTADO);
        assertThat(testEvento.getCompraFactura()).isEqualTo(DEFAULT_COMPRA_FACTURA);
        assertThat(testEvento.getCompraProveedor()).isEqualTo(DEFAULT_COMPRA_PROVEEDOR);
        assertThat(testEvento.getCompraProducto()).isEqualTo(DEFAULT_COMPRA_PRODUCTO);
        assertThat(testEvento.getCompraTotal()).isEqualTo(DEFAULT_COMPRA_TOTAL);
        assertThat(testEvento.getCompraTotalIgv()).isEqualTo(DEFAULT_COMPRA_TOTAL_IGV);
        assertThat(testEvento.getCompraEstado()).isEqualTo(DEFAULT_COMPRA_ESTADO);
    }

    @Test
    @Transactional
    public void checkNumeroOrdenVentaIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setNumeroOrdenVenta(null);

        // Create the Evento, which fails.

        restEventoMockMvc.perform(post("/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evento)))
                .andExpect(status().isBadRequest());

        List<Evento> eventos = eventoRepository.findAll();
        assertThat(eventos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEventos() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventos
        restEventoMockMvc.perform(get("/api/eventos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(evento.getId().intValue())))
                .andExpect(jsonPath("$.[*].numeroOrdenVenta").value(hasItem(DEFAULT_NUMERO_ORDEN_VENTA.toString())))
                .andExpect(jsonPath("$.[*].clienteGuia").value(hasItem(DEFAULT_CLIENTE_GUIA.toString())))
                .andExpect(jsonPath("$.[*].clienteFactura").value(hasItem(DEFAULT_CLIENTE_FACTURA.toString())))
                .andExpect(jsonPath("$.[*].clienteRuc").value(hasItem(DEFAULT_CLIENTE_RUC.toString())))
                .andExpect(jsonPath("$.[*].clienteRazonSocial").value(hasItem(DEFAULT_CLIENTE_RAZON_SOCIAL.toString())))
                .andExpect(jsonPath("$.[*].clientePeso").value(hasItem(DEFAULT_CLIENTE_PESO.toString())))
                .andExpect(jsonPath("$.[*].clienteMonto").value(hasItem(DEFAULT_CLIENTE_MONTO.doubleValue())))
                .andExpect(jsonPath("$.[*].clienteTotal").value(hasItem(DEFAULT_CLIENTE_TOTAL.doubleValue())))
                .andExpect(jsonPath("$.[*].clienteAdelanto").value(hasItem(DEFAULT_CLIENTE_ADELANTO.doubleValue())))
                .andExpect(jsonPath("$.[*].fechaEmisionFactura").value(hasItem(DEFAULT_FECHA_EMISION_FACTURA.toString())))
                .andExpect(jsonPath("$.[*].fechaVencimientoFactura").value(hasItem(DEFAULT_FECHA_VENCIMIENTO_FACTURA.toString())))
                .andExpect(jsonPath("$.[*].plazo").value(hasItem(DEFAULT_PLAZO.toString())))
                .andExpect(jsonPath("$.[*].fechaEntrega").value(hasItem(DEFAULT_FECHA_ENTREGA.toString())))
                .andExpect(jsonPath("$.[*].letra").value(hasItem(DEFAULT_LETRA.toString())))
                .andExpect(jsonPath("$.[*].estadoLetra").value(hasItem(DEFAULT_ESTADO_LETRA.toString())))
                .andExpect(jsonPath("$.[*].fechaEmisionLetra").value(hasItem(DEFAULT_FECHA_EMISION_LETRA.toString())))
                .andExpect(jsonPath("$.[*].plazoPago").value(hasItem(DEFAULT_PLAZO_PAGO.toString())))
                .andExpect(jsonPath("$.[*].vencimientoPago").value(hasItem(DEFAULT_VENCIMIENTO_PAGO.toString())))
                .andExpect(jsonPath("$.[*].saldoCobrar").value(hasItem(DEFAULT_SALDO_COBRAR.doubleValue())))
                .andExpect(jsonPath("$.[*].montoDetraccion").value(hasItem(DEFAULT_MONTO_DETRACCION.doubleValue())))
                .andExpect(jsonPath("$.[*].clienteEstado").value(hasItem(DEFAULT_CLIENTE_ESTADO.toString())))
                .andExpect(jsonPath("$.[*].proveedorGuia").value(hasItem(DEFAULT_PROVEEDOR_GUIA.toString())))
                .andExpect(jsonPath("$.[*].proveedorFactura").value(hasItem(DEFAULT_PROVEEDOR_FACTURA.toString())))
                .andExpect(jsonPath("$.[*].proveedorRuc").value(hasItem(DEFAULT_PROVEEDOR_RUC.toString())))
                .andExpect(jsonPath("$.[*].proveedorRazonSocial").value(hasItem(DEFAULT_PROVEEDOR_RAZON_SOCIAL.toString())))
                .andExpect(jsonPath("$.[*].materialTransporte").value(hasItem(DEFAULT_MATERIAL_TRANSPORTE.toString())))
                .andExpect(jsonPath("$.[*].fechaSalida").value(hasItem(DEFAULT_FECHA_SALIDA.toString())))
                .andExpect(jsonPath("$.[*].montoServicio").value(hasItem(DEFAULT_MONTO_SERVICIO.doubleValue())))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
                .andExpect(jsonPath("$.[*].adelanto1").value(hasItem(DEFAULT_ADELANTO_1.doubleValue())))
                .andExpect(jsonPath("$.[*].fechaAdelanto1").value(hasItem(DEFAULT_FECHA_ADELANTO_1.toString())))
                .andExpect(jsonPath("$.[*].adelanto2").value(hasItem(DEFAULT_ADELANTO_2.doubleValue())))
                .andExpect(jsonPath("$.[*].fechaAdelanto2").value(hasItem(DEFAULT_FECHA_ADELANTO_2.toString())))
                .andExpect(jsonPath("$.[*].adelanto3").value(hasItem(DEFAULT_ADELANTO_3.doubleValue())))
                .andExpect(jsonPath("$.[*].fechaAdelanto3").value(hasItem(DEFAULT_FECHA_ADELANTO_3.toString())))
                .andExpect(jsonPath("$.[*].adelanto4").value(hasItem(DEFAULT_ADELANTO_4.doubleValue())))
                .andExpect(jsonPath("$.[*].fechaAdelanto4").value(hasItem(DEFAULT_FECHA_ADELANTO_4.toString())))
                .andExpect(jsonPath("$.[*].fechaPagoSaldo").value(hasItem(DEFAULT_FECHA_PAGO_SALDO.toString())))
                .andExpect(jsonPath("$.[*].proveedorMontoDetraccion").value(hasItem(DEFAULT_PROVEEDOR_MONTO_DETRACCION.doubleValue())))
                .andExpect(jsonPath("$.[*].proveedorComisionDetraccion").value(hasItem(DEFAULT_PROVEEDOR_COMISION_DETRACCION.doubleValue())))
                .andExpect(jsonPath("$.[*].proveedorTotalPagar").value(hasItem(DEFAULT_PROVEEDOR_TOTAL_PAGAR.doubleValue())))
                .andExpect(jsonPath("$.[*].proveedorEstado").value(hasItem(DEFAULT_PROVEEDOR_ESTADO.toString())))
                .andExpect(jsonPath("$.[*].compraFactura").value(hasItem(DEFAULT_COMPRA_FACTURA.toString())))
                .andExpect(jsonPath("$.[*].compraProveedor").value(hasItem(DEFAULT_COMPRA_PROVEEDOR.toString())))
                .andExpect(jsonPath("$.[*].compraProducto").value(hasItem(DEFAULT_COMPRA_PRODUCTO.toString())))
                .andExpect(jsonPath("$.[*].compraTotal").value(hasItem(DEFAULT_COMPRA_TOTAL.doubleValue())))
                .andExpect(jsonPath("$.[*].compraTotalIgv").value(hasItem(DEFAULT_COMPRA_TOTAL_IGV.doubleValue())))
                .andExpect(jsonPath("$.[*].compraEstado").value(hasItem(DEFAULT_COMPRA_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void getEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get the evento
        restEventoMockMvc.perform(get("/api/eventos/{id}", evento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evento.getId().intValue()))
            .andExpect(jsonPath("$.numeroOrdenVenta").value(DEFAULT_NUMERO_ORDEN_VENTA.toString()))
            .andExpect(jsonPath("$.clienteGuia").value(DEFAULT_CLIENTE_GUIA.toString()))
            .andExpect(jsonPath("$.clienteFactura").value(DEFAULT_CLIENTE_FACTURA.toString()))
            .andExpect(jsonPath("$.clienteRuc").value(DEFAULT_CLIENTE_RUC.toString()))
            .andExpect(jsonPath("$.clienteRazonSocial").value(DEFAULT_CLIENTE_RAZON_SOCIAL.toString()))
            .andExpect(jsonPath("$.clientePeso").value(DEFAULT_CLIENTE_PESO.toString()))
            .andExpect(jsonPath("$.clienteMonto").value(DEFAULT_CLIENTE_MONTO.doubleValue()))
            .andExpect(jsonPath("$.clienteTotal").value(DEFAULT_CLIENTE_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.clienteAdelanto").value(DEFAULT_CLIENTE_ADELANTO.doubleValue()))
            .andExpect(jsonPath("$.fechaEmisionFactura").value(DEFAULT_FECHA_EMISION_FACTURA.toString()))
            .andExpect(jsonPath("$.fechaVencimientoFactura").value(DEFAULT_FECHA_VENCIMIENTO_FACTURA.toString()))
            .andExpect(jsonPath("$.plazo").value(DEFAULT_PLAZO.toString()))
            .andExpect(jsonPath("$.fechaEntrega").value(DEFAULT_FECHA_ENTREGA.toString()))
            .andExpect(jsonPath("$.letra").value(DEFAULT_LETRA.toString()))
            .andExpect(jsonPath("$.estadoLetra").value(DEFAULT_ESTADO_LETRA.toString()))
            .andExpect(jsonPath("$.fechaEmisionLetra").value(DEFAULT_FECHA_EMISION_LETRA.toString()))
            .andExpect(jsonPath("$.plazoPago").value(DEFAULT_PLAZO_PAGO.toString()))
            .andExpect(jsonPath("$.vencimientoPago").value(DEFAULT_VENCIMIENTO_PAGO.toString()))
            .andExpect(jsonPath("$.saldoCobrar").value(DEFAULT_SALDO_COBRAR.doubleValue()))
            .andExpect(jsonPath("$.montoDetraccion").value(DEFAULT_MONTO_DETRACCION.doubleValue()))
            .andExpect(jsonPath("$.clienteEstado").value(DEFAULT_CLIENTE_ESTADO.toString()))
            .andExpect(jsonPath("$.proveedorGuia").value(DEFAULT_PROVEEDOR_GUIA.toString()))
            .andExpect(jsonPath("$.proveedorFactura").value(DEFAULT_PROVEEDOR_FACTURA.toString()))
            .andExpect(jsonPath("$.proveedorRuc").value(DEFAULT_PROVEEDOR_RUC.toString()))
            .andExpect(jsonPath("$.proveedorRazonSocial").value(DEFAULT_PROVEEDOR_RAZON_SOCIAL.toString()))
            .andExpect(jsonPath("$.materialTransporte").value(DEFAULT_MATERIAL_TRANSPORTE.toString()))
            .andExpect(jsonPath("$.fechaSalida").value(DEFAULT_FECHA_SALIDA.toString()))
            .andExpect(jsonPath("$.montoServicio").value(DEFAULT_MONTO_SERVICIO.doubleValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.adelanto1").value(DEFAULT_ADELANTO_1.doubleValue()))
            .andExpect(jsonPath("$.fechaAdelanto1").value(DEFAULT_FECHA_ADELANTO_1.toString()))
            .andExpect(jsonPath("$.adelanto2").value(DEFAULT_ADELANTO_2.doubleValue()))
            .andExpect(jsonPath("$.fechaAdelanto2").value(DEFAULT_FECHA_ADELANTO_2.toString()))
            .andExpect(jsonPath("$.adelanto3").value(DEFAULT_ADELANTO_3.doubleValue()))
            .andExpect(jsonPath("$.fechaAdelanto3").value(DEFAULT_FECHA_ADELANTO_3.toString()))
            .andExpect(jsonPath("$.adelanto4").value(DEFAULT_ADELANTO_4.doubleValue()))
            .andExpect(jsonPath("$.fechaAdelanto4").value(DEFAULT_FECHA_ADELANTO_4.toString()))
            .andExpect(jsonPath("$.fechaPagoSaldo").value(DEFAULT_FECHA_PAGO_SALDO.toString()))
            .andExpect(jsonPath("$.proveedorMontoDetraccion").value(DEFAULT_PROVEEDOR_MONTO_DETRACCION.doubleValue()))
            .andExpect(jsonPath("$.proveedorComisionDetraccion").value(DEFAULT_PROVEEDOR_COMISION_DETRACCION.doubleValue()))
            .andExpect(jsonPath("$.proveedorTotalPagar").value(DEFAULT_PROVEEDOR_TOTAL_PAGAR.doubleValue()))
            .andExpect(jsonPath("$.proveedorEstado").value(DEFAULT_PROVEEDOR_ESTADO.toString()))
            .andExpect(jsonPath("$.compraFactura").value(DEFAULT_COMPRA_FACTURA.toString()))
            .andExpect(jsonPath("$.compraProveedor").value(DEFAULT_COMPRA_PROVEEDOR.toString()))
            .andExpect(jsonPath("$.compraProducto").value(DEFAULT_COMPRA_PRODUCTO.toString()))
            .andExpect(jsonPath("$.compraTotal").value(DEFAULT_COMPRA_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.compraTotalIgv").value(DEFAULT_COMPRA_TOTAL_IGV.doubleValue()))
            .andExpect(jsonPath("$.compraEstado").value(DEFAULT_COMPRA_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvento() throws Exception {
        // Get the evento
        restEventoMockMvc.perform(get("/api/eventos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvento() throws Exception {
        // Initialize the database
        eventoService.save(evento);

        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Update the evento
        Evento updatedEvento = eventoRepository.findOne(evento.getId());
        updatedEvento
                .numeroOrdenVenta(UPDATED_NUMERO_ORDEN_VENTA)
                .clienteGuia(UPDATED_CLIENTE_GUIA)
                .clienteFactura(UPDATED_CLIENTE_FACTURA)
                .clienteRuc(UPDATED_CLIENTE_RUC)
                .clienteRazonSocial(UPDATED_CLIENTE_RAZON_SOCIAL)
                .clientePeso(UPDATED_CLIENTE_PESO)
                .clienteMonto(UPDATED_CLIENTE_MONTO)
                .clienteTotal(UPDATED_CLIENTE_TOTAL)
                .clienteAdelanto(UPDATED_CLIENTE_ADELANTO)
                .fechaEmisionFactura(UPDATED_FECHA_EMISION_FACTURA)
                .fechaVencimientoFactura(UPDATED_FECHA_VENCIMIENTO_FACTURA)
                .plazo(UPDATED_PLAZO)
                .fechaEntrega(UPDATED_FECHA_ENTREGA)
                .letra(UPDATED_LETRA)
                .estadoLetra(UPDATED_ESTADO_LETRA)
                .fechaEmisionLetra(UPDATED_FECHA_EMISION_LETRA)
                .plazoPago(UPDATED_PLAZO_PAGO)
                .vencimientoPago(UPDATED_VENCIMIENTO_PAGO)
                .saldoCobrar(UPDATED_SALDO_COBRAR)
                .montoDetraccion(UPDATED_MONTO_DETRACCION)
                .clienteEstado(UPDATED_CLIENTE_ESTADO)
                .proveedorGuia(UPDATED_PROVEEDOR_GUIA)
                .proveedorFactura(UPDATED_PROVEEDOR_FACTURA)
                .proveedorRuc(UPDATED_PROVEEDOR_RUC)
                .proveedorRazonSocial(UPDATED_PROVEEDOR_RAZON_SOCIAL)
                .materialTransporte(UPDATED_MATERIAL_TRANSPORTE)
                .fechaSalida(UPDATED_FECHA_SALIDA)
                .montoServicio(UPDATED_MONTO_SERVICIO)
                .total(UPDATED_TOTAL)
                .adelanto1(UPDATED_ADELANTO_1)
                .fechaAdelanto1(UPDATED_FECHA_ADELANTO_1)
                .adelanto2(UPDATED_ADELANTO_2)
                .fechaAdelanto2(UPDATED_FECHA_ADELANTO_2)
                .adelanto3(UPDATED_ADELANTO_3)
                .fechaAdelanto3(UPDATED_FECHA_ADELANTO_3)
                .adelanto4(UPDATED_ADELANTO_4)
                .fechaAdelanto4(UPDATED_FECHA_ADELANTO_4)
                .fechaPagoSaldo(UPDATED_FECHA_PAGO_SALDO)
                .proveedorMontoDetraccion(UPDATED_PROVEEDOR_MONTO_DETRACCION)
                .proveedorComisionDetraccion(UPDATED_PROVEEDOR_COMISION_DETRACCION)
                .proveedorTotalPagar(UPDATED_PROVEEDOR_TOTAL_PAGAR)
                .proveedorEstado(UPDATED_PROVEEDOR_ESTADO)
                .compraFactura(UPDATED_COMPRA_FACTURA)
                .compraProveedor(UPDATED_COMPRA_PROVEEDOR)
                .compraProducto(UPDATED_COMPRA_PRODUCTO)
                .compraTotal(UPDATED_COMPRA_TOTAL)
                .compraTotalIgv(UPDATED_COMPRA_TOTAL_IGV)
                .compraEstado(UPDATED_COMPRA_ESTADO);

        restEventoMockMvc.perform(put("/api/eventos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEvento)))
                .andExpect(status().isOk());

        // Validate the Evento in the database
        List<Evento> eventos = eventoRepository.findAll();
        assertThat(eventos).hasSize(databaseSizeBeforeUpdate);
        Evento testEvento = eventos.get(eventos.size() - 1);
        assertThat(testEvento.getNumeroOrdenVenta()).isEqualTo(UPDATED_NUMERO_ORDEN_VENTA);
        assertThat(testEvento.getClienteGuia()).isEqualTo(UPDATED_CLIENTE_GUIA);
        assertThat(testEvento.getClienteFactura()).isEqualTo(UPDATED_CLIENTE_FACTURA);
        assertThat(testEvento.getClienteRuc()).isEqualTo(UPDATED_CLIENTE_RUC);
        assertThat(testEvento.getClienteRazonSocial()).isEqualTo(UPDATED_CLIENTE_RAZON_SOCIAL);
        assertThat(testEvento.getClientePeso()).isEqualTo(UPDATED_CLIENTE_PESO);
        assertThat(testEvento.getClienteMonto()).isEqualTo(UPDATED_CLIENTE_MONTO);
        assertThat(testEvento.getClienteTotal()).isEqualTo(UPDATED_CLIENTE_TOTAL);
        assertThat(testEvento.getClienteAdelanto()).isEqualTo(UPDATED_CLIENTE_ADELANTO);
        assertThat(testEvento.getFechaEmisionFactura()).isEqualTo(UPDATED_FECHA_EMISION_FACTURA);
        assertThat(testEvento.getFechaVencimientoFactura()).isEqualTo(UPDATED_FECHA_VENCIMIENTO_FACTURA);
        assertThat(testEvento.getPlazo()).isEqualTo(UPDATED_PLAZO);
        assertThat(testEvento.getFechaEntrega()).isEqualTo(UPDATED_FECHA_ENTREGA);
        assertThat(testEvento.getLetra()).isEqualTo(UPDATED_LETRA);
        assertThat(testEvento.getEstadoLetra()).isEqualTo(UPDATED_ESTADO_LETRA);
        assertThat(testEvento.getFechaEmisionLetra()).isEqualTo(UPDATED_FECHA_EMISION_LETRA);
        assertThat(testEvento.getPlazoPago()).isEqualTo(UPDATED_PLAZO_PAGO);
        assertThat(testEvento.getVencimientoPago()).isEqualTo(UPDATED_VENCIMIENTO_PAGO);
        assertThat(testEvento.getSaldoCobrar()).isEqualTo(UPDATED_SALDO_COBRAR);
        assertThat(testEvento.getMontoDetraccion()).isEqualTo(UPDATED_MONTO_DETRACCION);
        assertThat(testEvento.getClienteEstado()).isEqualTo(UPDATED_CLIENTE_ESTADO);
        assertThat(testEvento.getProveedorGuia()).isEqualTo(UPDATED_PROVEEDOR_GUIA);
        assertThat(testEvento.getProveedorFactura()).isEqualTo(UPDATED_PROVEEDOR_FACTURA);
        assertThat(testEvento.getProveedorRuc()).isEqualTo(UPDATED_PROVEEDOR_RUC);
        assertThat(testEvento.getProveedorRazonSocial()).isEqualTo(UPDATED_PROVEEDOR_RAZON_SOCIAL);
        assertThat(testEvento.getMaterialTransporte()).isEqualTo(UPDATED_MATERIAL_TRANSPORTE);
        assertThat(testEvento.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testEvento.getMontoServicio()).isEqualTo(UPDATED_MONTO_SERVICIO);
        assertThat(testEvento.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testEvento.getAdelanto1()).isEqualTo(UPDATED_ADELANTO_1);
        assertThat(testEvento.getFechaAdelanto1()).isEqualTo(UPDATED_FECHA_ADELANTO_1);
        assertThat(testEvento.getAdelanto2()).isEqualTo(UPDATED_ADELANTO_2);
        assertThat(testEvento.getFechaAdelanto2()).isEqualTo(UPDATED_FECHA_ADELANTO_2);
        assertThat(testEvento.getAdelanto3()).isEqualTo(UPDATED_ADELANTO_3);
        assertThat(testEvento.getFechaAdelanto3()).isEqualTo(UPDATED_FECHA_ADELANTO_3);
        assertThat(testEvento.getAdelanto4()).isEqualTo(UPDATED_ADELANTO_4);
        assertThat(testEvento.getFechaAdelanto4()).isEqualTo(UPDATED_FECHA_ADELANTO_4);
        assertThat(testEvento.getFechaPagoSaldo()).isEqualTo(UPDATED_FECHA_PAGO_SALDO);
        assertThat(testEvento.getProveedorMontoDetraccion()).isEqualTo(UPDATED_PROVEEDOR_MONTO_DETRACCION);
        assertThat(testEvento.getProveedorComisionDetraccion()).isEqualTo(UPDATED_PROVEEDOR_COMISION_DETRACCION);
        assertThat(testEvento.getProveedorTotalPagar()).isEqualTo(UPDATED_PROVEEDOR_TOTAL_PAGAR);
        assertThat(testEvento.getProveedorEstado()).isEqualTo(UPDATED_PROVEEDOR_ESTADO);
        assertThat(testEvento.getCompraFactura()).isEqualTo(UPDATED_COMPRA_FACTURA);
        assertThat(testEvento.getCompraProveedor()).isEqualTo(UPDATED_COMPRA_PROVEEDOR);
        assertThat(testEvento.getCompraProducto()).isEqualTo(UPDATED_COMPRA_PRODUCTO);
        assertThat(testEvento.getCompraTotal()).isEqualTo(UPDATED_COMPRA_TOTAL);
        assertThat(testEvento.getCompraTotalIgv()).isEqualTo(UPDATED_COMPRA_TOTAL_IGV);
        assertThat(testEvento.getCompraEstado()).isEqualTo(UPDATED_COMPRA_ESTADO);
    }

    @Test
    @Transactional
    public void deleteEvento() throws Exception {
        // Initialize the database
        eventoService.save(evento);

        int databaseSizeBeforeDelete = eventoRepository.findAll().size();

        // Get the evento
        restEventoMockMvc.perform(delete("/api/eventos/{id}", evento.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Evento> eventos = eventoRepository.findAll();
        assertThat(eventos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
