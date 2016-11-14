package com.hmt.carga.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TipoUnidad.
 */
@Entity
@Table(name = "tipo_unidad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoUnidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "configuracion", nullable = false)
    private String configuracion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoUnidad nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConfiguracion() {
        return configuracion;
    }

    public TipoUnidad configuracion(String configuracion) {
        this.configuracion = configuracion;
        return this;
    }

    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TipoUnidad tipoUnidad = (TipoUnidad) o;
        if(tipoUnidad.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipoUnidad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoUnidad{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", configuracion='" + configuracion + "'" +
            '}';
    }
}
