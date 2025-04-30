package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.FuncionarioService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.PasswordResetService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alterar")
@RequiredArgsConstructor
public class AlterarController {

    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;
    private PasswordResetService passwordResetService;

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

    @PostMapping("/solicitar-redefinicao-senha")
    public ResponseEntity<String> solicitarRedefinicao(@RequestBody SolicitarRedefinicaoRequest request) {
        passwordResetService.solicitarRedefinicaoSenha(request.getEmail());
        return ResponseEntity.ok("Um e-mail com as instruções para redefinir sua senha foi enviado para o seu endereço de e-mail.");
    }
}

@Data
class SolicitarRedefinicaoRequest {
    private String email;
}
