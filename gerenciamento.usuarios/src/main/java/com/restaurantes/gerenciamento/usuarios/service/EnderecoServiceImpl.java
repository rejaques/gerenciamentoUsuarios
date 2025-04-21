package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarEnderecoDto;
import com.restaurantes.gerenciamento.usuarios.dto.EnderecoDto;
import com.restaurantes.gerenciamento.usuarios.exception.EnderecoNaoEncontrado;
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

    @Override
    public Endereco alterarEndereco(AlterarEnderecoDto dto) {

        Endereco endereco = enderecoRepository.findById(dto.getId())
                .orElseThrow(() -> new EnderecoNaoEncontrado("Endereco nao encontrado no banco de dados"));

        if (dto.getRua() != null) endereco.setRua(dto.getRua());
        if (dto.getNumero() != null) endereco.setNumero(dto.getNumero());
        if (dto.getComplemento() != null) endereco.setComplemento(dto.getComplemento());
        if (dto.getBairro() != null) endereco.setBairro(dto.getBairro());
        if (dto.getCidade() != null) endereco.setCidade(dto.getCidade());
        if (dto.getEstado() != null) endereco.setEstado(dto.getEstado());
        if (dto.getCep() != null) endereco.setCep(dto.getCep());

        return enderecoRepository.save(endereco);
    }
}
