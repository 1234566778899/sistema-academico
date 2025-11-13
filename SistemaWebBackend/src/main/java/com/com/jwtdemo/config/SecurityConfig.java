package com.com.jwtdemo.config;

import com.com.jwtdemo.filter.JwtAuthenticationFilter;
import com.com.jwtdemo.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                // Autenticación y registro
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/").permitAll()

                                // ⬇️ ADMIN (Director): Acceso completo
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/curso/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/curso/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/curso/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/competencia/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/competencia/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/competencia/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/estudiante/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/estudiante/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/estudiante/**").hasAuthority("ADMIN")
                                .requestMatchers("/asignacion/**").hasAuthority("ADMIN")
                                .requestMatchers("/users/**").hasAuthority("ADMIN")

                                // ⬇️ USER (Docentes): Acceso a sus propias notas y reportes
                                .requestMatchers(HttpMethod.GET, "/curso/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/competencia/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/estudiante/**").hasAnyAuthority("ADMIN", "USER")

                                // Notas: Crear y editar para docentes, todo para admin
                                .requestMatchers(HttpMethod.POST, "/nota/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/nota/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/nota/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/nota/**").hasAnyAuthority("ADMIN", "USER")

                                // Endpoints específicos para docentes (HU-13, 15, 16, 17)
                                .requestMatchers("/nota/docente/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/nota/evolucion/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/nota/riesgo/**").hasAnyAuthority("ADMIN", "USER")

                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}