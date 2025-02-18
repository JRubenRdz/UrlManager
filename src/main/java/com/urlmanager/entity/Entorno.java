package com.urlmanager.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Entorno extends DomainEntity {
	
	@NotBlank
	private String name;
	
	@OneToMany(mappedBy = "entorno", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Url> urls;

	public Entorno(String name, Set<Url> urls) {
		super();
		this.name = name;
		this.urls = urls;
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
	
	
}
