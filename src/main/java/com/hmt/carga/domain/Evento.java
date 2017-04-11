package com.hmt.carga.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Evento.
 */
@Entity
@Table(name = "evento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "numero_orden_venta", nullable = false)
    private String numeroOrdenVenta;

    @Column(name = "cliente_guia")
    private String clienteGuia;

    @Column(name = "cliente_factura")
    private String clienteFactura;

    @Column(name = "cliente_ruc")
    private String clienteRuc;

    @Column(name = "cliente_razon_social")
    private String clienteRazonSocial;

    @Column(name = "cliente_peso")
    private String clientePeso;

    @Column(name = "cliente_monto")
    private Double clienteMonto;

    @Column(name = "cliente_total")
    private Double clienteTotal;

    @Column(name = "cliente_adelanto")
    private Double clienteAdelanto;

    @Column(name = "fecha_emision_factura")
    private LocalDate fechaEmisionFactura;

    @Column(name = "fecha_vencimiento_factura")
    private LocalDate fechaVencimientoFactura;

    @Column(name = "plazo")
    private String plazo;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(name = "letra")
    private String letra;

    @Column(name = "estado_letra")
    private String estadoLetra;

    @Column(name = "fecha_emision_letra")
    private LocalDate fechaEmisionLetra;

    @Column(name = "plazo_pago")
    private String plazoPago;

    @Column(name = "vencimiento_pago")
    private LocalDate vencimientoPago;

    @Column(name = "saldo_cobrar")
    private Double saldoCobrar;

    @Column(name = "monto_detraccion")
    private Double montoDetraccion;

    @Column(name = "cliente_estado")
    private String clienteEstado;

    @Column(name = "proveedor_guia")
    private String proveedorGuia;

    @Column(name = "proveedor_factura")
    private String proveedorFactura;

    @Column(name = "proveedor_ruc")
    private String proveedorRuc;

    @Column(name = "proveedor_razon_social")
    private String proveedorRazonSocial;

    @Column(name = "material_transporte")
    private String materialTransporte;

    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    @Column(name = "monto_servicio")
    private Double montoServicio;

    @Column(name = "total")
    private Double total;

    @Column(name = "adelanto_1")
    private Double adelanto1;

    @Column(name = "fecha_adelanto_1")
    private LocalDate fechaAdelanto1;

    @Column(name = "adelanto_2")
    private Double adelanto2;

    @Column(name = "fecha_adelanto_2")
    private LocalDate fechaAdelanto2;

    @Column(name = "adelanto_3")
    private Double adelanto3;

    @Column(name = "fecha_adelanto_3")
    private LocalDate fechaAdelanto3;

    @Column(name = "adelanto_4")
    private Double adelanto4;

    @Column(name = "fecha_adelanto_4")
    private LocalDate fechaAdelanto4;

    @Column(name = "fecha_pago_saldo")
    private LocalDate fechaPagoSaldo;

    @Column(name = "proveedor_monto_detraccion")
    private Double proveedorMontoDetraccion;

    @Column(name = "proveedor_comision_detraccion")
    private Double proveedorComisionDetraccion;

    @Column(name = "proveedor_total_pagar")
    private Double proveedorTotalPagar;

    @Column(name = "proveedor_estado")
    private String proveedorEstado;

    @Column(name = "compra_factura")
    private String compraFactura;

    @Column(name = "compra_proveedor")
    private String compraProveedor;

    @Column(name = "compra_producto")
    private String compraProducto;

    @Column(name = "compra_total")
    private Double compraTotal;

    @Column(name = "compra_total_igv")
    private Double compraTotalIgv;

    @Column(name = "compra_estado")
    private String compraEstado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroOrdenVenta() {
        return numeroOrdenVenta;
    }

    public Evento numeroOrdenVenta(String numeroOrdenVenta) {
        this.numeroOrdenVenta = numeroOrdenVenta;
        return this;
    }

    public void setNumeroOrdenVenta(String numeroOrdenVenta) {
        this.numeroOrdenVenta = numeroOrdenVenta;
    }

    public String getClienteGuia() {
        return clienteGuia;
    }

    public Evento clienteGuia(String clienteGuia) {
        this.clienteGuia = clienteGuia;
        return this;
    }

    public void setClienteGuia(String clienteGuia) {
        this.clienteGuia = clienteGuia;
    }

    public String getClienteFactura() {
        return clienteFactura;
    }

    public Evento clienteFactura(String clienteFactura) {
        this.clienteFactura = clienteFactura;
        return this;
    }

    public void setClienteFactura(String clienteFactura) {
        this.clienteFactura = clienteFactura;
    }

    public String getClienteRuc() {
        return clienteRuc;
    }

    public Evento clienteRuc(String clienteRuc) {
        this.clienteRuc = clienteRuc;
        return this;
    }

    public void setClienteRuc(String clienteRuc) {
        this.clienteRuc = clienteRuc;
    }

    public String getClienteRazonSocial() {
        return clienteRazonSocial;
    }

    public Evento clienteRazonSocial(String clienteRazonSocial) {
        this.clienteRazonSocial = clienteRazonSocial;
        return this;
    }

    public void setClienteRazonSocial(String clienteRazonSocial) {
        this.clienteRazonSocial = clienteRazonSocial;
    }

    public String getClientePeso() {
        return clientePeso;
    }

    public Evento clientePeso(String clientePeso) {
        this.clientePeso = clientePeso;
        return this;
    }

    public void setClientePeso(String clientePeso) {
        this.clientePeso = clientePeso;
    }

    public Double getClienteMonto() {
        return clienteMonto;
    }

    public Evento clienteMonto(Double clienteMonto) {
        this.clienteMonto = clienteMonto;
        return this;
    }

    public void setClienteMonto(Double clienteMonto) {
        this.clienteMonto = clienteMonto;
    }

    public Double getClienteTotal() {
        return clienteTotal;
    }

    public Evento clienteTotal(Double clienteTotal) {
        this.clienteTotal = clienteTotal;
        return this;
    }

    public void setClienteTotal(Double clienteTotal) {
        this.clienteTotal = clienteTotal;
    }

    public Double getClienteAdelanto() {
        return clienteAdelanto;
    }

    public Evento clienteAdelanto(Double clienteAdelanto) {
        this.clienteAdelanto = clienteAdelanto;
        return this;
    }

    public void setClienteAdelanto(Double clienteAdelanto) {
        this.clienteAdelanto = clienteAdelanto;
    }

    public LocalDate getFechaEmisionFactura() {
        return fechaEmisionFactura;
    }

    public Evento fechaEmisionFactura(LocalDate fechaEmisionFactura) {
        this.fechaEmisionFactura = fechaEmisionFactura;
        return this;
    }

    public void setFechaEmisionFactura(LocalDate fechaEmisionFactura) {
        this.fechaEmisionFactura = fechaEmisionFactura;
    }

    public LocalDate getFechaVencimientoFactura() {
        return fechaVencimientoFactura;
    }

    public Evento fechaVencimientoFactura(LocalDate fechaVencimientoFactura) {
        this.fechaVencimientoFactura = fechaVencimientoFactura;
        return this;
    }

    public void setFechaVencimientoFactura(LocalDate fechaVencimientoFactura) {
        this.fechaVencimientoFactura = fechaVencimientoFactura;
    }

    public String getPlazo() {
        return plazo;
    }

    public Evento plazo(String plazo) {
        this.plazo = plazo;
        return this;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public Evento fechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        return this;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getLetra() {
        return letra;
    }

    public Evento letra(String letra) {
        this.letra = letra;
        return this;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getEstadoLetra() {
        return estadoLetra;
    }

    public Evento estadoLetra(String estadoLetra) {
        this.estadoLetra = estadoLetra;
        return this;
    }

    public void setEstadoLetra(String estadoLetra) {
        this.estadoLetra = estadoLetra;
    }

    public LocalDate getFechaEmisionLetra() {
        return fechaEmisionLetra;
    }

    public Evento fechaEmisionLetra(LocalDate fechaEmisionLetra) {
        this.fechaEmisionLetra = fechaEmisionLetra;
        return this;
    }

    public void setFechaEmisionLetra(LocalDate fechaEmisionLetra) {
        this.fechaEmisionLetra = fechaEmisionLetra;
    }

    public String getPlazoPago() {
        return plazoPago;
    }

    public Evento plazoPago(String plazoPago) {
        this.plazoPago = plazoPago;
        return this;
    }

    public void setPlazoPago(String plazoPago) {
        this.plazoPago = plazoPago;
    }

    public LocalDate getVencimientoPago() {
        return vencimientoPago;
    }

    public Evento vencimientoPago(LocalDate vencimientoPago) {
        this.vencimientoPago = vencimientoPago;
        return this;
    }

    public void setVencimientoPago(LocalDate vencimientoPago) {
        this.vencimientoPago = vencimientoPago;
    }

    public Double getSaldoCobrar() {
        return saldoCobrar;
    }

    public Evento saldoCobrar(Double saldoCobrar) {
        this.saldoCobrar = saldoCobrar;
        return this;
    }

    public void setSaldoCobrar(Double saldoCobrar) {
        this.saldoCobrar = saldoCobrar;
    }

    public Double getMontoDetraccion() {
        return montoDetraccion;
    }

    public Evento montoDetraccion(Double montoDetraccion) {
        this.montoDetraccion = montoDetraccion;
        return this;
    }

    public void setMontoDetraccion(Double montoDetraccion) {
        this.montoDetraccion = montoDetraccion;
    }

    public String getClienteEstado() {
        return clienteEstado;
    }

    public Evento clienteEstado(String clienteEstado) {
        this.clienteEstado = clienteEstado;
        return this;
    }

    public void setClienteEstado(String clienteEstado) {
        this.clienteEstado = clienteEstado;
    }

    public String getProveedorGuia() {
        return proveedorGuia;
    }

    public Evento proveedorGuia(String proveedorGuia) {
        this.proveedorGuia = proveedorGuia;
        return this;
    }

    public void setProveedorGuia(String proveedorGuia) {
        this.proveedorGuia = proveedorGuia;
    }

    public String getProveedorFactura() {
        return proveedorFactura;
    }

    public Evento proveedorFactura(String proveedorFactura) {
        this.proveedorFactura = proveedorFactura;
        return this;
    }

    public void setProveedorFactura(String proveedorFactura) {
        this.proveedorFactura = proveedorFactura;
    }

    public String getProveedorRuc() {
        return proveedorRuc;
    }

    public Evento proveedorRuc(String proveedorRuc) {
        this.proveedorRuc = proveedorRuc;
        return this;
    }

    public void setProveedorRuc(String proveedorRuc) {
        this.proveedorRuc = proveedorRuc;
    }

    public String getProveedorRazonSocial() {
        return proveedorRazonSocial;
    }

    public Evento proveedorRazonSocial(String proveedorRazonSocial) {
        this.proveedorRazonSocial = proveedorRazonSocial;
        return this;
    }

    public void setProveedorRazonSocial(String proveedorRazonSocial) {
        this.proveedorRazonSocial = proveedorRazonSocial;
    }

    public String getMaterialTransporte() {
        return materialTransporte;
    }

    public Evento materialTransporte(String materialTransporte) {
        this.materialTransporte = materialTransporte;
        return this;
    }

    public void setMaterialTransporte(String materialTransporte) {
        this.materialTransporte = materialTransporte;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public Evento fechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
        return this;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Double getMontoServicio() {
        return montoServicio;
    }

    public Evento montoServicio(Double montoServicio) {
        this.montoServicio = montoServicio;
        return this;
    }

    public void setMontoServicio(Double montoServicio) {
        this.montoServicio = montoServicio;
    }

    public Double getTotal() {
        return total;
    }

    public Evento total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAdelanto1() {
        return adelanto1;
    }

    public Evento adelanto1(Double adelanto1) {
        this.adelanto1 = adelanto1;
        return this;
    }

    public void setAdelanto1(Double adelanto1) {
        this.adelanto1 = adelanto1;
    }

    public LocalDate getFechaAdelanto1() {
        return fechaAdelanto1;
    }

    public Evento fechaAdelanto1(LocalDate fechaAdelanto1) {
        this.fechaAdelanto1 = fechaAdelanto1;
        return this;
    }

    public void setFechaAdelanto1(LocalDate fechaAdelanto1) {
        this.fechaAdelanto1 = fechaAdelanto1;
    }

    public Double getAdelanto2() {
        return adelanto2;
    }

    public Evento adelanto2(Double adelanto2) {
        this.adelanto2 = adelanto2;
        return this;
    }

    public void setAdelanto2(Double adelanto2) {
        this.adelanto2 = adelanto2;
    }

    public LocalDate getFechaAdelanto2() {
        return fechaAdelanto2;
    }

    public Evento fechaAdelanto2(LocalDate fechaAdelanto2) {
        this.fechaAdelanto2 = fechaAdelanto2;
        return this;
    }

    public void setFechaAdelanto2(LocalDate fechaAdelanto2) {
        this.fechaAdelanto2 = fechaAdelanto2;
    }

    public Double getAdelanto3() {
        return adelanto3;
    }

    public Evento adelanto3(Double adelanto3) {
        this.adelanto3 = adelanto3;
        return this;
    }

    public void setAdelanto3(Double adelanto3) {
        this.adelanto3 = adelanto3;
    }

    public LocalDate getFechaAdelanto3() {
        return fechaAdelanto3;
    }

    public Evento fechaAdelanto3(LocalDate fechaAdelanto3) {
        this.fechaAdelanto3 = fechaAdelanto3;
        return this;
    }

    public void setFechaAdelanto3(LocalDate fechaAdelanto3) {
        this.fechaAdelanto3 = fechaAdelanto3;
    }

    public Double getAdelanto4() {
        return adelanto4;
    }

    public Evento adelanto4(Double adelanto4) {
        this.adelanto4 = adelanto4;
        return this;
    }

    public void setAdelanto4(Double adelanto4) {
        this.adelanto4 = adelanto4;
    }

    public LocalDate getFechaAdelanto4() {
        return fechaAdelanto4;
    }

    public Evento fechaAdelanto4(LocalDate fechaAdelanto4) {
        this.fechaAdelanto4 = fechaAdelanto4;
        return this;
    }

    public void setFechaAdelanto4(LocalDate fechaAdelanto4) {
        this.fechaAdelanto4 = fechaAdelanto4;
    }

    public LocalDate getFechaPagoSaldo() {
        return fechaPagoSaldo;
    }

    public Evento fechaPagoSaldo(LocalDate fechaPagoSaldo) {
        this.fechaPagoSaldo = fechaPagoSaldo;
        return this;
    }

    public void setFechaPagoSaldo(LocalDate fechaPagoSaldo) {
        this.fechaPagoSaldo = fechaPagoSaldo;
    }

    public Double getProveedorMontoDetraccion() {
        return proveedorMontoDetraccion;
    }

    public Evento proveedorMontoDetraccion(Double proveedorMontoDetraccion) {
        this.proveedorMontoDetraccion = proveedorMontoDetraccion;
        return this;
    }

    public void setProveedorMontoDetraccion(Double proveedorMontoDetraccion) {
        this.proveedorMontoDetraccion = proveedorMontoDetraccion;
    }

    public Double getProveedorComisionDetraccion() {
        return proveedorComisionDetraccion;
    }

    public Evento proveedorComisionDetraccion(Double proveedorComisionDetraccion) {
        this.proveedorComisionDetraccion = proveedorComisionDetraccion;
        return this;
    }

    public void setProveedorComisionDetraccion(Double proveedorComisionDetraccion) {
        this.proveedorComisionDetraccion = proveedorComisionDetraccion;
    }

    public Double getProveedorTotalPagar() {
        return proveedorTotalPagar;
    }

    public Evento proveedorTotalPagar(Double proveedorTotalPagar) {
        this.proveedorTotalPagar = proveedorTotalPagar;
        return this;
    }

    public void setProveedorTotalPagar(Double proveedorTotalPagar) {
        this.proveedorTotalPagar = proveedorTotalPagar;
    }

    public String getProveedorEstado() {
        return proveedorEstado;
    }

    public Evento proveedorEstado(String proveedorEstado) {
        this.proveedorEstado = proveedorEstado;
        return this;
    }

    public void setProveedorEstado(String proveedorEstado) {
        this.proveedorEstado = proveedorEstado;
    }

    public String getCompraFactura() {
        return compraFactura;
    }

    public Evento compraFactura(String compraFactura) {
        this.compraFactura = compraFactura;
        return this;
    }

    public void setCompraFactura(String compraFactura) {
        this.compraFactura = compraFactura;
    }

    public String getCompraProveedor() {
        return compraProveedor;
    }

    public Evento compraProveedor(String compraProveedor) {
        this.compraProveedor = compraProveedor;
        return this;
    }

    public void setCompraProveedor(String compraProveedor) {
        this.compraProveedor = compraProveedor;
    }

    public String getCompraProducto() {
        return compraProducto;
    }

    public Evento compraProducto(String compraProducto) {
        this.compraProducto = compraProducto;
        return this;
    }

    public void setCompraProducto(String compraProducto) {
        this.compraProducto = compraProducto;
    }

    public Double getCompraTotal() {
        return compraTotal;
    }

    public Evento compraTotal(Double compraTotal) {
        this.compraTotal = compraTotal;
        return this;
    }

    public void setCompraTotal(Double compraTotal) {
        this.compraTotal = compraTotal;
    }

    public Double getCompraTotalIgv() {
        return compraTotalIgv;
    }

    public Evento compraTotalIgv(Double compraTotalIgv) {
        this.compraTotalIgv = compraTotalIgv;
        return this;
    }

    public void setCompraTotalIgv(Double compraTotalIgv) {
        this.compraTotalIgv = compraTotalIgv;
    }

    public String getCompraEstado() {
        return compraEstado;
    }

    public Evento compraEstado(String compraEstado) {
        this.compraEstado = compraEstado;
        return this;
    }

    public void setCompraEstado(String compraEstado) {
        this.compraEstado = compraEstado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Evento evento = (Evento) o;
        if(evento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, evento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Evento{" +
            "id=" + id +
            ", numeroOrdenVenta='" + numeroOrdenVenta + "'" +
            ", clienteGuia='" + clienteGuia + "'" +
            ", clienteFactura='" + clienteFactura + "'" +
            ", clienteRuc='" + clienteRuc + "'" +
            ", clienteRazonSocial='" + clienteRazonSocial + "'" +
            ", clientePeso='" + clientePeso + "'" +
            ", clienteMonto='" + clienteMonto + "'" +
            ", clienteTotal='" + clienteTotal + "'" +
            ", clienteAdelanto='" + clienteAdelanto + "'" +
            ", fechaEmisionFactura='" + fechaEmisionFactura + "'" +
            ", fechaVencimientoFactura='" + fechaVencimientoFactura + "'" +
            ", plazo='" + plazo + "'" +
            ", fechaEntrega='" + fechaEntrega + "'" +
            ", letra='" + letra + "'" +
            ", estadoLetra='" + estadoLetra + "'" +
            ", fechaEmisionLetra='" + fechaEmisionLetra + "'" +
            ", plazoPago='" + plazoPago + "'" +
            ", vencimientoPago='" + vencimientoPago + "'" +
            ", saldoCobrar='" + saldoCobrar + "'" +
            ", montoDetraccion='" + montoDetraccion + "'" +
            ", clienteEstado='" + clienteEstado + "'" +
            ", proveedorGuia='" + proveedorGuia + "'" +
            ", proveedorFactura='" + proveedorFactura + "'" +
            ", proveedorRuc='" + proveedorRuc + "'" +
            ", proveedorRazonSocial='" + proveedorRazonSocial + "'" +
            ", materialTransporte='" + materialTransporte + "'" +
            ", fechaSalida='" + fechaSalida + "'" +
            ", montoServicio='" + montoServicio + "'" +
            ", total='" + total + "'" +
            ", adelanto1='" + adelanto1 + "'" +
            ", fechaAdelanto1='" + fechaAdelanto1 + "'" +
            ", adelanto2='" + adelanto2 + "'" +
            ", fechaAdelanto2='" + fechaAdelanto2 + "'" +
            ", adelanto3='" + adelanto3 + "'" +
            ", fechaAdelanto3='" + fechaAdelanto3 + "'" +
            ", adelanto4='" + adelanto4 + "'" +
            ", fechaAdelanto4='" + fechaAdelanto4 + "'" +
            ", fechaPagoSaldo='" + fechaPagoSaldo + "'" +
            ", proveedorMontoDetraccion='" + proveedorMontoDetraccion + "'" +
            ", proveedorComisionDetraccion='" + proveedorComisionDetraccion + "'" +
            ", proveedorTotalPagar='" + proveedorTotalPagar + "'" +
            ", proveedorEstado='" + proveedorEstado + "'" +
            ", compraFactura='" + compraFactura + "'" +
            ", compraProveedor='" + compraProveedor + "'" +
            ", compraProducto='" + compraProducto + "'" +
            ", compraTotal='" + compraTotal + "'" +
            ", compraTotalIgv='" + compraTotalIgv + "'" +
            ", compraEstado='" + compraEstado + "'" +
            '}';
    }
}
