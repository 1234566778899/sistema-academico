package com.com.jwtdemo.service;

import com.com.jwtdemo.dto.UserDTO;
import com.com.jwtdemo.model.*;
import com.com.jwtdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(UserDTO request) {

        // 1. Verificar si es actualización o nuevo registro
        boolean esActualizacion = request.getId() != null && userRepository.existsById(request.getId());

        // 2. Validar duplicados solo si es nuevo registro -_- Implementar en el front
        if (!esActualizacion) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("El correo electrónico ya está registrado.");
            }

            if (userRepository.existsByUsername(request.getUsername())) {
                throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
            }
        }

        // 3. Determinar rol según el caso
        Role rolAsignado;
        if (esActualizacion) {
            // Si es actualización, se respeta el rol que llega en el DTO
            rolAsignado = Role.valueOf(request.getRole().toUpperCase());
        } else {
            // Si es registro nuevo, se aplica la lógica del primer usuario
            boolean noHayUsuarios = userRepository.count() == 0;
            rolAsignado = noHayUsuarios ? Role.ADMIN : Role.USER;
        }

        // 4. Si es actualización, cargar el usuario existente
        User user = esActualizacion
                ? userRepository.findById(request.getId()).orElse(new User())
                : new User();

        // 5. Actualizar o crear datos del usuario
        user.setDni(request.getDni());
        user.setNombres(request.getNombres());
        user.setApellidos(request.getApellidos());
        user.setEmail(request.getEmail());
        user.setCelular(request.getCelular());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(rolAsignado);
        user.setEnabled(request.getEnabled());

        //  Guardar cambios
        userRepository.save(user);


        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public List<User> list(){
        return userRepository.findAll();
    }

    public User listarId(Integer id) { return userRepository.findById(id).orElse(new User());}

    public void delete(Integer idUser) {userRepository.deleteById(idUser);}
}
