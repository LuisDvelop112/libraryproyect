package LibraryProyect.services;



import java.util.List;

import org.springframework.stereotype.Service;
import LibraryProyect.entities.*;
import LibraryProyect.repositories.*;
import LibraryProyect.dtos.*;

@Service
public class ServicioReseña {

    private final RepositorioReseña repositorioReseña;
    private final RepositorioLibro repositorioLibro;
    private final RepositorioUsuario repositorioUsuario;

    public ServicioReseña(RepositorioReseña repositorioReseña,
                          RepositorioLibro repositorioLibro,
                          RepositorioUsuario repositorioUsuario) {
        this.repositorioReseña = repositorioReseña;
        this.repositorioLibro = repositorioLibro;
        this.repositorioUsuario = repositorioUsuario;
    }

    public ReseñaResponseDTO previsualizarReseña(ReseñaRequestDTO dto) {

        if (dto.contenido == null || dto.contenido.isBlank()) {
            throw new RuntimeException("La reseña no puede estar vacía");
        }

        return new ReseñaResponseDTO(dto.nombreUsuario, dto.contenido);
    }

    public Reseña guardarReseña(Long libroId, ReseñaRequestDTO dto) {

        Libro libro = repositorioLibro.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (dto.contenido == null || dto.contenido.isBlank()) {
            throw new RuntimeException("La reseña no puede estar vacía");
        }

        // 🔹 Buscar usuario por correo
        Usuario usuario = repositorioUsuario.findByCorreo(dto.correoUsuario)
                .orElseGet(() -> {
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.nombre = dto.nombreUsuario;
                    nuevoUsuario.correo = dto.correoUsuario;
                    return repositorioUsuario.save(nuevoUsuario);
                });

        Reseña reseña = new Reseña();
        reseña.contenido = dto.contenido;
        reseña.libro = libro;
        reseña.usuario = usuario;

        return repositorioReseña.save(reseña);
    }
    public List<Reseña> obtenerReseñasPorLibro(Long libroId) {
    
    // Verifica que el libro existe (opcional, pero buena práctica)
    if (!repositorioLibro.existsById(libroId)) {
        throw new RuntimeException("Libro no encontrado");
    }
    
    // Busca las reseñas directamente en el repositorio de reseñas
    return repositorioReseña.findByLibroId(libroId);
}
}