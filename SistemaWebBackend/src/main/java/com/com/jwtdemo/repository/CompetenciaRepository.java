package com.com.jwtdemo.repository;

import com.com.jwtdemo.model.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Integer> {

}
