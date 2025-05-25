package com.restaurantes.gerenciamento.usuarios.service.interfaces;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import org.springframework.stereotype.Service;

@Service
public interface FuncionarioService {

    void criarFuncionario(CadastrarUsuarioDto dto);
}
