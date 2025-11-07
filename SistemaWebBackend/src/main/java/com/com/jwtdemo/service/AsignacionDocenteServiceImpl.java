package com.com.jwtdemo.service;

import com.com.jwtdemo.model.AsignacionDocente;
import com.com.jwtdemo.repository.AsignacionDocenteRepository;
import com.com.jwtdemo.servicesinterfaces.AsignacionDocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignacionDocenteServiceImpl implements AsignacionDocenteService {

    @Autowired
    private AsignacionDocenteRepository adR;

    @Override
    public void insert(AsignacionDocente asignacion) {
        adR.save(asignacion);
    }

    @Override
    public void update(AsignacionDocente asignacion) {
        adR.save(asignacion);
    }

    @Override
    public List<AsignacionDocente> list() {
        return adR.findAll();
    }

    @Override
    public AsignacionDocente listarId(int id) {
        return adR.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        adR.deleteById(id);
    }

    @Override
    public List<AsignacionDocente> findByDocente(int idDocente) {
        return adR.findByDocenteId(idDocente);
    }

    @Override
    public List<AsignacionDocente> findByCurso(int idCurso) {
        return adR.findByCursoIdCurso(idCurso);
    }

    @Override
    public List<AsignacionDocente> findByGradoSeccion(String grado, String seccion) {
        return adR.findByGradoAndSeccion(grado, seccion);
    }

    @Override
    public List<AsignacionDocente> findByAnioAcademico(Integer anio) {
        return adR.findByAnioAcademico(anio);
    }

    @Override
    public boolean existeAsignacion(int idDocente, int idCurso, String grado, String seccion, Integer anio) {
        return adR.existeAsignacion(idDocente, idCurso, grado, seccion, anio) > 0;
    }
}