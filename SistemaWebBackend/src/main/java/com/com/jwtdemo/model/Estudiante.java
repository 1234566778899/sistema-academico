package com.com.jwtdemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiante")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEstudiante;

    @Column(name = "dni", length = 8, nullable = false)
    private String dni;
    @Column(name = "nombres", length = 45, nullable = false)
    private String nombres;
    @Column(name = "apellidos", length = 45, nullable = false)
    private String apellidos;
    @Column(name = "grado", length = 15, nullable = false)
    private String grado;
    @Column(name = "seccion", length = 15, nullable = false)
    private String seccion;
    private Boolean enabled;        //Validar con el nombre que aparece en la bd

    public Estudiante() {
    }

    public Estudiante(int idEstudiante, String dni, String nombres, String apellidos, String grado, String seccion, Boolean enabled) {
        this.idEstudiante = idEstudiante;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.grado = grado;
        this.seccion = seccion;
        this.enabled = enabled;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
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

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
