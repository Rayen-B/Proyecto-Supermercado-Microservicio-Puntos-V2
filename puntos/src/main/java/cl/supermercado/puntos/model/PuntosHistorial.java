package cl.supermercado.puntos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "puntos_historial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PuntosHistorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "compra_id", nullable = false, unique = true)
    private Long compraId;

    @Column(name = "puntos_otorgados", nullable = false)
    private Integer puntosOtorgados;

}
