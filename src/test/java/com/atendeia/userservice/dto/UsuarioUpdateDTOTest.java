package com.atendeia.userservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioUpdateDTOTest {

    @Test
    void deveCriarUsuarioUpdateDTOComValoresCorretos() {
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO("João da Silva", "joao@email.com");

        assertEquals("João da Silva", dto.nome());
        assertEquals("joao@email.com", dto.email());
    }

    @Test
    void deveCompararObjetosCorretamente() {
        UsuarioUpdateDTO dto1 = new UsuarioUpdateDTO("João", "joao@email.com");
        UsuarioUpdateDTO dto2 = new UsuarioUpdateDTO("João", "joao@email.com");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void toStringDeveConterCampos() {
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO("João", "joao@email.com");

        String toString = dto.toString();
        assertTrue(toString.contains("João"));
        assertTrue(toString.contains("joao@email.com"));
    }
}
