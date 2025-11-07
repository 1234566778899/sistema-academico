package com.com.jwtdemo.controller;

import com.com.jwtdemo.dto.EstudianteDTO;
import com.com.jwtdemo.model.Estudiante;
import com.com.jwtdemo.servicesinterfaces.EstudianteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    private EstudianteService eS;

    @PostMapping
    public void registrar(@RequestBody EstudianteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Estudiante estudiante = modelMapper.map(dto, Estudiante.class);
        eS.insert(estudiante);
    }

    @GetMapping
    public List<EstudianteDTO> listar() {
        return eS.list().stream().map(x -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(x, EstudianteDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        eS.delete(id);
    }

    //Validar si corre tudo been
    @PutMapping
    public void actualizar(@RequestBody EstudianteDTO dto){
        ModelMapper m = new ModelMapper();
        Estudiante e = m.map(dto,Estudiante.class);
        eS.insert(e);
    }

    @GetMapping("/{id}")
    public EstudianteDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m=new ModelMapper();
        EstudianteDTO dto=m.map(eS.listarId(id),EstudianteDTO.class);
        return dto;
    }

}
