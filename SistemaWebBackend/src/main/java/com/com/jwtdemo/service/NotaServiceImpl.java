package com.com.jwtdemo.service;

import com.com.jwtdemo.model.Nota;
import com.com.jwtdemo.repository.NotaRepository;
import com.com.jwtdemo.servicesinterfaces.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    // ⬇️ NUEVAS IMPLEMENTACIONES

    @Override
    public List<Map<String, Object>> findEstudiantesConRiesgoAcademico(String periodo, Integer anio) {
        List<Object[]> resultados = nR.findEstudiantesConRiesgoAcademico(periodo, anio);

        return resultados.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("idEstudiante", row[0]);
            map.put("nombreEstudiante", row[1]);
            map.put("grado", row[2]);
            map.put("seccion", row[3]);
            map.put("competenciasEnC", row[4]);
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Nota> findByDocenteCursoSeccionPeriodo(int idDocente, int idCurso, String seccion, String periodo, Integer anio) {
        return nR.findByDocenteCursoSeccionPeriodo(idDocente, idCurso, seccion, periodo, anio);
    }

    @Override
    public List<Nota> findByDocenteCompetenciaSeccionPeriodo(int idDocente, int idCompetencia, String seccion, String periodo, Integer anio) {
        return nR.findByDocenteCompetenciaSeccionPeriodo(idDocente, idCompetencia, seccion, periodo, anio);
    }

    @Override
    public Map<String, Integer> findDistribucionCalificaciones(int idDocente, int idCompetencia, String seccion, String periodo, Integer anio) {
        List<Object[]> resultados = nR.findDistribucionCalificaciones(idDocente, idCompetencia, seccion, periodo, anio);

        // Inicializar con 0 todas las calificaciones
        Map<String, Integer> distribucion = new LinkedHashMap<>();
        distribucion.put("AD", 0);
        distribucion.put("A", 0);
        distribucion.put("B", 0);
        distribucion.put("C", 0);

        // Llenar con los datos obtenidos
        for (Object[] row : resultados) {
            String calificacion = (String) row[0];
            Integer cantidad = ((Number) row[1]).intValue();
            distribucion.put(calificacion, cantidad);
        }

        return distribucion;
    }

    @Override
    public List<Map<String, Object>> findCompetenciasConMayorRiesgo(int idDocente, int idCurso, String seccion, String periodo, Integer anio) {
        List<Object[]> resultados = nR.findCompetenciasConMayorRiesgo(idDocente, idCurso, seccion, periodo, anio);

        return resultados.stream().map(row -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("idCompetencia", row[0]);
            map.put("nombreCompetencia", row[1]);
            map.put("AD", row[2]);
            map.put("A", row[3]);
            map.put("B", row[4]);
            map.put("C", row[5]);
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Nota> findEvolucionEstudiantePorCompetencia(int idEstudiante, int idCompetencia) {
        return nR.findEvolucionEstudiantePorCompetencia(idEstudiante, idCompetencia);
    }
}