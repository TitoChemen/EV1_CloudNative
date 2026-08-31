package cl.duoc.carrito_service.feign;

import cl.duoc.carrito_service.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service")
public interface UsuarioFeign {
    // Si tu Controller en usuario-service tiene @RequestMapping("/api/v1/usuario")
    // y @GetMapping("/{id}"), esta es la ruta correcta:
    @GetMapping("/api/v1/usuario/{id}")
    UsuarioDTO buscarPorID(@PathVariable("id") Long id);
}