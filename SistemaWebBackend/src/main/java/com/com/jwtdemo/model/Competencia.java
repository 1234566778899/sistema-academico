package com.com.jwtdemo.model;


import jakarta.persistence.*;

@Entity
@Table(name = "competencia")
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCompetencia;

    @Column(name = "nombreCompetencia", length = 35, nullable = false)
    private String nombreCompetencia;
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "idCursos")   //Validar si no hay problema, sino cambiar
    private Curso curso;

    public Competencia() {
    }

    public Competencia(int idCompetencia, String nombreCompetencia, Boolean enabled, Curso curso) {
        this.idCompetencia = idCompetencia;
        this.nombreCompetencia = nombreCompetencia;
        this.enabled = enabled;
        this.curso = curso;
    }

    public int getIdCompetencia() {
        return idCompetencia;
    }

    public void setIdCompetencia(int idCompetencia) {
        this.idCompetencia = idCompetencia;
    }

    public String getNombreCompetencia() {
        return nombreCompetencia;
    }

    public void setNombreCompetencia(String nombreCompetencia) {
        this.nombreCompetencia = nombreCompetencia;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
