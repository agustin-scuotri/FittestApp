package com.fittestapp.repository;

import com.fittestapp.model.Rol;
import com.fittestapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByRol(Rol rol);

    List<Usuario> findByRolIn(List<Rol> roles);

    @Query("SELECT MAX(u.codigo) FROM Usuario u")
    Optional<Long> findMaxCodigo();
}
