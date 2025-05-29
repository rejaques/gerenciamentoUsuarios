package com.restaurantes.gerenciamento.usuarios.exception;

public class FalhaAoSalvarException extends RuntimeException {
    public FalhaAoSalvarException(String message, Throwable cause) {
        super(message, cause);
    }
}
