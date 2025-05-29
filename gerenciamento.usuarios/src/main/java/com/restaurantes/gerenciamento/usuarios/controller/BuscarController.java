package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.dto.BuscaDadosDto;
import com.restaurantes.gerenciamento.usuarios.dto.DadosBuscaCompletosDto;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.EnderecoService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/buscar")
@RequiredArgsConstructor
public class BuscarController {

    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;
    private static final Logger log = LoggerFactory.getLogger(BuscarController.class);

    @GetMapping("/buscaDados")
    public ResponseEntity<?> buscaDadosCliente(@RequestParam Long idUsuario) {

        log.info("Requisição recebida para buscar dados do cliente com ID de usuário: {}", idUsuario);
        Usuarios dadosUsuario = usuarioService.buscaUsuarioPeloId(idUsuario);
        log.debug("Usuário encontrado: {}", dadosUsuario.getEmail());

        log.warn("Endereço não encontrado no banco de dados para o ID: {} (associado ao usuário ID: {})", dadosUsuario.getEndereco(), idUsuario);
        Endereco endereco = enderecoService.buscarEndereco(dadosUsuario.getEndereco());
        log.debug("Endereço encontrado: {}", endereco.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DadosBuscaCompletosDto(dadosUsuario, endereco));
    }
}
