package com.restaurantes.gerenciamento.usuarios.service.interfaces;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.ClienteResponseDto;
import org.springframework.stereotype.Service;

public interface ClienteService {

    ClienteResponseDto criarCliente(CadastrarUsuarioDto dto);
    String buscarUsuarioExistente(Long usuarioId);
}
