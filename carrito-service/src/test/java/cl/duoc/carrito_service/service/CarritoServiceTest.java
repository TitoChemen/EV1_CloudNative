package cl.duoc.carrito_service.service;

import cl.duoc.carrito_service.dto.InventarioDTO;
import cl.duoc.carrito_service.dto.UsuarioDTO;
import cl.duoc.carrito_service.feign.InventarioFeign;
import cl.duoc.carrito_service.feign.UsuarioFeign;
import cl.duoc.carrito_service.model.Carrito;
import cl.duoc.carrito_service.repository.CarritoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private UsuarioFeign usuarioFeign;

    @Mock
    private InventarioFeign inventarioFeign;

    @InjectMocks
    private CarritoService carritoService; // <--- Aquí Mockito inyecta los mocks de arriba automáticamente

    @Test
    void testSaveCarrito_Success() {
        // 1. Given (Preparamos datos de mentira para el test)
        Carrito c = new Carrito();
        c.setIdUsuario(1L);
        c.setCodigoProducto("PROD-123");
        c.setCantidad(2);

        UsuarioDTO mockUser = new UsuarioDTO();
        mockUser.setNombre("Diego");

        InventarioDTO mockInv = new InventarioDTO();
        mockInv.setNombre("Polera");

        // Simulamos el comportamiento de la BD y de los Feign
        when(usuarioFeign.buscarPorID(1L)).thenReturn(mockUser);
        when(inventarioFeign.buscarPorId("PROD-123", 2)).thenReturn(mockInv);
        when(carritoRepository.save(any(Carrito.class))).thenReturn(c);

        // 2. When (Llamamos al método real de tu servicio)
        Carrito resultado = carritoService.save(c);

        // 3. Then (Aseguramos que todo salió de pana)
        assertNotNull(resultado);
        verify(carritoRepository, times(1)).save(c);
    }
}