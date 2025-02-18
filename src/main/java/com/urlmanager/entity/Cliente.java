package com.urlmanager.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente extends Actor{
	
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Url> urls;

	public Cliente() {
		super();
	}

	public Set<Url> getUrls() {
		return urls;
	}

	public void setUrls(Set<Url> urls) {
		this.urls = urls;
	}
}