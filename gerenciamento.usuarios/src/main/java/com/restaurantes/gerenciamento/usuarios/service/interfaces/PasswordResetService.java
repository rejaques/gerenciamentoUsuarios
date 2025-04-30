package com.restaurantes.gerenciamento.usuarios.service.interfaces;

import com.restaurantes.gerenciamento.usuarios.model.PasswordResetToken;
import org.springframework.stereotype.Service;

@Service
public interface PasswordResetService {

    void solicitarRedefinicaoSenha(String email);
    void enviarEmailRedefinicaoSenha(String paraEmail, String token);
    PasswordResetToken buscarToken(String token);
    void invalidarToken(PasswordResetToken token);
}
