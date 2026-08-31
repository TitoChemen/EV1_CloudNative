package cl.duoc.usuario_service.service;

import cl.duoc.usuario_service.dto.UsuarioDTO;
import cl.duoc.usuario_service.mapper.UsuarioMapper;
import cl.duoc.usuario_service.model.Usuario;
import cl.duoc.usuario_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public UsuarioDTO findById(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return usuarioMapper.toDTO(usuario);
    }

    public Usuario save(Usuario u){
        return usuarioRepository.save(u);
    }

    public void delete(Long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario update(Long id, Usuario usuario){
        Usuario usuarioActualizar = usuarioRepository.findById(id).orElse(null);
        if (usuarioActualizar == null) return null;
        usuarioActualizar.setNombre(usuario.getNombre());
        usuarioActualizar.setApellido(usuario.getApellido());
        usuarioActualizar.setRut(usuario.getRut());
        usuarioActualizar.setEmail(usuario.getEmail());
        usuarioActualizar.setDireccion(usuario.getDireccion());

        return usuarioRepository.save(usuarioActualizar);
    }
}