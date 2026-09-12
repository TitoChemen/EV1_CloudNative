package cl.duoc.pagos_service.service;


import cl.duoc.pagos_service.dto.*;
import cl.duoc.pagos_service.feign.FacturacionFeign;
import cl.duoc.pagos_service.feign.NotificacionFeign;
import cl.duoc.pagos_service.feign.TransporteFeign;
import cl.duoc.pagos_service.mapper.PagoMapper;
import cl.duoc.pagos_service.model.Pago;
import cl.duoc.pagos_service.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private PagoMapper pagoMapper;
    @Autowired
    private FacturacionFeign facturacionFeign;
    @Autowired
    private TransporteFeign transporteFeign;
    @Autowired
    private NotificacionFeign notificacionFeign;

    public List<Pago> findAll(){
        return pagoRepository.findAll();
    }

    public PagoDTO findById(Long id){
        Pago pago = pagoRepository.findById(id).orElse(null);
        return pagoMapper.toDTO(pago);
    }

    public Pago save(Pago p) {
        // 1. Lógica de Descuento (Sin llamadas externas, pura lógica de negocio)
        double montoOriginal = p.getMontoPago().doubleValue();
        double descuento = 0.0;

        // Ejemplo: Si el pago supera 100.000, aplicamos un 10% de descuento
        if (montoOriginal > 100000) {
            descuento = montoOriginal * 0.10;
        }

        // Calculamos y actualizamos el objeto Pago antes de guardarlo
        int montoFinal = (int) (montoOriginal - descuento);
        p.setMontoPago(montoFinal);

        // 2. Ahora guardamos el pago YA DESCONTADO
        Pago pagoGuardado = pagoRepository.save(p);

        // 3. Facturación (Ahora recibe el monto ya descontado)
        FacturacionDTO factura = new FacturacionDTO();
        factura.setIdPago(pagoGuardado.getId());
        factura.setMonto(pagoGuardado.getMontoPago().doubleValue());
        factura.setDetalle("Pago realizado con " + pagoGuardado.getMetodoPago() + " (Descuento aplicado)");
        facturacionFeign.crearFactura(factura);

        // 4. Transporte
        TransporteDTO despacho = new TransporteDTO();
        despacho.setIdPago(pagoGuardado.getId());
        despacho.setEstado("Preparando_Despacho");
        transporteFeign.crearOrdenDespacho(despacho);

        // 5. Notificaciones
        NotificacionDTO noti = new NotificacionDTO();
        noti.setCodSeguimiento("SEG-" + pagoGuardado.getId());
        noti.setEstadoEnv("Pago_Confirmado_Y_Procesado");
        noti.setEmailNotificacion("cliente@correo.cl");
        notificacionFeign.enviarNotificacion(noti);

        return pagoGuardado;
    }

    public void delete(Long id){
        pagoRepository.deleteById(id);
    }

    public Pago update(Long id, Pago pago){
        Pago pagoActualizar = pagoRepository.findById(id).orElse(null);
        if (pagoActualizar == null) return null;
        pagoActualizar.setEstadoPago(pago.getEstadoPago()+ " " + pago.getMetodoPago());
        return pagoRepository.save(pagoActualizar);
    }
}
