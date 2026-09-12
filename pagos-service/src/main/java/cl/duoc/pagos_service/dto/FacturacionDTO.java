package cl.duoc.pagos_service.dto;

import lombok.Data;

@Data
public class FacturacionDTO {
    private Long idPago;        // Para saber qué boleta es de qué pago
    private Double monto;       // El total a cobrar
    private String detalle;     // "Pago de carrito #..."
}