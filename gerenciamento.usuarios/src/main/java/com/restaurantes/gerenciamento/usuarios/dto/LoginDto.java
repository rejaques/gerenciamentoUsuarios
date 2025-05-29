package com.restaurantes.gerenciamento.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "O campo login é obrigatório")
    private String login;
    @NotBlank(message = "O campo senha é obrigatório")
    private String senha;
}
