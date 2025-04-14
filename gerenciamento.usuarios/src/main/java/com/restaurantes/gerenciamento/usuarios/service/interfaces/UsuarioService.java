package com.restaurantes.gerenciamento.usuarios.service.interfaces;

import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {

    Usuarios criarUsuario(CadastrarUsuarioDto dto, Endereco endereco);
}
