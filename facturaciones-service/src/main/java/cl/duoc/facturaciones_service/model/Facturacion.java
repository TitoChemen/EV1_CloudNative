package cl.duoc.facturaciones_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Facturacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El idPago es obligatorio")
    private Long idPago;

    @NotNull(message = "El idUsuario es obligatorio")
    private Long idUsuario;

    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private Double monto;

    @NotBlank(message = "El número de boleta es obligatorio")
    private String nroBoleta;

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;
}