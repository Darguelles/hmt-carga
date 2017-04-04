package com.hmt.carga.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Cotizacion.
 */
@Entity
@Table(name = "cotizacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cotizacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonSerialize
    @JsonDeserialize
    @Transient
    private String email;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "origen", nullable = false)
    private String origen;

    @NotNull
    @Column(name = "destino", nullable = false)
    private String destino;

    @NotNull
    @Column(name = "mercaderia", nullable = false)
    private String mercaderia;

    @NotNull
    @Column(name = "precio", nullable = false)
    private Double precio;

    @NotNull
    @Column(name = "moneda", nullable = false)
    private Integer moneda;

    @NotNull
    @Column(name = "estado", nullable = false)
    private String estado;

    @NotNull
    @Column(name = "nombre_receptor", nullable = false)
    private String nombreReceptor;

    @NotNull
    @Column(name = "cargo_receptor", nullable = false)
    private String cargoReceptor;

    @NotNull
    @Column(name = "condiciones", nullable = false)
    private String condiciones;

    @ManyToOne
    @NotNull
    private Cliente cliente;

    @ManyToOne
    @NotNull
    private Servicio servicio;

    @ManyToOne
    @NotNull
    private TipoUnidad tipoUnidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Cotizacion fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getOrigen() {
        return origen;
    }

    public Cotizacion origen(String origen) {
        this.origen = origen;
        return this;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public Cotizacion destino(String destino) {
        this.destino = destino;
        return this;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getMercaderia() {
        return mercaderia;
    }

    public Cotizacion mercaderia(String mercaderia) {
        this.mercaderia = mercaderia;
        return this;
    }

    public void setMercaderia(String mercaderia) {
        this.mercaderia = mercaderia;
    }

    public Double getPrecio() {
        return precio;
    }

    public Cotizacion precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getMoneda() {
        return moneda;
    }

    public Cotizacion moneda(Integer moneda) {
        this.moneda = moneda;
        return this;
    }

    public void setMoneda(Integer moneda) {
        this.moneda = moneda;
    }

    public String getEstado() {
        return estado;
    }

    public Cotizacion estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public Cotizacion nombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
        return this;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    public String getCargoReceptor() {
        return cargoReceptor;
    }

    public Cotizacion cargoReceptor(String cargoReceptor) {
        this.cargoReceptor = cargoReceptor;
        return this;
    }

    public void setCargoReceptor(String cargoReceptor) {
        this.cargoReceptor = cargoReceptor;
    }

    public String getCondiciones() {
        return condiciones;
    }

    public Cotizacion condiciones(String condiciones) {
        this.condiciones = condiciones;
        return this;
    }

    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Cotizacion cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public Cotizacion servicio(Servicio servicio) {
        this.servicio = servicio;
        return this;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public TipoUnidad getTipoUnidad() {
        return tipoUnidad;
    }

    public Cotizacion tipoUnidad(TipoUnidad tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
        return this;
    }

    public void setTipoUnidad(TipoUnidad tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cotizacion cotizacion = (Cotizacion) o;
        if(cotizacion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cotizacion.id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cotizacion{" +
            "id=" + id +
            ", fecha='" + fecha + "'" +
            ", origen='" + origen + "'" +
            ", destino='" + destino + "'" +
            ", mercaderia='" + mercaderia + "'" +
            ", precio='" + precio + "'" +
            ", moneda='" + moneda + "'" +
            ", estado='" + estado + "'" +
            ", nombreReceptor='" + nombreReceptor + "'" +
            ", cargoReceptor='" + cargoReceptor + "'" +
            ", condiciones='" + condiciones + "'" +
            '}';
    }
}
