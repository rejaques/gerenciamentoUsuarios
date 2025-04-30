package com.restaurantes.gerenciamento.usuarios.controller;

import com.restaurantes.gerenciamento.usuarios.model.PasswordResetToken;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.PasswordResetService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reset-pass")
@RequiredArgsConstructor
public class RedefinirSenhaController {

    private PasswordResetService passwordResetService;
    private UsuarioService usuarioService;

    @GetMapping("/redefinir-senha") // Endpoint para o link no e-mail (pode renderizar uma página no frontend)
    public ResponseEntity<String> exibirFormularioRedefinicao(@RequestParam("token") String token) {
        PasswordResetToken resetToken = passwordResetService.buscarToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O link de redefinição de senha é inválido ou expirou.");
        }
        // Aqui você pode redirecionar o usuário para uma página no frontend onde ele poderá inserir a nova senha,
        // enviando o token junto com a nova senha.
        return ResponseEntity.ok("Token válido. Redirecione o usuário para o formulário de nova senha.");
    }

    @PostMapping("/redefinir-senha") // Endpoint para receber a nova senha e o token do frontend
    public ResponseEntity<String> redefinirSenha(@RequestBody RedefinirSenhaRequest request) {
        String token = request.getToken();
        String novaSenha = request.getNovaSenha();

        PasswordResetToken resetToken = passwordResetService.buscarToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O token de redefinição de senha é inválido ou expirou.");
        }

        Usuarios usuario = resetToken.getUsuario();
        usuarioService.atualizarSenha(usuario, novaSenha); // Implemente este método no seu UsuarioService

        passwordResetService.invalidarToken(resetToken); // Opcional: invalidar o token após o uso

        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}

@Data
class RedefinirSenhaRequest {
    private String token;
    private String novaSenha;
}
