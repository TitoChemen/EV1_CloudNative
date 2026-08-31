package cl.duoc.facturaciones_service.service;

import cl.duoc.facturaciones_service.dto.FacturacionDTO;
import cl.duoc.facturaciones_service.dto.UsuarioDTO;
import cl.duoc.facturaciones_service.feign.UsuarioFeignClient;
import cl.duoc.facturaciones_service.model.Facturacion;
import cl.duoc.facturaciones_service.repository.FacturacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacturacionesServiceTest {

    @Mock
    private FacturacionRepository facturacionRepository;

    @Mock
    private UsuarioFeignClient usuarioFeignClient;

    @InjectMocks
    private FacturacionService facturacionService;

    @Test
    void testBuscarPorIdPago_Success() {
        // 1. Given
        Long idPago = 100L;
        Facturacion f = new Facturacion();
        f.setIdPago(idPago);
        f.setMonto(5000.0);
        f.setIdUsuario(1L);

        UsuarioDTO mockUser = new UsuarioDTO();
        mockUser.setNombre("Kevin");
        mockUser.setApellido("Sosa");
        mockUser.setEmail("kevin@duoc.cl");

        // Simulamos que el repositorio encuentra la factura y el Feign encuentra al usuario
        when(facturacionRepository.findByIdPago(idPago)).thenReturn(Optional.of(f));
        when(usuarioFeignClient.datosUsuarioPorId(1L)).thenReturn(mockUser);

        // 2. When
        FacturacionDTO resultado = facturacionService.buscarPorIdPago(idPago);

        // 3. Then
        assertNotNull(resultado);
        assertEquals("Kevin", resultado.getNombreCliente());
        verify(usuarioFeignClient, times(1)).datosUsuarioPorId(1L);
    }
}