package com.urlmanager.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urlmanager.entity.Entorno;
import com.urlmanager.entity.Url;
import com.urlmanager.repository.EntornoRepository;
import com.urlmanager.repository.UrlRepository;
import com.urlmanager.security.JWTUtils;

import jakarta.transaction.Transactional;

@Service
public class EntornoService {
    
    @Autowired
    private EntornoRepository entornoRepository;
    
    @Autowired
    private UrlRepository urlRepository;
    
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
    public boolean deleteEntorno(int id) {
        Optional<Entorno> entorno = getEntornoById(id);
        if (entorno.isPresent()) {
            entornoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean anadirUrl(Url url, int entCod) {
        boolean res = false;
        try {
            Entorno ent = getEntornoById(entCod).get();
            url.setEntorno(ent);
            url.setFechaCreacion(LocalDateTime.now());
            ent.getUrls().add(url);
            res = true;
        } catch (Exception e) {
            res = false;
        }
        return res;
    }
    
    @Transactional
    public boolean updateUrl(Url url, int entCod) {
        boolean res = false;
        try {
            Entorno ent = getEntornoById(entCod).get();
            url.setEntorno(ent);
            url.setFechaCreacion(LocalDateTime.now());
            urlRepository.save(url);
            res = true;
        } catch (Exception e) {
            res = false;
        }
        return res;
    }
}