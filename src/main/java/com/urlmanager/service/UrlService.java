package com.urlmanager.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urlmanager.entity.Entorno;
import com.urlmanager.entity.Url;
import com.urlmanager.repository.UrlRepository;
import com.urlmanager.security.JWTUtils;

import jakarta.transaction.Transactional;

@Service
public class UrlService {
    
    @Autowired
    private UrlRepository urlRepository;

	@Autowired
	private EntornoService entornoService;
    
    @Autowired
    private JWTUtils JWTUtils;
    
    public Set<Url> getAllUrlsByEntorno(Optional<Entorno> e) {
        return e.get().getUrls();
    }
    
    public Url getUrlById(int id) {
        Optional<Url> urlO = urlRepository.findById(id);
        if (urlO.isPresent()) {
            Object userLogin = JWTUtils.userLogin();
            if (userLogin instanceof Entorno) {
                Entorno entorno = (Entorno) userLogin;
                entorno.getUrls().contains(urlO.get());
                return urlO.get();
            }
        }
        return null;
    }
    
    @Transactional
    public Url save(Url u, int entCod) {
        Url res = null;
        Optional<Entorno> entornoO = entornoService.getEntornoById(entCod);
        if (!entornoO.isEmpty()) {
            u.setFechaCreacion(LocalDateTime.now());
            res = urlRepository.save(u);
            Entorno entorno = entornoO.get();
            entorno.getUrls().add(u);
            entornoService.saveEntorno(entorno);
        }
        return res;
    }
    
    public boolean deleteUrl(int id) {
        boolean res = false;
        Optional<Url> urlO = urlRepository.findById(id);
        if (urlO.isPresent()) {
            Entorno entorno = JWTUtils.userLogin();
            if (entorno.getUrls().contains(urlO.get())) {
                urlRepository.deleteById(id);
                res = true;
            }
        }
        return res;
    }
}