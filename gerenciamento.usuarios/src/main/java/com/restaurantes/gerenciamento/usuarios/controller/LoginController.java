package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.dto.LoginRequest;
import com.restaurantes.gerenciamento.usuarios.dto.RetornoLoginDto;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioService usuarioService;
    private final ClienteService clienteService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        Optional<Usuarios> usuario = usuarioService.buscaUsuarioPeloEmail(loginRequest.getEmail());

        Long usuariosId;

        if(usuario.isPresent())
            usuariosId = usuario.get().getId();
        else
            return ResponseEntity.badRequest().body("Usuario não encontrado");

        String tipoUsuario = clienteService.buscarUsuarioExistente(usuariosId);

        return  ResponseEntity.ok(new RetornoLoginDto(tipoUsuario, usuariosId));
    }
}
