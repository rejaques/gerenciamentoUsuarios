package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.dto.*;
import com.restaurantes.gerenciamento.usuarios.exception.ClienteNaoEncontradoException;
import com.restaurantes.gerenciamento.usuarios.model.Clientes;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.ClienteRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.EnderecoService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final EnderecoService enderecoService;
    private final UsuarioService usuarioService;
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public void criarCliente(CadastrarUsuarioDto dto) {

        Endereco endereco = enderecoService.salvarEndereco(dto.getEndereco());
        System.out.println("Endereco ID: " + endereco.getId());
        Usuarios usuario = usuarioService.criarUsuario(dto, endereco);

        Clientes cliente = new Clientes();
        cliente.setUsuario(usuario);

        clienteRepository.save(cliente);
    }

    @Override
    public String buscarUsuarioExistente(Long usuarioId) {

        if(clienteRepository.findByUsuario_Id(usuarioId).isPresent())
            return "Cliente";
        else
            return "Funcionario";
    }


}
