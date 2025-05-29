package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.ClienteResponseDto;
import com.restaurantes.gerenciamento.usuarios.dto.FuncionarioResponseDto;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cadastro")
@RequiredArgsConstructor
public class CadastroController {

    private static final Logger log = LoggerFactory.getLogger(CadastroController.class);
    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;

    @PostMapping("/cliente")
    public ResponseEntity<?> cadastrarCliente(@Valid @RequestBody CadastrarUsuarioDto dto) {
        log.info("Recebida requisição para cadastrar novo cliente: {}", dto.getNome());
        ClienteResponseDto clienteResponseDto = clienteService.criarCliente(dto);

        log.info("Cliente cadastrado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDto);
    }

    @PostMapping("/funcionario")
    public ResponseEntity<?> cadastrarFuncionario(@Valid @RequestBody CadastrarUsuarioDto dto) {
        log.info("Recebida requisição para cadastrar novo funcionário: {}", dto.getNome());
        FuncionarioResponseDto funcionarioResponseDto =  funcionarioService.criarFuncionario(dto);

        log.info("Funcionário cadastrado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioResponseDto);
    }
}
