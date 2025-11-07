package com.com.jwtdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String dni;
    String nombres;
    String apellidos;
    String email;
    String celular;
    String username;
    String password;
    String rol;         //validar si es necesario manejarlo como Enum
    Boolean enabled;
}
