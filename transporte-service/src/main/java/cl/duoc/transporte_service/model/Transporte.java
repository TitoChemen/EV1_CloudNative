package cl.duoc.transporte_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transporte") // <--- ¡Asegúrate de agregar esto!
public class Transporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "direc_destino") // <--- Mapeo necesario
    @NotBlank(message = "La dirección es obligatoria")
    private String direcDestino;

    @Column(name = "empresa_transporte") // <--- Mapeo necesario
    @NotBlank(message = "La empresa es obligatoria")
    private String empresaTransporte;

    @Column(name = "nro_boleta")
    private String nroBoleta;

    @Column(name = "rut_destinatario")
    private String rutDestinatario;

    @Column(name = "fecha_entrega_aprox")
    private String fechaEntregaAprox;
}