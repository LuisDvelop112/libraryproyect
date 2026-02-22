package LibraryProyect.services;



import java.util.List;

import org.springframework.stereotype.Service;
import LibraryProyect.entities.*;
import LibraryProyect.repositories.*;
import LibraryProyect.dtos.*;

@Service
public class ReseñaService {

    private final ReseñaRepository reseñaRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public ReseñaService(ReseñaRepository reseñaRepository,
                         LibroRepository libroRepository,
                         UsuarioRepository usuarioRepository) {
        this.reseñaRepository = reseñaRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ReseñaResponseDTO previsualizarReseña(ReseñaRequestDTO dto) {

        if (dto.contenido == null || dto.contenido.isBlank()) {
            throw new RuntimeException("La reseña no puede estar vacía");
        }

        return new ReseñaResponseDTO(dto.nombreUsuario, dto.contenido);
    }

    public Reseña guardarReseña(Long libroId, ReseñaRequestDTO dto) {

        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (dto.contenido == null || dto.contenido.isBlank()) {
            throw new RuntimeException("La reseña no puede estar vacía");
        }

        // 🔹 Buscar usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(dto.correoUsuario)
                .orElseGet(() -> {
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.nombre = dto.nombreUsuario;
                    nuevoUsuario.correo = dto.correoUsuario;
                    return usuarioRepository.save(nuevoUsuario);
                });

        Reseña reseña = new Reseña();
        reseña.contenido = dto.contenido;
        reseña.libro = libro;
        reseña.usuario = usuario;

        return reseñaRepository.save(reseña);
    }
    public List<Reseña> obtenerReseñasPorLibro(Long libroId) {
    
    // Verifica que el libro existe (opcional, pero buena práctica)
    if (!libroRepository.existsById(libroId)) {
        throw new RuntimeException("Libro no encontrado");
    }
    
    // Busca las reseñas directamente en el repositorio de reseñas
    return reseñaRepository.findByLibroId(libroId);
}
}