package com.atendeia.userservice.service;

import com.atendeia.userservice.dto.*;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    UsuarioResponseDTO criarUsuario(UsuarioCreateDTO dto);

    UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateDTO dto);

    void deletarUsuario(Long id);

    UsuarioResponseDTO buscarPorId(Long id);

    Optional<UsuarioResponseDTO> buscarPorEmail(String email);

    List<UsuarioResponseDTO> listarUsuarios();

    void atribuirPapel(Long idUsuario, String papel); // exemplo para RBAC futuro
}
