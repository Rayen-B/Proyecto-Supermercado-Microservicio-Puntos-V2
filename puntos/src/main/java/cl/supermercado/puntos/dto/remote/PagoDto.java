package cl.supermercado.puntos.dto.remote;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter             @Setter
@AllArgsConstructor @NoArgsConstructor
public class PagoDto {

    private Long id;
    private Long usuarioId;
    private Double monto;
    private Boolean exitoso;
    private LocalDateTime fechaPago;

}
