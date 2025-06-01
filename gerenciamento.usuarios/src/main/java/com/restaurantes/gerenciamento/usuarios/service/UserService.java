package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.exception.EmailNaoEncontradoExcepition;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository; // Seu repositório de usuários

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuarios usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNaoEncontradoExcepition("Usuário não encontrado com o e-mail: " + email));


        return new User(usuario.getEmail(),
                usuario.getSenha(),
                Collections.emptyList());
    }
}
