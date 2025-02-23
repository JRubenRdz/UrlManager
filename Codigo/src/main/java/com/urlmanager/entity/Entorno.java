package com.urlmanager.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Entorno extends DomainEntity {
    
    @NotNull
    private String name;
    
    @ManyToOne
    @JsonBackReference
    private Cliente cliente;
    
    @OneToMany(mappedBy = "entorno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Url> urls;

    public Entorno(String nombre, Set<Url> urls) {
        super();
        this.name = nombre;
        this.urls = urls;
    }
    
    public Entorno() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Url> getUrls() {
        return urls;
    }

    public void setUrls(Set<Url> urls) {
        this.urls = urls;
    }
    
    public Cliente getCliente() {
        return this.cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}