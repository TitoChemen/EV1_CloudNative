package cl.duoc.pagos_service.feign;

import cl.duoc.pagos_service.dto.TransporteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "transporte-service")
public interface TransporteFeign {

    // FALTABA LA 'S' AL FINAL DE TRANSPORTES
    @PostMapping("/api/v1/transportes")
    TransporteDTO crearOrdenDespacho(@RequestBody TransporteDTO transporte);
}