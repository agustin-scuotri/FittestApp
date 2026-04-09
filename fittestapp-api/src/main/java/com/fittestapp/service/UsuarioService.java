package com.fittestapp.service;

import com.fittestapp.model.Rol;
import com.fittestapp.model.Usuario;
import com.fittestapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /** Contraseña temporal asignada cuando se crea un usuario sin contraseña desde el ABM.
     *  Será reemplazada por el flujo de activación por email. */
    public static final String PASSWORD_TEMPORAL = "Temporal123";

    @Transactional
    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        Long maxCodigo = usuarioRepository.findMaxCodigo().orElse(0L);
        usuario.setCodigo(maxCodigo + 1);
        String rawPassword = (usuario.getPassword() == null || usuario.getPassword().isBlank())
                ? PASSWORD_TEMPORAL : usuario.getPassword();
        usuario.setPassword(passwordEncoder.encode(rawPassword));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizar(Usuario usuario) {
        Usuario existente = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuario.getId()));
        existente.setNombre(usuario.getNombre());
        existente.setApellido(usuario.getApellido());
        existente.setDni(usuario.getDni());
        existente.setFechaNacimiento(usuario.getFechaNacimiento());
        existente.setRol(usuario.getRol());
        existente.setActivo(usuario.isActivo());
        return usuarioRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol);
    }

    public List<Usuario> listarPorRoles(List<Rol> roles) {
        return usuarioRepository.findByRolIn(roles);
    }
}
