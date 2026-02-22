package LibraryProyect.controllers;

import LibraryProyect.dtos.ReseñaRequestDTO;
import LibraryProyect.entities.Reseña;
import LibraryProyect.services.ReseñaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class ReseñaController {
    private final ReseñaService reseñaService;

    public ReseñaController(ReseñaService reseñaService) {
        this.reseñaService = reseñaService;
    }

    @PostMapping("/{libroId}/reseñas")
    public Reseña guardarReseña(@PathVariable Long libroId,
                                @RequestBody ReseñaRequestDTO dto) {
        return reseñaService.guardarReseña(libroId, dto);
    }

    @GetMapping("/{libroId}/reseñas")
    public List<Reseña> obtenerReseñas(@PathVariable Long libroId) {
        return reseñaService.obtenerReseñasPorLibro(libroId);
    }
}
