package LibraryProyect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import LibraryProyect.entities.Reseña;
import java.util.List;

@Repository
public interface RepositorioReseña extends JpaRepository<Reseña, Long> {
    
    // Nuevo método para buscar reseñas por ID de libro
    List<Reseña> findByLibroId(Long libroId);
}