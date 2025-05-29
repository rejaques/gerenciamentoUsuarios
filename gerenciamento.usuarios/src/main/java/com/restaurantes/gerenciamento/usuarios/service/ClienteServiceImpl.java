package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.dto.*;
import com.restaurantes.gerenciamento.usuarios.exception.*;
import com.restaurantes.gerenciamento.usuarios.model.Clientes;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Funcionarios;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.ClienteRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.EnderecoService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final EnderecoService enderecoService;
    private final UsuarioService usuarioService;
    private final ClienteRepository clienteRepository;
    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Override
    @Transactional
    public ClienteResponseDto criarCliente(CadastrarUsuarioDto dto) {
        log.info("Criando novo cliente: {}", dto.getNome());

        if (dto.getEndereco().getCep() == null) {
            throw new CepNuloException("CEP não pode ser nulo");
        }
        if (dto.getEmail().isBlank()) {
            throw new EmailNuloException("Email não pode ser nulo");
        }

        Endereco endereco;
        try {
            log.debug("Delegando salvamento de endereço.");
            endereco = enderecoService.salvarEndereco(dto.getEndereco());
            log.info("Endereço salvo com ID: {}", endereco.getId());
        } catch (Exception e) {
            log.error("Erro ao salvar endereço para o cliente com email {}: {}", dto.getEmail(), e.getMessage(), e);
            throw new FalhaAoSalvarException("Falha ao salvar endereço do cliente.", e);
        }

        Usuarios usuario;
        try {
            log.debug("Delegando criação de usuário.");
            usuario = usuarioService.criarUsuario(dto, endereco);
            log.info("Usuário associado ao cliente criado com ID: {}", usuario.getId());
        } catch (Exception e) {
            log.error("Erro ao criar usuário para o cliente com email {}: {}", dto.getEmail(), e.getMessage(), e);
            throw new FalhaAoSalvarException("Falha ao criar usuário base para o cliente.", e);
        }

        Clientes cliente = new Clientes();
        cliente.setUsuario(usuario);
        try {
            log.debug("Salvando entidade Cleinets...");
            cliente = clienteRepository.save(cliente);
            log.info("Cliente {} salvo com sucesso com ID: {}", dto.getEmail(), cliente.getId());
        } catch (Exception e) {
            log.error("Erro ao salvar entidade Clientes para o email {}: {}", dto.getEmail(), e.getMessage(), e);
            throw new FalhaAoSalvarException("Falha ao persistir dados do cliente.", e);
        }
        // Criar e retornar o DTO de resposta
        return new ClienteResponseDto(
                usuario.getId(),
                cliente.getId(),
                usuario.getEmail(),
                endereco.getId()
        );


    }

    @Override
    public String buscarUsuarioExistente(Long usuarioId) {

        if(usuarioId == null)
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");

        if(clienteRepository.findByUsuario_Id(usuarioId).isPresent())
            return "Cliente";
        else
            return "Funcionario";
    }


}
