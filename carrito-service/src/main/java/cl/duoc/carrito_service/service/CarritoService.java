package cl.duoc.carrito_service.service;

import cl.duoc.carrito_service.dto.CarritoDTO;
import cl.duoc.carrito_service.dto.InventarioDTO;
import cl.duoc.carrito_service.dto.UsuarioDTO;
import cl.duoc.carrito_service.feign.InventarioFeign;
import cl.duoc.carrito_service.feign.UsuarioFeign;
import cl.duoc.carrito_service.model.Carrito;
import cl.duoc.carrito_service.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioFeign usuarioFeign;

    @Autowired
    private InventarioFeign inventarioFeign;

    public List<CarritoDTO> findAll() {
        return carritoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CarritoDTO findById(Long id) {
        return carritoRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    private CarritoDTO mapToDTO(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setIdCarrito(carrito.getId());
        dto.setCantidad(carrito.getCantidad());
        dto.setPrecioUnitario(carrito.getPrecioUnitario());
        dto.setTotalBruto(carrito.getCantidad() * carrito.getPrecioUnitario());

        // Lógica de usuario
        UsuarioDTO user = usuarioFeign.buscarPorID(carrito.getIdUsuario());
        dto.setNombreCliente(user != null ? (user.getNombre() + " " + user.getApellido()) : "Usuario no encontrado");
        dto.setCorreoCliente(user != null ? user.getEmail() : "Sin correo");

        // Lógica de inventario
        InventarioDTO inv = inventarioFeign.buscarPorId(carrito.getCodigoProducto(), carrito.getCantidad());
        dto.setNombreProducto(inv != null ? inv.getNombre() : "Producto no encontrado");

        return dto;
    }

    public Carrito save(Carrito c) {
        // Validación antes de guardar
        UsuarioDTO user = usuarioFeign.buscarPorID(c.getIdUsuario());
        if (user == null) throw new RuntimeException("Usuario no existe");

        InventarioDTO inv = inventarioFeign.buscarPorId(c.getCodigoProducto(), c.getCantidad());
        if (inv == null) throw new RuntimeException("Producto no existe en inventario");

        return carritoRepository.save(c);
    }

    public void delete(Long id) {
        carritoRepository.deleteById(id);
    }

    public Carrito update(Long id, Carrito carrito) {
        return carritoRepository.findById(id).map(c -> {
            c.setCantidad(carrito.getCantidad());
            c.setCodigoProducto(carrito.getCodigoProducto());
            c.setPrecioUnitario(carrito.getPrecioUnitario());
            c.setIdUsuario(carrito.getIdUsuario());
            return carritoRepository.save(c);
        }).orElse(null);
    }
}