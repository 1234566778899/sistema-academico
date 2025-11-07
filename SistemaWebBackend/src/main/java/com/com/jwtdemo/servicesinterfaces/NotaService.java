package com.com.jwtdemo.servicesinterfaces;

import com.com.jwtdemo.model.Nota;

import java.util.List;

public interface NotaService {
    void insert(Nota nota);
    void update(Nota nota);
    List<Nota> list();
    Nota listarId(int id);
    void delete(int id);

    // Métodos específicos para reportes
    List<Nota> findByEstudiante(int idEstudiante);
    List<Nota> findByCompetencia(int idCompetencia);
    List<Nota> findByDocente(int idDocente);
    List<Nota> findEstudiantesEnRiesgo(String periodo, Integer anio);
    Double findPromedioEstudiante(int idEstudiante, String periodo, Integer anio);
    List<Nota> findByGradoSeccionPeriodo(String grado, String seccion, String periodo, Integer anio);
    List<Nota> findEvolucionEstudiante(int idEstudiante);
    List<Nota> findByCompetenciaGradoPeriodo(int idCompetencia, String grado, String periodo, Integer anio);
    Long countEstudiantesEnRiesgoByGrado(String grado, String periodo, Integer anio);
}