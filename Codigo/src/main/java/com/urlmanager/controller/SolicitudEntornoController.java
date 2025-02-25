package com.urlmanager.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.urlmanager.entity.SolicitudEntorno;
import com.urlmanager.service.SolicitudEntornoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/solicitudEntorno")
@Tag(name = "SolicitudEntorno", description = "Operaciones relacionadas con la gestión de las solicitudes de entornos")
public class SolicitudEntornoController {

    @Autowired
    private SolicitudEntornoService solicitudEntornoService;

    @PostMapping
    @Operation(summary = "Crear una nueva solicitud de entorno")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Solicitud creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida") })
    public ResponseEntity<String> createSolicitudEntorno(@RequestBody SolicitudEntorno solicitudEntorno) {
        SolicitudEntorno savedSolicitud = solicitudEntornoService.saveSolicitudEntorno(solicitudEntorno);
        if (savedSolicitud != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Solicitud creada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear la solicitud");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una solicitud de entorno por ID")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada") })
    public ResponseEntity<SolicitudEntorno> getSolicitudEntornoById(@PathVariable int id) {
        Optional<SolicitudEntorno> solicitudOpt = solicitudEntornoService.getSolicitudEntornoById(id);
        if (solicitudOpt.isPresent()) {
            return ResponseEntity.ok(solicitudOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Obtener todas las solicitudes de entorno")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Solicitudes encontradas"),
            @ApiResponse(responseCode = "404", description = "No se encontraron solicitudes") })
    public ResponseEntity<Iterable<SolicitudEntorno>> getAllSolicitudEntorno() {
        Iterable<SolicitudEntorno> solicitudes = solicitudEntornoService.getAllSolicitudEntorno();
        if (solicitudes.iterator().hasNext()) {
            return ResponseEntity.ok(solicitudes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/aceptar/{id}")
    @Operation(summary = "Aceptar una solicitud de entorno")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Solicitud aceptada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada") })
    public ResponseEntity<String> acceptSolicitudEntorno(@PathVariable int id) {
        boolean result = solicitudEntornoService.acceptSolicitudEntorno(id);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Solicitud aceptada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitud no encontrada");
        }
    }

    @GetMapping("/rechazar/{id}")
    @Operation(summary = "Rechazar una solicitud de entorno")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Solicitud rechazada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada") })
    public ResponseEntity<String> refuseSolicitudEntorno(@PathVariable int id) {
        boolean result = solicitudEntornoService.refuseSolicitudEntorno(id);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Solicitud rechazada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitud no encontrada");
        }
    }
}