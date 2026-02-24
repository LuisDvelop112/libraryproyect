package LibraryProyect.repositories;

import LibraryProyect.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    // Buscar calificación de un usuario específico para un libro
    Optional<Calificacion> findByUsuarioIdAndLibroId(Long usuarioId, Long libroId);

    // Verificar si ya calificó
    boolean existsByUsuarioIdAndLibroId(Long usuarioId, Long libroId);

    // Obtener todas las calificaciones de un libro
    List<Calificacion> findByLibroId(Long libroId);

    // Calcular promedio de calificaciones de un libro
    @Query("SELECT AVG(c.puntuacion) FROM Calificacion c WHERE c.libro.id = :libroId")
    Double calcularPromedioPorLibro(@Param("libroId") Long libroId);

    // Contar cuántas calificaciones tiene un libro
    @Query("SELECT COUNT(c) FROM Calificacion c WHERE c.libro.id = :libroId")
    Long contarCalificacionesPorLibro(@Param("libroId") Long libroId);

    // Obtener todas las calificaciones de un usuario
    List<Calificacion> findByUsuarioId(Long usuarioId);
}
