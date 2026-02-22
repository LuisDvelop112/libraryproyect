
package LibraryProyect;  

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

import LibraryProyect.entities.Libro;
import LibraryProyect.entities.Reseña;
import LibraryProyect.dtos.ReseñaRequestDTO;
import LibraryProyect.repositories.RepositorioLibro;
import LibraryProyect.services.ServicioReseña;

@SpringBootApplication
public class LibraryProyectApplication implements CommandLineRunner {

    private final ServicioReseña servicioReseña;
    private final RepositorioLibro repositorioLibro;

    public LibraryProyectApplication(ServicioReseña servicioReseña,
                                     RepositorioLibro repositorioLibro) {
        this.servicioReseña = servicioReseña;
        this.repositorioLibro = repositorioLibro;
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
            System.out.println("5. Salir");
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
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 5);

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

        repositorioLibro.save(libro);

        System.out.println("Libro creado correctamente.");
    }

    private void listarLibros() {

        List<Libro> libros = repositorioLibro.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("\n=== LISTA DE LIBROS ===");
        for (Libro libro : libros) {
            System.out.println("ID: " + libro.id +
                               " | Título: " + libro.titulo +
                               " | Autor: " + libro.autor +
                               " | Precio: $" + libro.precio);
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

        servicioReseña.guardarReseña(libroId, dto);

        System.out.println("Reseña guardada correctamente.");
    }

    private void verReseñas(Scanner scanner) {

        System.out.print("ID del libro: ");
        Long libroId = Long.parseLong(scanner.nextLine());

        List<Reseña> reseñas = servicioReseña.obtenerReseñasPorLibro(libroId);

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
}