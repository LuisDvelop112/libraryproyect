
package LibraryProyect;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

import LibraryProyect.services.LibroService;
import LibraryProyect.entities.Libro;
import LibraryProyect.entities.Reseña;
import LibraryProyect.dtos.ReseñaRequestDTO;
import LibraryProyect.repositories.LibroRepository;
import LibraryProyect.services.ReseñaService;
import LibraryProyect.entities.Calificacion;           // <-- NUEVO
import LibraryProyect.dtos.CalificacionRequestDTO;     // <-- NUEVO
import LibraryProyect.services.CalificacionService;

@SpringBootApplication
public class LibraryProyectApplication implements CommandLineRunner {

    private final ReseñaService reseñaService;
    private final LibroRepository libroRepository;
    private final LibroService libroService;
    private final CalificacionService calificacionService;

    public LibraryProyectApplication(ReseñaService reseñaService,
                                     LibroRepository libroRepository,
                                     LibroService libroService,
                                     CalificacionService calificacionService) {
        this.reseñaService = reseñaService;
        this.libroRepository = libroRepository;
        this.libroService = libroService;
        this.calificacionService = calificacionService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryProyectApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== LIBRARY PROJECT =====");
            System.out.println("1. Crear libro");
            System.out.println("2. Listar libros");
            System.out.println("3. Escribir reseña");
            System.out.println("4. Ver reseñas de un libro");
            System.out.println("5. Búsqueda básica (autor o título)");
            System.out.println("6. Búsqueda avanzada (título, autor, ISBN)");
            System.out.println("7. Calificar libro (1-5)");
            System.out.println("8. Ver calificaciones de un libro");
            System.out.println("9. Ver promedio de calificaciones");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {

                case 1:
                    crearLibro(scanner);
                    break;

                case 2:
                    listarLibros();
                    break;

                case 3:
                    escribirReseña(scanner);
                    break;

                case 4:
                    verReseñas(scanner);
                    break;

                case 5:
                    busquedaBasica(scanner);
                    break;

                case 6:
                    busquedaAvanzada(scanner);
                    break;


                case 7:
                    calificarLibro(scanner);
                    break;

                case 8:
                    verCalificaciones(scanner);
                    break;

                case 9:
                    verPromedioCalificaciones(scanner);
                    break;

                case 10:
                    System.out.println("Saliendo del sistema...");
                    System.exit(0);
                    break;


                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 10);

        scanner.close();
    }

    // ==========================
    // MÉTODOS AUXILIARES
    // ==========================

    private void crearLibro(Scanner scanner) {

        Libro libro = new Libro();

        System.out.print("Título: ");
        libro.titulo = scanner.nextLine();

        System.out.print("Autor: ");
        libro.autor = scanner.nextLine();

        System.out.print("Precio: ");
        libro.precio = new java.math.BigDecimal(scanner.nextLine());

        System.out.print("Descripción: ");
        libro.descripcion = scanner.nextLine();

        System.out.print("ISBN: ");
        libro.isbn = scanner.nextLine();

        libroRepository.save(libro);

        System.out.println("Libro creado correctamente.");
    }

    private void listarLibros() {

        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("\n=== LISTA DE LIBROS ===");
        for (Libro libro : libros) {
            System.out.println("ID: " + libro.id +
                               " | Título: " + libro.titulo +
                               " | Autor: " + libro.autor +
                               " | Precio: $" + libro.precio +
                               " | ISBN: " + libro.isbn);
        }
    }

    private void escribirReseña(Scanner scanner) {

        System.out.print("ID del libro: ");
        Long libroId = Long.parseLong(scanner.nextLine());

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Contenido de la reseña: ");
        String contenido = scanner.nextLine();

        ReseñaRequestDTO dto = new ReseñaRequestDTO();
        dto.nombreUsuario = nombre;
        dto.correoUsuario = correo;
        dto.contenido = contenido;

        reseñaService.guardarReseña(libroId, dto);

        System.out.println("Reseña guardada correctamente.");
    }

    private void verReseñas(Scanner scanner) {

        System.out.print("ID del libro: ");
        Long libroId = Long.parseLong(scanner.nextLine());

        List<Reseña> reseñas = reseñaService.obtenerReseñasPorLibro(libroId);

        if (reseñas.isEmpty()) {
            System.out.println("Este libro no tiene reseñas.");
            return;
        }

        System.out.println("\n=== RESEÑAS ===");
        for (Reseña r : reseñas) {
            System.out.println("Usuario: " + r.usuario.nombre);
            System.out.println("Comentario: " + r.contenido);
            System.out.println("-----------------------");
        }
    }

    private void busquedaBasica(Scanner scanner) {

        System.out.print("Ingrese palabra o frase: ");
        String texto = scanner.nextLine();

        List<Libro> resultados = libroService.busquedaBasica(texto);

        mostrarResultadosBusqueda(resultados);
    }

    private void busquedaAvanzada(Scanner scanner) {

        System.out.print("Título (opcional): ");
        String titulo = scanner.nextLine();

        System.out.print("Autor (opcional): ");
        String autor = scanner.nextLine();

        System.out.print("ISBN (opcional): ");
        String isbn = scanner.nextLine();

        List<Libro> resultados =
                libroService.busquedaAvanzada(titulo, autor, isbn);

        mostrarResultadosBusqueda(resultados);
    }

    private void mostrarResultadosBusqueda(List<Libro> libros) {

    if (libros.isEmpty()) {
        System.out.println("No se encontraron resultados.");
        return;
    }

    System.out.println("\n=== RESULTADOS ===");
        for (Libro l : libros) {
            System.out.println("ID: " + l.id +
                                " | Título: " + l.titulo +
                                " | Autor: " + l.autor +
                                " | ISBN: " + l.isbn);
        }
    }

    private void calificarLibro(Scanner scanner) {
        System.out.print("ID del libro: ");
        Long libroId = Long.parseLong(scanner.nextLine());

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Calificación (1-5): ");
        Integer puntuacion = Integer.parseInt(scanner.nextLine());

        CalificacionRequestDTO dto = new CalificacionRequestDTO();
        dto.nombreUsuario = nombre;
        dto.correoUsuario = correo;
        dto.puntuacion = puntuacion;

        try {
            Calificacion calificacion = calificacionService.guardarCalificacion(libroId, dto);
            System.out.println("Calificación guardada: " + calificacion.puntuacion + " estrellas");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verCalificaciones(Scanner scanner) {
        System.out.print("ID del libro: ");
        Long libroId = Long.parseLong(scanner.nextLine());

        try {
            List<Calificacion> calificaciones = calificacionService.obtenerCalificacionesPorLibro(libroId);

            if (calificaciones.isEmpty()) {
                System.out.println("Este libro no tiene calificaciones.");
                return;
            }

            System.out.println("\n=== CALIFICACIONES ===");
            for (Calificacion c : calificaciones) {
                System.out.println("Usuario: " + c.usuario.nombre);
                System.out.println("Puntuación: " + c.puntuacion + "/5");
                System.out.println("-----------------------");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verPromedioCalificaciones(Scanner scanner) {
        System.out.print("ID del libro: ");
        Long libroId = Long.parseLong(scanner.nextLine());

        try {
            Double promedio = calificacionService.obtenerPromedioCalificaciones(libroId);
            List<Calificacion> calificaciones = calificacionService.obtenerCalificacionesPorLibro(libroId);

            System.out.println("\n=== ESTADÍSTICAS ===");
            System.out.println("Promedio: " + promedio + "/5");
            System.out.println("Total calificaciones: " + calificaciones.size());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
