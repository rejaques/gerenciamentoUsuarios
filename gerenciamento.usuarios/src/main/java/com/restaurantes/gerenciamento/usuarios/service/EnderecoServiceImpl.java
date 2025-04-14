package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.dto.EnderecoDto;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.EnderecoRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Override
    public Endereco salvarEndereco(EnderecoDto dto) {
        Endereco endereco = new Endereco(null, dto.getRua(), dto.getNumero(), dto.getComplemento(),
                dto.getBairro(), dto.getCidade(), dto.getEstado(), dto.getCep());
        return enderecoRepository.save(endereco);
    }
}
