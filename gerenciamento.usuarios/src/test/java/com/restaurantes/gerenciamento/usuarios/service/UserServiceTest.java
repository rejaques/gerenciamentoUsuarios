package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.exception.EmailNaoEncontradoExcepition;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void deveRetornarUserDetailsQuandoEmailExistir() {
        Usuarios usuario = new Usuarios();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("123456");

        Mockito.when(usuarioRepository.findByEmail("teste@email.com"))
                .thenReturn(Optional.of(usuario));

        UserDetails userDetails = userService.loadUserByUsername("teste@email.com");

        assertNotNull(userDetails);
        assertEquals("teste@email.com", userDetails.getUsername());
        assertEquals("123456", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void deveLancarExcecaoQuandoEmailNaoForEncontrado() {
        Mockito.when(usuarioRepository.findByEmail("naoexiste@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(EmailNaoEncontradoExcepition.class,
                () -> userService.loadUserByUsername("naoexiste@email.com"));
    }
}
