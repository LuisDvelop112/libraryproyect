package LibraryProyect.dtos;


public class ReseñaResponseDTO {

    public String nombreUsuario;
    public String contenido;

    public ReseñaResponseDTO(String nombreUsuario, String contenido) {
        this.nombreUsuario = nombreUsuario;
        this.contenido = contenido;
    }
}