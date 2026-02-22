package LibraryProyect.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String titulo;

    public String autor;

    public BigDecimal precio;

    @Column(length = 2000)
    public String descripcion;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    public List<Reseña> reseñas;
}