package com.atendeia.userservice.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void deveSetarEObterCamposCorretamente() {
        Usuario usuario = new Usuario();

        LocalDateTime dataCriacao = LocalDateTime.now().minusDays(1);
        LocalDateTime dataAtualizacao = LocalDateTime.now();

        usuario.setId(1L);
        usuario.setLogin("joao123");
        usuario.setNome("João da Silva");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("senhaSegura123");
        usuario.setDataCriacao(dataCriacao);
        usuario.setDataAtualizacao(dataAtualizacao);

        assertEquals(1L, usuario.getId());
        assertEquals("joao123", usuario.getLogin());
        assertEquals("João da Silva", usuario.getNome());
        assertEquals("joao@email.com", usuario.getEmail());
        assertEquals("senhaSegura123", usuario.getSenha());
        assertEquals(dataCriacao, usuario.getDataCriacao());
        assertEquals(dataAtualizacao, usuario.getDataAtualizacao());
    }

    @Test
    void devePermitirValoresNulosNosCamposTemporarios() {
        Usuario usuario = new Usuario();
        usuario.setDataCriacao(null);
        usuario.setDataAtualizacao(null);

        assertNull(usuario.getDataCriacao());
        assertNull(usuario.getDataAtualizacao());
    }
}
