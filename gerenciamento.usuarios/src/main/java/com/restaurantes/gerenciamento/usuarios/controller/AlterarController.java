package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.dto.AlterarDadosUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.CadastrarUsuarioDto;
import com.restaurantes.gerenciamento.usuarios.dto.DadosAlteradosDto;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.EnderecoService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.FuncionarioService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    public ResponseEntity<?> AlterarDadosUsuario(@Valid @RequestBody AlterarDadosUsuarioDto dto) {

        System.out.println("Chamou o endpoint de cliente!");
        Endereco endereco = enderecoService.alterarEndereco(dto.getEndereco());
        Usuarios usuarios = usuarioService.alterarUsuario(dto, endereco);

        if(usuarios == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível alterar os dados do usuário");

        if(endereco == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível alterar os dados do endereço");

        DadosAlteradosDto dadosAlteradosDto = new DadosAlteradosDto(usuarios, endereco);

        return ResponseEntity.ok(dadosAlteradosDto);
    }
}
