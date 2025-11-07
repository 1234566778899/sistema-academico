package com.com.jwtdemo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asignacion_docente")
public class AsignacionDocente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAsignacion;

    @ManyToOne
    @JoinColumn(name = "idDocente", nullable = false)
    private User docente;

    @ManyToOne
    @JoinColumn(name = "idCurso", nullable = false)
    private Curso curso;

    @Column(name = "grado", length = 15, nullable = false)
    private String grado;

    @Column(name = "seccion", length = 5, nullable = false)
    private String seccion;

    @Column(name = "anioAcademico", nullable = false)
    private Integer anioAcademico;

    @Column(name = "enabled")
    private Boolean enabled;

    // Constructores
    public AsignacionDocente() {
        this.enabled = true;
    }

    public AsignacionDocente(int idAsignacion, User docente, Curso curso, String grado,
                             String seccion, Integer anioAcademico, Boolean enabled) {
        this.idAsignacion = idAsignacion;
        this.docente = docente;
        this.curso = curso;
        this.grado = grado;
        this.seccion = seccion;
        this.anioAcademico = anioAcademico;
        this.enabled = enabled;
    }

    // Getters y Setters
    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public User getDocente() {
        return docente;
    }

    public void setDocente(User docente) {
        this.docente = docente;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
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

    public Integer getAnioAcademico() {
        return anioAcademico;
    }

    public void setAnioAcademico(Integer anioAcademico) {
        this.anioAcademico = anioAcademico;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}