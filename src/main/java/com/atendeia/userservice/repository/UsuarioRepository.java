package com.atendeia.userservice.repository;

import com.atendeia.userservice.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}
