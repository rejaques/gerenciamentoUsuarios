package com.restaurantes.gerenciamento.usuarios.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuscaDadosDto {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private Long endereco;
}
