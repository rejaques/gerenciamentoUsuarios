package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.UsuarioRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuarios criarUsuario(CadastrarUsuarioDto dto, Endereco endereco) {

        if(dto == null || dto.getLogin() == null || dto.getSenha() == null) {
            throw new IllegalArgumentException("Dados inválidos para criação de usuário");
        }

        if(usuarioRepository.existsByLogin(dto.getLogin())) {
            throw new IllegalArgumentException("Login já está em uso");
        }

        validarForcaSenha(dto.getSenha());

        Usuarios usuario = new Usuarios();
        usuario.setNome(dto.getNome());
        usuario.setLogin(dto.getLogin());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setDataUltimaAlteracao(LocalDate.now());
        usuario.setEndereco(endereco);

        return usuarioRepository.save(usuario);
    }

    private void validarForcaSenha(String senha) {
        if(senha.length() < 8) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 8 caracteres");
        }
    }
}
