package cl.duoc.transporte_service.dto;

import lombok.Data;

@Data
public class TransporteDTO {
    private Long idPago;
    private String ruta;
    private String estado;
    private String direccion;
}