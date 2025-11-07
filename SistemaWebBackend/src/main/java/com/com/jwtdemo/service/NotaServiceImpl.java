package com.com.jwtdemo.service;

import com.com.jwtdemo.model.Nota;
import com.com.jwtdemo.repository.NotaRepository;
import com.com.jwtdemo.servicesinterfaces.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepository nR;

    @Override
    public void insert(Nota nota) {
        nR.save(nota);
    }

    @Override
    public void update(Nota nota) {
        nR.save(nota);
    }

    @Override
    public List<Nota> list() {
        return nR.findAll();
    }

    @Override
    public Nota listarId(int id) {
        return nR.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        nR.deleteById(id);
    }

    @Override
    public List<Nota> findByEstudiante(int idEstudiante) {
        return nR.findByEstudianteIdEstudiante(idEstudiante);
    }

    @Override
    public List<Nota> findByCompetencia(int idCompetencia) {
        return nR.findByCompetenciaIdCompetencia(idCompetencia);
    }

    @Override
    public List<Nota> findByDocente(int idDocente) {
        return nR.findByDocenteId(idDocente);
    }

    @Override
    public List<Nota> findEstudiantesEnRiesgo(String periodo, Integer anio) {
        return nR.findEstudiantesEnRiesgo(periodo, anio);
    }

    @Override
    public Double findPromedioEstudiante(int idEstudiante, String periodo, Integer anio) {
        return nR.findPromedioByEstudiantePeriodo(idEstudiante, periodo, anio);
    }

    @Override
    public List<Nota> findByGradoSeccionPeriodo(String grado, String seccion, String periodo, Integer anio) {
        return nR.findByGradoSeccionPeriodo(grado, seccion, periodo, anio);
    }

    @Override
    public List<Nota> findEvolucionEstudiante(int idEstudiante) {
        return nR.findEvolucionEstudiante(idEstudiante);
    }

    @Override
    public List<Nota> findByCompetenciaGradoPeriodo(int idCompetencia, String grado, String periodo, Integer anio) {
        return nR.findByCompetenciaGradoPeriodo(idCompetencia, grado, periodo, anio);
    }

    @Override
    public Long countEstudiantesEnRiesgoByGrado(String grado, String periodo, Integer anio) {
        return nR.countEstudiantesEnRiesgoByGrado(grado, periodo, anio);
    }
}