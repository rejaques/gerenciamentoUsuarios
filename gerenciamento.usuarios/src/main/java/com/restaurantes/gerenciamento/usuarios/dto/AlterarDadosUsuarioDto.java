package com.restaurantes.gerenciamento.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlterarDadosUsuarioDto {

    private String nome;
    private String login;
    private String senha;
    private EnderecoDto endereco;
}
