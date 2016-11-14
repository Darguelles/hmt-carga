package com.hmt.carga.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CondicionPago.
 */
@Entity
@Table(name = "condicion_pago")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CondicionPago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public CondicionPago nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CondicionPago condicionPago = (CondicionPago) o;
        if(condicionPago.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, condicionPago.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CondicionPago{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
