package cl.supermercado.puntos.service;

import cl.supermercado.puntos.dto.request.PuntosRequestDto;
import cl.supermercado.puntos.dto.response.PuntosResponseDto;
import cl.supermercado.puntos.mapper.PuntosMapper;
import cl.supermercado.puntos.model.Puntos;
import cl.supermercado.puntos.model.PuntosHistorial;
import cl.supermercado.puntos.repository.PuntosHistorialRepository;
import cl.supermercado.puntos.repository.PuntosRepository;
import cl.supermercado.puntos.service.impl.PuntosServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - PuntosServiceImpl")
public class PuntosServiceImplTest {

    @Mock private PuntosRepository repository;
    @Mock private PuntosHistorialRepository historialRepository;
    @Mock private PuntosMapper mapper;

    @InjectMocks
    private PuntosServiceImpl service;

    private Puntos puntos;
    private PuntosRequestDto requestDto;
    private PuntosResponseDto responseDto;

    @BeforeEach
    void setUp() {
        puntos = new Puntos(null, 1L, 0);
        requestDto = new PuntosRequestDto();
        requestDto.setUsuarioId(1L);
        requestDto.setCompraId(100L);
        requestDto.setTotal(5000.0);
        responseDto = new PuntosResponseDto();
        responseDto.setUsuarioId(1L);
        responseDto.setPuntosAcumulados(50);
    }

    @Test
    @DisplayName("asignarPuntos: debe calcular y guardar puntos correctamente")
    void asignarPuntos_DeberiaGuardar_CuandoCompraEsNueva() {
        when(historialRepository.existsByCompraId(100L)).thenReturn(false);
        when(repository.findByUsuarioId(1L)).thenReturn(Optional.of(puntos));
        when(repository.save(any(Puntos.class))).thenReturn(puntos);
        when(historialRepository.save(any(PuntosHistorial.class))).thenReturn(new PuntosHistorial());
        when(mapper.toDto(puntos)).thenReturn(responseDto);

        PuntosResponseDto resultado = service.asignarPuntos(requestDto);

        assertThat(resultado).isNotNull();
        verify(repository, times(1)).save(any(Puntos.class));
        verify(historialRepository, times(1)).save(any(PuntosHistorial.class));
    }

    @Test
    @DisplayName("asignarPuntos: debe crear nuevo registro si el usuario no tenía puntos")
    void asignarPuntos_DeberiaCrearNuevo_CuandoUsuarioSinPuntos() {
        when(historialRepository.existsByCompraId(100L)).thenReturn(false);
        when(repository.findByUsuarioId(1L)).thenReturn(Optional.empty());
        when(repository.save(any(Puntos.class))).thenReturn(puntos);
        when(historialRepository.save(any(PuntosHistorial.class))).thenReturn(new PuntosHistorial());
        when(mapper.toDto(puntos)).thenReturn(responseDto);

        PuntosResponseDto resultado = service.asignarPuntos(requestDto);

        assertThat(resultado).isNotNull();
        verify(repository, times(1)).save(any(Puntos.class));
    }

    @Test
    @DisplayName("consultarPuntos: debe devolver los puntos del usuario")
    void consultarPuntos_DeberiaDevolverPuntos_CuandoExiste() {
        when(repository.findByUsuarioId(1L)).thenReturn(Optional.of(puntos));
        when(mapper.toDto(puntos)).thenReturn(responseDto);

        PuntosResponseDto resultado = service.consultarPuntos(1L);

        assertThat(resultado).isNotNull();
        verify(repository, times(1)).findByUsuarioId(1L);
    }
}