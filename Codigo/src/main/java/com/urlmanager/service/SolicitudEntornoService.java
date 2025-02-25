package com.urlmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urlmanager.entity.Entorno;
import com.urlmanager.entity.EstadoSolicitud;
import com.urlmanager.entity.SolicitudEntorno;
import com.urlmanager.repository.SolicitudEntornoRepository;

import jakarta.transaction.Transactional;

@Service
public class SolicitudEntornoService {

    @Autowired
    private SolicitudEntornoRepository solicitudEntornoRepository;

    @Autowired
    private EntornoService entornoService;

    @Transactional
    public SolicitudEntorno saveSolicitudEntorno(SolicitudEntorno solicitudEntorno) {
        return solicitudEntornoRepository.save(solicitudEntorno);
    }

    public Optional<SolicitudEntorno> getSolicitudEntornoById(int id) {
        return solicitudEntornoRepository.findById(id);
    }

    public Iterable<SolicitudEntorno> getAllSolicitudEntorno() {
        return solicitudEntornoRepository.findAll();
    }

    @Transactional
    public boolean acceptSolicitudEntorno(int id) {
        Optional<SolicitudEntorno> solicitudOpt = getSolicitudEntornoById(id);
        if (solicitudOpt.isPresent()) {
            SolicitudEntorno solicitud = solicitudOpt.get();
            solicitud.setEstado(EstadoSolicitud.ACEPTADO);
            Entorno entorno = new Entorno();
            entorno.setName(solicitud.getNombreEntorno());
            entorno.setCliente(solicitud.getCliente());
            entornoService.saveEntorno(entorno);
            solicitudEntornoRepository.save(solicitud);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean refuseSolicitudEntorno(int id) {
        Optional<SolicitudEntorno> solicitudOpt = getSolicitudEntornoById(id);
        if (solicitudOpt.isPresent()) {
            SolicitudEntorno solicitud = solicitudOpt.get();
            solicitud.setEstado(EstadoSolicitud.RECHAZADO);
            solicitudEntornoRepository.save(solicitud);
            return true;
        }
        return false;
    }
}