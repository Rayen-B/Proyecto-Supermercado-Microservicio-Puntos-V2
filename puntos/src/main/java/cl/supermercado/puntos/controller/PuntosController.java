package cl.supermercado.puntos.controller;
import cl.supermercado.puntos.dto.request.PuntosRequestDto;
import cl.supermercado.puntos.dto.response.PuntosResponseDto;
import cl.supermercado.puntos.service.PuntosService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/puntos")
@RequiredArgsConstructor
public class PuntosController {

    private final PuntosService puntosService;

    @PostMapping
    public ResponseEntity<PuntosResponseDto> asignar(@Valid @RequestBody PuntosRequestDto request) {
        return ResponseEntity.ok(puntosService.asignarPuntos(request));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<PuntosResponseDto> consultar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(puntosService.consultarPuntos(usuarioId));
    }
}