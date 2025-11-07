package com.com.jwtdemo.servicesinterfaces;

import com.com.jwtdemo.model.Estudiante;

import java.util.List;

public interface EstudianteService {
    public void insert(Estudiante estudiante);

    public List<Estudiante> list();

    public void delete(int idEstudiante);
    public Estudiante listarId(int id);
}
