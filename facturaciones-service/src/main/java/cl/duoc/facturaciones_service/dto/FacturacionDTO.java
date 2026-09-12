package cl.duoc.facturaciones_service.dto;

import lombok.Data;

@Data
public class FacturacionDTO {
    private Long idPago;
    private Double monto;
    private String detalle;

    private String nombreCliente;
    private String apellidoCliente;
    private String correoCliente;
}