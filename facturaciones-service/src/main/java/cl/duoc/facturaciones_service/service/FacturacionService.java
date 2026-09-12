package cl.duoc.facturaciones_service.service;

import cl.duoc.facturaciones_service.dto.FacturacionDTO;
import cl.duoc.facturaciones_service.dto.UsuarioDTO;
import cl.duoc.facturaciones_service.feign.UsuarioFeignClient;
import cl.duoc.facturaciones_service.mapper.FacturacionMapper;
import cl.duoc.facturaciones_service.model.Facturacion;
import cl.duoc.facturaciones_service.repository.FacturacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturacionService {
    @Autowired
    private FacturacionRepository facturacionRepository;
    @Autowired
    private FacturacionMapper facturacionMapper;
    @Autowired
    private UsuarioFeignClient usuarioFeignClient;

    public List<Facturacion> findAll(){
        return facturacionRepository.findAll();
    }

    public FacturacionDTO findById(Long id){
        Facturacion facturacion = facturacionRepository.findById(id).orElse(null);
        return facturacionMapper.toDTO(facturacion);
    }

    public Facturacion save(Facturacion f){
        // Si no llega número de boleta, lo generamos al azar
        if (f.getNroBoleta() == null) {
            f.setNroBoleta("BOL-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        // Si no llega fecha, ponemos la actual (formato simple)
        if (f.getFecha() == null) {
            f.setFecha(java.time.LocalDate.now().toString());
        }
        return facturacionRepository.save(f);
    }

    public void delete(Long id){
        facturacionRepository.deleteById(id);
    }

    public Facturacion update(Long id, Facturacion facturacion){
        // Buscamos la factura existente
        Facturacion facturaExistente = facturacionRepository.findById(id).orElse(null);

        if (facturaExistente == null) return null;

        // Seteamos los campos que de verdad existen en tu modelo
        facturaExistente.setMonto(facturacion.getMonto());
        facturaExistente.setIdPago(facturacion.getIdPago());
        facturaExistente.setNroBoleta(facturacion.getNroBoleta());
        facturaExistente.setFecha(facturacion.getFecha());

        return facturacionRepository.save(facturaExistente);
    }

    /*AQUI*/
    public FacturacionDTO buscarPorIdPago(Long idPago){
        Facturacion facturacion = (Facturacion) facturacionRepository.findByIdPago(idPago).orElse(null);
        if (facturacion == null) return null;

        FacturacionDTO dto = new FacturacionDTO();
        dto.setIdPago(facturacion.getIdPago());
        dto.setMonto(facturacion.getMonto());
        dto.setDetalle("Factura generada");

        // Usamos Feign para ir a buscar los datos faltantes
        if(facturacion.getIdUsuario() != null) {
            try {
                // Hacemos la llamada HTTP interna a usuario-service
                UsuarioDTO usuario = usuarioFeignClient.datosUsuarioPorId(facturacion.getIdUsuario());

                dto.setNombreCliente(usuario.getNombre());
                dto.setApellidoCliente(usuario.getApellido());
                dto.setCorreoCliente(usuario.getEmail());
            } catch (Exception e) {
                // Manejo de errores por si usuario-service está apagado
                dto.setNombreCliente("Usuario no disponible");
            }
        }
        return dto;
    }
}
