package com.com.jwtdemo.controller;

import com.com.jwtdemo.dto.AsignacionDocenteDTO;
import com.com.jwtdemo.model.AsignacionDocente;
import com.com.jwtdemo.servicesinterfaces.AsignacionDocenteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/asignacion")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AsignacionDocenteController {

    @Autowired
    private AsignacionDocenteService adS;

    @PostMapping
    public ResponseEntity<Map<String, String>> registrar(@RequestBody AsignacionDocenteDTO dto) {
        Map<String, String> response = new HashMap<>();
        try {
            // Validar si ya existe la asignación
            if (adS.existeAsignacion(dto.getIdDocente(), dto.getIdCurso(),
                    dto.getGrado(), dto.getSeccion(), dto.getAnioAcademico())) {
                response.put("message", "Ya existe una asignación con estos datos");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            ModelMapper m = new ModelMapper();
            AsignacionDocente asignacion = m.map(dto, AsignacionDocente.class);
            adS.insert(asignacion);
            response.put("message", "Asignación registrada exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error al registrar asignación");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public List<AsignacionDocenteDTO> listar() {
        return adS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            AsignacionDocenteDTO dto = m.map(x, AsignacionDocenteDTO.class);
            dto.setNombreDocente(x.getDocente().getNombres() + " " + x.getDocente().getApellidos());
            dto.setNombreCurso(x.getCurso().getNombreCurso());
            return dto;
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable("id") Integer id) {
        Map<String, String> response = new HashMap<>();
        try {
            adS.delete(id);
            response.put("message", "Asignación eliminada exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error al eliminar asignación");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> modificar(@RequestBody AsignacionDocenteDTO dto) {
        Map<String, String> response = new HashMap<>();
        try {
            ModelMapper m = new ModelMapper();
            AsignacionDocente asignacion = m.map(dto, AsignacionDocente.class);
            adS.update(asignacion);
            response.put("message", "Asignación actualizada exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error al actualizar asignación");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public AsignacionDocenteDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        AsignacionDocente asignacion = adS.listarId(id);
        AsignacionDocenteDTO dto = m.map(asignacion, AsignacionDocenteDTO.class);
        dto.setNombreDocente(asignacion.getDocente().getNombres() + " " + asignacion.getDocente().getApellidos());
        dto.setNombreCurso(asignacion.getCurso().getNombreCurso());
        return dto;
    }

    @GetMapping("/docente/{idDocente}")
    public List<AsignacionDocenteDTO> listarPorDocente(@PathVariable("idDocente") int idDocente) {
        return adS.findByDocente(idDocente).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            AsignacionDocenteDTO dto = m.map(x, AsignacionDocenteDTO.class);
            dto.setNombreCurso(x.getCurso().getNombreCurso());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/grado/{grado}/seccion/{seccion}")
    public List<AsignacionDocenteDTO> listarPorGradoSeccion(@PathVariable("grado") String grado,
                                                            @PathVariable("seccion") String seccion) {
        return adS.findByGradoSeccion(grado, seccion).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            AsignacionDocenteDTO dto = m.map(x, AsignacionDocenteDTO.class);
            dto.setNombreDocente(x.getDocente().getNombres() + " " + x.getDocente().getApellidos());
            dto.setNombreCurso(x.getCurso().getNombreCurso());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/anio/{anio}")
    public List<AsignacionDocenteDTO> listarPorAnio(@PathVariable("anio") Integer anio) {
        return adS.findByAnioAcademico(anio).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            AsignacionDocenteDTO dto = m.map(x, AsignacionDocenteDTO.class);
            dto.setNombreDocente(x.getDocente().getNombres() + " " + x.getDocente().getApellidos());
            dto.setNombreCurso(x.getCurso().getNombreCurso());
            return dto;
        }).collect(Collectors.toList());
    }
}