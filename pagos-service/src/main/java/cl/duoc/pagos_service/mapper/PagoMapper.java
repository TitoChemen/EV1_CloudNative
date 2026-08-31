package cl.duoc.pagos_service.mapper;

import cl.duoc.pagos_service.dto.PagoDTO;
import cl.duoc.pagos_service.model.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {
    public PagoDTO toDTO(Pago pago){
        if (pago == null) return null;
        PagoDTO dto = new PagoDTO();
        dto.setEstadoPago(pago.getEstadoPago());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setMontoPago(pago.getMontoPago());
        return dto;
    }
}
