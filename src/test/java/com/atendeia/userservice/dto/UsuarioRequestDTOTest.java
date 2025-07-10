package com.atendeia.userservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRequestDTOTest {

    @Test
    void deveCriarUsuarioRequestDTOComValoresCorretos() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("user123", "João", "joao@email.com", "senha123");

        assertEquals("user123", dto.login());
        assertEquals("João", dto.nome());
        assertEquals("joao@email.com", dto.email());
        assertEquals("senha123", dto.senha());
    }

    @Test
    void deveCompararObjetosComEquals() {
        UsuarioRequestDTO dto1 = new UsuarioRequestDTO("user123", "João", "joao@email.com", "senha123");
        UsuarioRequestDTO dto2 = new UsuarioRequestDTO("user123", "João", "joao@email.com", "senha123");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void toStringDeveConterCampos() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("user123", "João", "joao@email.com", "senha123");

        String toString = dto.toString();
        assertTrue(toString.contains("user123"));
        assertTrue(toString.contains("João"));
        assertTrue(toString.contains("joao@email.com"));
        assertTrue(toString.contains("senha123"));
    }
}
