package com.com.jwtdemo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "nota")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNota;

    @ManyToOne
    @JoinColumn(name = "idEstudiante", nullable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "idCompetencia", nullable = false)
    private Competencia competencia;

    @ManyToOne
    @JoinColumn(name = "idDocente", nullable = false)
    private User docente;

    // ⬇️ CAMBIO PRINCIPAL: De Double a String
    @Column(name = "calificacion", length = 2, nullable = false)
    private String calificacion; // "AD", "A", "B", "C"

    @Column(name = "periodo", length = 20, nullable = false)
    private String periodo;

    @Column(name = "anio", nullable = false)
    private Integer anio;

    @Column(name = "fechaRegistro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "observacion", length = 500)
    private String observacion;

    @Column(name = "enabled")
    private Boolean enabled;

    // Constructores
    public Nota() {
        this.fechaRegistro = LocalDate.now();
        this.enabled = true;
    }

    public Nota(int idNota, Estudiante estudiante, Competencia competencia, User docente,
                String calificacion, String periodo, Integer anio, LocalDate fechaRegistro,
                String observacion, Boolean enabled) {
        this.idNota = idNota;
        this.estudiante = estudiante;
        this.competencia = competencia;
        this.docente = docente;
        this.calificacion = calificacion;
        this.periodo = periodo;
        this.anio = anio;
        this.fechaRegistro = fechaRegistro;
        this.observacion = observacion;
        this.enabled = enabled;
    }

    // Getters y Setters
    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Competencia getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Competencia competencia) {
        this.competencia = competencia;
    }

    public User getDocente() {
        return docente;
    }

    public void setDocente(User docente) {
        this.docente = docente;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}