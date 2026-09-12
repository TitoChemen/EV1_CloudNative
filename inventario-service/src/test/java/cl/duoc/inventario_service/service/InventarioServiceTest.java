package cl.duoc.inventario_service.service;

import cl.duoc.inventario_service.dto.InventarioDTO;
import cl.duoc.inventario_service.dto.ProductoDTO;
import cl.duoc.inventario_service.feing.ProductoFeign;
import cl.duoc.inventario_service.mapper.InventarioMapper;
import cl.duoc.inventario_service.model.Inventario;
import cl.duoc.inventario_service.repository.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private InventarioMapper inventarioMapper;

    @Mock
    private ProductoFeign productoFeign;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void testProcesarInventario_Success() {
        // Given
        String codigo = "PROD-001";
        int cantidad = 5;

        Inventario mockInv = new Inventario();
        mockInv.setCodigoProducto(codigo);
        mockInv.setPasilloBodega("A-12");
        mockInv.setEstadoStock("DISPONIBLE");

        ProductoDTO mockProd = new ProductoDTO();
        mockProd.setNombre("Zapatillas");
        mockProd.setPrecio(29990.0);

        InventarioDTO expectedDto = new InventarioDTO();
        expectedDto.setCodigo(codigo);
        expectedDto.setNombre("Zapatillas");
        expectedDto.setPrecio(29990.0);
        expectedDto.setPasillo("A-12");
        expectedDto.setEstado("DISPONIBLE");
        expectedDto.setCantidadPedida(cantidad);

        // Simulamos el comportamiento
        when(inventarioRepository.findByCodigoProducto(codigo)).thenReturn(mockInv);
        when(productoFeign.obtenerDetalleProducto(codigo)).thenReturn(mockProd);
        when(inventarioMapper.toDTO(mockInv, mockProd, cantidad)).thenReturn(expectedDto);

        // When
        InventarioDTO resultado = inventarioService.procesarInventario(codigo, cantidad);

        // Then
        assertNotNull(resultado);
        assertEquals("Zapatillas", resultado.getNombre());
        assertEquals("A-12", resultado.getPasillo());
        assertEquals(29990.0, resultado.getPrecio());

        verify(productoFeign, times(1)).obtenerDetalleProducto(codigo);
    }

    @Test
    void testProcesarInventario_NotFound() {
        // Given: El inventario no existe (devuelve null)
        when(inventarioRepository.findByCodigoProducto("INVALIDO")).thenReturn(null);

        // When
        InventarioDTO resultado = inventarioService.procesarInventario("INVALIDO", 1);

        // Then
        assertNull(resultado);
        verify(productoFeign, never()).obtenerDetalleProducto(anyString());
    }
}