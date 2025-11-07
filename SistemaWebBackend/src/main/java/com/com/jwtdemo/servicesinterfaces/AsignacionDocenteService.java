package com.com.jwtdemo.servicesinterfaces;

import com.com.jwtdemo.model.AsignacionDocente;

import java.util.List;

public interface AsignacionDocenteService {
    void insert(AsignacionDocente asignacion);
    void update(AsignacionDocente asignacion);
    List<AsignacionDocente> list();
    AsignacionDocente listarId(int id);
    void delete(int id);

    // Métodos específicos
    List<AsignacionDocente> findByDocente(int idDocente);
    List<AsignacionDocente> findByCurso(int idCurso);
    List<AsignacionDocente> findByGradoSeccion(String grado, String seccion);
    List<AsignacionDocente> findByAnioAcademico(Integer anio);
    boolean existeAsignacion(int idDocente, int idCurso, String grado, String seccion, Integer anio);
}
