package com.com.jwtdemo.controller;

import com.com.jwtdemo.dto.NotaDTO;
import com.com.jwtdemo.model.Nota;
import com.com.jwtdemo.servicesinterfaces.NotaService;
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
@RequestMapping("/nota")
@CrossOrigin(origins = {"http://localhost:4200"})
public class NotaController {

    @Autowired
    private NotaService nS;

    @PostMapping
    public ResponseEntity<Map<String, String>> registrar(@RequestBody NotaDTO dto) {
        Map<String, String> response = new HashMap<>();
        try {
            ModelMapper m = new ModelMapper();
            Nota nota = m.map(dto, Nota.class);
            nS.insert(nota);
            response.put("message", "Nota registrada exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error al registrar nota");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public List<NotaDTO> listar() {
        return nS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            NotaDTO dto = m.map(x, NotaDTO.class);
            // Agregar información adicional
            dto.setNombreEstudiante(x.getEstudiante().getNombres() + " " + x.getEstudiante().getApellidos());
            dto.setNombreCompetencia(x.getCompetencia().getNombreCompetencia());
            dto.setNombreDocente(x.getDocente().getNombres() + " " + x.getDocente().getApellidos());
            dto.setEstadoAcademico(calcularEstadoAcademico(x.getCalificacion()));
            return dto;
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable("id") Integer id) {
        Map<String, String> response = new HashMap<>();
        try {
            nS.delete(id);
            response.put("message", "Nota eliminada exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error al eliminar nota");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> modificar(@RequestBody NotaDTO dto) {
        Map<String, String> response = new HashMap<>();
        try {
            ModelMapper m = new ModelMapper();
            Nota nota = m.map(dto, Nota.class);
            nS.update(nota);
            response.put("message", "Nota actualizada exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error al actualizar nota");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public NotaDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        Nota nota = nS.listarId(id);
        NotaDTO dto = m.map(nota, NotaDTO.class);
        dto.setNombreEstudiante(nota.getEstudiante().getNombres() + " " + nota.getEstudiante().getApellidos());
        dto.setNombreCompetencia(nota.getCompetencia().getNombreCompetencia());
        dto.setNombreDocente(nota.getDocente().getNombres() + " " + nota.getDocente().getApellidos());
        return dto;
    }

    // Endpoints específicos para reportes (mantener igual)

    @GetMapping("/estudiante/{idEstudiante}")
    public List<NotaDTO> listarPorEstudiante(@PathVariable("idEstudiante") int idEstudiante) {
        return nS.findByEstudiante(idEstudiante).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            NotaDTO dto = m.map(x, NotaDTO.class);
            dto.setNombreCompetencia(x.getCompetencia().getNombreCompetencia());
            dto.setEstadoAcademico(calcularEstadoAcademico(x.getCalificacion()));
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/riesgo/{periodo}/{anio}")
    public List<NotaDTO> listarEstudiantesEnRiesgo(@PathVariable("periodo") String periodo,
                                                   @PathVariable("anio") Integer anio) {
        return nS.findEstudiantesEnRiesgo(periodo, anio).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            NotaDTO dto = m.map(x, NotaDTO.class);
            dto.setNombreEstudiante(x.getEstudiante().getNombres() + " " + x.getEstudiante().getApellidos());
            dto.setNombreCompetencia(x.getCompetencia().getNombreCompetencia());
            dto.setEstadoAcademico("En Riesgo");
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/promedio/{idEstudiante}/{periodo}/{anio}")
    public ResponseEntity<Map<String, Double>> obtenerPromedio(@PathVariable("idEstudiante") int idEstudiante,
                                                               @PathVariable("periodo") String periodo,
                                                               @PathVariable("anio") Integer anio) {
        Map<String, Double> response = new HashMap<>();
        Double promedio = nS.findPromedioEstudiante(idEstudiante, periodo, anio);
        response.put("promedio", promedio != null ? promedio : 0.0);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/grado/{grado}/seccion/{seccion}/periodo/{periodo}/anio/{anio}")
    public List<NotaDTO> listarPorGradoSeccion(@PathVariable("grado") String grado,
                                               @PathVariable("seccion") String seccion,
                                               @PathVariable("periodo") String periodo,
                                               @PathVariable("anio") Integer anio) {
        return nS.findByGradoSeccionPeriodo(grado, seccion, periodo, anio).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            NotaDTO dto = m.map(x, NotaDTO.class);
            dto.setNombreEstudiante(x.getEstudiante().getNombres() + " " + x.getEstudiante().getApellidos());
            dto.setNombreCompetencia(x.getCompetencia().getNombreCompetencia());
            dto.setEstadoAcademico(calcularEstadoAcademico(x.getCalificacion()));
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/evolucion/{idEstudiante}")
    public List<NotaDTO> obtenerEvolucion(@PathVariable("idEstudiante") int idEstudiante) {
        return nS.findEvolucionEstudiante(idEstudiante).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            NotaDTO dto = m.map(x, NotaDTO.class);
            dto.setNombreCompetencia(x.getCompetencia().getNombreCompetencia());
            dto.setEstadoAcademico(calcularEstadoAcademico(x.getCalificacion()));
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/competencia/{idCompetencia}/grado/{grado}/periodo/{periodo}/anio/{anio}")
    public List<NotaDTO> listarPorCompetenciaGrado(@PathVariable("idCompetencia") int idCompetencia,
                                                   @PathVariable("grado") String grado,
                                                   @PathVariable("periodo") String periodo,
                                                   @PathVariable("anio") Integer anio) {
        return nS.findByCompetenciaGradoPeriodo(idCompetencia, grado, periodo, anio).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            NotaDTO dto = m.map(x, NotaDTO.class);
            dto.setNombreEstudiante(x.getEstudiante().getNombres() + " " + x.getEstudiante().getApellidos());
            dto.setEstadoAcademico(calcularEstadoAcademico(x.getCalificacion()));
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/riesgo/count/{grado}/{periodo}/{anio}")
    public ResponseEntity<Map<String, Long>> contarEstudiantesEnRiesgo(@PathVariable("grado") String grado,
                                                                       @PathVariable("periodo") String periodo,
                                                                       @PathVariable("anio") Integer anio) {
        Map<String, Long> response = new HashMap<>();
        Long count = nS.countEstudiantesEnRiesgoByGrado(grado, periodo, anio);
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    // Método auxiliar para calcular estado académico
    private String calcularEstadoAcademico(Double calificacion) {
        if (calificacion < 11) return "En Riesgo";
        if (calificacion < 14) return "Regular";
        if (calificacion < 17) return "Satisfactorio";
        return "Destacado";
    }
}