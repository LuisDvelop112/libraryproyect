package LibraryProyect.dtos;

import LibraryProyect.entities.Calificacion;

import java.time.format.DateTimeFormatter;

public class CalificacionResponseDTO {
    public String nombreUsuario;
    public Integer puntuacion;

    public CalificacionResponseDTO(String nombreUsuario, Integer puntuacion) {
        this.nombreUsuario = nombreUsuario;
        this.puntuacion = puntuacion;
    }
}
