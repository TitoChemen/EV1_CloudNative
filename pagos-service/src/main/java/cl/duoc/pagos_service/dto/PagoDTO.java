package cl.duoc.pagos_service.dto;

import lombok.Data;

@Data
public class PagoDTO {
    private Long idCarrito;
    private Integer montoPago;
    private String metodoPago;
    private String estadoPago;
    // Opcional: private String transaccionToken;
}
