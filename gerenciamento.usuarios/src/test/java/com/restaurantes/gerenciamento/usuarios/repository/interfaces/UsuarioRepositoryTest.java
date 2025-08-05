package com.restaurantes.gerenciamento.usuarios.repository.interfaces;

import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar verdadeiro se existir usuário com login")
    void existsByLogin_retornaTrueQuandoLoginExiste() {
        // Arrange
        Usuarios usuario = new Usuarios();
        usuario.setLogin("usuario_teste");
        usuario.setEmail("email@test.com");
        usuario.setSenha("123456");
        usuarioRepository.save(usuario);

        // Act
        boolean exists = usuarioRepository.existsByLogin("usuario_teste");

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve encontrar usuário pelo email")
    void findByEmail_retornaUsuarioQuandoEmailExiste() {
        // Arrange
        Usuarios usuario = new Usuarios();
        usuario.setLogin("teste_login");
        usuario.setEmail("usuario@email.com");
        usuario.setSenha("senha123");
        usuarioRepository.save(usuario);

        // Act
        Optional<Usuarios> resultado = usuarioRepository.findByEmail("usuario@email.com");

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getEmail()).isEqualTo("usuario@email.com");
    }

    @Test
    @DisplayName("Deve retornar vazio se não encontrar email")
    void findByEmail_retornaVazioQuandoNaoExiste() {
        Optional<Usuarios> resultado = usuarioRepository.findByEmail("naoexiste@email.com");
        assertThat(resultado).isNotPresent();
    }

    @Test
    @DisplayName("Deve encontrar ID pelo email")
    void findIdByEmail_retornaIdDoUsuario() {
        // Arrange
        Usuarios usuario = new Usuarios();
        usuario.setLogin("id_login");
        usuario.setEmail("id@email.com");
        usuario.setSenha("senha");
        Usuarios salvo = usuarioRepository.save(usuario);

        // Act
        Optional<Usuarios> resultado = usuarioRepository.findIdByEmail("id@email.com");

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(salvo.getId());
    }
}