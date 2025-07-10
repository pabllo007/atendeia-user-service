package com.atendeia.userservice.dto;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioCreateDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarUsuarioCreateDTOValido() {
        UsuarioCreateDTO dto = UsuarioCreateDTO.builder()
                .nome("João")
                .email("joao@email.com")
                .senha("123456")
                .build();

        Set<ConstraintViolation<UsuarioCreateDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "Não deve haver violações de validação");
        assertEquals("João", dto.nome());
        assertEquals("joao@email.com", dto.email());
        assertEquals("123456", dto.senha());
    }

    @Test
    void deveValidarCamposObrigatorios() {
        UsuarioCreateDTO dto = UsuarioCreateDTO.builder()
                .nome("")
                .email("invalido")
                .senha("")
                .build();

        Set<ConstraintViolation<UsuarioCreateDTO>> violations = validator.validate(dto);

        assertEquals(3, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("senha")));
    }
}
