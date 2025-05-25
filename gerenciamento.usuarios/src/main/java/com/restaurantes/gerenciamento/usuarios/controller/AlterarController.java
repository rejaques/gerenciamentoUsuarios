package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.EnderecoService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.FuncionarioService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alterar")
@RequiredArgsConstructor
public class AlterarController {

    private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;

    @PutMapping("/dadosCompletos")
    public ResponseEntity<?> cadastrarCliente(@RequestBody AlterarDadosUsuarioDto dto) {

        System.out.println("Chamou o endpoint de cliente!");
        Endereco endereco = enderecoService.alterarEndereco(dto.getEndereco());
        usuarioService.alterarUsuario(dto, endereco);

        return ResponseEntity.status(HttpStatus.CREATED).body("Dados alterados com sucesso!");
    }


}
