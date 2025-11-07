package com.com.jwtdemo.service;

import com.com.jwtdemo.model.Estudiante;
import com.com.jwtdemo.repository.EstudianteRepository;
import com.com.jwtdemo.servicesinterfaces.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteServiceImplements implements EstudianteService {

    @Autowired
    private EstudianteRepository eR;

    @Override
    public void insert(Estudiante estudiante) {
        eR.save(estudiante);
    }

    @Override
    public List<Estudiante> list() {
        return eR.findAll();
    }

    @Override
    public void delete(int idEstudiante) {
        eR.deleteById(idEstudiante);
    }

    @Override
    public Estudiante listarId(int id) {
        return eR.findById(id).orElse(new Estudiante()); //validar porqué se crea uno nuevo?
    }
}
