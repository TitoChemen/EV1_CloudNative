package cl.duoc.producto_service.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoDTO {
    private String codigo;
    private String nombre;
    private Double precio;
    private Integer stock; // <--- ¡Faltaba este!
    private String nombreCategoria;
}