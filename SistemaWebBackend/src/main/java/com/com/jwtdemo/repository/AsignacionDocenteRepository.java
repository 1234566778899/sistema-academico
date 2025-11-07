package com.com.jwtdemo.repository;

import com.com.jwtdemo.model.AsignacionDocente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionDocenteRepository extends JpaRepository<AsignacionDocente, Integer> {

    // Buscar asignaciones por docente
    List<AsignacionDocente> findByDocenteId(int idDocente);

    // Buscar asignaciones por curso
    List<AsignacionDocente> findByCursoIdCurso(int idCurso);

    // Buscar asignaciones por grado y sección
    List<AsignacionDocente> findByGradoAndSeccion(String grado, String seccion);

    // Buscar asignaciones por año académico
    List<AsignacionDocente> findByAnioAcademico(Integer anio);

    // Buscar si existe una asignación específica (evitar duplicados)
    @Query("SELECT COUNT(a) FROM AsignacionDocente a WHERE a.docente.id = :idDocente AND a.curso.idCurso = :idCurso AND a.grado = :grado AND a.seccion = :seccion AND a.anioAcademico = :anio")
    Long existeAsignacion(@Param("idDocente") int idDocente,
                          @Param("idCurso") int idCurso,
                          @Param("grado") String grado,
                          @Param("seccion") String seccion,
                          @Param("anio") Integer anio);

    // Listar docentes asignados a un grado específico
    @Query("SELECT DISTINCT a.docente FROM AsignacionDocente a WHERE a.grado = :grado AND a.anioAcademico = :anio")
    List<Object> findDocentesByGrado(@Param("grado") String grado, @Param("anio") Integer anio);
}