package com.com.jwtdemo.controller;

import com.com.jwtdemo.dto.NotaDTO;
import com.com.jwtdemo.model.Nota;
import com.com.jwtdemo.servicesinterfaces.NotaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            // Validar que la calificación sea válida
            if (!esCalificacionValida(dto.getCalificacion())) {
                response.put("message", "Calificación inválida. Use: AD, A, B o C");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ModelMapper m = new ModelMapper();
            Nota nota = m.map(dto, Nota.class);
            nS.insert(nota);
            response.put("message", "Nota registrada exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error al registrar nota: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public List<NotaDTO> listar() {
        return nS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            NotaDTO dto = m.map(x, NotaDTO.class);
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
            if (!esCalificacionValida(dto.getCalificacion())) {
                response.put("message", "Calificación inválida. Use: AD, A, B o C");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

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

    // ⬇️ NUEVO: Endpoint para riesgo académico (≥2 competencias con C)
    @GetMapping("/riesgo/academico/{periodo}/{anio}")
    public ResponseEntity<List<Map<String, Object>>> listarEstudiantesRiesgoAcademico(
            @PathVariable("periodo") String periodo,
            @PathVariable("anio") Integer anio) {
        List<Map<String, Object>> estudiantes = nS.findEstudiantesConRiesgoAcademico(periodo, anio);
        return ResponseEntity.ok(estudiantes);
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

    // ========== NUEVOS ENDPOINTS PARA DOCENTES (HU-13, 15, 16, 17) ==========

    // ⬇️ HU-15: Filtrar notas por curso, competencia y sección del docente
    @GetMapping("/docente/{idDocente}/curso/{idCurso}/competencia/{idCompetencia}/seccion/{seccion}/periodo/{periodo}/anio/{anio}")
    public List<NotaDTO> listarPorDocenteCompetenciaSeccion(
            @PathVariable("idDocente") int idDocente,
            @PathVariable("idCurso") int idCurso,
            @PathVariable("idCompetencia") int idCompetencia,
            @PathVariable("seccion") String seccion,
            @PathVariable("periodo") String periodo,
            @PathVariable("anio") Integer anio) {

        return nS.findByDocenteCompetenciaSeccionPeriodo(idDocente, idCompetencia, seccion, periodo, anio)
                .stream().map(x -> {
                    ModelMapper m = new ModelMapper();
                    NotaDTO dto = m.map(x, NotaDTO.class);
                    dto.setNombreEstudiante(x.getEstudiante().getNombres() + " " + x.getEstudiante().getApellidos());
                    dto.setNombreCompetencia(x.getCompetencia().getNombreCompetencia());
                    dto.setEstadoAcademico(calcularEstadoAcademico(x.getCalificacion()));
                    return dto;
                }).collect(Collectors.toList());
    }

    // ⬇️ HU-16: Distribución de calificaciones para gráfico
    @GetMapping("/docente/{idDocente}/distribucion/competencia/{idCompetencia}/seccion/{seccion}/periodo/{periodo}/anio/{anio}")
    public ResponseEntity<Map<String, Integer>> obtenerDistribucionCalificaciones(
            @PathVariable("idDocente") int idDocente,
            @PathVariable("idCompetencia") int idCompetencia,
            @PathVariable("seccion") String seccion,
            @PathVariable("periodo") String periodo,
            @PathVariable("anio") Integer anio) {

        Map<String, Integer> distribucion = nS.findDistribucionCalificaciones(
                idDocente, idCompetencia, seccion, periodo, anio);
        return ResponseEntity.ok(distribucion);
    }

    // ⬇️ HU-17: Competencias con mayor riesgo
    @GetMapping("/docente/{idDocente}/competencias-riesgo/curso/{idCurso}/seccion/{seccion}/periodo/{periodo}/anio/{anio}")
    public ResponseEntity<List<Map<String, Object>>> obtenerCompetenciasConRiesgo(
            @PathVariable("idDocente") int idDocente,
            @PathVariable("idCurso") int idCurso,
            @PathVariable("seccion") String seccion,
            @PathVariable("periodo") String periodo,
            @PathVariable("anio") Integer anio) {

        List<Map<String, Object>> competencias = nS.findCompetenciasConMayorRiesgo(
                idDocente, idCurso, seccion, periodo, anio);
        return ResponseEntity.ok(competencias);
    }

    // ⬇️ HU-13: Evolución de estudiante por competencia específica
    @GetMapping("/evolucion/estudiante/{idEstudiante}/competencia/{idCompetencia}")
    public List<NotaDTO> obtenerEvolucionPorCompetencia(
            @PathVariable("idEstudiante") int idEstudiante,
            @PathVariable("idCompetencia") int idCompetencia) {

        return nS.findEvolucionEstudiantePorCompetencia(idEstudiante, idCompetencia)
                .stream().map(x -> {
                    ModelMapper m = new ModelMapper();
                    NotaDTO dto = m.map(x, NotaDTO.class);
                    dto.setNombreCompetencia(x.getCompetencia().getNombreCompetencia());
                    dto.setEstadoAcademico(calcularEstadoAcademico(x.getCalificacion()));
                    return dto;
                }).collect(Collectors.toList());
    }

    // ⬇️ Método auxiliar para validar calificaciones
    private boolean esCalificacionValida(String calificacion) {
        return calificacion != null &&
                (calificacion.equals("AD") || calificacion.equals("A") ||
                        calificacion.equals("B") || calificacion.equals("C"));
    }

    // ⬇️ ACTUALIZADO: Método para calcular estado académico con letras
    private String calcularEstadoAcademico(String calificacion) {
        switch (calificacion) {
            case "AD": return "Logro Destacado";
            case "A": return "Logro Esperado";
            case "B": return "En Proceso";
            case "C": return "En Inicio";
            default: return "Sin Calificación";
        }
    }
}