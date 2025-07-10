package com.atendeia.userservice.service.impl;

import com.atendeia.userservice.dto.*;
import com.atendeia.userservice.entity.Usuario;
import com.atendeia.userservice.mapper.UsuarioMapper;
import com.atendeia.userservice.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    private AutoCloseable mocks;

    private Usuario usuario;
    private UsuarioCreateDTO createDTO;
    private UsuarioUpdateDTO updateDTO;
    private UsuarioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setLogin("joao@email.com");
        usuario.setSenha("123456");
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setDataAtualizacao(LocalDateTime.now());

        createDTO = new UsuarioCreateDTO("João", "joao@email.com", "123456");
        updateDTO = new UsuarioUpdateDTO("João da Silva", "nova@email.com");
        responseDTO = new UsuarioResponseDTO(1L, "João", "joao@email.com", "joao@email.com", usuario.getDataCriacao(), usuario.getDataAtualizacao());
    }

    @Test
    void criarUsuario_deveSalvarEMapearCorretamente() {
        when(usuarioRepository.findByEmail(createDTO.email())).thenReturn(Optional.empty());
        when(usuarioMapper.toEntity(createDTO)).thenReturn(usuario);
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(usuarioMapper.toResponseDTO(usuario)).thenReturn(responseDTO);

        var result = usuarioService.criarUsuario(createDTO);

        assertThat(result).isEqualTo(responseDTO);
        verify(usuarioRepository).save(any());
    }

    @Test
    void criarUsuario_deveLancarExcecao_quandoEmailJaExiste() {
        when(usuarioRepository.findByEmail(createDTO.email())).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> usuarioService.criarUsuario(createDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("E-mail já cadastrado");
    }

    @Test
    void atualizarUsuario_deveAtualizarComSucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioMapper).updateEntityFromDto(updateDTO, usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponseDTO(usuario)).thenReturn(responseDTO);

        var result = usuarioService.atualizarUsuario(1L, updateDTO);

        assertThat(result).isEqualTo(responseDTO);
    }

    @Test
    void atualizarUsuario_deveLancarExcecao_seNaoEncontrarUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.atualizarUsuario(1L, updateDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Usuário não encontrado");
    }

    @Test
    void deletarUsuario_deveDeletarComSucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        usuarioService.deletarUsuario(1L);

        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void deletarUsuario_deveLancarExcecao_seUsuarioNaoEncontrado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.deletarUsuario(1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void buscarPorId_deveRetornarUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponseDTO(usuario)).thenReturn(responseDTO);

        var result = usuarioService.buscarPorId(1L);

        assertThat(result).isEqualTo(responseDTO);
    }

    @Test
    void buscarPorEmail_deveRetornarOptional() {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponseDTO(usuario)).thenReturn(responseDTO);

        var result = usuarioService.buscarPorEmail(usuario.getEmail());

        assertThat(result).isPresent().contains(responseDTO);
    }

    @Test
    void listarUsuarios_deveRetornarTodos() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        when(usuarioMapper.toResponseDTO(usuario)).thenReturn(responseDTO);

        var result = usuarioService.listarUsuarios();

        assertThat(result).hasSize(1).contains(responseDTO);
    }

    @Test
    void atribuirPapel_deveLancarExcecao() {
        assertThatThrownBy(() -> usuarioService.atribuirPapel(1L, "ADMIN"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
