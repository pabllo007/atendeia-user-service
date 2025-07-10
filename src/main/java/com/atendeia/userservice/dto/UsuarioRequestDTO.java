package com.atendeia.userservice.dto;
public record UsuarioRequestDTO(
        String login,
        String nome,
        String email,
        String senha
) {}