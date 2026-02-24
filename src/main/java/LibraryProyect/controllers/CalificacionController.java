package LibraryProyect.controllers;


import LibraryProyect.dtos.CalificacionRequestDTO;
import LibraryProyect.entities.Calificacion;
import LibraryProyect.services.CalificacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    @PostMapping("/{libroId}/calificaciones")
    public Calificacion guardarCalificacion(@PathVariable Long libroId,
                                            @RequestBody CalificacionRequestDTO dto) {
        return calificacionService.guardarCalificacion(libroId, dto);
    }

    @GetMapping("/{libroId}/calificaciones")
    public List<Calificacion> obtenerCalificaciones(@PathVariable Long libroId) {
        return calificacionService.obtenerCalificacionesPorLibro(libroId);
    }

    @GetMapping("/{libroId}/calificaciones/promedio")
    public Double obtenerPromedio(@PathVariable Long libroId) {
        return calificacionService.obtenerPromedioCalificaciones(libroId);
    }
}
