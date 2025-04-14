package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cadastro")
@RequiredArgsConstructor
public class CadastroController {

    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;

    @PostMapping("/cliente")
    public ResponseEntity<String> cadastrarCliente(@RequestBody CadastrarUsuarioDto dto) {
        clienteService.criarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
    }

    @PostMapping("/funcionario")
    public ResponseEntity<String> cadastrarFuncionario(@RequestBody CadastrarUsuarioDto dto) {
        funcionarioService.criarFuncionario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Funcionário cadastrado com sucesso!");
    }
}
