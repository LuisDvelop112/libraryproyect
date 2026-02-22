package LibraryProyect.services;

import LibraryProyect.entities.Libro;
import LibraryProyect.repositories.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository repositorioLibro;

    public LibroService(LibroRepository repositorioLibro) {
        this.repositorioLibro = repositorioLibro;
    }

    public Libro guardarLibro(Libro libro) {
        return repositorioLibro.save(libro);
    }

    public List<Libro> listarLibros() {
        return repositorioLibro.findAll();
    }

    public Libro obtenerLibroPorId(Long id) {
        return repositorioLibro.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    public void eliminarLibro(Long id) {
        repositorioLibro.deleteById(id);
    }
}
