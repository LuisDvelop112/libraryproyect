package LibraryProyect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import LibraryProyect.entities.Reseña;
import java.util.List;

@Repository
public interface ReseñaRepository extends JpaRepository<Reseña, Long> {

    List<Reseña> findByLibroId(Long libroId);

}