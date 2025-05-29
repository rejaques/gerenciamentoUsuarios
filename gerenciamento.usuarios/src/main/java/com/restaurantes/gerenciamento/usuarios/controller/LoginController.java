package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.dto.LoginRequest;
import com.restaurantes.gerenciamento.usuarios.dto.RetornoLoginDto;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.ClienteService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioService usuarioService;
    private final ClienteService clienteService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getSenha()
                    )
            );

            Usuarios usuario = (Usuarios) authentication.getPrincipal();
            Long usuariosId = usuario.getId();
            String tipoUsuario = clienteService.buscarUsuarioExistente(usuariosId);

            return ResponseEntity.ok(new RetornoLoginDto(tipoUsuario, usuariosId));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }
}
