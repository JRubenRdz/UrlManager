package com.urlmanager.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Url extends DomainEntity {
    
    @NotBlank
    private String url;
    
    @NotBlank
    private String descripcion;
    
    @NotNull
    private LocalDateTime fechaCreacion;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "entorno_id", nullable = false)
    private Entorno entorno;

    public Url(String url, String descripcion, LocalDateTime fechaCreacion, Entorno entorno) {
        super();
        this.url = url;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.entorno = entorno;
    }
    
    public Url() {
        super();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Entorno getEntorno() {
        return entorno;
    }

    public void setEntorno(Entorno entorno) {
        this.entorno = entorno;
    }
}