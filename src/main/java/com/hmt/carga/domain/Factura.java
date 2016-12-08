package com.hmt.carga.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Factura.
 */
@Entity
@Table(name = "factura")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @NotNull
    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    @NotNull
    @Column(name = "igv", nullable = false)
    private Double igv;

    @NotNull
    @Column(name = "precio_total", nullable = false)
    private Double precioTotal;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @ManyToOne
    @NotNull
    private Cliente cliente;

    @ManyToOne
    @NotNull
    private Servicio servicio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public Factura precioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
        return this;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Factura cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioBase() {
        return precioBase;
    }

    public Factura precioBase(Double precioBase) {
        this.precioBase = precioBase;
        return this;
    }

    public void setPrecioBase(Double precioBase) {
        this.precioBase = precioBase;
    }

    public Double getIgv() {
        return igv;
    }

    public Factura igv(Double igv) {
        this.igv = igv;
        return this;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public Factura precioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
        return this;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getCodigo() {
        return codigo;
    }

    public Factura codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Factura cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public Factura servicio(Servicio servicio) {
        this.servicio = servicio;
        return this;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Factura factura = (Factura) o;
        if(factura.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, factura.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Factura{" +
            "id=" + id +
            ", precioUnitario='" + precioUnitario + "'" +
            ", cantidad='" + cantidad + "'" +
            ", precioBase='" + precioBase + "'" +
            ", igv='" + igv + "'" +
            ", precioTotal='" + precioTotal + "'" +
            ", codigo='" + codigo + "'" +
            '}';
    }
}
