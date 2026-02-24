package LibraryProyect.services;

import LibraryProyect.dtos.CalificacionRequestDTO;
import LibraryProyect.entities.Calificacion;
import LibraryProyect.entities.Libro;
import LibraryProyect.entities.Usuario;
import LibraryProyect.repositories.CalificacionRepository;
import LibraryProyect.repositories.LibroRepository;
import LibraryProyect.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CalificacionService {
    private final CalificacionRepository repositorioCalificacion;
    private final LibroRepository repositorioLibro;
    private final UsuarioRepository repositorioUsuario;


    public CalificacionService(CalificacionRepository repositorioCalificacion,
                               LibroRepository repositorioLibro,
                               UsuarioRepository repositorioUsuario) {
        this.repositorioCalificacion = repositorioCalificacion;
        this.repositorioLibro = repositorioLibro;
        this.repositorioUsuario = repositorioUsuario;
    }

    public Calificacion guardarCalificacion(Long libroId, CalificacionRequestDTO dto) {

        if (dto.puntuacion == null || dto.puntuacion < 1 || dto.puntuacion > 5) {
            throw new RuntimeException("La calificación debe ser entre 1 y 5");
        }

        Libro libro = repositorioLibro.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Usuario usuario = repositorioUsuario.findByCorreo(dto.correoUsuario)
                .orElseGet(() -> {
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.nombre = dto.nombreUsuario;
                    nuevoUsuario.correo = dto.correoUsuario;
                    return repositorioUsuario.save(nuevoUsuario);
                });


        Optional<Calificacion> calificacionExistente = repositorioCalificacion
                .findByUsuarioIdAndLibroId(usuario.id, libroId);

        if (calificacionExistente.isPresent()) {
            Calificacion calificacion = calificacionExistente.get();
            calificacion.puntuacion = dto.puntuacion;
            return repositorioCalificacion.save(calificacion);
        }

        Calificacion calificacion = new Calificacion();
        calificacion.puntuacion = dto.puntuacion;
        calificacion.libro = libro;
        calificacion.usuario = usuario;

        return repositorioCalificacion.save(calificacion);
    }

    public List<Calificacion> obtenerCalificacionesPorLibro(Long libroId) {

        if (!repositorioLibro.existsById(libroId)) {
            throw new RuntimeException("Libro no encontrado");
        }

        return repositorioCalificacion.findByLibroId(libroId);
    }

    public Double obtenerPromedioCalificaciones(Long libroId) {

        List<Calificacion> calificaciones = repositorioCalificacion.findByLibroId(libroId);

        if (calificaciones.isEmpty()) {
            return 0.0;
        }

        double suma = 0;
        for (Calificacion c : calificaciones) {
            suma += c.puntuacion;
        }

        double promedio = suma / calificaciones.size();
        return Math.round(promedio * 10) / 10.0;
    }

}
