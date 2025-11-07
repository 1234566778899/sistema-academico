package com.com.jwtdemo.dto;

import com.com.jwtdemo.model.Role;

public class UserDTO {
        private Integer id;
        private String dni;
        private String nombres;
        private String apellidos;
        private String email;
        private String celular;
        private String username;
        private String password;
        private String role;              //Validar si debo manejarlo como un Enum
        private Boolean enabled;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getDni() {
                return dni;
        }

        public void setDni(String dni) {
                this.dni = dni;
        }

        public String getNombres() {
                return nombres;
        }

        public void setNombres(String nombres) {
                this.nombres = nombres;
        }

        public String getApellidos() {
                return apellidos;
        }

        public void setApellidos(String apellidos) {
                this.apellidos = apellidos;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getCelular() {
                return celular;
        }

        public void setCelular(String celular) {
                this.celular = celular;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getRole() {
                return role;
        }

        public void setRole(String role) {
                this.role = role;
        }

        public Boolean getEnabled() {
                return enabled;
        }

        public void setEnabled(Boolean enabled) {
                this.enabled = enabled;
        }
}