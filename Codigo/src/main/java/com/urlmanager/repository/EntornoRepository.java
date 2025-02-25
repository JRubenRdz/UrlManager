package com.urlmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urlmanager.entity.Entorno;

@Repository
public interface EntornoRepository extends JpaRepository<Entorno, Integer>{
	public Optional<Entorno> findByName(String nombre);
	public Optional<List<Entorno>> findByClienteId(int idCliente);
}
