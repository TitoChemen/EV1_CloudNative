package cl.duoc.transporte_service.mapper;

import cl.duoc.transporte_service.dto.TransporteDTO;
import cl.duoc.transporte_service.model.Transporte;
import org.springframework.stereotype.Component;

@Component
public class TransporteMapper {
    public TransporteDTO toDTO(Transporte t) {
        if (t == null) return null;
        TransporteDTO dto = new TransporteDTO();
        dto.setDireccion(t.getDirecDestino());
        dto.setRuta(t.getEmpresaTransporte()); // O lo que prefieras mostrar
        dto.setEstado("En camino"); // Aquí podrías mapear el estado real
        return dto;
    }

    public Transporte toEntity(TransporteDTO dto) {
        if (dto == null) return null;
        Transporte t = new Transporte();
        t.setDirecDestino(dto.getDireccion());
        // Aquí podrías asignar valores por defecto o dejar campos vacíos
        t.setEmpresaTransporte("Chilexpress por defecto");
        return t;
    }
}