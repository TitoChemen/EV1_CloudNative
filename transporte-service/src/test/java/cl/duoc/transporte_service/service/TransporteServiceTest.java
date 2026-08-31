package cl.duoc.transporte_service.service;

import cl.duoc.transporte_service.dto.TransporteDTO;
import cl.duoc.transporte_service.mapper.TransporteMapper;
import cl.duoc.transporte_service.model.Transporte;
import cl.duoc.transporte_service.repository.TransporteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransporteServiceTest {

    @Mock
    private TransporteRepository transporteRepository;

    @Mock
    private TransporteMapper transporteMapper;

    @InjectMocks
    private TransporteService transporteService;

    @Test
    void testSaveTransporte_Success() {
        // Given
        TransporteDTO dto = new TransporteDTO();
        dto.setDireccion("Av. Puente Alto 123");

        Transporte entity = new Transporte();
        entity.setDirecDestino("Av. Puente Alto 123");

        // Simulamos la conversión Mapper y el guardado en Repo
        when(transporteMapper.toEntity(dto)).thenReturn(entity);
        when(transporteRepository.save(entity)).thenReturn(entity);
        when(transporteMapper.toDTO(entity)).thenReturn(dto);

        // When
        TransporteDTO resultado = transporteService.save(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("Av. Puente Alto 123", resultado.getDireccion());

        // Verificamos que se llamó al Mapper y al Repo
        verify(transporteMapper, times(1)).toEntity(dto);
        verify(transporteRepository, times(1)).save(entity);
    }
}