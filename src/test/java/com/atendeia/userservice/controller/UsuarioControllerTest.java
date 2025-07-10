package com.atendeia.userservice.controller;

import com.atendeia.userservice.dto.*;
import com.atendeia.userservice.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioCreateDTO createDTO;
    private UsuarioUpdateDTO updateDTO;
    private UsuarioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        createDTO = new UsuarioCreateDTO("Novo Usuário", "novo@email.com", "senha123");
        updateDTO = new UsuarioUpdateDTO("Usuário Atualizado", "atualizado@email.com");
        responseDTO = new UsuarioResponseDTO(
                1L,
                "novo@email.com", // login
                "Novo Usuário",   // nome
                "teste@email.com", // email
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }


    @Test
    void deveCriarUsuario() throws Exception {
        Mockito.when(usuarioService.criarUsuario(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/usuarios/1"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Novo Usuário"))
                .andExpect(jsonPath("$.email").value("teste@email.com"));
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        Mockito.when(usuarioService.atualizarUsuario(eq(1L), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deveDeletarUsuario() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(usuarioService).deletarUsuario(1L);
    }

    @Test
    void deveListarUsuarios() throws Exception {
        Mockito.when(usuarioService.listarUsuarios()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void deveBuscarUsuarioPorId() throws Exception {
        Mockito.when(usuarioService.buscarPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deveBuscarUsuarioPorEmail_quandoExistente() throws Exception {
        Mockito.when(usuarioService.buscarPorEmail("teste@email.com"))
                .thenReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/api/usuarios/email")
                        .param("email", "teste@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("teste@email.com"));
    }

    @Test
    void deveRetornarNotFound_quandoEmailNaoEncontrado() throws Exception {
        Mockito.when(usuarioService.buscarPorEmail("naoexiste@email.com"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/email")
                        .param("email", "naoexiste@email.com"))
                .andExpect(status().isNotFound());
    }
}
