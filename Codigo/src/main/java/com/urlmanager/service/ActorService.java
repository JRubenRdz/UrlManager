package com.urlmanager.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.urlmanager.entity.Actor;
import com.urlmanager.entity.Cliente;
import com.urlmanager.repository.ActorRepository;

import jakarta.transaction.Transactional;

@Service
public class ActorService implements UserDetailsService {

	@Autowired
	private ActorRepository actorRepository;

	public Optional<Actor> findByUsername(String username) {
		return actorRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
		Optional<Actor> actorO = this.findByUsername(nombre);
		if (actorO.isPresent()) {
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(actorO.get().getRol().toString()));
			User user = new User(actorO.get().getUsername(), actorO.get().getPassword(), authorities);
			return user;
		} else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
	}
	
	@Transactional
	public boolean createCliente(Cliente cliente) {
		boolean res = false;
		Optional<Actor> actorO = this.findByUsername(cliente.getUsername());
		if (actorO.isPresent()) {
			actorRepository.save(cliente);
			res = true;
		}
		return res;
	}

}
