package com.com.jwtdemo.repository;

import com.com.jwtdemo.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {

    // Buscar notas por estudiante
    List<Nota> findByEstudianteIdEstudiante(int idEstudiante);

    // Buscar notas por competencia
    List<Nota> findByCompetenciaIdCompetencia(int idCompetencia);

    // Buscar notas por docente
    List<Nota> findByDocenteId(int idDocente);

    // Buscar notas por periodo y año
    List<Nota> findByPeriodoAndAnio(String periodo, Integer anio);

    // Estudiantes en riesgo (calificación menor a 11)
    @Query("SELECT n FROM Nota n WHERE n.calificacion < 11 AND n.periodo = :periodo AND n.anio = :anio")
    List<Nota> findEstudiantesEnRiesgo(@Param("periodo") String periodo, @Param("anio") Integer anio);

    // Promedio de notas por estudiante en un periodo
    @Query("SELECT AVG(n.calificacion) FROM Nota n WHERE n.estudiante.idEstudiante = :idEstudiante AND n.periodo = :periodo AND n.anio = :anio")
    Double findPromedioByEstudiantePeriodo(@Param("idEstudiante") int idEstudiante,
                                           @Param("periodo") String periodo,
                                           @Param("anio") Integer anio);

    // Notas por grado y sección
    @Query("SELECT n FROM Nota n WHERE n.estudiante.grado = :grado AND n.estudiante.seccion = :seccion AND n.periodo = :periodo AND n.anio = :anio")
    List<Nota> findByGradoSeccionPeriodo(@Param("grado") String grado,
                                         @Param("seccion") String seccion,
                                         @Param("periodo") String periodo,
                                         @Param("anio") Integer anio);

    // Evolución de un estudiante (todas sus notas ordenadas por fecha)
    @Query("SELECT n FROM Nota n WHERE n.estudiante.idEstudiante = :idEstudiante ORDER BY n.fechaRegistro ASC")
    List<Nota> findEvolucionEstudiante(@Param("idEstudiante") int idEstudiante);

    // Notas por competencia en un grado específico
    @Query("SELECT n FROM Nota n WHERE n.competencia.idCompetencia = :idCompetencia AND n.estudiante.grado = :grado AND n.periodo = :periodo AND n.anio = :anio")
    List<Nota> findByCompetenciaGradoPeriodo(@Param("idCompetencia") int idCompetencia,
                                             @Param("grado") String grado,
                                             @Param("periodo") String periodo,
                                             @Param("anio") Integer anio);

    // Contar estudiantes en riesgo por grado
    @Query("SELECT COUNT(DISTINCT n.estudiante.idEstudiante) FROM Nota n WHERE n.calificacion < 11 AND n.estudiante.grado = :grado AND n.periodo = :periodo AND n.anio = :anio")
    Long countEstudiantesEnRiesgoByGrado(@Param("grado") String grado,
                                         @Param("periodo") String periodo,
                                         @Param("anio") Integer anio);
}