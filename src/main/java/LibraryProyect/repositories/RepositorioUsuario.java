package LibraryProyect.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import LibraryProyect.entities.Usuario;
import java.util.Optional;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);
}