package com.com.jwtdemo.controller;

import com.com.jwtdemo.dto.CursoDTO;
import com.com.jwtdemo.model.Curso;
import com.com.jwtdemo.servicesinterfaces.CursoService;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService cS;

    @PostMapping
    public void registrar(@RequestBody CursoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Curso curso = modelMapper.map(dto, Curso.class);
        cS.insert(curso);
    }

    @GetMapping
    public List<CursoDTO> listar() {
        return cS.list().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, CursoDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        cS.delete(id);
    }

    @PutMapping
    public void actualizar(@RequestBody CursoDTO dto){
        ModelMapper m = new ModelMapper();
        Curso c = m.map(dto, Curso.class);
        cS.insert(c);
    }

    @GetMapping("/{id}")
    public CursoDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m=new ModelMapper();
        CursoDTO dto = m.map(cS.listarId(id),CursoDTO.class);
        return dto;
    }
}
