package com.urlmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urlmanager.entity.Admin;
import com.urlmanager.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@PostMapping
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Administrador creado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud inválida"),
			@ApiResponse(responseCode = "409", description = "El username ya está en uso") })
	public ResponseEntity<String> saveAdmin(@RequestBody Admin admin) {
		if (adminService.findByUsername(admin.getUsername()).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El username ya está en uso");
		} else {
			Admin a = adminService.saveAdmin(admin);
			if (a != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Administrador creado exitosamente");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el administrador");
			}
		}
	}

	@PutMapping
	@Operation(summary = "Actualizar un administrador existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Administrador actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Administrador no encontrado"),
			@ApiResponse(responseCode = "400", description = "Solicitud inválida") })
	public ResponseEntity<String> updateAdmin(@RequestBody Admin updatedAdmin) {
		Admin response = adminService.updateAdmin(updatedAdmin);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.OK).body("Administrador actualizado exitosamente");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrador no encontrado");
		}
	}

	@DeleteMapping
	@Operation(summary = "Eliminar un administrador logueado")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Administrador eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Administrador no encontrado") })
	public ResponseEntity<String> deleteAdmin() {
		if (adminService.deleteAdmin()) {
			return ResponseEntity.status(HttpStatus.OK).body("Administrador eliminado exitosamente");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrador no encontrado");
		}
	}
}
