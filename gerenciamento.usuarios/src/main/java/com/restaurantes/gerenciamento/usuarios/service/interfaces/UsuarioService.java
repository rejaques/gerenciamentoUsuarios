package com.restaurantes.gerenciamento.usuarios.service.interfaces;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.AlterarEnderecoDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {

    Usuarios criarUsuario(CadastrarUsuarioDto dto, Endereco endereco);

    Usuarios alterarUsuario(AlterarDadosUsuarioDto dto, Endereco endereco);

    Usuarios buscarUsuarioPorEmail(String email);

    void atualizarSenha(Usuarios usuario, String novaSenha);
}
