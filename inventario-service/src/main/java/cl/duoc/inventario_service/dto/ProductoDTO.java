package cl.duoc.inventario_service.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // <--- ¡EL SALVAVIDAS DE JACKSON! (Constructor vacío)
@AllArgsConstructor // <--- Necesario para que el @Builder siga funcionando
public class ProductoDTO {
    private String codigo;
    private String nombre;
    private Double precio;
    private Integer stock;
    private String nombreCategoria;
}