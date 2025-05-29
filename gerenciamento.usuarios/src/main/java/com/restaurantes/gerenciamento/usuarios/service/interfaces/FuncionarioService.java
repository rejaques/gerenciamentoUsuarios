package com.restaurantes.gerenciamento.usuarios.service.interfaces;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.FuncionarioResponseDto;
import org.springframework.stereotype.Service;


public interface FuncionarioService {

    FuncionarioResponseDto criarFuncionario(CadastrarUsuarioDto dto);
}
