package com.restaurantes.gerenciamento.usuarios.handler;

import com.restaurantes.gerenciamento.usuarios.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CepNuloException.class)
    public ResponseEntity<String> handleClienteNaoEncontradoException(CepNuloException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<String> handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EmailNaoEncontradoExcepition.class)
    public ResponseEntity<String> handleClienteNaoEncontradoException(EmailNaoEncontradoExcepition ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EmailNuloException.class)
    public ResponseEntity<String> handleClienteNaoEncontradoException(EmailNuloException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EnderecoNaoEncontrado.class)
    public ResponseEntity<String> handleClienteNaoEncontradoException(EnderecoNaoEncontrado ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EnderecoNulo.class)
    public ResponseEntity<String> handleClienteNaoEncontradoException(EnderecoNulo ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FalhaAoSalvarException.class)
    public ResponseEntity<String> handleClienteNaoEncontradoException(FalhaAoSalvarException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FuncionarioNaoEncontradoException.class)
    public ResponseEntity<String> handleFuncionarioNaoEncontradoException(FuncionarioNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IdNulo.class)
    public ResponseEntity<String> handleFuncionarioNaoEncontradoException(IdNulo ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(LoginNaoDisponivelException.class)
    public ResponseEntity<String> handleLoginNaoDisponivelException(LoginNaoDisponivelException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<String> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        ex.printStackTrace(); // Log para debug
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocorreu um erro inesperado. Tente novamente mais tarde.");
    }
}
