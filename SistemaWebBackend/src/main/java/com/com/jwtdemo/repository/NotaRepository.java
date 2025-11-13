package com.com.jwtdemo.repository;

import com.com.jwtdemo.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    // ⬇️ ACTUALIZADO: Estudiantes en riesgo (calificación C)
    @Query("SELECT n FROM Nota n WHERE n.calificacion = 'C' AND n.periodo = :periodo AND n.anio = :anio")
    List<Nota> findEstudiantesEnRiesgo(@Param("periodo") String periodo, @Param("anio") Integer anio);

    // ⬇️ NUEVO: Estudiantes con riesgo académico (≥2 competencias con C)
    @Query(value = "SELECT e.id_estudiante, " +
            "CONCAT(e.nombres, ' ', e.apellidos) as nombre_estudiante, " +
            "e.grado, e.seccion, " +
            "COUNT(DISTINCT n.id_competencia) as competencias_en_c " +
            "FROM nota n " +
            "INNER JOIN estudiante e ON n.id_estudiante = e.id_estudiante " +
            "WHERE n.calificacion = 'C' AND n.periodo = :periodo AND n.anio = :anio " +
            "GROUP BY e.id_estudiante, e.nombres, e.apellidos, e.grado, e.seccion " +
            "HAVING COUNT(DISTINCT n.id_competencia) >= 2 " +
            "ORDER BY competencias_en_c DESC",
            nativeQuery = true)
    List<Object[]> findEstudiantesConRiesgoAcademico(@Param("periodo") String periodo, @Param("anio") Integer anio);

    // Notas por grado y sección
    @Query("SELECT n FROM Nota n WHERE n.estudiante.grado = :grado AND n.estudiante.seccion = :seccion AND n.periodo = :periodo AND n.anio = :anio")
    List<Nota> findByGradoSeccionPeriodo(@Param("grado") String grado,
                                         @Param("seccion") String seccion,
                                         @Param("periodo") String periodo,
                                         @Param("anio") Integer anio);

    // Evolución de un estudiante (todas sus notas ordenadas por fecha)
    @Query("SELECT n FROM Nota n WHERE n.estudiante.idEstudiante = :idEstudiante ORDER BY n.anio ASC, n.periodo ASC, n.fechaRegistro ASC")
    List<Nota> findEvolucionEstudiante(@Param("idEstudiante") int idEstudiante);

    // Notas por competencia en un grado específico
    @Query("SELECT n FROM Nota n WHERE n.competencia.idCompetencia = :idCompetencia AND n.estudiante.grado = :grado AND n.periodo = :periodo AND n.anio = :anio")
    List<Nota> findByCompetenciaGradoPeriodo(@Param("idCompetencia") int idCompetencia,
                                             @Param("grado") String grado,
                                             @Param("periodo") String periodo,
                                             @Param("anio") Integer anio);

    // ⬇️ ACTUALIZADO: Contar estudiantes en riesgo por grado (con C)
    @Query("SELECT COUNT(DISTINCT n.estudiante.idEstudiante) FROM Nota n WHERE n.calificacion = 'C' AND n.estudiante.grado = :grado AND n.periodo = :periodo AND n.anio = :anio")
    Long countEstudiantesEnRiesgoByGrado(@Param("grado") String grado,
                                         @Param("periodo") String periodo,
                                         @Param("anio") Integer anio);

    // ⬇️ NUEVO: Consultas para docentes específicos

    // Buscar notas por docente, curso y sección
    @Query("SELECT n FROM Nota n WHERE n.docente.id = :idDocente AND n.competencia.curso.idCurso = :idCurso AND n.estudiante.seccion = :seccion AND n.periodo = :periodo AND n.anio = :anio")
    List<Nota> findByDocenteCursoSeccionPeriodo(@Param("idDocente") int idDocente,
                                                @Param("idCurso") int idCurso,
                                                @Param("seccion") String seccion,
                                                @Param("periodo") String periodo,
                                                @Param("anio") Integer anio);

    // Buscar notas por docente, curso, competencia y sección
    @Query("SELECT n FROM Nota n WHERE n.docente.id = :idDocente AND n.competencia.idCompetencia = :idCompetencia AND n.estudiante.seccion = :seccion AND n.periodo = :periodo AND n.anio = :anio ORDER BY n.calificacion ASC")
    List<Nota> findByDocenteCompetenciaSeccionPeriodo(@Param("idDocente") int idDocente,
                                                      @Param("idCompetencia") int idCompetencia,
                                                      @Param("seccion") String seccion,
                                                      @Param("periodo") String periodo,
                                                      @Param("anio") Integer anio);

    // ⬇️ NUEVO: Distribución de calificaciones por competencia (para gráfico de barras/torta)
    @Query(value = "SELECT n.calificacion, COUNT(*) as cantidad " +
            "FROM nota n " +
            "WHERE n.id_docente = :idDocente " +
            "AND n.id_competencia = :idCompetencia " +
            "AND n.periodo = :periodo " +
            "AND n.anio = :anio " +
            "AND n.id_estudiante IN (" +
            "  SELECT e.id_estudiante FROM estudiante e " +
            "  WHERE e.seccion = :seccion" +
            ") " +
            "GROUP BY n.calificacion " +
            "ORDER BY FIELD(n.calificacion, 'AD', 'A', 'B', 'C')",
            nativeQuery = true)
    List<Object[]> findDistribucionCalificaciones(@Param("idDocente") int idDocente,
                                                  @Param("idCompetencia") int idCompetencia,
                                                  @Param("seccion") String seccion,
                                                  @Param("periodo") String periodo,
                                                  @Param("anio") Integer anio);

    // ⬇️ NUEVO: Competencias con mayor riesgo (para HU-17)
    @Query(value = "SELECT c.id_competencia, c.nombre_competencia, " +
            "SUM(CASE WHEN n.calificacion = 'AD' THEN 1 ELSE 0 END) as ad_count, " +
            "SUM(CASE WHEN n.calificacion = 'A' THEN 1 ELSE 0 END) as a_count, " +
            "SUM(CASE WHEN n.calificacion = 'B' THEN 1 ELSE 0 END) as b_count, " +
            "SUM(CASE WHEN n.calificacion = 'C' THEN 1 ELSE 0 END) as c_count " +
            "FROM nota n " +
            "INNER JOIN competencia c ON n.id_competencia = c.id_competencia " +
            "INNER JOIN estudiante e ON n.id_estudiante = e.id_estudiante " +
            "WHERE n.id_docente = :idDocente " +
            "AND c.id_curso = :idCurso " +
            "AND e.seccion = :seccion " +
            "AND n.periodo = :periodo " +
            "AND n.anio = :anio " +
            "GROUP BY c.id_competencia, c.nombre_competencia " +
            "ORDER BY c_count DESC",
            nativeQuery = true)
    List<Object[]> findCompetenciasConMayorRiesgo(@Param("idDocente") int idDocente,
                                                  @Param("idCurso") int idCurso,
                                                  @Param("seccion") String seccion,
                                                  @Param("periodo") String periodo,
                                                  @Param("anio") Integer anio);

    // ⬇️ NUEVO: Evolución de estudiante por competencia específica (para HU-13)
    @Query("SELECT n FROM Nota n WHERE n.estudiante.idEstudiante = :idEstudiante AND n.competencia.idCompetencia = :idCompetencia ORDER BY n.anio ASC, n.periodo ASC")
    List<Nota> findEvolucionEstudiantePorCompetencia(@Param("idEstudiante") int idEstudiante,
                                                     @Param("idCompetencia") int idCompetencia);
}