package LibraryProyect.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "calificaciones",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "libro_id"}))
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Integer puntuacion;  // 1 a 5

    @ManyToOne
    @JoinColumn(name = "libro_id")
    public Libro libro;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public Usuario usuario;


}
