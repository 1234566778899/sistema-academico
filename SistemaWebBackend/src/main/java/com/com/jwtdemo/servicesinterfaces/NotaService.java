package com.com.jwtdemo.servicesinterfaces;

import com.com.jwtdemo.model.Nota;

import java.util.List;
import java.util.Map;

public interface NotaService {
    void insert(Nota nota);
    void update(Nota nota);
    List<Nota> list();
    Nota listarId(int id);
    void delete(int id);

    // Métodos existentes
    List<Nota> findByEstudiante(int idEstudiante);
    List<Nota> findByCompetencia(int idCompetencia);
    List<Nota> findByDocente(int idDocente);
    List<Nota> findEstudiantesEnRiesgo(String periodo, Integer anio);
    List<Nota> findByGradoSeccionPeriodo(String grado, String seccion, String periodo, Integer anio);
    List<Nota> findEvolucionEstudiante(int idEstudiante);
    List<Nota> findByCompetenciaGradoPeriodo(int idCompetencia, String grado, String periodo, Integer anio);
    Long countEstudiantesEnRiesgoByGrado(String grado, String periodo, Integer anio);

    // ⬇️ NUEVOS MÉTODOS

    // Estudiantes con riesgo académico (≥2 competencias con C)
    List<Map<String, Object>> findEstudiantesConRiesgoAcademico(String periodo, Integer anio);

    // Para docentes - HU-15: Filtrar por curso, competencia y sección del docente
    List<Nota> findByDocenteCursoSeccionPeriodo(int idDocente, int idCurso, String seccion, String periodo, Integer anio);

    // Para docentes - HU-15: Información ordenada por calificación
    List<Nota> findByDocenteCompetenciaSeccionPeriodo(int idDocente, int idCompetencia, String seccion, String periodo, Integer anio);

    // Para docentes - HU-16: Distribución de calificaciones (gráfico de barras/torta)
    Map<String, Integer> findDistribucionCalificaciones(int idDocente, int idCompetencia, String seccion, String periodo, Integer anio);

    // Para docentes - HU-17: Competencias con mayor riesgo
    List<Map<String, Object>> findCompetenciasConMayorRiesgo(int idDocente, int idCurso, String seccion, String periodo, Integer anio);

    // Para docentes - HU-13: Evolución por competencia específica
    List<Nota> findEvolucionEstudiantePorCompetencia(int idEstudiante, int idCompetencia);
}