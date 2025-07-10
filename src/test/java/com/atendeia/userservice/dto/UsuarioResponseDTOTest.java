package com.atendeia.userservice.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioResponseDTOTest {

    @Test
    void deveCriarUsuarioResponseDTOComValoresCorretos() {
        LocalDateTime agora = LocalDateTime.now();

        UsuarioResponseDTO dto = new UsuarioResponseDTO(
                1L,
                "user123",
                "João",
                "joao@email.com",
                agora,
                agora
        );

        assertEquals(1L, dto.id());
        assertEquals("user123", dto.login());
        assertEquals("João", dto.nome());
        assertEquals("joao@email.com", dto.email());
        assertEquals(agora, dto.dataCriacao());
        assertEquals(agora, dto.dataAtualizacao());
    }

    @Test
    void deveCompararObjetosCorretamente() {
        LocalDateTime agora = LocalDateTime.now();

        UsuarioResponseDTO dto1 = new UsuarioResponseDTO(1L, "user123", "João", "joao@email.com", agora, agora);
        UsuarioResponseDTO dto2 = new UsuarioResponseDTO(1L, "user123", "João", "joao@email.com", agora, agora);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void toStringDeveConterCampos() {
        LocalDateTime agora = LocalDateTime.now();
        UsuarioResponseDTO dto = new UsuarioResponseDTO(1L, "user123", "João", "joao@email.com", agora, agora);

        String toString = dto.toString();
        assertTrue(toString.contains("user123"));
        assertTrue(toString.contains("João"));
        assertTrue(toString.contains("joao@email.com"));
        assertTrue(toString.contains(agora.toString()));
    }
}
