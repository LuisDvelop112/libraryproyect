package LibraryProyect.entities;

import jakarta.persistence.*;

@Entity
public class Reseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(length = 2000)
    public String contenido;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    public Libro libro;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public Usuario usuario;
}

