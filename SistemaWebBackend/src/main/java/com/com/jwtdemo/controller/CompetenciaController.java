package com.com.jwtdemo.controller;

import com.com.jwtdemo.dto.CompetenciaDTO;
import com.com.jwtdemo.model.Competencia;
import com.com.jwtdemo.servicesinterfaces.CompetenciaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/competencia")
public class CompetenciaController {

    @Autowired
    private CompetenciaService cS;

    @PostMapping
    public void registrar(@RequestBody CompetenciaDTO dto) {
        ModelMapper m = new ModelMapper();
        Competencia c = m.map(dto, Competencia.class);
        cS.insert(c);
    }

    @GetMapping
    public List<CompetenciaDTO> listar() {
        return cS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, CompetenciaDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        cS.delete(id);
    }

    @PutMapping
    public void modificar(@RequestBody CompetenciaDTO dto) {
        ModelMapper m = new ModelMapper();
        Competencia c = m.map(dto, Competencia.class);
        cS.insert(c);
    }

    @GetMapping("/{id}")
    public CompetenciaDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m=new ModelMapper();
        CompetenciaDTO dto=m.map(cS.listarId(id), CompetenciaDTO.class);
        return dto;
    }
}
