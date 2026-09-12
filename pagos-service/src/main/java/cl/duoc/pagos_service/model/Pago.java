package cl.duoc.pagos_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// cl.duoc.pagos_service.model.Pago.java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El idCarrito es requerido")
    private Long idCarrito;

    @Min(value = 1, message = "El monto debe ser al menos 1")
    private Integer montoPago;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    private String transaccionToken;

    private String estadoPago;
}