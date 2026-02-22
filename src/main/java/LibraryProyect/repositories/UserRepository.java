package LibraryProyect.repositories;

import LibraryProyect.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    // Ejemplo: método de consulta adicional
    boolean existsByEmail(String email);
}
