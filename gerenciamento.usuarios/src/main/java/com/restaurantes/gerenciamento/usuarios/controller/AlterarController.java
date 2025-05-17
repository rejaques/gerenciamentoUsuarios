package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alterar")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AlterarController {

    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;

    @PostMapping("/cliente")
    public ResponseEntity<?> cadastrarCliente(@RequestBody AlterarDadosUsuarioDto dto) {

        System.out.println("Chamou o endpoint de cliente!");
        clienteService.alterarCliente(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente alterado com sucesso!");
    }

    @PostMapping("/funcionario")
    public ResponseEntity<?> cadastrarFuncionario(@RequestBody AlterarDadosUsuarioDto dto) {
        funcionarioService.alterarFuncionario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Funcionário alterado com sucesso!");
    }
}
