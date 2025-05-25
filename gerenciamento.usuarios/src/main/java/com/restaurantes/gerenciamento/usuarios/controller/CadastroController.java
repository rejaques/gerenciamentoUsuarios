package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cadastro")
@RequiredArgsConstructor
//Renan@1996RR99x321@
public class CadastroController {

    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;

    @PostMapping("/cliente")
    public ResponseEntity<?> cadastrarCliente(@RequestBody CadastrarUsuarioDto dto) {
        System.out.println("Chamou o endpoint de cliente!");
        clienteService.criarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
    }

    @PostMapping("/funcionario")
    public ResponseEntity<?> cadastrarFuncionario(@RequestBody CadastrarUsuarioDto dto) {
        funcionarioService.criarFuncionario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Funcionário cadastrado com sucesso!");
    }


}
