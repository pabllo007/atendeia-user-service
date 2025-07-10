package com.atendeia.userservice.repository;

import com.atendeia.userservice.entity.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setLogin("johndoe");
        usuario.setNome("John Doe");
        usuario.setEmail("john.doe@email.com");
        usuario.setSenha("senha123");
        return repository.save(usuario);
    }

    @Test
    @DisplayName("Deve salvar e recuperar usuário por ID")
    void deveSalvarERecuperarUsuario() {
        Usuario saved = criarUsuario();
        Optional<Usuario> encontrado = repository.findById(saved.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getEmail()).isEqualTo("john.doe@email.com");
    }

    @Test
    @DisplayName("Deve encontrar usuário por e-mail")
    void deveEncontrarPorEmail() {
        criarUsuario();
        Optional<Usuario> encontrado = repository.findByEmail("john.doe@email.com");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Deve encontrar usuário por login")
    void deveEncontrarPorLogin() {
        criarUsuario();
        Optional<Usuario> encontrado = repository.findByLogin("johndoe");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getEmail()).isEqualTo("john.doe@email.com");
    }

    @Test
    @DisplayName("Deve verificar existência por e-mail")
    void deveVerificarExistenciaPorEmail() {
        criarUsuario();
        boolean exists = repository.existsByEmail("john.doe@email.com");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve verificar existência por login")
    void deveVerificarExistenciaPorLogin() {
        criarUsuario();
        boolean exists = repository.existsByLogin("johndoe");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Não deve encontrar usuário inexistente")
    void naoDeveEncontrarUsuarioInexistente() {
        Optional<Usuario> encontrado = repository.findByEmail("naoexiste@email.com");
        assertThat(encontrado).isNotPresent();

        boolean exists = repository.existsByLogin("naoexiste");
        assertThat(exists).isFalse();
    }
}
