package com.urlmanager.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.urlmanager.entity.Cliente;
import com.urlmanager.entity.Roles;
import com.urlmanager.repository.ClienteRepository;
import com.urlmanager.security.JWTUtils;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JWTUtils JWTUtils;
    
    @Transactional
    public Cliente saveCliente(Cliente cliente) {
        cliente.setEntornos(new HashSet<>());
        cliente.setRol(Roles.CLIENTE);
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        return clienteRepository.save(cliente);
    }
    
    @Transactional
    public Cliente updateCliente(Cliente clienteU) {
        Cliente cliente = JWTUtils.userLogin();
        if (cliente != null) {
            cliente.setUsername(clienteU.getUsername());
            cliente.setPassword(clienteU.getPassword());
            cliente.setEntornos(clienteU.getEntornos());

            return clienteRepository.save(cliente);
        }
        return null;
    }
    
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> getClienteById(int id) {
        return clienteRepository.findById(id);
    }
    
    public Optional<Cliente> findByUsername(String username) {
        return clienteRepository.findByUsername(username);
    }

    @Transactional
    public boolean deleteClienteById(int idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isPresent()) {
            clienteRepository.deleteById(idCliente);
            return true;
        } else {
            return false;
        }
    }
    
    @Transactional
    public boolean deleteCliente() {
        Cliente cliente = JWTUtils.userLogin();
        if (cliente != null) {
            clienteRepository.deleteById(cliente.getId());
            return true;
        }
        return false;
    }
}