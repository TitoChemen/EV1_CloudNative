package cl.duoc.pagos_service.service;

import cl.duoc.pagos_service.feign.FacturacionFeign;
import cl.duoc.pagos_service.feign.NotificacionFeign;
import cl.duoc.pagos_service.feign.TransporteFeign;
import cl.duoc.pagos_service.model.Pago;
import cl.duoc.pagos_service.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagosServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private FacturacionFeign facturacionFeign;

    @Mock
    private TransporteFeign transporteFeign;

    @Mock
    private NotificacionFeign notificacionFeign;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void testSavePago_WithDiscount_Success() {
        // Given
        Pago p = new Pago();
        p.setMontoPago(200000); // Supera los 100.000, debería aplicar descuento
        p.setMetodoPago("CREDITO");

        // Simulamos que el repositorio devuelve el pago con ID 1
        when(pagoRepository.save(any(Pago.class))).thenAnswer(i -> {
            Pago saved = i.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // When
        Pago resultado = pagoService.save(p);

        // Then
        // 1. Validamos que el descuento se aplicó (200.000 - 10% = 180.000)
        assertEquals(180000, resultado.getMontoPago());

        // 2. Validamos que se llamaron a los Feign (orquestación)
        verify(facturacionFeign, times(1)).crearFactura(any());
        verify(transporteFeign, times(1)).crearOrdenDespacho(any());
        verify(notificacionFeign, times(1)).enviarNotificacion(any());
    }
}