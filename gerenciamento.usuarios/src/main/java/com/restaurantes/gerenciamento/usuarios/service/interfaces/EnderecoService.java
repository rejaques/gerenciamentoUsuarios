package com.restaurantes.gerenciamento.usuarios.service.interfaces;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarEnderecoDto;
import com.restaurantes.gerenciamento.usuarios.dto.EnderecoDto;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface EnderecoService {

    Endereco salvarEndereco(EnderecoDto dto);
    Endereco alterarEndereco(AlterarEnderecoDto dto);
    Endereco buscarEndereco (Long enderecoId);
}
