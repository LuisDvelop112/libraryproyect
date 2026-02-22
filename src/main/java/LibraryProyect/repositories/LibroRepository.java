package LibraryProyect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import LibraryProyect.entities.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // BÚSQUEDA BÁSICA (autor o título)
    List<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(String titulo, String autor);

    // BÚSQUEDA AVANZADA
    @Query("""
        SELECT l FROM Libro l
        WHERE (:titulo IS NULL OR LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')))
        AND (:autor IS NULL OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :autor, '%')))
        AND (:isbn IS NULL OR l.isbn = :isbn)
    """)
    List<Libro> busquedaAvanzada(String titulo, String autor, String isbn);
}