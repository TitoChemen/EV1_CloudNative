package cl.duoc.carrito_service.feign;

import cl.duoc.carrito_service.dto.InventarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventario-service")
public interface InventarioFeign {
    @GetMapping("/api/v1/inventario/{codigo}")
    InventarioDTO buscarPorId(@PathVariable("codigo") String codigo, @RequestParam("cantidad") int cantidad);
}