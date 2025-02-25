package com.urlmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.urlmanager.entity.Cliente;
import com.urlmanager.service.ClienteService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Operaciones relacionadas con la gestión de los clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "409", description = "El username ya está en uso") })
    public ResponseEntity<String> saveCliente(@RequestBody Cliente cliente) {
        if (clienteService.findByUsername(cliente.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El username ya está en uso");
        } else {
            Cliente savedCliente = clienteService.saveCliente(cliente);
            if (savedCliente != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo guardar el cliente");
            }
        }
    }
}