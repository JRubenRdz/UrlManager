package com.urlmanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urlmanager.entity.Cliente;
import com.urlmanager.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Operaciones relacionadas con la gestión de los clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	@Operation(summary = "Obtener todos los clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	public ResponseEntity<List<Cliente>> getAllClientes() {
		List<Cliente> clientes = clienteService.getAllClientes();
		return ResponseEntity.ok(clientes);
	}
	
	
	@GetMapping("/{id}")
	@Operation(summary = "Buscar un cliente por ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
			@ApiResponse(responseCode = "404", description = "Cliente no encontrado") })
	public ResponseEntity<Cliente> findOneCliente(@PathVariable int id) {
		Optional<Cliente> cliente = clienteService.getClienteById(id);
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	
	@PostMapping
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
			@ApiResponse(responseCode = "400", description = "Solicitud inválida"),
			@ApiResponse(responseCode = "409", description = "El username ya está en uso") })
	public ResponseEntity<String> saveCliente(@RequestBody Cliente cliente) {
		if (clienteService.findByUsername(cliente.getUsername()).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El username ya está en uso");
		} else {
			Cliente c = clienteService.saveCliente(cliente);
			if (c != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Clietne creado exitosamente");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el cliente");
			}
		}
	}
	
	
	
	@PutMapping()
	@Operation(summary = "Actualizar un cliente existente")
	@ApiResponses(value = { 
	    @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
	    @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
	    @ApiResponse(responseCode = "400", description = "Solicitud inválida") 
	})
	public ResponseEntity<String> updateCliente(@RequestBody Cliente updatedCliente) {
	    Cliente response = clienteService.updateCliente(updatedCliente);
	    if (response != null) {
	        return ResponseEntity.status(HttpStatus.OK).body("Cliente actualizado exitosamente");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ayuntamiento no encontrado");
	    }
	}
	
	
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar un cliente logueado")
	@ApiResponses(value = { 
	    @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
	    @ApiResponse(responseCode = "404", description = "Clietne no encontrado o no eliminado") 
	})
	public ResponseEntity<String> deleteCliente() {
	    boolean result = clienteService.deleteCliente();
	    if (result) {
	        return ResponseEntity.status(HttpStatus.OK).body("Cliente 	eliminado exitosamente");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ayuntamiento no encontrado o no eliminado");
	    }
	}
}
