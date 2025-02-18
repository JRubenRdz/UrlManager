package com.urlmanager.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Url extends DomainEntity {
	
	@NotBlank
	private String url;
	@NotBlank
    private String descripcion;
	@NotBlank
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "entorno_id", nullable = false)
    private Entorno entorno;

	public Url(String url, String descripcion, LocalDateTime fechaCreacion, Cliente cliente, Entorno entorno) {
		super();
		this.url = url;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.cliente = cliente;
		this.entorno = entorno;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Entorno getEntorno() {
		return entorno;
	}

	public void setEntorno(Entorno entorno) {
		this.entorno = entorno;
	}
    
    
	
}
