package com.atendeia.userservice.service.impl;

import com.atendeia.userservice.dto.*;
import com.atendeia.userservice.entity.Usuario;
import com.atendeia.userservice.mapper.UsuarioMapper;
import com.atendeia.userservice.repository.UsuarioRepository;
import com.atendeia.userservice.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional
    public UsuarioResponseDTO criarUsuario(UsuarioCreateDTO dto) {
        validarEmailExistente(dto.email());

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setLogin(dto.email()); // ou gerar login de outra forma
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setDataAtualizacao(LocalDateTime.now());

        usuario = usuarioRepository.save(usuario);

        log.info("Usuário criado com ID {}", usuario.getId());
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateDTO dto) {
        Usuario usuario = buscarOuFalhar(id);
        usuarioMapper.updateEntityFromDto(dto, usuario);
        usuario.setDataAtualizacao(LocalDateTime.now());

        usuario = usuarioRepository.save(usuario);
        log.info("Usuário atualizado com ID {}", usuario.getId());
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Override
    @Transactional
    public void deletarUsuario(Long id) {
        Usuario usuario = buscarOuFalhar(id);
        usuarioRepository.delete(usuario);
        log.info("Usuário deletado com ID {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        return usuarioMapper.toResponseDTO(buscarOuFalhar(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDTO> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public void atribuirPapel(Long idUsuario, String papel) {
        // Implementação futura: integrar com Role ou com SSO
        throw new UnsupportedOperationException("Atribuição de papel ainda não implementada.");
    }

    private Usuario buscarOuFalhar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID " + id));
    }

    private void validarEmailExistente(String email) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + email);
        }
    }
}
