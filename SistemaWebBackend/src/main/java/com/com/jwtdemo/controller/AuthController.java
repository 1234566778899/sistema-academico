package com.com.jwtdemo.controller;

import com.com.jwtdemo.dto.UserDTO;
import com.com.jwtdemo.model.*;
import com.com.jwtdemo.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
@RestController
@RequestMapping()
public class AuthController {

    final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Login
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // Registro (público)
    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO request) {
        try {
            return ResponseEntity.ok(authService.register(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Crear usuario (requiere autenticación)
    @PostMapping
    public ResponseEntity<AuthResponse> crearUsuario(@RequestBody UserDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PutMapping
    public ResponseEntity<AuthResponse> actualizar(@RequestBody UserDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping
    public List<UserDTO> listar() {
        return authService.list().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, UserDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        UserDTO dto = m.map(authService.listarId(id), UserDTO.class);
        return dto;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        authService.delete(id);
    }
}