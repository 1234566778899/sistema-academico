package com.com.jwtdemo.model;


import jakarta.persistence.*;

@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCurso;

    @Column(name = "nombreCurso", length = 35, nullable = false)
    private String nombreCurso;
    private Boolean enabled;

    public Curso() {
    }

    public Curso(int idCurso, String nombreCurso, Boolean enabled) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.enabled = enabled;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
