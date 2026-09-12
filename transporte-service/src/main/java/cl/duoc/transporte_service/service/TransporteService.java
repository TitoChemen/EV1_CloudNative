package cl.duoc.transporte_service.service;

import cl.duoc.transporte_service.dto.TransporteDTO;
import cl.duoc.transporte_service.mapper.TransporteMapper;
import cl.duoc.transporte_service.model.Transporte;
import cl.duoc.transporte_service.repository.TransporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransporteService {

    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private TransporteMapper transporteMapper;

    // Listar todos los transportes convertidos a DTO
    public List<TransporteDTO> findAll() {
        return transporteRepository.findAll()
                .stream()
                .map(transporteMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar transporte por ID
    public TransporteDTO findById(Long id) {
        return transporteRepository.findById(id)
                .map(transporteMapper::toDTO)
                .orElse(null);
    }

    // Guardar nuevo transporte (Recibe DTO, transforma a Entidad y vuelve a DTO)
    public TransporteDTO save(TransporteDTO dto) {
        Transporte entity = transporteMapper.toEntity(dto);
        Transporte savedEntity = transporteRepository.save(entity);
        return transporteMapper.toDTO(savedEntity);
    }

    // Actualizar transporte existente
    public TransporteDTO update(Long id, TransporteDTO dto) {
        if (transporteRepository.existsById(id)) {
            Transporte entity = transporteMapper.toEntity(dto);
            entity.setId(id); // Aseguramos que se actualice el registro correcto
            return transporteMapper.toDTO(transporteRepository.save(entity));
        }
        return null;
    }

    // Eliminar transporte
    public void delete(Long id) {
        transporteRepository.deleteById(id);
    }
}