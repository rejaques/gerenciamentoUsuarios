package com.restaurantes.gerenciamento.usuarios.exception;

public class LoginNaoDisponivelException extends RuntimeException {
    public LoginNaoDisponivelException(String message) {
        super(message);
    }
}
