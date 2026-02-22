package LibraryProyect.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String titulo;

    public String autor;

    public BigDecimal precio;

    @Column(length = 2000)
    public String descripcion;

    @JsonManagedReference
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    private List<Reseña> reseñas;
}