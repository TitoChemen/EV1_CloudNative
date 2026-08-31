package cl.duoc.pagos_service.feign;

import cl.duoc.pagos_service.dto.FacturacionDTO; // Importa el DTO que creamos
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping; // Cambia a POST
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "facturaciones-service")
public interface FacturacionFeign {

    // Cambiamos a PostMapping porque vamos a CREAR una factura en la otra API
    @PostMapping("/api/v1/facturas")
    FacturacionDTO crearFactura(@RequestBody FacturacionDTO factura);
}