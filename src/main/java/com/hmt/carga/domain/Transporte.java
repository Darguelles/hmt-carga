package com.hmt.carga.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Transporte.
 */
@Entity
@Table(name = "transporte")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transporte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "tracto")
    private String tracto;

    @Column(name = "carreta")
    private String carreta;

    @Column(name = "placa_tracto")
    private String placaTracto;

    @Column(name = "placa_carreta")
    private String placaCarreta;

    @Column(name = "largo_carreta")
    private Double largoCarreta;

    @Column(name = "ancho_carreta")
    private Double anchoCarreta;

    @Column(name = "alto_carreta")
    private Double altoCarreta;

    @Column(name = "carga_util")
    private Double cargaUtil;

    @Column(name = "registro_matpel")
    private Boolean registroMatpel;

    @Column(name = "gps")
    private Boolean gps;

    @Column(name = "ano_fabricacion")
    private Integer anoFabricacion;

    @Column(name = "unidad_propia")
    private Boolean unidadPropia;

    @Column(name = "kilometraje")
    private Double kilometraje;

    @Column(name = "fecha_revision_tecnica")
    private LocalDate fechaRevisionTecnica;

    @Column(name = "soat")
    private String soat;

    @Column(name = "fecha_vencimiento_soat")
    private LocalDate fechaVencimientoSoat;

    @NotNull
    @Column(name = "modelo", nullable = false)
    private String modelo;

    @ManyToOne
    @NotNull
    private TipoUnidad tipoUnidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public Transporte marca(String marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTracto() {
        return tracto;
    }

    public Transporte tracto(String tracto) {
        this.tracto = tracto;
        return this;
    }

    public void setTracto(String tracto) {
        this.tracto = tracto;
    }

    public String getCarreta() {
        return carreta;
    }

    public Transporte carreta(String carreta) {
        this.carreta = carreta;
        return this;
    }

    public void setCarreta(String carreta) {
        this.carreta = carreta;
    }

    public String getPlacaTracto() {
        return placaTracto;
    }

    public Transporte placaTracto(String placaTracto) {
        this.placaTracto = placaTracto;
        return this;
    }

    public void setPlacaTracto(String placaTracto) {
        this.placaTracto = placaTracto;
    }

    public String getPlacaCarreta() {
        return placaCarreta;
    }

    public Transporte placaCarreta(String placaCarreta) {
        this.placaCarreta = placaCarreta;
        return this;
    }

    public void setPlacaCarreta(String placaCarreta) {
        this.placaCarreta = placaCarreta;
    }

    public Double getLargoCarreta() {
        return largoCarreta;
    }

    public Transporte largoCarreta(Double largoCarreta) {
        this.largoCarreta = largoCarreta;
        return this;
    }

    public void setLargoCarreta(Double largoCarreta) {
        this.largoCarreta = largoCarreta;
    }

    public Double getAnchoCarreta() {
        return anchoCarreta;
    }

    public Transporte anchoCarreta(Double anchoCarreta) {
        this.anchoCarreta = anchoCarreta;
        return this;
    }

    public void setAnchoCarreta(Double anchoCarreta) {
        this.anchoCarreta = anchoCarreta;
    }

    public Double getAltoCarreta() {
        return altoCarreta;
    }

    public Transporte altoCarreta(Double altoCarreta) {
        this.altoCarreta = altoCarreta;
        return this;
    }

    public void setAltoCarreta(Double altoCarreta) {
        this.altoCarreta = altoCarreta;
    }

    public Double getCargaUtil() {
        return cargaUtil;
    }

    public Transporte cargaUtil(Double cargaUtil) {
        this.cargaUtil = cargaUtil;
        return this;
    }

    public void setCargaUtil(Double cargaUtil) {
        this.cargaUtil = cargaUtil;
    }

    public Boolean isRegistroMatpel() {
        return registroMatpel;
    }

    public Transporte registroMatpel(Boolean registroMatpel) {
        this.registroMatpel = registroMatpel;
        return this;
    }

    public void setRegistroMatpel(Boolean registroMatpel) {
        this.registroMatpel = registroMatpel;
    }

    public Boolean isGps() {
        return gps;
    }

    public Transporte gps(Boolean gps) {
        this.gps = gps;
        return this;
    }

    public void setGps(Boolean gps) {
        this.gps = gps;
    }

    public Integer getAnoFabricacion() {
        return anoFabricacion;
    }

    public Transporte anoFabricacion(Integer anoFabricacion) {
        this.anoFabricacion = anoFabricacion;
        return this;
    }

    public void setAnoFabricacion(Integer anoFabricacion) {
        this.anoFabricacion = anoFabricacion;
    }

    public Boolean isUnidadPropia() {
        return unidadPropia;
    }

    public Transporte unidadPropia(Boolean unidadPropia) {
        this.unidadPropia = unidadPropia;
        return this;
    }

    public void setUnidadPropia(Boolean unidadPropia) {
        this.unidadPropia = unidadPropia;
    }

    public Double getKilometraje() {
        return kilometraje;
    }

    public Transporte kilometraje(Double kilometraje) {
        this.kilometraje = kilometraje;
        return this;
    }

    public void setKilometraje(Double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public LocalDate getFechaRevisionTecnica() {
        return fechaRevisionTecnica;
    }

    public Transporte fechaRevisionTecnica(LocalDate fechaRevisionTecnica) {
        this.fechaRevisionTecnica = fechaRevisionTecnica;
        return this;
    }

    public void setFechaRevisionTecnica(LocalDate fechaRevisionTecnica) {
        this.fechaRevisionTecnica = fechaRevisionTecnica;
    }

    public String getSoat() {
        return soat;
    }

    public Transporte soat(String soat) {
        this.soat = soat;
        return this;
    }

    public void setSoat(String soat) {
        this.soat = soat;
    }

    public LocalDate getFechaVencimientoSoat() {
        return fechaVencimientoSoat;
    }

    public Transporte fechaVencimientoSoat(LocalDate fechaVencimientoSoat) {
        this.fechaVencimientoSoat = fechaVencimientoSoat;
        return this;
    }

    public void setFechaVencimientoSoat(LocalDate fechaVencimientoSoat) {
        this.fechaVencimientoSoat = fechaVencimientoSoat;
    }

    public String getModelo() {
        return modelo;
    }

    public Transporte modelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public TipoUnidad getTipoUnidad() {
        return tipoUnidad;
    }

    public Transporte tipoUnidad(TipoUnidad tipoUnidad) {
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
        Transporte transporte = (Transporte) o;
        if(transporte.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transporte.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transporte{" +
            "id=" + id +
            ", marca='" + marca + "'" +
            ", tracto='" + tracto + "'" +
            ", carreta='" + carreta + "'" +
            ", placaTracto='" + placaTracto + "'" +
            ", placaCarreta='" + placaCarreta + "'" +
            ", largoCarreta='" + largoCarreta + "'" +
            ", anchoCarreta='" + anchoCarreta + "'" +
            ", altoCarreta='" + altoCarreta + "'" +
            ", cargaUtil='" + cargaUtil + "'" +
            ", registroMatpel='" + registroMatpel + "'" +
            ", gps='" + gps + "'" +
            ", anoFabricacion='" + anoFabricacion + "'" +
            ", unidadPropia='" + unidadPropia + "'" +
            ", kilometraje='" + kilometraje + "'" +
            ", fechaRevisionTecnica='" + fechaRevisionTecnica + "'" +
            ", soat='" + soat + "'" +
            ", fechaVencimientoSoat='" + fechaVencimientoSoat + "'" +
            ", modelo='" + modelo + "'" +
            '}';
    }
}
