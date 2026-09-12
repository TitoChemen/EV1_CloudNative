package cl.duoc.inventario_service.feing;

import cl.duoc.inventario_service.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "producto-service")
public interface ProductoFeign {
    // FALTABA LA RUTA COMPLETA AQUÍ
    @GetMapping("/api/v1/productos/{codigo}")
    ProductoDTO obtenerDetalleProducto(@PathVariable("codigo") String codigo);
}