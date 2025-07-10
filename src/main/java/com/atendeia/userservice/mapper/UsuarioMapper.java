package com.atendeia.userservice.mapper;

import com.atendeia.userservice.dto.*;
import com.atendeia.userservice.entity.Usuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    /**
     * Mapeia os campos do DTO de criação para a entidade Usuario.
     * A responsabilidade de preencher os campos dataCriacao, dataAtualizacao e login continua sendo da Service.
     */
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "senha", source = "senha")
    Usuario toEntity(UsuarioCreateDTO dto);

    /**
     * Atualiza a entidade Usuario com os campos permitidos do DTO de atualização.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "email", source = "email")
    void updateEntityFromDto(UsuarioUpdateDTO dto, @MappingTarget Usuario usuario);

    /**
     * Converte a entidade Usuario para um DTO de resposta.
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "dataCriacao", source = "dataCriacao")
    @Mapping(target = "dataAtualizacao", source = "dataAtualizacao")
    UsuarioResponseDTO toResponseDTO(Usuario usuario);
}
