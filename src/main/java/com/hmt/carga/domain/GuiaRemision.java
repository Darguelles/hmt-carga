package com.hmt.carga.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A GuiaRemision.
 */
@Entity
@Table(name = "guia_remision")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GuiaRemision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @NotNull
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "fecha_traslado")
    private LocalDate fechaTraslado;

    @Column(name = "cantidad")
    private Double cantidad;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "costo_minimo")
    private Double costoMinimo;

    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    private ZonedDateTime fechaIngreso;

    @NotNull
    @Column(name = "fecha_salida", nullable = false)
    private ZonedDateTime fechaSalida;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
//    @NotNull
    private Cotizacion cotizacion;

    @ManyToOne
    @NotNull
    private Proveedor proveedor;

    @ManyToOne
    @NotNull
    private Transporte transporte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public GuiaRemision codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public GuiaRemision fechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
        return this;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaTraslado() {
        return fechaTraslado;
    }

    public GuiaRemision fechaTraslado(LocalDate fechaTraslado) {
        this.fechaTraslado = fechaTraslado;
        return this;
    }

    public void setFechaTraslado(LocalDate fechaTraslado) {
        this.fechaTraslado = fechaTraslado;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public GuiaRemision cantidad(Double cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPeso() {
        return peso;
    }

    public GuiaRemision peso(Double peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public GuiaRemision unidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
        return this;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Double getCostoMinimo() {
        return costoMinimo;
    }

    public GuiaRemision costoMinimo(Double costoMinimo) {
        this.costoMinimo = costoMinimo;
        return this;
    }

    public void setCostoMinimo(Double costoMinimo) {
        this.costoMinimo = costoMinimo;
    }

    public ZonedDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public GuiaRemision fechaIngreso(ZonedDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
        return this;
    }

    public void setFechaIngreso(ZonedDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public ZonedDateTime getFechaSalida() {
        return fechaSalida;
    }

    public GuiaRemision fechaSalida(ZonedDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
        return this;
    }

    public void setFechaSalida(ZonedDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public GuiaRemision observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public GuiaRemision cotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
        return this;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public GuiaRemision proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public GuiaRemision transporte(Transporte transporte) {
        this.transporte = transporte;
        return this;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GuiaRemision guiaRemision = (GuiaRemision) o;
        if (guiaRemision.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, guiaRemision.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GuiaRemision{" +
            "id=" + id +
            ", codigo='" + codigo + "'" +
            ", fechaEmision='" + fechaEmision + "'" +
            ", fechaTraslado='" + fechaTraslado + "'" +
            ", cantidad='" + cantidad + "'" +
            ", peso='" + peso + "'" +
            ", unidadMedida='" + unidadMedida + "'" +
            ", costoMinimo='" + costoMinimo + "'" +
            ", fechaIngreso='" + fechaIngreso + "'" +
            ", fechaSalida='" + fechaSalida + "'" +
            ", observaciones='" + observaciones + "'" +
            '}';
    }
}
