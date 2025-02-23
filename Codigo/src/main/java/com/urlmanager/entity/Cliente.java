package com.urlmanager.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente extends Actor {
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Entorno> entornos;

    public Cliente() {
        super();
    }

    public Set<Entorno> getEntornos() {
        return entornos;
    }

    public void setEntornos(Set<Entorno> entornos) {
        this.entornos = entornos;
    }
}