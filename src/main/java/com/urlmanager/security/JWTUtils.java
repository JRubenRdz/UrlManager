package com.urlmanager.security;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.urlmanager.entity.Actor;
import com.urlmanager.entity.Admin;
import com.urlmanager.entity.Cliente;
import com.urlmanager.service.ActorService;
import com.urlmanager.service.AdminService;
import com.urlmanager.service.ClienteService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTUtils {
	private static final String JWT_FIRMA = "JaviSBC";
	private static final long EXTENCION_TOKEN = 86400000;
	
	@Autowired
	@Lazy
	private ActorService actorService;

	@Autowired
	@Lazy
	private ClienteService clienteService;

	@Autowired
	@Lazy
	private AdminService adminService;

	@Autowired
	@Lazy
	private SocioService socioService;

	@Autowired
	@Lazy
	private AyuntamientoService ayuntamientoService;

	public static String getToken(HttpServletRequest request) {
		String tokenBearer = request.getHeader("Authorization");
		if (StringUtils.hasText(tokenBearer) && tokenBearer.startsWith("Bearer ")) {
			return tokenBearer.substring(7);
		}
		return null;
	}

	public static boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(JWT_FIRMA).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			throw new AuthenticationCredentialsNotFoundException("JWT ha experido o no es valido");
		}
	}

	public static String getUsernameOfToken(String token) {
		return Jwts.parser().setSigningKey(JWT_FIRMA).parseClaimsJws(token).getBody().getSubject();
	}

	public static String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date fechaActual = new Date();
		Date fechaExpiracion = new Date(fechaActual.getTime() + EXTENCION_TOKEN);
		String rol = authentication.getAuthorities().iterator().next().getAuthority();

		String token = Jwts.builder()
				.setSubject(username)
				.claim("rol", rol)
				.setIssuedAt(fechaActual)
				.setExpiration(fechaExpiracion)
				.signWith(SignatureAlgorithm.HS512, JWT_FIRMA)
				.compact();
		return token;
	}

	public <T> T userLogin() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		T res = null;

		if (StringUtils.hasText(username)) {
			Optional<Actor> actorO = actorService.findByUsername(username);
			if (actorO.isPresent()) {
				Actor actor = actorO.get();
				switch (actor.getRol()) {
				case CLIENTE:
					Optional<Cliente> casetaOptional = clienteService.findByUsername(username);
					if (casetaOptional.isPresent()) {
						res = (T) casetaOptional.get();
					}
					break;
				case ADMIN:
					Optional<Admin> adminOptional = adminService.findByUsername(username);
					if (adminOptional.isPresent()) {
						res = (T) adminOptional.get();
					}
					break;
				}
			}
		}
		return res;
	}
}