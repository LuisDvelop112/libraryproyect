package LibraryProyect.entities;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nombre;

    @Column(unique = true)
    public String correo;

    @OneToMany(mappedBy = "usuario")
    public List<Reseña> reseñas;
}