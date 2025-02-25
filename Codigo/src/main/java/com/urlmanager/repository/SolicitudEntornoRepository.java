package com.urlmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urlmanager.entity.SolicitudEntorno;

@Repository
public interface SolicitudEntornoRepository extends JpaRepository<SolicitudEntorno, Integer> {
}