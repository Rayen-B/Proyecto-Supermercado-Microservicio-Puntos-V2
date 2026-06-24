package cl.supermercado.puntos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PuntosRequestDto {

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "La compra es obligatoria")
    private Long compraId;

    // Total del monto de la compra para calcular puntos sin llamar a ms-pago
    private Double total;

}
