package com.restaurantes.gerenciamento.usuarios.service;

import com.restaurantes.gerenciamento.usuarios.model.PasswordResetToken;
import com.restaurantes.gerenciamento.usuarios.model.Usuarios;
import com.restaurantes.gerenciamento.usuarios.repository.interfaces.PasswordResetTokenRepository;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.PasswordResetService;
import com.restaurantes.gerenciamento.usuarios.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private PasswordResetTokenRepository passwordResetTokenRepository;
    private JavaMailSender mailSender;
    private UsuarioService usuarioService;

    @Value("${app.base-url}") // Configure a URL base da sua aplicação no application.properties
    private String baseUrl;

    @Value("${password-reset-token-expiry-minutes}") // Configure o tempo de expiração do token
    private int tokenExpiryMinutes;

    @Override
    public void solicitarRedefinicaoSenha(String email) {
        Usuarios usuario = usuarioService.buscarUsuarioPorEmail(email); // Implemente este método no seu UsuarioService
        if (usuario != null) {
            String token = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(tokenExpiryMinutes);

            PasswordResetToken resetToken = new PasswordResetToken(token, usuario, expiryDate);
            passwordResetTokenRepository.save(resetToken);

            enviarEmailRedefinicaoSenha(usuario.getEmail(), token);
        }
        // Se o usuário não for encontrado, você pode optar por não fazer nada ou informar que o e-mail não foi encontrado
        // para evitar expor a existência de e-mails no seu sistema.
    }

    @Override
    public void enviarEmailRedefinicaoSenha(String paraEmail, String token) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(paraEmail);
        mensagem.setSubject("Solicitação de Redefinição de Senha");
        String linkRedefinicao = baseUrl + "/redefinir-senha?token=" + token; // Crie um endpoint no seu frontend para este link
        mensagem.setText("Olá,\n\nVocê solicitou a redefinição da sua senha. Para continuar, clique no link abaixo:\n\n"
                + linkRedefinicao + "\n\nEste link é válido por " + tokenExpiryMinutes + " minutos.\n\nSe você não solicitou esta redefinição, ignore este e-mail.\n\nAtenciosamente,\nSua Aplicação");

        mailSender.send(mensagem);
    }

    @Override
    public PasswordResetToken buscarToken(String token) {

        return passwordResetTokenRepository.findByToken(token).orElse(null);
    }

    @Override
    public void invalidarToken(PasswordResetToken token) {
        passwordResetTokenRepository.delete(token);
    }
}
