package com.com.jwtdemo.service;

import com.com.jwtdemo.model.Curso;
import com.com.jwtdemo.repository.CursoRepository;
import com.com.jwtdemo.servicesinterfaces.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImplements implements CursoService {

    @Autowired
    private CursoRepository cR;


    @Override
    public void insert(Curso curso) {
        cR.save(curso);
    }

    @Override
    public List<Curso> list() {
        return cR.findAll();
    }

    @Override
    public void delete(int idCurso) {
        cR.deleteById(idCurso);
    }

    @Override
    public Curso listarId(int id) {
        return cR.findById(id).orElse(new Curso());
    }
}
