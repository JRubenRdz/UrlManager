package com.urlmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private JWTAuthenticationFilter JWTAuthenticationFilter;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
                // LOGIN
            .requestMatchers(HttpMethod.POST, "/login").permitAll()
                // REGISTRO
            .requestMatchers(HttpMethod.POST, "/cliente").permitAll()
                // ADMIN
            .requestMatchers(HttpMethod.POST, "/admin").hasAnyAuthority("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/admin").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/admin").hasAuthority("ADMIN")
                // CLIENTE
            .requestMatchers(HttpMethod.PUT, "/cliente").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/cliente").hasAuthority("ADMIN")
                // ENTORNO
            .requestMatchers(HttpMethod.GET, "/entorno").hasAuthority("CLIENTE")
            .requestMatchers(HttpMethod.GET, "/entorno/{id}").hasAuthority("CLIENTE")
            .requestMatchers(HttpMethod.GET, "/entorno/{entornoCod}/urls").hasAuthority("CLIENTE")
            .requestMatchers(HttpMethod.GET, "/entorno/eliminarUrl/{idUrl}").hasAuthority("CLIENTE")
            .requestMatchers(HttpMethod.POST, "/entorno/{codEnt}/anadirUrl").hasAuthority("CLIENTE")
            .requestMatchers(HttpMethod.POST, "/entotno/actualizarUrl/{codEnt}").hasAuthority("CLIENTE")
            .requestMatchers(HttpMethod.PUT, "/entorno").hasAuthority("CLIENTE")
            .requestMatchers(HttpMethod.DELETE, "/entorno/{id}").hasAuthority("CLIENTE")
                // SOLICITUD ENTORNO
            .requestMatchers(HttpMethod.GET, "/solicitudEntorno/aceptar/{id}").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.GET, "/solicitudEntorno/rechazar/{id}").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.GET, "/solicitudEntorno/{id}").hasAnyAuthority("CLIENTE", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/solicitudEntorno").hasAuthority("ADMIN")
            .requestMatchers(HttpMethod.POST, "/solicitudEntorno").hasAuthority("CLIENTE")
            .anyRequest().authenticated();

        http.addFilterBefore(JWTAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}