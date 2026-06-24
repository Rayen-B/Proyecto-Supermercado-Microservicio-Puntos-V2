package cl.supermercado.puntos.service.impl;

import cl.supermercado.puntos.dto.request.PuntosRequestDto;
import cl.supermercado.puntos.dto.response.PuntosResponseDto;
import cl.supermercado.puntos.mapper.PuntosMapper;
import cl.supermercado.puntos.model.Puntos;
import cl.supermercado.puntos.model.PuntosHistorial;
import cl.supermercado.puntos.repository.PuntosHistorialRepository;
import cl.supermercado.puntos.repository.PuntosRepository;
import cl.supermercado.puntos.service.PuntosService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PuntosServiceImpl implements PuntosService {

    private final PuntosRepository repository;
    private final PuntosHistorialRepository historialRepository;
    private final PuntosMapper mapper;

    @Override
    @Transactional
    public PuntosResponseDto asignarPuntos(PuntosRequestDto request) {

        if (historialRepository.existsByCompraId(request.getCompraId())) {
            throw new IllegalArgumentException(
                    "Ya se asignaron puntos por la compra " + request.getCompraId()
            );
        }

        // Calcular puntos directamente desde el total recibido (1 punto por cada $100)
        // Si se llama desde la REST API sin total, se asigna 0 puntos hasta tener dato real
        double monto = request.getTotal() != null ? request.getTotal() : 0.0;
        int puntos = (int) (monto / 100);

        Puntos entity = repository.findByUsuarioId(request.getUsuarioId())
                .orElse(new Puntos(null, request.getUsuarioId(), 0));

        entity.setPuntosAcumulados(entity.getPuntosAcumulados() + puntos);
        repository.save(entity);

        historialRepository.save(
                new PuntosHistorial(null, request.getUsuarioId(), request.getCompraId(), puntos)
        );

        log.info("Puntos asignados: {} puntos al usuario {} por compra {} (monto: ${})",
                puntos, request.getUsuarioId(), request.getCompraId(), monto);

        return mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PuntosResponseDto consultarPuntos(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Usuario sin puntos registrados"));
    }
}
