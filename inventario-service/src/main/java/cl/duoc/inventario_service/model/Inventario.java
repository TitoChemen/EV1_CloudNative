package cl.duoc.inventario_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código de producto es obligatorio")
    @Size(min = 3, max = 20, message = "El código debe tener entre 3 y 20 caracteres")
    @Column(unique = true)
    private String codigoProducto;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stockDisponible;

    @NotBlank(message = "Debes indicar el pasillo de la bodega")
    private String pasilloBodega;

    @NotBlank(message = "El estado del stock es obligatorio")
    @Pattern(regexp = "^(DISPONIBLE|AGOTADO|RESERVADO)$", message = "Estado inválido: debe ser DISPONIBLE, AGOTADO o RESERVADO")
    private String estadoStock;

}