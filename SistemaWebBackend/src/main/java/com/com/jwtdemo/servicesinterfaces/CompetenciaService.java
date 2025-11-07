package com.com.jwtdemo.servicesinterfaces;

import com.com.jwtdemo.model.Competencia;

import java.util.List;

public interface CompetenciaService {

    public void insert(Competencia competencia);

    public List<Competencia> list();

    public void delete(int idCompetencia);

    public Competencia listarId(int idCompetencia);
}
