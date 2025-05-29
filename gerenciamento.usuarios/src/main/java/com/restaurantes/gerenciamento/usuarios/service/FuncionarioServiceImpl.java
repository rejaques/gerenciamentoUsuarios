package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.controller.CadastroController;
import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.FuncionarioResponseDto;
import com.restaurantes.gerenciamento.usuarios.exception.CepNuloException;
import com.restaurantes.gerenciamento.usuarios.exception.ClienteNaoEncontradoException;
import com.restaurantes.gerenciamento.usuarios.exception.EmailNuloException;
import com.restaurantes.gerenciamento.usuarios.exception.FalhaAoSalvarException;
import com.restaurantes.gerenciamento.usuarios.model.Clientes;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Funcionarios;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.FuncionarioRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.EnderecoService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.FuncionarioService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FuncionarioServiceImpl implements FuncionarioService {

    private final EnderecoService enderecoService;
    private final UsuarioService usuarioService;
    private final FuncionarioRepository funcionarioRepository;
    private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    @Override
    @Transactional
    public FuncionarioResponseDto criarFuncionario(CadastrarUsuarioDto dto) {

        log.info("Criando novo funcionário: {}", dto.getNome());

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
            log.error("Erro ao salvar endereço para o funcionário com email {}: {}", dto.getEmail(), e.getMessage(), e);
            throw new FalhaAoSalvarException("Falha ao salvar endereço do funcionário.", e);
        }

        Usuarios usuario;
        try {
            log.debug("Delegando criação de usuário.");
            usuario = usuarioService.criarUsuario(dto, endereco);
            log.info("Usuário associado ao funcionário criado com ID: {}", usuario.getId());
        } catch (Exception e) {
            log.error("Erro ao criar usuário para o funcionário com email {}: {}", dto.getEmail(), e.getMessage(), e);
            throw new FalhaAoSalvarException("Falha ao criar usuário base para o funcionário.", e);
        }

        Funcionarios funcionario = new Funcionarios();
        funcionario.setUsuario(usuario);

        try {
            log.debug("Salvando entidade Funcionarios...");
            Funcionarios funcionarioSalvo = funcionarioRepository.save(funcionario);
            log.info("Funcionário {} salvo com sucesso com ID: {}", dto.getEmail(), funcionarioSalvo.getId());
        } catch (Exception e) {
            log.error("Erro ao salvar entidade Funcionarios para o email {}: {}", dto.getEmail(), e.getMessage(), e);
            throw new FalhaAoSalvarException("Falha ao persistir dados do funcionário.", e);
        }

        // Criar e retornar o DTO de resposta
        return new FuncionarioResponseDto(
                usuario.getId(),
                funcionario.getId(),
                usuario.getEmail(),
                endereco.getId()
        );
    }
}
