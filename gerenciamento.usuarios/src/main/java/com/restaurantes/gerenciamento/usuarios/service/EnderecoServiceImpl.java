package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.controller.CadastroController;
import com.restaurantes.gerenciamento.usuarios.dto.AlterarEnderecoDto;
import com.restaurantes.gerenciamento.usuarios.dto.EnderecoDto;
import com.restaurantes.gerenciamento.usuarios.exception.EnderecoNaoEncontrado;
import com.restaurantes.gerenciamento.usuarios.exception.EnderecoNulo;
import com.restaurantes.gerenciamento.usuarios.exception.IdNulo;
import com.restaurantes.gerenciamento.usuarios.exception.UsuarioNaoEncontradoException;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.EnderecoRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.EnderecoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private static final Logger log = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    @Override
    @Transactional
    public Endereco salvarEndereco(EnderecoDto dto) {

        log.info("Requisição para salvar endereço: {}", dto);
        if (dto == null) {
            log.warn("DTO de endereço nulo.");
            throw new EnderecoNulo("DTO de endereço não pode ser nulo.");
        }

        Endereco endereco = new Endereco(null, dto.getRua(), dto.getNumero(), dto.getComplemento(),
                dto.getBairro(), dto.getCidade(), dto.getEstado(), dto.getCep());
        return enderecoRepository.save(endereco);
    }

    @Override
    public Endereco alterarEndereco(AlterarEnderecoDto dto) {

        log.info("Requisição para alterar endereço com ID: {}", dto.getEnderecoId());

        if (dto.getEnderecoId() == null) {
            log.warn("ID do endereço não fornecido para alteração.");
            throw new IdNulo("ID do endereço é obrigatório para alteração.");
        }

        Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                .orElseThrow(() -> new EnderecoNaoEncontrado("Endereco nao encontrado no banco de dados"));

        log.debug("Endereço encontrado: {}. Aplicando alterações...", endereco.getId());

        if (dto.getRua() != null) endereco.setRua(dto.getRua());
        if (dto.getNumero() != null) endereco.setNumero(dto.getNumero());
        if (dto.getComplemento() != null) endereco.setComplemento(dto.getComplemento());
        if (dto.getBairro() != null) endereco.setBairro(dto.getBairro());
        if (dto.getCidade() != null) endereco.setCidade(dto.getCidade());
        if (dto.getEstado() != null) endereco.setEstado(dto.getEstado());
        if (dto.getCep() != null) endereco.setCep(dto.getCep());

        return enderecoRepository.save(endereco);
    }

    public Endereco buscarEndereco (Long enderecoId) {

        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new EnderecoNaoEncontrado("Endereço não encontrado!"));

        return endereco;
    }
}
