package cl.duoc.facturaciones_service.feign;

import cl.duoc.facturaciones_service.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service")
public interface UsuarioFeignClient {

    @GetMapping("/api/v1/usuario/{id}")
    UsuarioDTO datosUsuarioPorId(@PathVariable("id")Long id);
}
