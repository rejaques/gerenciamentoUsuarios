package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.exception.LoginNaoDisponivelException;
import com.restaurantes.gerenciamento.usuarios.exception.UsuarioNaoEncontradoException;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.UsuarioRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuarios criarUsuario(CadastrarUsuarioDto dto, Endereco endereco) {

        if(usuarioRepository.existsByLogin(dto.getLogin())) {
            throw new LoginNaoDisponivelException("Login já está em uso");
        }

        Usuarios usuario = new Usuarios();
        usuario.setNome(dto.getNome());
        usuario.setLogin(dto.getLogin());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setDataUltimaAlteracao(LocalDate.now());
        usuario.setEndereco(endereco.getId());

        return usuarioRepository.save(usuario);
    }

    @Override
    public void alterarUsuario(AlterarDadosUsuarioDto dto, Endereco endereco) {

        Usuarios usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encontrado"));

        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getLogin() != null) usuario.setLogin(dto.getLogin());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        usuario.setDataUltimaAlteracao(LocalDate.now());
        usuario.setEndereco(endereco.getId());

        usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuarios> buscaUsuarioPeloEmail(String email) {

        return usuarioRepository.findIdByEmail(email);
    }

    @Override
    public Usuarios buscaUsuarioPeloId(Long idUsuario) {

        Usuarios usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encontrado"));

        return usuario;
    }

}
