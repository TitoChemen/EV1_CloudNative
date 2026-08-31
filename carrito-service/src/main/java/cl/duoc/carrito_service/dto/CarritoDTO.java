package cl.duoc.carrito_service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
        "idCarrito",
        "nombreCliente",
        "correoCliente",
        "nombreProducto",
        "cantidad",
        "precioUnitario",
        "totalBruto"
})
public class CarritoDTO {
    private Long idCarrito;
    private String nombreCliente;
    private String correoCliente;
    private String nombreProducto; // <-- Chao codigo, hola nombre
    private int cantidad;
    private int precioUnitario;
    private int totalBruto;
}