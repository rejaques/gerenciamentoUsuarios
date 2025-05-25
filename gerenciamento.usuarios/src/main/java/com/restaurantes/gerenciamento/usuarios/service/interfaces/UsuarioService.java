package com.restaurantes.gerenciamento.usuarios.service.interfaces;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UsuarioService {

    Usuarios criarUsuario(CadastrarUsuarioDto dto, Endereco endereco);
    void alterarUsuario(AlterarDadosUsuarioDto dto, Endereco endereco);

    Optional<Usuarios> buscaUsuarioPeloEmail(String email);

    Usuarios buscaUsuarioPeloId(Long idUsuario);

}
