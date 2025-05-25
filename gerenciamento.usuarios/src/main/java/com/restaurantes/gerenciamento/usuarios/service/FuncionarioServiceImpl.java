package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.exception.ClienteNaoEncontradoException;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FuncionarioServiceImpl implements FuncionarioService {

    private final EnderecoService enderecoService;
    private final UsuarioService usuarioService;
    private final FuncionarioRepository funcionarioRepository;

    @Override
    @Transactional
    public void criarFuncionario(CadastrarUsuarioDto dto) {
        Endereco endereco = enderecoService.salvarEndereco(dto.getEndereco());
        Usuarios usuario = usuarioService.criarUsuario(dto, endereco);

        Funcionarios funcionario = new Funcionarios();
        funcionario.setUsuario(usuario);

        funcionarioRepository.save(funcionario);
    }
}
