package com.atendeia.userservice.mapper;

import com.atendeia.userservice.dto.*;
import com.atendeia.userservice.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    private final UsuarioMapper mapper = Mappers.getMapper(UsuarioMapper.class);

    @Test
    void deveConverterUsuarioCreateDtoParaEntity() {
        UsuarioCreateDTO dto = new UsuarioCreateDTO("João", "joao@email.com", "senha123");

        Usuario entity = mapper.toEntity(dto);

        assertEquals("João", entity.getNome());
        assertEquals("joao@email.com", entity.getEmail());
        assertEquals("senha123", entity.getSenha());

        assertNull(entity.getId());
        assertNull(entity.getLogin()); // login é setado na Service
        assertNull(entity.getDataCriacao());
        assertNull(entity.getDataAtualizacao());
    }

    @Test
    void deveAtualizarEntityComUsuarioUpdateDto() {
        Usuario entity = new Usuario();
        entity.setNome("Antigo");
        entity.setEmail("antigo@email.com");
        entity.setSenha("senhaAntiga");

        UsuarioUpdateDTO dto = new UsuarioUpdateDTO("Novo Nome", "novo@email.com");

        mapper.updateEntityFromDto(dto, entity);

        assertEquals("Novo Nome", entity.getNome());
        assertEquals("novo@email.com", entity.getEmail());
        assertEquals("senhaAntiga", entity.getSenha()); // não alterado
    }

    @Test
    void deveConverterEntityParaUsuarioResponseDto() {
        LocalDateTime agora = LocalDateTime.now();
        Usuario entity = new Usuario();
        entity.setId(1L);
        entity.setLogin("joao123");
        entity.setNome("João");
        entity.setEmail("joao@email.com");
        entity.setDataCriacao(agora.minusDays(1));
        entity.setDataAtualizacao(agora);

        UsuarioResponseDTO dto = mapper.toResponseDTO(entity);

        assertEquals(1L, dto.id());
        assertEquals("joao123", dto.login());
        assertEquals("João", dto.nome());
        assertEquals("joao@email.com", dto.email());
        assertEquals(entity.getDataCriacao(), dto.dataCriacao());
        assertEquals(entity.getDataAtualizacao(), dto.dataAtualizacao());
    }
}
