package cl.supermercado.puntos.service.api;

import cl.supermercado.puntos.dto.remote.PagoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pago")
public interface PagoClient {

    @GetMapping("/api/v1/pagos/usuario/{usuarioId}/ultimo-exitoso-detalle")
    PagoDto obtenerUltimoPagoExitoso(@PathVariable Long usuarioId);

}
