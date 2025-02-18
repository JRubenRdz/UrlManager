package com.urlmanager.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urlmanager.entity.Entorno;
import com.urlmanager.repository.EntornoRepository;
import com.urlmanager.security.JWTUtils;

import jakarta.transaction.Transactional;

@Service
public class EntornoService {
	
	@Autowired
	private EntornoRepository entornoRepository;
	
	@Autowired
	private JWTUtils JWTUtils;
	
	
	@Transactional
	public Entorno saveEntorno(Entorno entorno) {
		entorno.setUrls(new HashSet<>());
		return entornoRepository.save(entorno);
	}
	
	
	@Transactional
	public Entorno updateEntorno(Entorno entornoU) {
		Entorno entorno = JWTUtils.userLogin();
		if (entorno != null) {
			entorno.setName(entornoU.getName());
			entorno.setUrls(entornoU.getUrls());

			return entornoRepository.save(entorno);
		}
		return null;
	}
	
	public List<Entorno> getAllEntornos() {
		return entornoRepository.findAll();
	}
	
	public Optional<Entorno> getEntornoById(int id) {
		return entornoRepository.findById(id);
	}
	
	public Optional<Entorno> findByName(String name) {
		return entornoRepository.findByName(name);
	}
	
	@Transactional
	public boolean deleteEntorno() {
		Entorno entorno = JWTUtils.userLogin();
		if (entorno != null) {
			entornoRepository.deleteById(entorno.getId());
			return true;
		}
		return false;
	}
}
