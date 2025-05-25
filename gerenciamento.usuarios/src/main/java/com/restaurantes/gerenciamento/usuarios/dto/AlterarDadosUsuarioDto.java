package com.restaurantes.gerenciamento.usuarios.dto;

import com.restaurantes.gerenciamento.usuarios.validations.SenhaForte;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlterarDadosUsuarioDto {

    private Long idUsuario;

    private String nome;

    private String login;

    @SenhaForte
    private String senha;

    private String email;

    private AlterarEnderecoDto endereco;
}
