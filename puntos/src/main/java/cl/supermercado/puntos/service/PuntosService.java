package cl.supermercado.puntos.service;

import cl.supermercado.puntos.dto.request.PuntosRequestDto;
import cl.supermercado.puntos.dto.response.PuntosResponseDto;
public interface PuntosService {
    PuntosResponseDto asignarPuntos(PuntosRequestDto request);
    PuntosResponseDto consultarPuntos(Long usuarioId);
}