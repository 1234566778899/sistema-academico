package com.com.jwtdemo.servicesinterfaces;

import com.com.jwtdemo.model.Curso;
import com.com.jwtdemo.model.Estudiante;

import java.util.List;

public interface CursoService {
    public void insert(Curso curso);

    public List<Curso> list();

    public void delete(int idCurso);
    public Curso listarId(int id);
}
