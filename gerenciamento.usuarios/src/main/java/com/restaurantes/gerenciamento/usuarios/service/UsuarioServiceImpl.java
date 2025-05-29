package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.controller.CadastroController;
import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.exception.*;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.EnderecoRepository;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.UsuarioRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EnderecoRepository enderecoRepository;
    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Override
    @Transactional
    public Usuarios criarUsuario(CadastrarUsuarioDto dto, Endereco endereco) {

        if(usuarioRepository.existsByLogin(dto.getLogin())) {
            throw new LoginNaoDisponivelException("Login já está em uso");
        }

        if (endereco == null || endereco.getId() == null || !enderecoRepository.existsById(endereco.getId())) {
            throw new IdNulo("Endereço inválido ou não encontrado");
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
    @Transactional
    public Usuarios alterarUsuario(AlterarDadosUsuarioDto dto, Endereco endereco) {

        log.info("Requisição para alterar usuário com ID: {}", dto.getIdUsuario());

        if (dto.getIdUsuario() == null) {
            log.warn("ID do usuário não fornecido para alteração.");
            throw new IdNulo("ID do usuário é obrigatório para alteração.");
        }

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

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuarios buscaUsuarioPeloEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new EmailNuloException("Email não pode ser nulo ou vazio");
        }

        Usuarios usuario = usuarioRepository.findIdByEmail(email)
                .orElseThrow(() -> new EmailNaoEncontradoExcepition("Email não encontado"));

        return usuario;
    }

    @Override
    public Usuarios buscaUsuarioPeloId(Long idUsuario) {

        Usuarios usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encontrado"));

        return usuario;
    }

}
