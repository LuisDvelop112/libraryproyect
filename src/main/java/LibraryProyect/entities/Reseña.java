package LibraryProyect.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name="reseñas")
public class Reseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(length = 2000)
    public String contenido;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "libro_id")
    public Libro libro;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public Usuario usuario;

}

