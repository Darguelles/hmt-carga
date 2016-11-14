package com.hmt.carga.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Proveedor.
 */
@Entity
@Table(name = "proveedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Proveedor implements Serializable {

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
    @Column(name = "telefono", nullable = false)
    private Integer telefono;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "contacto", nullable = false)
    private String contacto;

    @Column(name = "email")
    private String email;

    @Column(name = "banco")
    private String banco;

    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @Column(name = "numero_cuenta_interbancario")
    private String numeroCuentaInterbancario;

    @Column(name = "numero_cuenta_detraccion")
    private String numeroCuentaDetraccion;

    @Column(name = "matpel")
    private String matpel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRuc() {
        return ruc;
    }

    public Proveedor ruc(Long ruc) {
        this.ruc = ruc;
        return this;
    }

    public void setRuc(Long ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public Proveedor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public Proveedor telefono(Integer telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public Proveedor direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public Proveedor contacto(String contacto) {
        this.contacto = contacto;
        return this;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public Proveedor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBanco() {
        return banco;
    }

    public Proveedor banco(String banco) {
        this.banco = banco;
        return this;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public Proveedor numeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        return this;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNumeroCuentaInterbancario() {
        return numeroCuentaInterbancario;
    }

    public Proveedor numeroCuentaInterbancario(String numeroCuentaInterbancario) {
        this.numeroCuentaInterbancario = numeroCuentaInterbancario;
        return this;
    }

    public void setNumeroCuentaInterbancario(String numeroCuentaInterbancario) {
        this.numeroCuentaInterbancario = numeroCuentaInterbancario;
    }

    public String getNumeroCuentaDetraccion() {
        return numeroCuentaDetraccion;
    }

    public Proveedor numeroCuentaDetraccion(String numeroCuentaDetraccion) {
        this.numeroCuentaDetraccion = numeroCuentaDetraccion;
        return this;
    }

    public void setNumeroCuentaDetraccion(String numeroCuentaDetraccion) {
        this.numeroCuentaDetraccion = numeroCuentaDetraccion;
    }

    public String getMatpel() {
        return matpel;
    }

    public Proveedor matpel(String matpel) {
        this.matpel = matpel;
        return this;
    }

    public void setMatpel(String matpel) {
        this.matpel = matpel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Proveedor proveedor = (Proveedor) o;
        if(proveedor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, proveedor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Proveedor{" +
            "id=" + id +
            ", ruc='" + ruc + "'" +
            ", nombre='" + nombre + "'" +
            ", telefono='" + telefono + "'" +
            ", direccion='" + direccion + "'" +
            ", contacto='" + contacto + "'" +
            ", email='" + email + "'" +
            ", banco='" + banco + "'" +
            ", numeroCuenta='" + numeroCuenta + "'" +
            ", numeroCuentaInterbancario='" + numeroCuentaInterbancario + "'" +
            ", numeroCuentaDetraccion='" + numeroCuentaDetraccion + "'" +
            ", matpel='" + matpel + "'" +
            '}';
    }
}
