package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.dto.BuscaDadosDto;
import com.restaurantes.gerenciamento.usuarios.dto.DadosBuscaCompletosDto;
import com.restaurantes.gerenciamento.usuarios.model.Endereco;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.EnderecoService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/buscaDados")
    public ResponseEntity<?> buscaDadosCliente(@RequestParam Long idUsuario) {

        Usuarios dadosUsuario = usuarioService.buscaUsuarioPeloId(idUsuario);
        Optional<Endereco> endereco = enderecoService.buscarEndereco(dadosUsuario.getEndereco());

        if(endereco.isEmpty())
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endereço nõ encontrado");


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DadosBuscaCompletosDto(dadosUsuario, endereco));
    }
}
