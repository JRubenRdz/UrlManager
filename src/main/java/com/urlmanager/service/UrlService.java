package com.urlmanager.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.urlmanager.entity.Cliente;
import com.urlmanager.entity.Url;
import com.urlmanager.repository.UrlRepository;
import com.urlmanager.security.JWTUtils;

import jakarta.transaction.Transactional;


public class UrlService {
	
	@Autowired
	private UrlRepository urlRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private JWTUtils JWTUtils;
	
	public Set<Url> getAllUrlsByCliente() {
		Cliente cliente = JWTUtils.userLogin();
		return cliente.getUrls();
	}
	
	public Url getUrlById(int id) {
		Optional<Url> urlO = urlRepository.findById(id);
		if (urlO.isPresent()) {
			Object userLogin = JWTUtils.userLogin();
			if (userLogin instanceof Cliente) {
				Cliente cliente = (Cliente) userLogin;
				cliente.getUrls().contains(urlO.get());
				return urlO.get();
			}
		}
		return null;
	}
	
	@Transactional
	public Url save(Url u, int idCli) {
		Url res = null;
		Optional<Cliente> clienteO = clienteService.getClienteById(idCli);
		if (!clienteO.isEmpty()) {
			u.setFechaCreacion(LocalDateTime.now());

			res = urlRepository.save(u);

			Cliente cliente = clienteO.get();
			cliente.getUrls().add(u);
			clienteService.saveCliente(cliente);
		}
		return res;
	}
	
	
	public boolean deleteUrl(int id) {
		boolean res = false;
		Optional<Url> urlO = urlRepository.findById(id);
		if (urlO.isPresent()) {
			Cliente cliente = JWTUtils.userLogin();
			if (cliente.getUrls().contains(urlO.get())) {
				urlRepository.deleteById(id);
				res = true;
			}
		}
		return res;
	}
	
}
