package com.urlmanager.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Entorno extends DomainEntity {
	
	@NotBlank
	private String nombre;
	
	@OneToMany(mappedBy = "entorno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Url> urls = new ArrayList<>();

	public Entorno(String nombre, List<Url> urls) {
		super();
		this.nombre = nombre;
		this.urls = urls;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Url> getUrls() {
		return urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}
	
	
}
