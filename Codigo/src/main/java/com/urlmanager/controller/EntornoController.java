package com.urlmanager.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.urlmanager.entity.Entorno;
import com.urlmanager.entity.Url;
import com.urlmanager.service.EntornoService;
import com.urlmanager.service.UrlService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/entorno")
@Tag(name = "Entorno", description = "Operaciones relacionadas con la gestión de los entornos")
public class EntornoController {

	@Autowired
	private EntornoService entornoService;

	@Autowired
	private UrlService urlService;

	@GetMapping
	@Operation(summary = "Obtener todos los entornos")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista de entornos obtenida exitosamente"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	public ResponseEntity<List<Entorno>> getAllEntornos() {
		List<Entorno> entornos = entornoService.getAllEntornos();
		return ResponseEntity.ok(entornos);
	}

	@GetMapping("/{idCliente}")
	@Operation(summary = "Obtener todos los entornos de un cliente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista de entornos obtenida exitosamente"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor") })
	public ResponseEntity<List<Entorno>> getAllEntornosByCliente(@PathVariable int idCliente) {
		Optional<List<Entorno>> entornos = entornoService.findAllEntornosByClienteId(idCliente);
		if (entornos.isPresent()) {
			return ResponseEntity.ok(entornos.get());
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
		

	@GetMapping("/{id}")
	@Operation(summary = "Buscar un entorno por ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Entorno encontrado"),
			@ApiResponse(responseCode = "404", description = "Entorno no encontrado") })
	public ResponseEntity<Entorno> findOneEntorno(@PathVariable int id) {
		Optional<Entorno> entorno = entornoService.getEntornoById(id);
		if (entorno.isPresent()) {
			return ResponseEntity.ok(entorno.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/{entornoCod}/urls")
	@Operation(summary = "Obtener todas las urls del entorno")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Urls obtenidas correctamente"),
			@ApiResponse(responseCode = "404", description = "No se encontraron urls") })
	public ResponseEntity<Set<Url>> getAllUrlsByEntorno(@PathVariable int entornoCod) {
		Optional<Entorno> ent = entornoService.getEntornoById(entornoCod);
		Set<Url> urls = urlService.getAllUrlsByEntorno(ent);
		if (urls == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.ok(urls);
		}
	}

	@GetMapping("/eliminarUrl/{idUrl}")
	@Operation(summary = "Elimina una url de la lista de urls del entorno del cliente logueado.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Url eliminada correctamente"),
			@ApiResponse(responseCode = "404", description = "Url no encontrada") })
	public ResponseEntity<String> eliminarUrl(@PathVariable int idUrl) {
		boolean result = urlService.deleteUrl(idUrl);
		if (result) {
			return ResponseEntity.ok("Url eliminada correctamente.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Url no encontrada.");
	}

	@PostMapping("/{codEnt}/anadirUrl")
	@Operation(summary = "Añade una url a la lista de urls del entorno del cliente logueado.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Url añadida correctamente"),
			@ApiResponse(responseCode = "400", description = "Entorno no encontrado") })
	public ResponseEntity<String> anadirUrl(@RequestBody Url urlI, @PathVariable int codEnt) {
		boolean result = entornoService.anadirUrl(urlI, codEnt);
		if (result) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Url añadida correctamente");
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Entorno no encontrado");
	}

	@PostMapping("/actualizarUrl/{codEnt}")
	@Operation(summary = "Actualiza una url a la lista de urls del entorno del cliente logueado.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Url actualizada correctamente"),
			@ApiResponse(responseCode = "400", description = "Entorno no encontrado") })
	public ResponseEntity<String> updateUrl(@RequestBody String url, String desc, @PathVariable int codEnt) {
		Url urlI = new Url();
		urlI.setUrl(url);
		urlI.setDescripcion(desc);
		boolean result = entornoService.updateUrl(urlI, codEnt);
		if (result) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Url actualizada correctamente");
		}
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Entorno no encontrado");
	}

	@PutMapping
	@Operation(summary = "Actualizar un entorno existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Entorno actualizado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Entorno no encontrado"),
			@ApiResponse(responseCode = "400", description = "Solicitud inválida") })
	public ResponseEntity<String> updateEntorno(@RequestBody Entorno updatedEntorno) {
		Entorno response = entornoService.updateEntorno(updatedEntorno);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.OK).body("Entorno actualizado exitosamente");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entorno no encontrado");
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar un entorno registrado")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Entorno eliminado exitosamente"),
			@ApiResponse(responseCode = "404", description = "Entorno no eliminado") })
	public ResponseEntity<String> deleteEntorno(@PathVariable int id) {
		if (entornoService.deleteEntorno(id)) {
			return ResponseEntity.status(HttpStatus.OK).body("Entorno eliminado exitosamente");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entorno no eliminado");
		}
	}
}