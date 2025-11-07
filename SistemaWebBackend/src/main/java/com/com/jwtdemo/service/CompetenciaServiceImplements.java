package com.com.jwtdemo.service;

import com.com.jwtdemo.model.Competencia;
import com.com.jwtdemo.repository.CompetenciaRepository;
import com.com.jwtdemo.servicesinterfaces.CompetenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetenciaServiceImplements implements CompetenciaService {

    @Autowired
    private CompetenciaRepository cR;

    @Override
    public void insert(Competencia competencia) {
        cR.save(competencia);
    }

    @Override
    public List<Competencia> list() {
        return cR.findAll();
    }

    @Override
    public void delete(int idCompetencia) {
        cR.deleteById(idCompetencia);
    }

    @Override
    public Competencia listarId(int idCompetencia) {
        return cR.findById(idCompetencia).orElse(new Competencia());
    }
}
