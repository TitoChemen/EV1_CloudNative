package cl.duoc.notificaciones_service.service;

import cl.duoc.notificaciones_service.dto.NotificacionDTO;
import cl.duoc.notificaciones_service.mapper.NotificacionMapper;
import cl.duoc.notificaciones_service.model.Notificacion;
import cl.duoc.notificaciones_service.repository.NotificacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificacionesServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private NotificacionMapper notificacionMapper;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    void testFindById_Success() {
        // Given
        Long id = 1L;
        Notificacion notif = new Notificacion();
        notif.setId(id);
        notif.setCodSeguimiento("TRACK-123");

        NotificacionDTO dto = new NotificacionDTO();
        dto.setCodSeguimiento("TRACK-123");

        when(notificacionRepository.findById(id)).thenReturn(Optional.of(notif));
        when(notificacionMapper.toDTO(notif)).thenReturn(dto);

        // When
        NotificacionDTO resultado = notificacionService.findById(id);

        // Then
        assertNotNull(resultado);
        assertEquals("TRACK-123", resultado.getCodSeguimiento());
        verify(notificacionRepository, times(1)).findById(id);
    }

    @Test
    void testSaveNotificacion_Success() {
        // Given
        Notificacion n = new Notificacion();
        when(notificacionRepository.save(n)).thenReturn(n);

        // When
        Notificacion guardada = notificacionService.save(n);

        // Then
        assertNotNull(guardada);
        verify(notificacionRepository, times(1)).save(n);
    }
}