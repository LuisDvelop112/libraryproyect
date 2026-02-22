package LibraryProyect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import LibraryProyect.entities.Libro;

@Repository
public interface RepositorioLibro extends JpaRepository<Libro, Long> {
}