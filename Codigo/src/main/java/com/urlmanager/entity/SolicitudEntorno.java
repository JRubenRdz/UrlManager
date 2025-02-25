package com.urlmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class SolicitudEntorno extends DomainEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    @NotNull
    private String nombreEntorno;   

    @ManyToOne
    private Cliente cliente;

    public SolicitudEntorno() {
        super();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public String getNombreEntorno() {
        return nombreEntorno;
    }

    public void setNombreEntorno(String nombreEntorno) {
        this.nombreEntorno = nombreEntorno;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}