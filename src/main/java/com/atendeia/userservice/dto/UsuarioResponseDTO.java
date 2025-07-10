package com.atendeia.userservice.dto;
import java.time.LocalDateTime;

public record UsuarioResponseDTO(
        Long id,
        String login,
        String nome,
        String email,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {}