package com.restaurantes.gerenciamento.usuarios.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String senha;
}
