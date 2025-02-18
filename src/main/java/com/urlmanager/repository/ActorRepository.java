package com.urlmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urlmanager.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer>{
	public Optional<Actor> findByUsername(String username);
}
