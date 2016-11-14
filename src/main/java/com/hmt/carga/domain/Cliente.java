package com.hmt.carga.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ruc", nullable = false)
    private Long ruc;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private Integer telefono;

    @NotNull
    @Size(min = 5, max = 50)
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @NotNull
    @Column(name = "contacto", nullable = false)
    private String contacto;

    @Column(name = "descuento")
    private Double descuento;

    @ManyToOne
    @NotNull
    private FormaPago formaPago;

    @ManyToOne
    @NotNull
    private CondicionPago condicionPago;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRuc() {
        return ruc;
    }

    public Cliente ruc(Long ruc) {
        this.ruc = ruc;
        return this;
    }

    public void setRuc(Long ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public Cliente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Cliente direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public Cliente telefono(Integer telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public Cliente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacto() {
        return contacto;
    }

    public Cliente contacto(String contacto) {
        this.contacto = contacto;
        return this;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Double getDescuento() {
        return descuento;
    }

    public Cliente descuento(Double descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public Cliente formaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
        return this;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public CondicionPago getCondicionPago() {
        return condicionPago;
    }

    public Cliente condicionPago(CondicionPago condicionPago) {
        this.condicionPago = condicionPago;
        return this;
    }

    public void setCondicionPago(CondicionPago condicionPago) {
        this.condicionPago = condicionPago;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        if(cliente.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + id +
            ", ruc='" + ruc + "'" +
            ", nombre='" + nombre + "'" +
            ", direccion='" + direccion + "'" +
            ", telefono='" + telefono + "'" +
            ", email='" + email + "'" +
            ", contacto='" + contacto + "'" +
            ", descuento='" + descuento + "'" +
            '}';
    }
}
