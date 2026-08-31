package cl.duoc.producto_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código es requerido")
    @Column(unique = true)
    private String codigo;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @DecimalMin(value = "0.0", message = "El precio debe ser positivo")
    private Double precio;

    @Min(0)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Categoria categoria;
}